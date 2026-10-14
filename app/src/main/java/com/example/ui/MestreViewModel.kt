package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.AppNotificationEntity
import com.example.data.CommunityPostEntity
import com.example.data.EssaySubmissionEntity
import com.example.data.EssayThemeEntity
import com.example.data.ExerciseAttemptEntity
import com.example.data.ExerciseItem
import com.example.data.MestreRepository
import com.example.data.PastEnemOfficialTheme
import com.example.data.ProgressHistoryEntity
import com.example.data.RepertoireAndGuidesCatalog
import com.example.data.StudyTaskEntity
import com.example.data.UserProfileEntity
import com.example.data.VideoLessonEntity
import com.example.network.GeminiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainDestination {
    HOME,
    LEARN,
    PRACTICE,
    PERFORMANCE,
    HUB
}

class MestreViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MestreRepository

    init {
        val dao = AppDatabase.getDatabase(application).appDao()
        repository = MestreRepository(dao)
        viewModelScope.launch {
            repository.ensureSeedData()
        }
    }

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfileEntity())

    val themes: StateFlow<List<EssayThemeEntity>> = repository.allThemes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val essays: StateFlow<List<EssaySubmissionEntity>> = repository.allEssays
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val highestScoreEssay: StateFlow<EssaySubmissionEntity?> = repository.highestScoreEssay
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val progressHistory: StateFlow<List<ProgressHistoryEntity>> = repository.progressHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val studyTasks: StateFlow<List<StudyTaskEntity>> = repository.studyTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val exerciseAttempts: StateFlow<List<ExerciseAttemptEntity>> = repository.exerciseAttempts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val communityPosts: StateFlow<List<CommunityPostEntity>> = repository.communityPosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<AppNotificationEntity>> = repository.notifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val videoLessons: StateFlow<List<VideoLessonEntity>> = repository.videoLessons
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentDestination = MutableStateFlow(MainDestination.HOME)
    val currentDestination: StateFlow<MainDestination> = _currentDestination.asStateFlow()

    private val _practiceSubTab = MutableStateFlow(0) // 0=Temas, 1=Escrever & IA, 2=Reescrita, 3=Simulados, 4=Scanner ENEM
    val practiceSubTab: StateFlow<Int> = _practiceSubTab.asStateFlow()

    private val _learnSubTab = MutableStateFlow(0) // 0..4=Guias/Exercícios, 5=Vídeo Aulas (Gravadas & Ao Vivo)
    val learnSubTab: StateFlow<Int> = _learnSubTab.asStateFlow()

    private val _hubSubTab = MutableStateFlow(0) // 0=Comunidade, 1=Professor, 2=Admin, 3=Premium
    val hubSubTab: StateFlow<Int> = _hubSubTab.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _selectedThemeForWriting = MutableStateFlow<EssayThemeEntity?>(null)
    val selectedThemeForWriting: StateFlow<EssayThemeEntity?> = _selectedThemeForWriting.asStateFlow()

    private val _essayBeingRewritten = MutableStateFlow<EssaySubmissionEntity?>(null)
    val essayBeingRewritten: StateFlow<EssaySubmissionEntity?> = _essayBeingRewritten.asStateFlow()

    private val _latestCorrectedEssay = MutableStateFlow<EssaySubmissionEntity?>(null)
    val latestCorrectedEssay: StateFlow<EssaySubmissionEntity?> = _latestCorrectedEssay.asStateFlow()

    private val _latestScannedEssay = MutableStateFlow<EssaySubmissionEntity?>(null)
    val latestScannedEssay: StateFlow<EssaySubmissionEntity?> = _latestScannedEssay.asStateFlow()

    private val _latestGeneratedTheme = MutableStateFlow<EssayThemeEntity?>(null)
    val latestGeneratedTheme: StateFlow<EssayThemeEntity?> = _latestGeneratedTheme.asStateFlow()

    private val _isAiBusy = MutableStateFlow(false)
    val isAiBusy: StateFlow<Boolean> = _isAiBusy.asStateFlow()

    private val _statusBannerMessage = MutableStateFlow<String?>(null)
    val statusBannerMessage: StateFlow<String?> = _statusBannerMessage.asStateFlow()

    fun navigateTo(destination: MainDestination) {
        _currentDestination.value = destination
    }

    fun selectPracticeSubTab(index: Int) {
        _practiceSubTab.value = index
    }

    fun selectLearnSubTab(index: Int) {
        _learnSubTab.value = index
    }

    fun selectHubSubTab(index: Int) {
        _hubSubTab.value = index
    }

    fun toggleDarkTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun clearBannerMessage() {
        _statusBannerMessage.value = null
    }

    fun startWritingWithTheme(theme: EssayThemeEntity, isSimulado: Boolean = false) {
        _selectedThemeForWriting.value = theme
        _essayBeingRewritten.value = null
        _currentDestination.value = MainDestination.PRACTICE
        _practiceSubTab.value = if (isSimulado) 3 else 1
    }

    fun startRewriteForEssay(essay: EssaySubmissionEntity) {
        _essayBeingRewritten.value = essay
        val matchingTheme = themes.value.find { it.id == essay.themeId }
        if (matchingTheme != null) {
            _selectedThemeForWriting.value = matchingTheme
        }
        _currentDestination.value = MainDestination.PRACTICE
        _practiceSubTab.value = 2
    }

    fun inspectEssayCorrection(essay: EssaySubmissionEntity) {
        _latestCorrectedEssay.value = essay
        _currentDestination.value = MainDestination.PRACTICE
        _practiceSubTab.value = 1
    }

    fun submitEssayForAiCorrection(
        theme: EssayThemeEntity?,
        essayTitle: String,
        essayContent: String,
        timeSpentMinutes: Int,
        isSimulatedExam: Boolean,
        parentEssay: EssaySubmissionEntity? = null
    ) {
        if (essayContent.isBlank()) {
            _statusBannerMessage.value = "Escreva o texto da sua redação antes de enviar para correção com IA."
            return
        }
        viewModelScope.launch {
            _isAiBusy.value = true
            val resolvedThemeTitle = theme?.title ?: parentEssay?.themeTitle ?: "Tema Livre Dissertativo-Argumentativo"
            val resolvedCategory = theme?.category ?: parentEssay?.category ?: "Educação"
            val resolvedExamType = theme?.examType ?: parentEssay?.examType ?: "ENEM"
            val resolvedThemeId = theme?.id ?: parentEssay?.themeId ?: 1
            val version = if (parentEssay != null) parentEssay.versionNumber + 1 else 1

            val evaluated = GeminiClient.evaluateEssayWithAi(
                themeTitle = resolvedThemeTitle,
                category = resolvedCategory,
                examType = resolvedExamType,
                essayTitle = essayTitle,
                essayContent = essayContent,
                timeSpentMinutes = timeSpentMinutes,
                isSimulatedExam = isSimulatedExam,
                themeId = resolvedThemeId,
                versionNumber = version,
                parentEssayId = parentEssay?.id
            )
            val newId = repository.saveEssaySubmission(evaluated)
            val saved = evaluated.copy(id = newId.toInt())
            _latestCorrectedEssay.value = saved
            _isAiBusy.value = false
            _statusBannerMessage.value = "Correção com IA concluída! Nota simulada: ${saved.totalScore}/1000 pontos."
        }
    }

    fun generateNewThemeWithAi(
        category: String,
        examType: String,
        customFocus: String = "",
        difficultyStyle: String = "Padrão INEP"
    ) {
        val instantPreview = GeminiClient.generateInstantThemePreview(
            category = category,
            examType = examType,
            customFocus = customFocus,
            difficultyStyle = difficultyStyle
        )
        _latestGeneratedTheme.value = instantPreview
        _selectedThemeForWriting.value = instantPreview
        val focusLabel = if (customFocus.isNotBlank()) " sobre '${customFocus.trim()}'" else ""
        _statusBannerMessage.value = "Tema inédito gerado em tempo real no eixo $category$focusLabel!"

        viewModelScope.launch {
            _isAiBusy.value = true
            val generated = GeminiClient.generateThemeWithAi(
                category = category,
                examType = examType,
                customFocus = customFocus,
                difficultyStyle = difficultyStyle
            )
            val id = repository.insertTheme(generated)
            val saved = generated.copy(id = id.toInt())
            _latestGeneratedTheme.value = saved
            _selectedThemeForWriting.value = saved
            _isAiBusy.value = false
        }
    }

    fun regenerateStudyPlanWithAi() {
        viewModelScope.launch {
            _isAiBusy.value = true
            val all = essays.value
            val avg = if (all.isNotEmpty()) all.map { it.totalScore }.average().toInt() else 840
            val c1Avg = if (all.isNotEmpty()) all.map { it.c1Score }.average().toInt() else 160
            val c2Avg = if (all.isNotEmpty()) all.map { it.c2Score }.average().toInt() else 180
            val c3Avg = if (all.isNotEmpty()) all.map { it.c3Score }.average().toInt() else 160
            val c4Avg = if (all.isNotEmpty()) all.map { it.c4Score }.average().toInt() else 160
            val c5Avg = if (all.isNotEmpty()) all.map { it.c5Score }.average().toInt() else 180

            val competencyMap = listOf(
                "Competência 1 (Norma Padrão)" to c1Avg,
                "Competência 2 (Repertório & Tema)" to c2Avg,
                "Competência 3 (Argumentação)" to c3Avg,
                "Competência 4 (Coesão & Conectivos)" to c4Avg,
                "Competência 5 (Proposta de Intervenção)" to c5Avg
            )
            val weakest = competencyMap.minByOrNull { it.second }?.first ?: "Competência 4 (Coesão & Conectivos)"
            val target = userProfile.value?.targetScore ?: 960

            val newPlan = GeminiClient.generatePersonalizedStudyPlan(
                weakestCompetencia = weakest,
                averageScore = avg,
                targetScore = target
            )
            repository.replaceStudyPlan(newPlan)
            _isAiBusy.value = false
            _statusBannerMessage.value = "Plano de Estudos personalizado com IA recriado com foco em $weakest!"
        }
    }

    fun toggleStudyTask(task: StudyTaskEntity) {
        viewModelScope.launch {
            repository.toggleStudyTask(task)
        }
    }

    fun submitObjectiveExercise(exercise: ExerciseItem, selectedOptionIndex: Int) {
        viewModelScope.launch {
            val isCorrect = selectedOptionIndex == exercise.correctIndex
            repository.recordExerciseAttempt(
                ExerciseAttemptEntity(
                    exerciseId = exercise.id,
                    category = exercise.type,
                    userResponse = exercise.options.getOrNull(selectedOptionIndex) ?: "",
                    isCorrect = isCorrect,
                    scoreEarned = if (isCorrect) exercise.xpReward else 10,
                    feedback = exercise.explanation
                )
            )
            _statusBannerMessage.value = if (isCorrect) {
                "Resposta correta! +${exercise.xpReward} XP adicionados ao seu perfil."
            } else {
                "Confira a explicação comentada para dominar esse tópico!"
            }
        }
    }

    fun submitDiscursiveExercise(exercise: ExerciseItem, studentParagraph: String) {
        if (studentParagraph.isBlank()) return
        viewModelScope.launch {
            _isAiBusy.value = true
            val (score, feedback) = GeminiClient.evaluateDiscursiveParagraph(exercise.prompt, studentParagraph)
            repository.recordExerciseAttempt(
                ExerciseAttemptEntity(
                    exerciseId = exercise.id,
                    category = exercise.type,
                    userResponse = studentParagraph,
                    isCorrect = score >= 70,
                    scoreEarned = exercise.xpReward,
                    feedback = feedback
                )
            )
            _isAiBusy.value = false
            _statusBannerMessage.value = "Exercício discursivo avaliado pela IA: $score/100 pts (+${exercise.xpReward} XP)!"
        }
    }

    fun updateAccountAndProfile(
        name: String,
        email: String,
        targetScore: Int,
        role: String,
        authProvider: String = "Email & Senha"
    ) {
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfileEntity()
            val initials = name.trim().split(Regex("\\s+"))
                .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                .take(2)
                .joinToString("")
                .ifBlank { "MR" }
            repository.updateUserProfile(
                current.copy(
                    name = name.ifBlank { current.name },
                    email = email.ifBlank { current.email },
                    avatarInitials = initials,
                    targetScore = targetScore.coerceIn(600, 1000),
                    role = role,
                    isLoggedIn = true,
                    authProvider = authProvider
                )
            )
            _statusBannerMessage.value = "Perfil atualizado com sucesso (${name.ifBlank { current.name }})!"
        }
    }

    fun requestPasswordRecovery(email: String) {
        _statusBannerMessage.value = "Link de recuperação de senha enviado para ${email.ifBlank { "seu e-mail cadastrado" }}."
    }

    fun togglePremiumSubscription() {
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfileEntity()
            val nextPremium = !current.isPremium
            val plan = if (nextPremium) "Mestre Diamante 1000 (Ilimitado)" else "Plano Gratuito Essencial"
            repository.updateUserProfile(current.copy(isPremium = nextPremium, planName = plan))
            _statusBannerMessage.value = if (nextPremium) {
                "Assinatura Mestre Diamante 1000 ativada! Correções e temas exclusivos liberados."
            } else {
                "Plano alterado para Gratuito Essencial."
            }
        }
    }

    fun createNewCommunityPost(category: String, title: String, content: String, quote: String) {
        if (title.isBlank() || content.isBlank()) return
        viewModelScope.launch {
            val user = userProfile.value ?: UserProfileEntity()
            repository.createCommunityPost(
                CommunityPostEntity(
                    authorName = user.name,
                    authorLevel = user.level,
                    authorBadge = if (user.isPremium) "Mestre 960+" else "Estudante",
                    category = category,
                    title = title,
                    content = content,
                    repertoireQuote = quote,
                    likesCount = 1,
                    isLikedByMe = true
                )
            )
            _statusBannerMessage.value = "Publicação compartilhada no Fórum da Comunidade (+40 XP)!"
        }
    }

    fun toggleLikeOnPost(post: CommunityPostEntity) {
        viewModelScope.launch {
            repository.togglePostLike(post)
        }
    }

    fun addCommentOnPost(post: CommunityPostEntity, commentText: String) {
        if (commentText.isBlank()) return
        viewModelScope.launch {
            val author = userProfile.value?.name ?: "Estudante"
            repository.addCommentToPost(post, author, commentText)
        }
    }

    fun saveTeacherManualCorrection(
        essay: EssaySubmissionEntity,
        c1: Int,
        c2: Int,
        c3: Int,
        c4: Int,
        c5: Int,
        teacherNotes: String
    ) {
        viewModelScope.launch {
            val updated = essay.copy(
                c1Score = c1.coerceIn(0, 200),
                c2Score = c2.coerceIn(0, 200),
                c3Score = c3.coerceIn(0, 200),
                c4Score = c4.coerceIn(0, 200),
                c5Score = c5.coerceIn(0, 200),
                totalScore = (c1 + c2 + c3 + c4 + c5).coerceIn(0, 1000),
                teacherFeedback = teacherNotes,
                status = "REVISADA_PROFESSOR"
            )
            repository.updateEssayByTeacher(updated)
            _statusBannerMessage.value = "Correção manual do professor salva com sucesso (${updated.totalScore} pts)!"
        }
    }

    fun createManualTheme(
        title: String,
        category: String,
        examType: String,
        motivatingText: String,
        statisticalData: String,
        repertoireSuggestions: String,
        guidingQuestions: String,
        isExclusive: Boolean
    ) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.insertTheme(
                EssayThemeEntity(
                    title = title,
                    category = category,
                    examType = examType,
                    motivatingText = motivatingText.ifBlank { "Texto motivador contextualizado para $category." },
                    proposalPrompt = "Redija texto dissertativo-argumentativo sobre '$title'.",
                    statisticalData = statisticalData.ifBlank { "Dados estatísticos do IBGE e IPEA sobre $category." },
                    repertoireSuggestions = repertoireSuggestions.ifBlank { "Constituição Federal de 1988 • Zygmunt Bauman • Milton Santos." },
                    guidingQuestions = guidingQuestions.ifBlank { "1. Quais as raízes históricas do problema?\n2. Como intervir de modo eficaz?" },
                    isExclusivePremium = isExclusive
                )
            )
            _statusBannerMessage.value = "Tema '$title' publicado com sucesso!"
        }
    }

    fun deleteThemeById(id: Int) {
        viewModelScope.launch {
            repository.deleteTheme(id)
            _statusBannerMessage.value = "Tema removido do catálogo."
        }
    }

    fun recordHistoricalEssayScore(
        title: String,
        category: String,
        examType: String,
        c1: Int,
        c2: Int,
        c3: Int,
        c4: Int,
        c5: Int,
        milestoneNote: String
    ) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.recordManualHistoricalProgress(
                title = title,
                category = category,
                examType = examType,
                c1 = c1.coerceIn(0, 200),
                c2 = c2.coerceIn(0, 200),
                c3 = c3.coerceIn(0, 200),
                c4 = c4.coerceIn(0, 200),
                c5 = c5.coerceIn(0, 200),
                milestoneNote = milestoneNote
            )
            val total = (c1 + c2 + c3 + c4 + c5).coerceIn(0, 1000)
            _statusBannerMessage.value = "Registro histórico '$title' ($total pts) salvo no banco de dados Room!"
        }
    }

    fun deleteSavedEssay(essayId: Int) {
        viewModelScope.launch {
            repository.deleteEssayAndProgress(essayId)
            _statusBannerMessage.value = "Redação e respectivo registro histórico removidos do banco de dados."
        }
    }

    fun practicePastEnemTheme(pastTheme: PastEnemOfficialTheme, isSimulado: Boolean = false) {
        viewModelScope.launch {
            val combinedMotivating = listOf(
                pastTheme.textoI,
                pastTheme.textoII,
                pastTheme.textoIII,
                pastTheme.textoIV
            ).joinToString("\n\n")

            val entity = EssayThemeEntity(
                title = "ENEM ${pastTheme.year}: ${pastTheme.officialThemeTitle}",
                category = pastTheme.axis.substringBefore(" &").trim(),
                examType = "ENEM",
                motivatingText = combinedMotivating,
                proposalPrompt = "A partir da leitura dos textos motivadores e com base nos conhecimentos construídos ao longo de sua formação, redija texto dissertativo-argumentativo em modalidade escrita formal da língua portuguesa sobre o tema '${pastTheme.officialThemeTitle}', apresentando proposta de intervenção que respeite os direitos humanos.",
                statisticalData = "Coletânea Oficial INEP/ENEM ${pastTheme.year} com Textos I, II, III e IV integrados.",
                repertoireSuggestions = pastTheme.recommendedRepertoires,
                guidingQuestions = pastTheme.suggestedArguments,
                isExclusivePremium = false
            )
            val newId = repository.insertTheme(entity)
            val saved = entity.copy(id = newId.toInt())
            _selectedThemeForWriting.value = saved
            _essayBeingRewritten.value = null
            _currentDestination.value = MainDestination.PRACTICE
            _practiceSubTab.value = if (isSimulado) 3 else 1
            _statusBannerMessage.value = "Tema oficial do ENEM ${pastTheme.year} carregado no editor com textos motivadores!"
        }
    }

    fun advanceUserXpForMetalEvolution(xpBonus: Int = 350) {
        viewModelScope.launch {
            repository.addXpAndStudyTime(xpDelta = xpBonus, minutesDelta = 25)
            val updated = repository.userProfile
            val profile = userProfile.value
            val newXp = (profile?.xp ?: 2480) + xpBonus
            val newLevel = (newXp / 350) + 1
            val tier = RepertoireAndGuidesCatalog.getMetalTierForUser(newLevel, newXp)
            _statusBannerMessage.value = "Evolução de Nível! Nível $newLevel (${newXp} XP) • Patente Atual: ${tier.metalName}!"
        }
    }

    fun markNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsRead()
        }
    }

    fun scanHandwrittenEssayFromPhoto(
        imageBase64: String?,
        theme: EssayThemeEntity?,
        customThemeTitle: String,
        category: String,
        essayTitle: String,
        transcriptHint: String
    ) {
        viewModelScope.launch {
            _isAiBusy.value = true
            val resolvedThemeTitle = customThemeTitle.ifBlank {
                theme?.title ?: "Desafios contemporâneos da cidadania no Brasil"
            }
            val resolvedCategory = category.ifBlank { theme?.category ?: "Educação" }
            val resolvedThemeId = theme?.id ?: 1

            val scanned = GeminiClient.scanAndEvaluateHandwrittenEssay(
                imageBase64 = imageBase64,
                themeTitle = resolvedThemeTitle,
                category = resolvedCategory,
                examType = "ENEM",
                essayTitle = essayTitle.ifBlank { "Folha Escaneada: $resolvedCategory" },
                fallbackOrEditedTranscript = transcriptHint,
                themeId = resolvedThemeId
            )
            val newId = repository.saveEssaySubmission(scanned)
            val saved = scanned.copy(id = newId.toInt())
            _latestScannedEssay.value = saved
            _latestCorrectedEssay.value = saved
            _isAiBusy.value = false
            _statusBannerMessage.value = "Scanner ENEM concluído! Nota da folha: ${saved.totalScore}/1000 pts com diagnóstico C1–C5."
        }
    }

    fun postNewVideoLesson(
        title: String,
        description: String,
        instructorName: String,
        category: String,
        isLive: Boolean,
        liveStatus: String,
        scheduledDateTime: String,
        durationMinutes: Int,
        videoUrl: String,
        keyTopics: String,
        supportMaterial: String
    ) {
        if (title.isBlank()) {
            _statusBannerMessage.value = "Informe o título da vídeo aula antes de publicar."
            return
        }
        viewModelScope.launch {
            val lesson = VideoLessonEntity(
                title = title.trim(),
                description = description.ifBlank { "Vídeo aula completa alinhada à matriz de referência do ENEM." },
                instructorName = instructorName.ifBlank { "Prof. João Batista" },
                category = category.ifBlank { "Aulão Ao Vivo" },
                isLive = isLive,
                liveStatus = liveStatus,
                scheduledDateTime = scheduledDateTime.ifBlank {
                    if (isLive) "AO VIVO AGORA • Interação Aberta" else "Aula Gravada • Disponível"
                },
                durationMinutes = durationMinutes.coerceIn(5, 180),
                videoUrl = videoUrl.ifBlank { "https://youtube.com/live/mestre-redacao-enem" },
                keyTopics = keyTopics.ifBlank { "00:00 Abertura e Contextualização|10:00 Estrutura e Argumentação ENEM|25:00 Dicas Práticas rumo aos 1000 pontos" },
                supportMaterial = supportMaterial.ifBlank { "Resumo da aula, conectivos recomendados e checklist das 5 competências do ENEM." },
                viewsCount = 1,
                isWatched = false,
                chatMessagesRaw = "${instructorName.ifBlank { "Prof. João Batista" }}::Bem-vindos à aula! Deixem suas dúvidas sobre o tema aqui no chat."
            )
            repository.insertVideoLesson(lesson)
            val typeLabel = if (isLive) "Aula Ao Vivo" else "Aula Gravada"
            _statusBannerMessage.value = "$typeLabel '${lesson.title}' publicada com sucesso na Sessão de Vídeo Aulas!"
        }
    }

    fun toggleVideoLessonWatched(lesson: VideoLessonEntity) {
        viewModelScope.launch {
            repository.toggleVideoLessonWatched(lesson)
            _statusBannerMessage.value = if (!lesson.isWatched) {
                "Aula '${lesson.title}' concluída! +90 XP adicionados à sua evolução."
            } else {
                "Status da aula atualizado."
            }
        }
    }

    fun cycleVideoLessonLiveStatus(lesson: VideoLessonEntity) {
        viewModelScope.launch {
            repository.cycleVideoLessonLiveStatus(lesson)
            _statusBannerMessage.value = "Status da transmissão atualizado para '${lesson.title}'!"
        }
    }

    fun sendVideoLessonChatMessage(lesson: VideoLessonEntity, message: String) {
        if (message.isBlank()) return
        viewModelScope.launch {
            val author = userProfile.value?.name ?: "Estudante"
            repository.addVideoLessonChatMessage(lesson, author, message)
        }
    }

    fun deleteVideoLesson(id: Int) {
        viewModelScope.launch {
            repository.deleteVideoLesson(id)
            _statusBannerMessage.value = "Vídeo aula removida do catálogo."
        }
    }
}
