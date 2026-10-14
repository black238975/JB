package com.example.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Eduarda Andrade",
    val email: String = "eduarda.andrade@estudante.br",
    val role: String = "ALUNO", // ALUNO, PROFESSOR, ADMIN
    val avatarInitials: String = "EA",
    val targetScore: Int = 960,
    val xp: Int = 2480,
    val level: Int = 7,
    val streakDays: Int = 12,
    val studyMinutes: Int = 460,
    val isPremium: Boolean = true,
    val planName: String = "Mestre Diamante 1000",
    val isLoggedIn: Boolean = true,
    val authProvider: String = "Email & Google"
)

@Serializable
@Entity(tableName = "essay_themes")
data class EssayThemeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val category: String, // Educação, Tecnologia, Saúde, Meio Ambiente, Política, Cultura, Redes Sociais, Economia, Segurança, Inclusão Social
    val examType: String = "ENEM", // ENEM, Vestibular, Concurso
    val motivatingText: String,
    val proposalPrompt: String,
    val statisticalData: String,
    val repertoireSuggestions: String,
    val guidingQuestions: String,
    val isExclusivePremium: Boolean = false
)

@Serializable
data class HighlightedExcerpt(
    val snippet: String,
    val type: String, // ERRO, FORTE, MELHORIA
    val comment: String
)

@Serializable
@Entity(
    tableName = "essay_submissions",
    indices = [Index(value = ["category"]), Index(value = ["timestamp"]), Index(value = ["totalScore"])]
)
data class EssaySubmissionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val themeId: Int,
    val themeTitle: String,
    val category: String,
    val examType: String = "ENEM",
    val title: String,
    val content: String,
    val wordCount: Int,
    val timeSpentMinutes: Int = 45,
    val isSimulatedExam: Boolean = false,
    val timestamp: Long = System.currentTimeMillis(),
    val totalScore: Int,
    val c1Score: Int, // Domínio da norma padrão (0-200)
    val c2Score: Int, // Compreensão do tema e repertório (0-200)
    val c3Score: Int, // Projeto de texto e argumentação (0-200)
    val c4Score: Int, // Coesão e conectivos (0-200)
    val c5Score: Int, // Proposta de intervenção (0-200)
    val errorsFound: String, // Separado por |
    val strengths: String, // Separado por |
    val weaknesses: String, // Separado por |
    val improvements: String, // Separado por |
    val highlightedExcerpts: String, // Formato: Trecho::Tipo::Comentario|...
    val evolutionPlan: String, // Separado por |
    val teacherFeedback: String = "",
    val status: String = "CORRIGIDA_IA", // CORRIGIDA_IA, REVISADA_PROFESSOR, ESCANEADA_IA
    val versionNumber: Int = 1,
    val parentEssayId: Int? = null,
    val isScannedFromPaper: Boolean = false,
    val scannedLinesEstimate: Int = 28,
    val legibilityComment: String = "",
    val competencyCommentsRaw: String = "" // C1::comentario|C2::comentario|C3::comentario|C4::comentario|C5::comentario
)

@Serializable
@Entity(
    tableName = "video_lessons",
    indices = [Index(value = ["isLive"]), Index(value = ["category"]), Index(value = ["timestamp"])]
)
data class VideoLessonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val instructorName: String = "Prof. João Batista",
    val category: String, // Passo a Passo, Competência 1..5, Aulão Ao Vivo, Análise Nota 1000, Repertórios
    val isLive: Boolean = false,
    val liveStatus: String = "GRAVADA", // AO_VIVO_AGORA, AGENDADA, GRAVADA
    val scheduledDateTime: String = "Disponível sob demanda",
    val durationMinutes: Int = 35,
    val videoUrl: String = "https://youtube.com/live/mestre-redacao-enem",
    val keyTopics: String = "", // Separado por |
    val supportMaterial: String = "",
    val viewsCount: Int = 128,
    val isWatched: Boolean = false,
    val chatMessagesRaw: String = "", // Autor::Mensagem|Autor::Mensagem
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
@Entity(
    tableName = "progress_history",
    indices = [Index(value = ["timestamp"])]
)
data class ProgressHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateLabel: String,
    val timestamp: Long = System.currentTimeMillis(),
    val essayId: Int? = null,
    val essayTitle: String,
    val category: String,
    val examType: String = "ENEM",
    val totalScore: Int,
    val c1Score: Int,
    val c2Score: Int,
    val c3Score: Int,
    val c4Score: Int,
    val c5Score: Int,
    val rollingAverageScore: Int,
    val studyMinutesCumulative: Int,
    val xpCumulative: Int,
    val milestoneNote: String = ""
)

@Serializable
@Entity(tableName = "study_tasks")
data class StudyTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String, // Fundamentos, Competência 1..5, Exercício, Reescrita, Simulado
    val competenciaFocus: String,
    val durationMinutes: Int,
    val xpReward: Int,
    val isCompleted: Boolean = false,
    val isAiRecommended: Boolean = true,
    val dayLabel: String = "Hoje"
)

@Serializable
@Entity(tableName = "exercise_attempts")
data class ExerciseAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseId: String,
    val category: String,
    val userResponse: String,
    val isCorrect: Boolean,
    val scoreEarned: Int,
    val feedback: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
@Entity(tableName = "community_posts")
data class CommunityPostEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val authorName: String,
    val authorLevel: Int,
    val authorBadge: String,
    val category: String, // Repertório, Discussão, Tese & Argumento, Dúvida
    val title: String,
    val content: String,
    val repertoireQuote: String = "",
    val likesCount: Int = 0,
    val isLikedByMe: Boolean = false,
    val commentsRaw: String = "", // Autor::Texto|Autor::Texto
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
@Entity(tableName = "app_notifications")
data class AppNotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val type: String, // ESTUDO, META, TEMA, CORRECAO, CONQUISTA
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
