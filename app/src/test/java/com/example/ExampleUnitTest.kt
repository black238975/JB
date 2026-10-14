package com.example

import com.example.data.CurriculumCatalog
import com.example.data.InitialSeedData
import com.example.network.GeminiClient
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun `verify 18 theme categories and initial seed data`() {
        val categories = InitialSeedData.themeCategories
        assertEquals(18, categories.size)
        assertTrue(categories.containsAll(
            listOf(
                "Educação", "Tecnologia", "Saúde", "Meio Ambiente", "Política",
                "Cultura", "Redes Sociais", "Economia", "Segurança", "Inclusão Social",
                "Direitos Humanos", "Trabalho & Previdência", "Mobilidade Urbana",
                "Ciência & Bioética", "Infância & Envelhecimento", "Esporte & Lazer",
                "Segurança Alimentar", "Cidadania Digital"
            )
        ))
        assertEquals(18, InitialSeedData.initialThemes.size)
        InitialSeedData.initialThemes.forEach { theme ->
            assertTrue(theme.title.isNotBlank())
            assertTrue(theme.motivatingText.isNotBlank())
            assertTrue(theme.proposalPrompt.isNotBlank())
            assertTrue(theme.statisticalData.isNotBlank())
            assertTrue(theme.repertoireSuggestions.isNotBlank())
            assertTrue(theme.guidingQuestions.isNotBlank())
        }
    }

    @Test
    fun `verify learning curriculum and exercises completeness`() {
        assertTrue(CurriculumCatalog.fundamentosLessons.size >= 5)
        assertTrue(CurriculumCatalog.enemCompetencias.size >= 6)
        assertTrue(CurriculumCatalog.libraryAssets.size >= 6)
        assertTrue(CurriculumCatalog.exercises.size >= 6)
    }

    @Test
    fun `verify essay evaluation engine calculates C1 to C5 and total score`() = runTest {
        val essayText = """
            Promulgada em 1988, a Constituição Cidadã assegura a educação como direito de todos e dever do Estado. Contudo, observa-se no Brasil um grave desafio quanto à democratização do ensino digital, decorrente da omissão estatal e da desigualdade socioeconômica.
            
            Em primeira análise, cabe pontuar que a escassez de infraestrutura escolar prejudica o aprendizado. Sob a ótica de Gilberto Dimenstein em 'Cidadãos de Papel', os direitos brasileiros muitas vezes ficam restritos à teoria. Por conseguinte, estudantes periféricos sofrem com a exclusão tecnológica.
            
            Outrossim, a ausência de letramento crítico aprofunda a lacuna educacional. Segundo Paulo Freire, a educação deve ser libertadora e integradora; todavia, muitas escolas carecem de formação continuada para os professores.
            
            Portanto, cabe ao Ministério da Educação — órgão máximo do ensino nacional — ampliar os laboratórios de informática e capacitar docentes, por meio de verbas do FUNDEB e parcerias com universidades, a fim de garantir a inclusão digital e efetivar a Constituição de 1988.
        """.trimIndent()

        val result = GeminiClient.evaluateEssayWithAi(
            themeTitle = "Democratização do ensino digital no Brasil",
            category = "Educação",
            examType = "ENEM",
            essayTitle = "Inclusão Digital e Cidadania",
            essayContent = essayText,
            timeSpentMinutes = 45,
            isSimulatedExam = true,
            themeId = 1
        )

        assertTrue(result.totalScore in 600..1000)
        assertEquals(
            result.totalScore,
            result.c1Score + result.c2Score + result.c3Score + result.c4Score + result.c5Score
        )
        assertFalse(result.strengths.isBlank())
        assertFalse(result.improvements.isBlank())
        assertFalse(result.evolutionPlan.isBlank())
    }

    @Test
    fun `verify automatic theme generation and study plan generation`() = runTest {
        val generatedTheme = GeminiClient.generateThemeWithAi("Tecnologia", "ENEM")
        assertEquals("Tecnologia", generatedTheme.category)
        assertTrue(generatedTheme.title.isNotBlank())
        assertTrue(generatedTheme.motivatingText.isNotBlank())

        val studyPlan = GeminiClient.generatePersonalizedStudyPlan(
            weakestCompetencia = "Competência 4 (Coesão)",
            averageScore = 880,
            targetScore = 960
        )
        assertTrue(studyPlan.isNotEmpty())

        val (discScore, discFeedback) = GeminiClient.evaluateDiscursiveParagraph(
            prompt = "Democratização da leitura",
            studentText = "Sob a égide da Constituição de 1988, cabe ao Ministério da Educação promover bibliotecas por meio de recursos públicos, portanto garantindo cidadania."
        )
        assertTrue(discScore >= 70)
        assertNotNull(discFeedback)
    }
}
