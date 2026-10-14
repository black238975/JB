package com.example.data

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MestreRepository(private val dao: AppDao) {

    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()
    val allThemes: Flow<List<EssayThemeEntity>> = dao.getAllThemes()
    val allEssays: Flow<List<EssaySubmissionEntity>> = dao.getAllEssays()
    val highestScoreEssay: Flow<EssaySubmissionEntity?> = dao.getHighestScoreEssay()
    val progressHistory: Flow<List<ProgressHistoryEntity>> = dao.getAllProgressHistory()
    val studyTasks: Flow<List<StudyTaskEntity>> = dao.getStudyTasks()
    val exerciseAttempts: Flow<List<ExerciseAttemptEntity>> = dao.getExerciseAttempts()
    val communityPosts: Flow<List<CommunityPostEntity>> = dao.getCommunityPosts()
    val notifications: Flow<List<AppNotificationEntity>> = dao.getNotifications()
    val videoLessons: Flow<List<VideoLessonEntity>> = dao.getAllVideoLessons()

    suspend fun ensureSeedData() {
        val currentProfile = dao.getUserProfileOnce()
        if (currentProfile == null) {
            dao.upsertUserProfile(UserProfileEntity())
        } else if (currentProfile.name.contains("Camila")) {
            dao.upsertUserProfile(
                currentProfile.copy(
                    name = "Eduarda Andrade",
                    email = "eduarda.andrade@estudante.br",
                    avatarInitials = "EA"
                )
            )
        }
        if (dao.getThemesCount() < InitialSeedData.initialThemes.size) {
            dao.insertThemes(InitialSeedData.initialThemes)
        }
        if (dao.getEssaysCount() == 0) {
            InitialSeedData.initialEssays.forEach { dao.insertEssay(it) }
            dao.insertStudyTasks(InitialSeedData.initialStudyTasks)
            dao.insertCommunityPosts(InitialSeedData.initialCommunityPosts)
            dao.insertNotifications(InitialSeedData.initialNotifications)
        }
        if (dao.getProgressHistoryCount() == 0) {
            dao.insertProgressSnapshots(InitialSeedData.initialProgressHistory)
        }
        if (dao.getVideoLessonsCount() == 0) {
            dao.insertVideoLessons(InitialSeedData.initialVideoLessons)
        } else {
            dao.getAllVideoLessonsOnce().forEach { lesson ->
                if (lesson.instructorName.contains("Leite") || lesson.instructorName.contains("Rafael") || lesson.chatMessagesRaw.contains("Leite") || lesson.chatMessagesRaw.contains("Rafael")) {
                    dao.insertVideoLesson(
                        lesson.copy(
                            instructorName = "Prof. João Batista",
                            chatMessagesRaw = lesson.chatMessagesRaw
                                .replace("Prof. João Leite", "Prof. João Batista")
                                .replace("Prof. Rafael Mendes", "Prof. João Batista")
                        )
                    )
                }
            }
        }
        dao.getAllCommunityPostsOnce().forEach { post ->
            if (post.authorName.contains("Rafael Mendes") || post.commentsRaw.contains("Prof. Rafael Mendes") || post.commentsRaw.contains("Prof. João Leite")) {
                dao.insertCommunityPost(
                    post.copy(
                        authorName = if (post.authorName.contains("Rafael Mendes")) "Prof. João Batista" else post.authorName,
                        commentsRaw = post.commentsRaw
                            .replace("Prof. Rafael Mendes", "Prof. João Batista")
                            .replace("Prof. João Leite", "Prof. João Batista")
                    )
                )
            }
        }
    }

    suspend fun updateUserProfile(profile: UserProfileEntity) {
        dao.upsertUserProfile(profile)
    }

    suspend fun addXpAndStudyTime(xpDelta: Int, minutesDelta: Int = 0) {
        val current = dao.getUserProfileOnce() ?: UserProfileEntity()
        val newXp = current.xp + xpDelta
        val newLevel = (newXp / 350) + 1
        dao.upsertUserProfile(
            current.copy(
                xp = newXp,
                level = newLevel,
                studyMinutes = current.studyMinutes + minutesDelta
            )
        )
    }

    suspend fun insertTheme(theme: EssayThemeEntity): Long {
        val id = dao.insertTheme(theme)
        dao.insertNotification(
            AppNotificationEntity(
                title = "Novo Tema em ${theme.category}! ✨",
                message = "Proposta adicionada: '${theme.title}'.",
                type = "TEMA"
            )
        )
        return id
    }

    suspend fun deleteTheme(id: Int) {
        dao.deleteThemeById(id)
    }

    suspend fun saveEssaySubmission(essay: EssaySubmissionEntity): Long {
        val id = dao.insertEssay(essay)
        val xpBonus = if (essay.parentEssayId != null) 180 else if (essay.isSimulatedExam) 220 else 150
        addXpAndStudyTime(xpBonus, essay.timeSpentMinutes)

        val updatedUser = dao.getUserProfileOnce() ?: UserProfileEntity()
        val allStoredEssays = dao.getAllEssaysOnceChronological()
        val rollingAvg = if (allStoredEssays.isNotEmpty()) {
            allStoredEssays.map { it.totalScore }.average().toInt()
        } else {
            essay.totalScore
        }

        val dateFormat = SimpleDateFormat("dd MMM", Locale.forLanguageTag("pt-BR"))
        val label = dateFormat.format(Date(essay.timestamp))
        val note = when {
            essay.parentEssayId != null -> "Reescrita comparativa concluída (${essay.totalScore} pts)."
            essay.isSimulatedExam -> "Simulado cronometrado finalizado (${essay.totalScore} pts)."
            else -> "Redação corrigida e registrada no histórico (${essay.totalScore} pts)."
        }

        dao.insertProgressSnapshot(
            ProgressHistoryEntity(
                dateLabel = label,
                timestamp = essay.timestamp,
                essayId = id.toInt(),
                essayTitle = essay.title,
                category = essay.category,
                examType = essay.examType,
                totalScore = essay.totalScore,
                c1Score = essay.c1Score,
                c2Score = essay.c2Score,
                c3Score = essay.c3Score,
                c4Score = essay.c4Score,
                c5Score = essay.c5Score,
                rollingAverageScore = rollingAvg,
                studyMinutesCumulative = updatedUser.studyMinutes,
                xpCumulative = updatedUser.xp,
                milestoneNote = note
            )
        )

        dao.insertNotification(
            AppNotificationEntity(
                title = "Correção Concluída: ${essay.totalScore} pts! 🎯",
                message = "Sua redação '${essay.title}' foi salva no banco de dados (+${xpBonus} XP).",
                type = "CORRECAO"
            )
        )
        return id
    }

    suspend fun deleteEssayAndProgress(essayId: Int) {
        dao.deleteEssayById(essayId)
        dao.deleteProgressByEssayId(essayId)
    }

    suspend fun recordManualHistoricalProgress(
        title: String,
        category: String,
        examType: String,
        c1: Int,
        c2: Int,
        c3: Int,
        c4: Int,
        c5: Int,
        milestoneNote: String
    ): Long {
        val total = (c1 + c2 + c3 + c4 + c5).coerceIn(0, 1000)
        val essay = EssaySubmissionEntity(
            themeId = 1,
            themeTitle = "Registro Histórico: $title",
            category = category,
            examType = examType,
            title = title,
            content = "Redação registrada no histórico de evolução ($examType - $category). Observações: $milestoneNote",
            wordCount = 220,
            timeSpentMinutes = 45,
            isSimulatedExam = true,
            totalScore = total,
            c1Score = c1,
            c2Score = c2,
            c3Score = c3,
            c4Score = c4,
            c5Score = c5,
            errorsFound = "Registro manual de histórico.",
            strengths = "Pontuação histórica consolidada no banco de dados Room.",
            weaknesses = "Continue acompanhando a evolução por competência.",
            improvements = milestoneNote.ifBlank { "Manter constância nos simulados semanais." },
            highlightedExcerpts = "",
            evolutionPlan = "1. Comparar evolução com a meta de nota.|2. Praticar reescrita."
        )
        return saveEssaySubmission(essay)
    }

    suspend fun updateEssayByTeacher(essay: EssaySubmissionEntity) {
        dao.updateEssay(essay)
        val updatedUser = dao.getUserProfileOnce() ?: UserProfileEntity()
        val allStoredEssays = dao.getAllEssaysOnceChronological()
        val rollingAvg = if (allStoredEssays.isNotEmpty()) {
            allStoredEssays.map { it.totalScore }.average().toInt()
        } else {
            essay.totalScore
        }
        dao.insertProgressSnapshot(
            ProgressHistoryEntity(
                dateLabel = "Revisão Prof.",
                timestamp = System.currentTimeMillis(),
                essayId = essay.id,
                essayTitle = essay.title,
                category = essay.category,
                examType = essay.examType,
                totalScore = essay.totalScore,
                c1Score = essay.c1Score,
                c2Score = essay.c2Score,
                c3Score = essay.c3Score,
                c4Score = essay.c4Score,
                c5Score = essay.c5Score,
                rollingAverageScore = rollingAvg,
                studyMinutesCumulative = updatedUser.studyMinutes,
                xpCumulative = updatedUser.xp,
                milestoneNote = "Revisão docente concluída: ${essay.teacherFeedback.take(80)}"
            )
        )
        dao.insertNotification(
            AppNotificationEntity(
                title = "Revisão do Professor Disponível 👨‍🏫",
                message = "O professor revisou '${essay.title}' (Nota final: ${essay.totalScore} pts).",
                type = "CORRECAO"
            )
        )
    }

    suspend fun toggleStudyTask(task: StudyTaskEntity) {
        val updated = task.copy(isCompleted = !task.isCompleted)
        dao.updateStudyTask(updated)
        if (updated.isCompleted) {
            addXpAndStudyTime(updated.xpReward, updated.durationMinutes)
        }
    }

    suspend fun replaceStudyPlan(newTasks: List<StudyTaskEntity>) {
        dao.clearStudyTasks()
        dao.insertStudyTasks(newTasks)
        dao.insertNotification(
            AppNotificationEntity(
                title = "Plano de Estudos IA Atualizado 🧠",
                message = "Seu cronograma foi recalculado de acordo com seu desempenho por competência.",
                type = "ESTUDO"
            )
        )
    }

    suspend fun recordExerciseAttempt(attempt: ExerciseAttemptEntity) {
        dao.insertExerciseAttempt(attempt)
        if (attempt.isCorrect) {
            addXpAndStudyTime(attempt.scoreEarned, 5)
        }
    }

    suspend fun createCommunityPost(post: CommunityPostEntity) {
        dao.insertCommunityPost(post)
        addXpAndStudyTime(40, 0)
    }

    suspend fun togglePostLike(post: CommunityPostEntity) {
        val liked = !post.isLikedByMe
        val count = if (liked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
        dao.updateCommunityPost(post.copy(isLikedByMe = liked, likesCount = count))
    }

    suspend fun addCommentToPost(post: CommunityPostEntity, authorName: String, comment: String) {
        val entry = "$authorName::$comment"
        val updatedComments = if (post.commentsRaw.isBlank()) entry else "${post.commentsRaw}|$entry"
        dao.updateCommunityPost(post.copy(commentsRaw = updatedComments))
        addXpAndStudyTime(15, 0)
    }

    suspend fun markAllNotificationsRead() {
        dao.markAllNotificationsRead()
    }

    suspend fun insertVideoLesson(lesson: VideoLessonEntity): Long {
        val id = dao.insertVideoLesson(lesson)
        val badge = if (lesson.isLive) "AO VIVO 🔴" else "AULA GRAVADA 🎬"
        dao.insertNotification(
            AppNotificationEntity(
                title = "Nova Vídeo Aula ($badge): ${lesson.title}",
                message = "${lesson.instructorName} publicou uma aula em ${lesson.category} (${lesson.scheduledDateTime}).",
                type = "ESTUDO"
            )
        )
        return id
    }

    suspend fun toggleVideoLessonWatched(lesson: VideoLessonEntity) {
        val nextWatched = !lesson.isWatched
        val nextViews = if (nextWatched) lesson.viewsCount + 1 else lesson.viewsCount
        dao.updateVideoLesson(lesson.copy(isWatched = nextWatched, viewsCount = nextViews))
        if (nextWatched) {
            addXpAndStudyTime(xpDelta = 90, minutesDelta = lesson.durationMinutes.coerceIn(10, 90))
        }
    }

    suspend fun cycleVideoLessonLiveStatus(lesson: VideoLessonEntity) {
        val (nextLive, nextStatus, nextSchedule) = when (lesson.liveStatus) {
            "AGENDADA" -> Triple(true, "AO_VIVO_AGORA", "AO VIVO AGORA • Transmissão Ativa")
            "AO_VIVO_AGORA" -> Triple(false, "GRAVADA", "Gravação de Live • Disponível")
            else -> Triple(true, "AO_VIVO_AGORA", "AO VIVO AGORA • Transmissão Ativa")
        }
        dao.updateVideoLesson(
            lesson.copy(
                isLive = nextLive,
                liveStatus = nextStatus,
                scheduledDateTime = nextSchedule
            )
        )
    }

    suspend fun addVideoLessonChatMessage(lesson: VideoLessonEntity, authorName: String, message: String) {
        if (message.isBlank()) return
        val entry = "$authorName::${message.trim()}"
        val updatedChat = if (lesson.chatMessagesRaw.isBlank()) entry else "${lesson.chatMessagesRaw}|$entry"
        dao.updateVideoLesson(lesson.copy(chatMessagesRaw = updatedChat, viewsCount = lesson.viewsCount + 1))
        addXpAndStudyTime(xpDelta = 20, minutesDelta = 2)
    }

    suspend fun deleteVideoLesson(id: Int) {
        dao.deleteVideoLessonById(id)
    }
}
