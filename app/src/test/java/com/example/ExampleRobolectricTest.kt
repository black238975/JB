package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.EnemPortugueseSimuladoCatalog
import com.example.data.MestreRepository
import com.example.data.RepertoireAndGuidesCatalog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Mestre da Redação", appName)
    }

    @Test
    fun `room database stores user essays scores 18 thematic axes and historical progress data`() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        val repo = MestreRepository(db.appDao())

        repo.ensureSeedData()

        val profile = repo.userProfile.first()
        assertNotNull(profile)
        assertEquals("Eduarda Andrade", profile?.name)

        // Verify all 18 thematic categories are seeded
        val themes = repo.allThemes.first()
        assertEquals(18, themes.size)

        // Verify catalogs for step-by-step, connectives, 1000-score essays, past ENEM themes, repertoires, quotes, and metal tiers
        assertEquals(8, RepertoireAndGuidesCatalog.stepByStepWritingGuide.size)
        assertTrue(RepertoireAndGuidesCatalog.connectivesGuide.size >= 8)
        assertTrue(RepertoireAndGuidesCatalog.pastEnem1000Essays.size >= 5)
        assertTrue(RepertoireAndGuidesCatalog.pastEnemOfficialThemes.size >= 8)
        assertTrue(RepertoireAndGuidesCatalog.socioculturalRepertoireBank.size >= 13)
        assertTrue(RepertoireAndGuidesCatalog.socioculturalRepertoireBank.any { it.title.contains("Constituição Federal de 1988") })
        assertTrue(RepertoireAndGuidesCatalog.socioculturalRepertoireBank.any { it.title.contains("LDB") && it.title.contains("Lei de Diretrizes e Bases") })
        assertTrue(RepertoireAndGuidesCatalog.socioculturalRepertoireBank.any { it.title.contains("ECA") && it.title.contains("Estatuto da Criança e do Adolescente") })
        assertTrue(RepertoireAndGuidesCatalog.quoteBankItems.size >= 23)
        assertTrue(RepertoireAndGuidesCatalog.quoteBankItems.any { it.workTitle.contains("Constituição Federal de 1988") })
        assertTrue(RepertoireAndGuidesCatalog.quoteBankItems.any { it.workTitle.contains("LDB") && it.workTitle.contains("Lei de Diretrizes e Bases") })
        assertTrue(RepertoireAndGuidesCatalog.quoteBankItems.any { it.workTitle.contains("ECA") && it.workTitle.contains("Estatuto da Criança e do Adolescente") })
        assertEquals(7, RepertoireAndGuidesCatalog.metalRankTiers.size)

        // Verify metal rank progression logic (Bronze -> Prata -> Ouro -> Platina -> Titânio -> Paládio -> Ródio/Diamante)
        assertEquals("Bronze Forjado", RepertoireAndGuidesCatalog.getMetalTierForUser(1, 200).metalName)
        assertEquals("Prata Acadêmica", RepertoireAndGuidesCatalog.getMetalTierForUser(3, 900).metalName)
        assertEquals("Ouro Imperial", RepertoireAndGuidesCatalog.getMetalTierForUser(5, 1600).metalName)
        assertEquals("Platina Cristalina", RepertoireAndGuidesCatalog.getMetalTierForUser(8, 2480).metalName)
        assertEquals("Ródio & Diamante Supremo", RepertoireAndGuidesCatalog.getMetalTierForUser(14, 4800).metalName)

        val initialEssays = repo.allEssays.first()
        assertTrue(initialEssays.size >= 3)

        val initialHistory = repo.progressHistory.first()
        assertEquals(4, initialHistory.size)

        // Insert a new historical essay and score snapshot into Room
        val newEssayId = repo.recordManualHistoricalProgress(
            title = "Inteligência Artificial e Ética na Educação",
            category = "Tecnologia",
            examType = "ENEM",
            c1 = 200,
            c2 = 200,
            c3 = 200,
            c4 = 180,
            c5 = 200,
            milestoneNote = "Nota 980 alcançada com repertório de Hannah Arendt."
        )
        assertTrue(newEssayId > 0)

        val updatedEssays = repo.allEssays.first()
        assertEquals(initialEssays.size + 1, updatedEssays.size)

        val highest = repo.highestScoreEssay.first()
        assertNotNull(highest)
        assertEquals(980, highest?.totalScore)

        val updatedHistory = repo.progressHistory.first()
        assertEquals(initialHistory.size + 1, updatedHistory.size)
        assertTrue(updatedHistory.any { it.totalScore == 980 && it.essayTitle.contains("Inteligência Artificial") })

        // Delete essay and its historical progress snapshot
        repo.deleteEssayAndProgress(newEssayId.toInt())
        val afterDeleteEssays = repo.allEssays.first()
        val afterDeleteHistory = repo.progressHistory.first()
        assertEquals(initialEssays.size, afterDeleteEssays.size)
        assertEquals(initialHistory.size, afterDeleteHistory.size)

        // Verify Video Lessons (Recorded & Live) seeded and new lesson insertion in Room
        val initialVideos = repo.videoLessons.first()
        assertTrue(initialVideos.size >= 5)
        assertTrue(initialVideos.any { it.isLive && it.liveStatus == "AO_VIVO_AGORA" })
        assertTrue(initialVideos.any { !it.isLive && it.liveStatus == "GRAVADA" })

        val newVideoId = repo.insertVideoLesson(
            com.example.data.VideoLessonEntity(
                title = "Live de Véspera: Estrutura Nota 1000",
                description = "Revisão geral ao vivo das 5 competências",
                instructorName = "Prof. João Batista",
                category = "Aulão Ao Vivo",
                isLive = true,
                liveStatus = "AO_VIVO_AGORA",
                scheduledDateTime = "Hoje às 20:00"
            )
        )
        assertTrue(newVideoId > 0)
        assertEquals(initialVideos.size + 1, repo.videoLessons.first().size)

        // Verify Handwritten Essay Scanner evaluation and Room persistence
        val scannedEntity = com.example.network.GeminiClient.scanAndEvaluateHandwrittenEssay(
            imageBase64 = null,
            themeTitle = "Desafios da educação digital no Brasil",
            category = "Educação"
        )
        assertTrue(scannedEntity.isScannedFromPaper)
        assertTrue(scannedEntity.totalScore in 400..1000)
        assertTrue(scannedEntity.competencyCommentsRaw.contains("C1::"))
        assertTrue(scannedEntity.competencyCommentsRaw.contains("C5::"))

        // Verify Real-Time AI Theme Generation for any axis and custom focus
        val realtimeTheme = com.example.network.GeminiClient.generateThemeWithAi(
            category = "Educação",
            examType = "ENEM",
            customFocus = "IA nas escolas públicas",
            difficultyStyle = "Desafio Nota 1000"
        )
        assertEquals("Educação", realtimeTheme.category)
        assertTrue(realtimeTheme.title.isNotBlank())
        assertTrue(realtimeTheme.motivatingText.isNotBlank())
        val insertedThemeId = repo.insertTheme(realtimeTheme)
        assertTrue(insertedThemeId > 0)

        db.close()
    }

    @Test
    fun `critical user journey navigates across guides connectives nota 1000 quotes and animated metal evolution`() {
        composeTestRule.waitForIdle()

        // Verify Home Screen hero button and navigation items are displayed
        composeTestRule.onNodeWithTag("start_now_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("theme_toggle_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("theme_toggle_button").performClick()
        composeTestRule.waitForIdle()

        // Navigate to Learn & Exercises module (Step-by-step, Connectives, Nota 1000 & Past ENEMs, Quotes & Repertoires)
        composeTestRule.onNodeWithTag("nav_item_learn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("learn_tab_0").assertIsDisplayed()
        composeTestRule.onNodeWithTag("step_card_1").assertIsDisplayed()

        // Switch to Connectives & Where to use tab
        composeTestRule.onNodeWithTag("learn_tab_1").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("connective_card_conn_d1").assertIsDisplayed()

        // Switch to Nota 1000 & Past ENEMs tab
        composeTestRule.onNodeWithTag("learn_tab_2").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("subview_past_enem_themes").performClick()
        composeTestRule.waitForIdle()

        // Switch to Repertoire & Quotes Bank tab (with CF/88, LDB, ECA and Immediate AI Theme Command)
        composeTestRule.onNodeWithTag("learn_tab_3").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("repertoire_screen_list").performScrollToIndex(1)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("repertoire_instant_ai_theme_command_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("quick_ai_theme_ldb").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("repertoire_instant_generate_ai_theme_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("repertoire_generated_theme_preview").assertIsDisplayed()
        composeTestRule.onNodeWithTag("repertoire_screen_list").performScrollToIndex(2)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("quote_bank_search_input").assertIsDisplayed()

        // Switch to Video Lessons (Recorded & Live) tab and open Post Video Lesson studio
        composeTestRule.onNodeWithTag("learn_tab_5").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("video_lessons_header_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("open_post_video_lesson_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(" video_lessons_screen_list".trim()).performScrollToIndex(1)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("submit_video_lesson_button").performClick()
        composeTestRule.waitForIdle()

        // Navigate to Practice & AI module including Real-Time AI Theme Generator on Eixos page and Scanner ENEM (Foto)
        composeTestRule.onNodeWithTag("nav_item_practice").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("practice_tab_0").assertIsDisplayed()
        composeTestRule.onNodeWithTag("themes_screen_list").performScrollToIndex(1)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("realtime_ai_theme_generator_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ai_quick_suggestion_0").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("generate_theme_ai_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("realtime_generated_theme_preview").assertIsDisplayed()

        composeTestRule.onNodeWithTag("practice_tab_4").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("scanner_header_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("scan_paper_essay_button").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("practice_tab_1").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("essay_title_input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("submit_essay_ai_button").performScrollTo().assertIsDisplayed()

        // Navigate to Performance & Gamification module (Metal Rank & Animated Evolution Chart)
        composeTestRule.onNodeWithTag("nav_item_performance").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("performance_screen_list").assertIsDisplayed()
        composeTestRule.onNodeWithTag("metal_rank_profile_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("advance_metal_rank_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("performance_screen_list").performScrollToIndex(1)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("add_historical_score_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("save_history_score_button").performClick()
        composeTestRule.waitForIdle()

        // Navigate to Community, Teacher, Admin & Premium Hub
        composeTestRule.onNodeWithTag("nav_item_hub").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("hub_tab_0").assertIsDisplayed()
        composeTestRule.onNodeWithTag("hub_tab_1").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun `enem portuguese grammar simulado provides multiple choice questions with omitted gabarito and reveals answer key on click`() {
        // Verify catalog has authentic questions
        assertTrue(EnemPortugueseSimuladoCatalog.questions.size >= 15)
        assertTrue(EnemPortugueseSimuladoCatalog.questions.all { it.options.size == 5 })
        assertTrue(EnemPortugueseSimuladoCatalog.questions.all { it.correctIndex in 0..4 })
        assertTrue(EnemPortugueseSimuladoCatalog.questions.all { it.explanation.contains("Prof. João Batista") })

        // Navigate to practice tab 3 (Simulado)
        composeTestRule.onNodeWithTag("nav_item_practice").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("practice_tab_3").performScrollTo().performClick()
        composeTestRule.waitForIdle()

        // Verify Simulado banner and default omitted gabarito with "Ver gabarito" button
        composeTestRule.onNodeWithTag("simulado_gramatica_banner").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ver_gabarito_button").assertIsDisplayed()

        // Click "Ver gabarito" to reveal answer key and see performance calculation
        composeTestRule.onNodeWithTag("ver_gabarito_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("resultado_gabarito_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ocultar_gabarito_button").assertIsDisplayed()
    }
}
