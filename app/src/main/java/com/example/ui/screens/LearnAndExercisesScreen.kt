package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.ConnectiveGuideItem
import com.example.data.CurriculumCatalog
import com.example.data.EssayThemeEntity
import com.example.data.ExerciseAttemptEntity
import com.example.data.ExerciseItem
import com.example.data.LessonItem
import com.example.data.LibraryAsset
import com.example.data.PastEnem1000Essay
import com.example.data.PastEnemOfficialTheme
import com.example.data.QuoteBankItem
import com.example.data.RepertoireAndGuidesCatalog
import com.example.data.SocioculturalRepertoireItem
import com.example.data.StepByStepItem
import com.example.data.TopicSentenceModel
import com.example.data.VideoLessonEntity
import com.example.ui.theme.ClickableIconBlueBg
import com.example.ui.theme.ClickableIconBlueBorder
import com.example.ui.theme.ClickableIconBlueTint
import com.example.ui.theme.GoldBadge
import com.example.ui.theme.ScoreExcellent
import com.example.ui.theme.ScoreNeedsWork

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LearnAndExercisesScreen(
    selectedTab: Int,
    onSelectTab: (Int) -> Unit,
    exerciseAttempts: List<ExerciseAttemptEntity>,
    videoLessons: List<VideoLessonEntity> = emptyList(),
    latestGeneratedTheme: EssayThemeEntity? = null,
    isAiBusy: Boolean,
    onSubmitObjectiveExercise: (ExerciseItem, Int) -> Unit,
    onSubmitDiscursiveExercise: (ExerciseItem, String) -> Unit,
    onPracticePastEnemTheme: (PastEnemOfficialTheme, Boolean) -> Unit = { _, _ -> },
    onGenerateImmediateAiTheme: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onWriteGeneratedTheme: (EssayThemeEntity, Boolean) -> Unit = { _, _ -> },
    onPostVideoLesson: (String, String, String, String, Boolean, String, String, Int, String, String, String) -> Unit = { _, _, _, _, _, _, _, _, _, _, _ -> },
    onToggleVideoWatched: (VideoLessonEntity) -> Unit = {},
    onCycleVideoLiveStatus: (VideoLessonEntity) -> Unit = {},
    onSendVideoChatMessage: (VideoLessonEntity, String) -> Unit = { _, _ -> },
    onDeleteVideoLesson: (Int) -> Unit = {}
) {
    val tabs = listOf(
        "Passo a Passo",
        "Conectivos",
        "Nota 1000",
        "Repertórios",
        "Exercícios",
        "Vídeo Aulas"
    )

    var selectedStepSubView by remember { mutableIntStateOf(0) } // 0 = 8 Etapas, 1 = Tópico Frasal, 2 = Aulas Base
    var expandedStepNumber by remember { mutableIntStateOf(1) }
    var expandedLessonId by remember { mutableStateOf<String?>(CurriculumCatalog.fundamentosLessons.first().id) }
    var selectedConnectiveFilter by remember { mutableStateOf("TODOS") }
    var selectedNota1000SubView by remember { mutableIntStateOf(0) } // 0 = Redações Nota 1000, 1 = Temas ENEM Passados com Textos Motivadores
    var expanded1000EssayId by remember { mutableStateOf(RepertoireAndGuidesCatalog.pastEnem1000Essays.first().id) }
    var expandedPastEnemYear by remember { mutableIntStateOf(2024) }
    var selectedPastYearFilter by remember { mutableIntStateOf(-1) }

    var selectedQuoteCategory by remember { mutableStateOf("TODOS") }
    var quoteSearchQuery by remember { mutableStateOf("") }
    var selectedRepertoireSubView by remember { mutableIntStateOf(0) } // 0 = Banco de Citações, 1 = Repertórios Socioculturais, 2 = Biblioteca Geral
    var selectedSocioculturalTopicFilter by remember { mutableStateOf("TODOS") }
    var selectedLibraryFilter by remember { mutableStateOf("TODOS") }
    var expandedQuoteIds by remember { mutableStateOf(emptySet<String>()) }
    var expandedSocioculturalIds by remember { mutableStateOf(emptySet<String>()) }
    var expandedLibraryAssetIds by remember { mutableStateOf(emptySet<String>()) }
    var selectedExerciseFilter by remember { mutableStateOf("TODOS") }
    var localRepertoireAiTheme by remember {
        mutableStateOf(
            com.example.network.GeminiClient.generateInstantThemePreview(
                category = "Educação",
                examType = "ENEM",
                customFocus = "efetivação da LDB (Lei 9.394/96), do ECA (Lei 8.069/90) e da Constituição Federal de 1988",
                difficultyStyle = "Padrão INEP"
            )
        )
    }

    val selectedChoices = remember { mutableStateMapOf<String, Int>() }
    val submittedObjective = remember { mutableStateMapOf<String, Boolean>() }
    var discursiveDraft by remember {
        mutableStateOf(
            "Sob a égide da Constituição Federal de 1988, o acesso à cultura e à educação configura direito fundamental. Portanto, cabe ao Ministério da Educação e ao Ministério da Cultura ampliar bibliotecas comunitárias e escolares, por meio do Plano Nacional do Livro e Leitura, a fim de formar leitores críticos no Brasil."
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        PrimaryScrollableTabRow(
            selectedTabIndex = selectedTab.coerceIn(0, tabs.lastIndex),
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { onSelectTab(index) },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 1,
                            softWrap = false
                        )
                    },
                    modifier = Modifier.testTag("learn_tab_$index")
                )
            }
        }

        when (selectedTab.coerceIn(0, tabs.lastIndex)) {
            // =====================================================================
            // TAB 0: PASSO A PASSO DA REDAÇÃO + TÓPICO FRASAL + FUNDAMENTOS (ORGANIZADO POR SUB-VISÃO)
            // =====================================================================
            0 -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Estrutura & Fundamentos",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                FilterChip(
                                    selected = selectedStepSubView == 0,
                                    onClick = { selectedStepSubView = 0 },
                                    label = { Text("8 Etapas") }
                                )
                                FilterChip(
                                    selected = selectedStepSubView == 1,
                                    onClick = { selectedStepSubView = 1 },
                                    label = { Text("Tópico Frasal") }
                                )
                                FilterChip(
                                    selected = selectedStepSubView == 2,
                                    onClick = { selectedStepSubView = 2 },
                                    label = { Text("Aulas Base") }
                                )
                            }
                        }
                    }

                    when (selectedStepSubView) {
                        0 -> {
                            items(RepertoireAndGuidesCatalog.stepByStepWritingGuide, key = { it.stepNumber }) { step ->
                                StepByStepCard(
                                    step = step,
                                    isExpanded = expandedStepNumber == step.stepNumber,
                                    onToggle = {
                                        expandedStepNumber = if (expandedStepNumber == step.stepNumber) -1 else step.stepNumber
                                    }
                                )
                            }
                        }
                        1 -> {
                            item {
                                TopicSentenceGuideSection()
                            }
                        }
                        2 -> {
                            items(CurriculumCatalog.fundamentosLessons, key = { it.id }) { lesson ->
                                ExpandableLessonCard(
                                    lesson = lesson,
                                    isExpanded = expandedLessonId == lesson.id,
                                    onToggle = {
                                        expandedLessonId = if (expandedLessonId == lesson.id) null else lesson.id
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // =====================================================================
            // TAB 1: LISTA DE CONECTIVOS E ONDE USAR CADA UM + 5 COMPETÊNCIAS ENEM
            // =====================================================================
            1 -> {
                val connFilters = listOf(
                    "TODOS" to "Todos os Conectivos",
                    "INICIO_D1" to "1ª Linha do D1",
                    "INICIO_D2" to "1ª Linha do D2",
                    "INICIO_CONCLUSAO" to "Início da Conclusão",
                    "INTRA_OPOSICAO" to "Oposição / Contraste",
                    "INTRA_REPERTORIO" to "Citar Repertório",
                    "INTRA_CAUSA" to "Causa / Explicação",
                    "INTRA_CONSEQUENCIA" to "Fechamento do D1/D2",
                    "INTRA_FINALIDADE" to "Meio & Finalidade (C5)"
                )
                val filteredConnectives = RepertoireAndGuidesCatalog.connectivesGuide.filter {
                    selectedConnectiveFilter == "TODOS" || it.categoryCode == selectedConnectiveFilter
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "Mapa Completo de Conectivos e Onde Usar Cada Um",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Saiba exatamente em qual parágrafo e em qual período empregar cada operador argumentativo para garantir 200 pontos na Competência 4.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            connFilters.forEach { (code, label) ->
                                FilterChip(
                                    selected = selectedConnectiveFilter == code,
                                    onClick = { selectedConnectiveFilter = code },
                                    label = { Text(label) }
                                )
                            }
                        }
                    }

                    items(filteredConnectives, key = { it.id }) { item ->
                        ConnectiveGuideCard(item = item)
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "As 5 Competências Oficiais do ENEM (0 a 200 pts)",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(CurriculumCatalog.enemCompetencias, key = { it.id }) { lesson ->
                        ExpandableLessonCard(
                            lesson = lesson,
                            isExpanded = expandedLessonId == lesson.id,
                            onToggle = {
                                expandedLessonId = if (expandedLessonId == lesson.id) null else lesson.id
                            }
                        )
                    }
                }
            }

            // =====================================================================
            // TAB 2: REDAÇÕES NOTA 1000 DE ALUNOS + TEMAS DE ENEMs PASSADOS COM TEXTOS MOTIVADORES
            // =====================================================================
            2 -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            FilterChip(
                                selected = selectedNota1000SubView == 0,
                                onClick = { selectedNota1000SubView = 0 },
                                label = {
                                    Text(
                                        text = "Redações Nota 1000",
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.WorkspacePremium, contentDescription = null, modifier = Modifier.size(18.dp))
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("subview_nota1000_models")
                            )
                            FilterChip(
                                selected = selectedNota1000SubView == 1,
                                onClick = { selectedNota1000SubView = 1 },
                                label = {
                                    Text(
                                        text = "Temas ENEM Passados",
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                leadingIcon = {
                                    Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(18.dp))
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("subview_past_enem_themes")
                            )
                        }
                    }

                    if (selectedNota1000SubView == 0) {
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "Redações Nota 1000 de Alunos de ENEMs Passados",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Estude a estrutura completa na horizontal: deslize entre Introdução, D1, D2, Conclusão, repertórios e análise dos corretores.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        items(RepertoireAndGuidesCatalog.pastEnem1000Essays, key = { it.id }) { essay1000 ->
                            PastEnem1000EssayCard(
                                essay = essay1000,
                                isExpanded = expanded1000EssayId == essay1000.id,
                                onToggle = {
                                    expanded1000EssayId = if (expanded1000EssayId == essay1000.id) "" else essay1000.id
                                }
                            )
                        }
                    } else {
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "Temas Oficiais do ENEM com Textos Motivadores",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Navegue pelos anos na horizontal e deslize entre os Textos Motivadores I a IV para praticar no Editor.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        item {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(vertical = 2.dp)
                            ) {
                                item {
                                    FilterChip(
                                        selected = selectedPastYearFilter == -1,
                                        onClick = { selectedPastYearFilter = -1 },
                                        label = { Text("Todos os Anos", maxLines = 1, softWrap = false) }
                                    )
                                }
                                items(RepertoireAndGuidesCatalog.pastEnemOfficialThemes, key = { it.year }) { pastTheme ->
                                    FilterChip(
                                        selected = selectedPastYearFilter == pastTheme.year,
                                        onClick = {
                                            selectedPastYearFilter = if (selectedPastYearFilter == pastTheme.year) -1 else pastTheme.year
                                            expandedPastEnemYear = pastTheme.year
                                        },
                                        label = { Text("${pastTheme.year}", maxLines = 1, softWrap = false) }
                                    )
                                }
                            }
                        }

                        val displayedPastThemes = if (selectedPastYearFilter == -1) {
                            RepertoireAndGuidesCatalog.pastEnemOfficialThemes
                        } else {
                            RepertoireAndGuidesCatalog.pastEnemOfficialThemes.filter { it.year == selectedPastYearFilter }
                        }

                        items(displayedPastThemes, key = { it.year }) { pastTheme ->
                            PastEnemThemeCard(
                                pastTheme = pastTheme,
                                isExpanded = expandedPastEnemYear == pastTheme.year,
                                onToggle = {
                                    expandedPastEnemYear = if (expandedPastEnemYear == pastTheme.year) -1 else pastTheme.year
                                },
                                onPracticeTheme = { isSimulado ->
                                    onPracticePastEnemTheme(pastTheme, isSimulado)
                                }
                            )
                        }
                    }
                }
            }

            // =====================================================================
            // TAB 3: BANCO DE CITAÇÕES (FILÓSOFOS, MÚSICAS, SÉRIES, FILMES, PENSADORES, LEIS) & REPERTÓRIOS SOCIOCULTURAIS
            // =====================================================================
            3 -> {
                val activeRepertoireTheme = latestGeneratedTheme ?: localRepertoireAiTheme
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("repertoire_screen_list"),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = selectedRepertoireSubView == 0,
                                onClick = { selectedRepertoireSubView = 0 },
                                label = { Text("Banco de Citações & Leis") },
                                modifier = Modifier.testTag("repertoire_subview_quotes")
                            )
                            FilterChip(
                                selected = selectedRepertoireSubView == 1,
                                onClick = { selectedRepertoireSubView = 1 },
                                label = { Text("Banco de Repertórios Socioculturais") },
                                modifier = Modifier.testTag("repertoire_subview_sociocultural")
                            )
                            FilterChip(
                                selected = selectedRepertoireSubView == 2,
                                onClick = { selectedRepertoireSubView = 2 },
                                label = { Text("Biblioteca & Modelos Coringa") },
                                modifier = Modifier.testTag("repertoire_subview_library")
                            )
                        }
                    }

                    // COMANDO QUE GERA TEMAS IMEDIATAMENTE POR IA (INTEGRADO AOS MARCOS LEGAIS CF/88, LDB E ECA)
                    item {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.65f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .testTag("repertoire_instant_ai_theme_command_card"),
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Surface(
                                            color = ClickableIconBlueBg,
                                            shape = RoundedCornerShape(8.dp),
                                            border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = Icons.Default.AutoAwesome,
                                                    contentDescription = null,
                                                    tint = ClickableIconBlueTint,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }
                                        Column {
                                            Text(
                                                text = "Comando IA: Gerar Tema Imediatamente",
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "CF/88 • LDB (Lei 9.394/96) • ECA (Lei 8.069/90)",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                    }
                                    Button(
                                        onClick = {
                                            localRepertoireAiTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                                category = "Educação",
                                                examType = "ENEM",
                                                customFocus = "",
                                                difficultyStyle = "Padrão INEP"
                                            )
                                            onGenerateImmediateAiTheme("Educação", "ENEM", "", "Padrão INEP")
                                        },
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.testTag("repertoire_instant_generate_ai_theme_button")
                                    ) {
                                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Gerar Tema IA", style = MaterialTheme.typography.labelMedium)
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .testTag("repertoire_generated_theme_preview"),
                                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.25f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "TEMA IMEDIATO • ${activeRepertoireTheme.category.uppercase()} (${activeRepertoireTheme.examType})",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.secondary,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = activeRepertoireTheme.title,
                                                style = MaterialTheme.typography.bodySmall,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        FilledTonalButton(
                                            onClick = { onWriteGeneratedTheme(activeRepertoireTheme, false) },
                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                            modifier = Modifier.testTag("repertoire_write_generated_theme_button")
                                        ) {
                                            Text("Redigir", style = MaterialTheme.typography.labelSmall)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    AssistChip(
                                        onClick = {
                                            val focus = "a efetivação dos Direitos Sociais da Constituição Federal de 1988 (Art. 6º)"
                                            localRepertoireAiTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                                category = "Cidadania",
                                                examType = "ENEM",
                                                customFocus = focus,
                                                difficultyStyle = "Padrão INEP"
                                            )
                                            onGenerateImmediateAiTheme("Cidadania", "ENEM", focus, "Padrão INEP")
                                        },
                                        label = { Text("Gerar com CF/88", style = MaterialTheme.typography.labelSmall) },
                                        modifier = Modifier.testTag("quick_ai_theme_cf88")
                                    )
                                    AssistChip(
                                        onClick = {
                                            val focus = "a garantia do pleno desenvolvimento educacional previsto na LDB (Lei nº 9.394/1996)"
                                            localRepertoireAiTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                                category = "Educação",
                                                examType = "ENEM",
                                                customFocus = focus,
                                                difficultyStyle = "Padrão INEP"
                                            )
                                            onGenerateImmediateAiTheme("Educação", "ENEM", focus, "Padrão INEP")
                                        },
                                        label = { Text("Gerar com LDB", style = MaterialTheme.typography.labelSmall) },
                                        modifier = Modifier.testTag("quick_ai_theme_ldb")
                                    )
                                    AssistChip(
                                        onClick = {
                                            val focus = "a proteção integral da infância e juventude assegurada pelo ECA (Lei nº 8.069/1990)"
                                            localRepertoireAiTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                                category = "Educação",
                                                examType = "ENEM",
                                                customFocus = focus,
                                                difficultyStyle = "Padrão INEP"
                                            )
                                            onGenerateImmediateAiTheme("Educação", "ENEM", focus, "Padrão INEP")
                                        },
                                        label = { Text("Gerar com ECA", style = MaterialTheme.typography.labelSmall) },
                                        modifier = Modifier.testTag("quick_ai_theme_eca")
                                    )
                                }
                            }
                        }
                    }

                    when (selectedRepertoireSubView) {
                        0 -> {
                            val filteredQuotes = RepertoireAndGuidesCatalog.quoteBankItems.filter { item ->
                                val matchesCategory = when (selectedQuoteCategory) {
                                    "TODOS" -> true
                                    "CF88" -> item.category == "CF88" || item.workTitle.contains("Constituição", ignoreCase = true) || item.workTitle.contains("CF/88", ignoreCase = true)
                                    "LDB" -> item.category == "LDB" || item.workTitle.contains("LDB", ignoreCase = true)
                                    "ECA" -> item.category == "ECA" || item.workTitle.contains("ECA", ignoreCase = true)
                                    "LEIS_ONU" -> item.category == "LEIS_ONU"
                                    "FILOSOFOS" -> item.category == "FILOSOFOS"
                                    "PENSADORES" -> item.category == "PENSADORES"
                                    else -> item.category == selectedQuoteCategory
                                }
                                val matchesSearch = quoteSearchQuery.isBlank() ||
                                    item.authorOrCreator.contains(quoteSearchQuery, ignoreCase = true) ||
                                    item.workTitle.contains(quoteSearchQuery, ignoreCase = true) ||
                                    item.quoteOrReferenceText.contains(quoteSearchQuery, ignoreCase = true) ||
                                    item.recommendedAxes.any { it.contains(quoteSearchQuery, ignoreCase = true) }
                                matchesCategory && matchesSearch
                            }

                            item {
                                Text(
                                    text = "Tópicos de Repertório & Citações (Clique para Abrir)",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Todos os repertórios (Constituição 1988, LDB, ECA, Filósofos, Sociólogos, Músicas, Séries, Filmes e Literatura) estão organizados em tópicos que só aparecem quando clicados.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = quoteSearchQuery,
                                    onValueChange = { quoteSearchQuery = it },
                                    label = { Text("Buscar tópico (ex.: LDB, ECA, Constituição 1988, filósofo...)", fontWeight = FontWeight.SemiBold) },
                                    leadingIcon = {
                                        Surface(
                                            color = ClickableIconBlueBg,
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = Icons.Default.Search,
                                                    contentDescription = null,
                                                    tint = ClickableIconBlueTint,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("quote_bank_search_input"),
                                    singleLine = true
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    RepertoireAndGuidesCatalog.quoteBankCategories.forEach { (code, label) ->
                                        FilterChip(
                                            selected = selectedQuoteCategory == code,
                                            onClick = { selectedQuoteCategory = code },
                                            label = { Text(label) },
                                            modifier = Modifier.testTag("quote_cat_$code")
                                        )
                                    }
                                }
                            }

                            items(filteredQuotes, key = { it.id }) { quoteItem ->
                                QuoteBankCard(
                                    item = quoteItem,
                                    isExpanded = quoteItem.id in expandedQuoteIds,
                                    onToggle = {
                                        expandedQuoteIds = if (quoteItem.id in expandedQuoteIds) {
                                            expandedQuoteIds - quoteItem.id
                                        } else {
                                            expandedQuoteIds + quoteItem.id
                                        }
                                    },
                                    onGenerateThemeFromQuote = { item ->
                                        val axis = item.recommendedAxes.firstOrNull() ?: "Educação"
                                        val focus = "a aplicação de ${item.workTitle} (${item.authorOrCreator})"
                                        localRepertoireAiTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                            category = axis,
                                            examType = "ENEM",
                                            customFocus = focus,
                                            difficultyStyle = "Padrão INEP"
                                        )
                                        onGenerateImmediateAiTheme(axis, "ENEM", focus, "Padrão INEP")
                                    }
                                )
                            }
                        }

                        1 -> {
                            val socTopicFilters = listOf(
                                "TODOS" to "Todos os Tópicos",
                                "CF88" to "Constituição 1988 (Artigos)",
                                "LDB" to "LDB (Artigos)",
                                "ECA" to "ECA (Artigos)",
                                "LEIS_OUTROS" to "Outras Leis & Estatutos",
                                "FILOSOFOS" to "Filósofos",
                                "PENSADORES" to "Pensadores & Sociólogos"
                            )
                            val filteredSociocultural = RepertoireAndGuidesCatalog.socioculturalRepertoireBank.filter { rep ->
                                when (selectedSocioculturalTopicFilter) {
                                    "CF88" -> rep.groupCode == "CF88" || rep.id.startsWith("rep_soc_cf88")
                                    "LDB" -> rep.groupCode == "LDB" || rep.id.startsWith("rep_soc_ldb")
                                    "ECA" -> rep.groupCode == "ECA" || rep.id.startsWith("rep_soc_eca")
                                    "LEIS_OUTROS" -> rep.groupCode == "LEIS_OUTROS"
                                    "FILOSOFOS" -> rep.groupCode == "FILOSOFOS"
                                    "PENSADORES" -> rep.groupCode == "PENSADORES"
                                    else -> true
                                }
                            }

                            item {
                                Text(
                                    text = "Tópicos de Repertórios Socioculturais (CF/88, LDB, ECA, Filósofos e Leis)",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Cada artigo e referência está organizado em tópicos que aparecem apenas quando clicados. Toque em qualquer tópico para expandir o conceito, fundamentação e como aplicar na redação.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    socTopicFilters.forEach { (code, label) ->
                                        FilterChip(
                                            selected = selectedSocioculturalTopicFilter == code,
                                            onClick = { selectedSocioculturalTopicFilter = code },
                                            label = { Text(label) },
                                            modifier = Modifier.testTag("soc_topic_cat_$code")
                                        )
                                    }
                                }
                            }

                            items(filteredSociocultural, key = { it.id }) { rep ->
                                SocioculturalRepertoireCard(
                                    item = rep,
                                    isExpanded = rep.id in expandedSocioculturalIds,
                                    onToggle = {
                                        expandedSocioculturalIds = if (rep.id in expandedSocioculturalIds) {
                                            expandedSocioculturalIds - rep.id
                                        } else {
                                            expandedSocioculturalIds + rep.id
                                        }
                                    },
                                    onGenerateThemeFromRepertoire = { item ->
                                        val axis = item.compatibleThemes.firstOrNull() ?: "Educação"
                                        val focus = "os desafios relacionados a ${item.title}"
                                        localRepertoireAiTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                            category = axis,
                                            examType = "ENEM",
                                            customFocus = focus,
                                            difficultyStyle = "Padrão INEP"
                                        )
                                        onGenerateImmediateAiTheme(axis, "ENEM", focus, "Padrão INEP")
                                    }
                                )
                            }
                        }

                        else -> {
                            val filterOptions = listOf(
                                "TODOS" to "Todos os Tópicos",
                                "VIDEOAULA" to "Videoaulas",
                                "PDF_RESUMO" to "PDFs & Resumos",
                                "MAPA_MENTAL" to "Mapas Mentais",
                                "MODELO_PRONTO" to "Modelos Prontos",
                                "FRASE_CORINGA" to "Frases Coringa",
                                "REPERTORIO_TEMA" to "Repertórios (CF/88, LDB, ECA)"
                            )
                            val filteredAssets = CurriculumCatalog.libraryAssets.filter {
                                selectedLibraryFilter == "TODOS" || it.type == selectedLibraryFilter
                            }

                            item {
                                Text(
                                    text = "Tópicos da Biblioteca, Mapas Mentais e Modelos Prontos",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Clique em qualquer tópico abaixo para abrir o conteúdo completo.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    filterOptions.forEach { (key, label) ->
                                        FilterChip(
                                            selected = selectedLibraryFilter == key,
                                            onClick = { selectedLibraryFilter = key },
                                            label = { Text(label) }
                                        )
                                    }
                                }
                            }

                            items(filteredAssets, key = { it.id }) { asset ->
                                LibraryAssetCard(
                                    asset = asset,
                                    isExpanded = asset.id in expandedLibraryAssetIds,
                                    onToggle = {
                                        expandedLibraryAssetIds = if (asset.id in expandedLibraryAssetIds) {
                                            expandedLibraryAssetIds - asset.id
                                        } else {
                                            expandedLibraryAssetIds + asset.id
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // =====================================================================
            // TAB 4: LABORATÓRIO DE EXERCÍCIOS COM CORREÇÃO AUTOMÁTICA
            // =====================================================================
            4 -> {
                val exFilters = listOf(
                    "TODOS" to "Todos",
                    "OBJETIVA" to "Objetivas",
                    "DISCURSIVA" to "Discursivas (IA)",
                    "CONECTIVOS" to "Conectivos",
                    "GRAMATICA" to "Gramática",
                    "REPERTORIO" to "Repertório",
                    "ARGUMENTACAO" to "Argumentação"
                )
                val filteredExercises = CurriculumCatalog.exercises.filter {
                    selectedExerciseFilter == "TODOS" || it.type == selectedExerciseFilter
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            text = "Laboratório de Exercícios com Correção Automática",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Resolva questões objetivas e discursivas para ganhar XP, evoluir sua patente metálica e fixar cada competência.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            exFilters.forEach { (key, label) ->
                                FilterChip(
                                    selected = selectedExerciseFilter == key,
                                    onClick = { selectedExerciseFilter = key },
                                    label = { Text(label) }
                                )
                            }
                        }
                    }

                    items(filteredExercises, key = { it.id }) { exercise ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("exercise_card_${exercise.id}")
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "${exercise.type} • ${exercise.competenciaTag}",
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = "+${exercise.xpReward} XP",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = ScoreExcellent,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = exercise.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = exercise.prompt,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                if (exercise.options.isNotEmpty()) {
                                    val chosen = selectedChoices[exercise.id] ?: -1
                                    val isDone = submittedObjective[exercise.id] == true

                                    exercise.options.forEachIndexed { idx, optText ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    selectedChoices[exercise.id] = idx
                                                }
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = chosen == idx,
                                                onClick = { selectedChoices[exercise.id] = idx }
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = optText,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = {
                                            if (chosen >= 0) {
                                                submittedObjective[exercise.id] = true
                                                onSubmitObjectiveExercise(exercise, chosen)
                                            }
                                        },
                                        enabled = chosen >= 0,
                                        modifier = Modifier.testTag("submit_exercise_${exercise.id}")
                                    ) {
                                        Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Verificar Resposta")
                                    }

                                    if (isDone) {
                                        val isRight = chosen == exercise.correctIndex
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Surface(
                                            color = if (isRight) ScoreExcellent.copy(alpha = 0.14f) else MaterialTheme.colorScheme.errorContainer,
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Column(modifier = Modifier.padding(12.dp)) {
                                                Text(
                                                    text = if (isRight) "Excelente! Alternativa correta." else "Atenção à explicação comentada:",
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = exercise.explanation,
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    OutlinedTextField(
                                        value = discursiveDraft,
                                        onValueChange = { discursiveDraft = it },
                                        label = { Text("Sua resposta discursiva (4 a 7 linhas)") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("discursive_input_field"),
                                        minLines = 4
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = { onSubmitDiscursiveExercise(exercise, discursiveDraft) },
                                        enabled = !isAiBusy && discursiveDraft.isNotBlank(),
                                        modifier = Modifier.testTag("submit_discursive_button")
                                    ) {
                                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Corrigir Parágrafo com IA")
                                    }

                                    val latestAttempt = exerciseAttempts.firstOrNull { it.exerciseId == exercise.id }
                                    if (latestAttempt != null) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Surface(
                                            color = MaterialTheme.colorScheme.secondaryContainer,
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Column(modifier = Modifier.padding(12.dp)) {
                                                Text(
                                                    text = "Feedback Automático da IA:",
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = latestAttempt.feedback,
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // =====================================================================
            // TAB 5: SESSÃO DE VÍDEO AULAS (GRAVADAS & AO VIVO)
            // =====================================================================
            5 -> {
                VideoLessonsSessionSection(
                    videoLessons = videoLessons,
                    onPostVideoLesson = onPostVideoLesson,
                    onToggleWatched = onToggleVideoWatched,
                    onCycleLiveStatus = onCycleVideoLiveStatus,
                    onSendChatMessage = onSendVideoChatMessage,
                    onDeleteLesson = onDeleteVideoLesson
                )
            }
        }
    }
}

@Composable
private fun StepByStepCard(
    step: StepByStepItem,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("step_card_${step.stepNumber}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${step.stepNumber}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "PASSO ${step.stepNumber} • Tempo sugerido: ${step.timeRecommended}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = step.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    color = ClickableIconBlueBg,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, ClickableIconBlueBorder),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expandir Passo ${step.stepNumber}",
                            tint = ClickableIconBlueTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))
                    step.detailedChecklist.forEach { check ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = ScoreExcellent,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = check, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Exemplo Prático Aplicado:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = step.practicalExample,
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        color = ScoreNeedsWork.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "⚠️ Erro Comum a Evitar:",
                                style = MaterialTheme.typography.labelSmall,
                                color = ScoreNeedsWork,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = step.pitfallToAvoid,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopicSentenceGuideSection() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("topic_sentence_guide_card"),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = GoldBadge
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "O Que É Tópico Frasal e Como Criar um Nota 1000",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = RepertoireAndGuidesCatalog.topicSentenceDefinition,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Por que o Tópico Frasal é Indispensável?",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            RepertoireAndGuidesCatalog.topicSentenceWhyItMatters.forEach { reason ->
                Text(
                    text = "• $reason",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            RepertoireAndGuidesCatalog.topicSentenceModels.forEach { model ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = model.styleName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Fórmula: ${model.formula}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = model.weakExample,
                            style = MaterialTheme.typography.bodySmall,
                            color = ScoreNeedsWork
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = model.strongExample1000,
                            style = MaterialTheme.typography.bodySmall,
                            color = ScoreExcellent,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Análise: ${model.explanation}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ConnectiveGuideCard(item: ConnectiveGuideItem) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("connective_card_${item.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.exactPositionWhereToUse,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.categoryTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.semanticFunction,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item.connectives.forEach { conn ->
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = conn,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Exemplo na Frase:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item.appliedSentenceExample,
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "💡 Regra de Ouro: ${item.grammarAndPunctuationTip}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun PastEnem1000EssayCard(
    essay: PastEnem1000Essay,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("enem1000_card_${essay.year}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = ScoreExcellent.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = ScoreExcellent,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ENEM ${essay.year} • NOTA 1000 OFICIAL",
                            style = MaterialTheme.typography.labelSmall,
                            color = ScoreExcellent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = "${essay.studentName} (${essay.stateCode})",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tema: \"${essay.officialTheme}\"",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    HorizontalDivider()

                    val sections = listOf(
                        "1º Introdução" to essay.introducao,
                        "2º D1" to essay.desenvolvimento1,
                        "3º D2" to essay.desenvolvimento2,
                        "4º Conclusão" to essay.conclusao
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Deslize na horizontal para ler os 4 parágrafos e análises:",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "6 seções",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(vertical = 4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(sections) { idx, (label, content) ->
                            HorizontalParagraphCard(
                                label = label,
                                text = content,
                                index = idx + 1,
                                total = 6,
                                modifier = Modifier.width(310.dp)
                            )
                        }

                        item {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                                modifier = Modifier.width(310.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Repertórios Usados",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Surface(
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = "5 de 6",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    HorizontalDivider()
                                    Spacer(modifier = Modifier.height(8.dp))
                                    essay.repertoriosUsed.forEach { rep ->
                                        Text(text = "• $rep", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }

                        item {
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f)),
                                modifier = Modifier.width(310.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Por que Nota 1000?",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                        Surface(
                                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = "6 de 6",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.secondary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    HorizontalDivider()
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = essay.whyItScored1000, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HorizontalParagraphCard(
    label: String,
    text: String,
    index: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    softWrap = false
                )
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "$index de $total",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun PastEnemThemeCard(
    pastTheme: PastEnemOfficialTheme,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onPracticeTheme: (Boolean) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("past_enem_theme_${pastTheme.year}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "ENEM ${pastTheme.year} • ${pastTheme.axis}",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Surface(
                    color = ClickableIconBlueBg,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, ClickableIconBlueBorder),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Ver Textos Motivadores ENEM ${pastTheme.year}",
                            tint = ClickableIconBlueTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pastTheme.officialThemeTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    HorizontalDivider()

                    val motivatorSections = listOf(
                        "Texto Motivador I" to pastTheme.textoI,
                        "Texto Motivador II" to pastTheme.textoII,
                        "Texto Motivador III" to pastTheme.textoIII,
                        "Texto Motivador IV" to pastTheme.textoIV,
                        "Tese Bipartida (A1 & A2)" to pastTheme.suggestedArguments,
                        "Repertórios Recomendados" to pastTheme.recommendedRepertoires
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Deslize na horizontal pelos Textos Motivadores I a IV:",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${motivatorSections.size} seções",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(vertical = 4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(motivatorSections) { idx, (label, content) ->
                            HorizontalParagraphCard(
                                label = label,
                                text = content,
                                index = idx + 1,
                                total = motivatorSections.size,
                                modifier = Modifier.width(310.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onPracticeTheme(false) },
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("practice_past_enem_${pastTheme.year}")
                        ) {
                            Icon(Icons.Default.EditNote, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Escrever",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                        OutlinedButton(
                            onClick = { onPracticeTheme(true) },
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("simulado_past_enem_${pastTheme.year}")
                        ) {
                            Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Simulado",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun QuoteBankCard(
    item: QuoteBankItem,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onGenerateThemeFromQuote: (QuoteBankItem) -> Unit = {}
) {
    val categoryIcon = when (item.category) {
        "FILOSOFOS" -> Icons.Default.Psychology
        "MUSICAS" -> Icons.Default.MusicNote
        "SERIES", "FILMES" -> Icons.Default.Movie
        "LEIS_ONU" -> Icons.Default.AccountBalance
        else -> Icons.Default.FormatQuote
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("quote_card_${item.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = ClickableIconBlueBg,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, ClickableIconBlueBorder),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = categoryIcon,
                                contentDescription = null,
                                tint = ClickableIconBlueTint,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = item.categoryLabel,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.workTitle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${item.authorOrCreator} • ${if (isExpanded) "Toque para recolher" else "Toque para abrir o tópico"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = ClickableIconBlueBg,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, ClickableIconBlueBorder),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "Recolher tópico ${item.workTitle}" else "Expandir tópico ${item.workTitle}",
                            tint = ClickableIconBlueTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = item.quoteOrReferenceText,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Contexto: ${item.contextMeaning}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "🎯 Como usar na Redação: ${item.displayHowToUse}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        item.recommendedAxes.forEach { axis ->
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = axis,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = { onGenerateThemeFromQuote(item) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("generate_ai_theme_from_quote_${item.id}")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Gerar Tema Imediato por IA com este Repertório")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SocioculturalRepertoireCard(
    item: SocioculturalRepertoireItem,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onGenerateThemeFromRepertoire: (SocioculturalRepertoireItem) -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("sociocultural_rep_${item.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = ClickableIconBlueBg,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, ClickableIconBlueBorder),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.AccountBalance,
                                contentDescription = null,
                                tint = ClickableIconBlueTint,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Tópico • ${item.areaOfKnowledge}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${item.authorOrSource} • ${if (isExpanded) "Toque para recolher" else "Toque para abrir o tópico"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = ClickableIconBlueBg,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, ClickableIconBlueBorder),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "Recolher tópico ${item.title}" else "Expandir tópico ${item.title}",
                            tint = ClickableIconBlueTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = item.coreConcept,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Aplicação Produtiva no Parágrafo (C2 + C3):",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.howToUseInParagraph,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        item.compatibleThemes.forEach { themeAxis ->
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = themeAxis,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = { onGenerateThemeFromRepertoire(item) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("generate_ai_theme_from_soc_${item.id}")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Gerar Tema Imediato por IA com este Repertório")
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandableLessonCard(
    lesson: LessonItem,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("lesson_card_${lesson.id}"),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${lesson.badge} • ${lesson.durationMinutes} min",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = lesson.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = lesson.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    color = ClickableIconBlueBg,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, ClickableIconBlueBorder),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expandir aula ${lesson.title}",
                            tint = ClickableIconBlueTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))
                    lesson.keyPoints.forEach { point ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = ScoreExcellent,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = point, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Fórmula Prática:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = lesson.practicalFormula,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Exemplo Aplicado Nota 1000:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = lesson.exampleSnippet,
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LibraryAssetCard(
    asset: LibraryAsset,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("library_asset_${asset.id}"),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = ClickableIconBlueBg,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, ClickableIconBlueBorder),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (asset.type == "VIDEOAULA") Icons.Default.PlayCircleFilled else Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = ClickableIconBlueTint,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Tópico • ${asset.categoryTag} (${asset.durationOrPages})",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = asset.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isExpanded) "Toque para recolher" else "Toque para abrir o tópico",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = ClickableIconBlueBg,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, ClickableIconBlueBorder),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "Recolher tópico ${asset.title}" else "Expandir tópico ${asset.title}",
                            tint = ClickableIconBlueTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = asset.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    asset.contentBlocks.forEach { line ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = line,
                                modifier = Modifier.padding(10.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}
