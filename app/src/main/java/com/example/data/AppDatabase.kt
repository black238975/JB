package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun getUserProfileOnce(): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserProfile(profile: UserProfileEntity)

    // Essay Themes
    @Query("SELECT * FROM essay_themes ORDER BY id DESC")
    fun getAllThemes(): Flow<List<EssayThemeEntity>>

    @Query("SELECT COUNT(*) FROM essay_themes")
    suspend fun getThemesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(theme: EssayThemeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThemes(themes: List<EssayThemeEntity>)

    @Query("DELETE FROM essay_themes WHERE id = :id")
    suspend fun deleteThemeById(id: Int)

    // Essay Submissions & Scores
    @Query("SELECT * FROM essay_submissions ORDER BY timestamp DESC")
    fun getAllEssays(): Flow<List<EssaySubmissionEntity>>

    @Query("SELECT * FROM essay_submissions ORDER BY timestamp ASC")
    suspend fun getAllEssaysOnceChronological(): List<EssaySubmissionEntity>

    @Query("SELECT * FROM essay_submissions WHERE category = :category ORDER BY timestamp DESC")
    fun getEssaysByCategory(category: String): Flow<List<EssaySubmissionEntity>>

    @Query("SELECT * FROM essay_submissions ORDER BY totalScore DESC LIMIT 1")
    fun getHighestScoreEssay(): Flow<EssaySubmissionEntity?>

    @Query("SELECT COUNT(*) FROM essay_submissions")
    suspend fun getEssaysCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEssay(essay: EssaySubmissionEntity): Long

    @Update
    suspend fun updateEssay(essay: EssaySubmissionEntity)

    @Query("DELETE FROM essay_submissions WHERE id = :id")
    suspend fun deleteEssayById(id: Int)

    // Historical Progress Snapshots
    @Query("SELECT * FROM progress_history ORDER BY timestamp ASC")
    fun getAllProgressHistory(): Flow<List<ProgressHistoryEntity>>

    @Query("SELECT COUNT(*) FROM progress_history")
    suspend fun getProgressHistoryCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgressSnapshot(snapshot: ProgressHistoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgressSnapshots(snapshots: List<ProgressHistoryEntity>)

    @Query("DELETE FROM progress_history WHERE essayId = :essayId")
    suspend fun deleteProgressByEssayId(essayId: Int)

    // Study Tasks
    @Query("SELECT * FROM study_tasks ORDER BY isCompleted ASC, id ASC")
    fun getStudyTasks(): Flow<List<StudyTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudyTasks(tasks: List<StudyTaskEntity>)

    @Update
    suspend fun updateStudyTask(task: StudyTaskEntity)

    @Query("DELETE FROM study_tasks")
    suspend fun clearStudyTasks()

    // Exercise Attempts
    @Query("SELECT * FROM exercise_attempts ORDER BY timestamp DESC")
    fun getExerciseAttempts(): Flow<List<ExerciseAttemptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseAttempt(attempt: ExerciseAttemptEntity)

    // Community Posts
    @Query("SELECT * FROM community_posts ORDER BY timestamp DESC")
    fun getCommunityPosts(): Flow<List<CommunityPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunityPost(post: CommunityPostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunityPosts(posts: List<CommunityPostEntity>)

    @Update
    suspend fun updateCommunityPost(post: CommunityPostEntity)

    // Notifications
    @Query("SELECT * FROM app_notifications ORDER BY timestamp DESC")
    fun getNotifications(): Flow<List<AppNotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: AppNotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<AppNotificationEntity>)

    @Query("UPDATE app_notifications SET isRead = 1")
    suspend fun markAllNotificationsRead()

    // Video Lessons (Recorded & Live Classes)
    @Query("SELECT * FROM video_lessons ORDER BY isLive DESC, timestamp DESC")
    fun getAllVideoLessons(): Flow<List<VideoLessonEntity>>

    @Query("SELECT * FROM video_lessons")
    suspend fun getAllVideoLessonsOnce(): List<VideoLessonEntity>

    @Query("SELECT * FROM community_posts")
    suspend fun getAllCommunityPostsOnce(): List<CommunityPostEntity>

    @Query("SELECT COUNT(*) FROM video_lessons")
    suspend fun getVideoLessonsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoLesson(lesson: VideoLessonEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoLessons(lessons: List<VideoLessonEntity>)

    @Update
    suspend fun updateVideoLesson(lesson: VideoLessonEntity)

    @Query("DELETE FROM video_lessons WHERE id = :id")
    suspend fun deleteVideoLessonById(id: Int)
}

@Database(
    entities = [
        UserProfileEntity::class,
        EssayThemeEntity::class,
        EssaySubmissionEntity::class,
        ProgressHistoryEntity::class,
        StudyTaskEntity::class,
        ExerciseAttemptEntity::class,
        CommunityPostEntity::class,
        AppNotificationEntity::class,
        VideoLessonEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mestre_da_redacao_db"
                ).fallbackToDestructiveMigration(true).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
