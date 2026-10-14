package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.EssaySubmissionEntity
import com.example.data.EssayThemeEntity
import com.example.data.InitialSeedData
import com.example.ui.theme.ClickableIconBlueBg
import com.example.ui.theme.ClickableIconBlueBorder
import com.example.ui.theme.ClickableIconBlueTint
import com.example.ui.theme.ScoreExcellent
import com.example.ui.theme.ScoreNeedsWork
import com.example.ui.theme.ScoreWarning
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PracticeAndAiScreen(
    selectedTab: Int,
    onSelectTab: (Int) -> Unit,
    themes: List<EssayThemeEntity>,
    essays: List<EssaySubmissionEntity>,
    selectedTheme: EssayThemeEntity?,
    essayBeingRewritten: EssaySubmissionEntity?,
    latestCorrectedEssay: EssaySubmissionEntity?,
    latestScannedEssay: EssaySubmissionEntity? = null,
    latestGeneratedTheme: EssayThemeEntity? = null,
    isAiBusy: Boolean,
    onSelectThemeForWriting: (EssayThemeEntity, Boolean) -> Unit,
    onGenerateThemeWithAi: (String, String, String, String) -> Unit,
    onSubmitEssayForAi: (EssayThemeEntity?, String, String, Int, Boolean, EssaySubmissionEntity?) -> Unit,
    onSelectEssayToRewrite: (EssaySubmissionEntity) -> Unit,
    onScanHandwrittenEssay: (String?, EssayThemeEntity?, String, String, String, String) -> Unit = { _, _, _, _, _, _ -> }
) {
    val subTabs = listOf(
        "Temas",
        "Redigir",
        "Reescrita",
        "Simulado",
        "Scanner"
    )

    var selectedCategory by remember { mutableStateOf("Educação") }
    var showAllCategories by remember { mutableStateOf(false) }
    var selectedExamType by remember { mutableStateOf("ENEM") }
    var selectedDifficultyStyle by remember { mutableStateOf("Padrão INEP") }
    var customFocusText by remember { mutableStateOf("") }
    var showGeneratorOptions by remember { mutableStateOf(false) }
    var showGeneratedThemeDetails by remember { mutableStateOf(false) }
    var expandedThemeId by remember { mutableIntStateOf(themes.firstOrNull()?.id ?: 1) }
    var simuladoSubMode by remember { mutableIntStateOf(0) } // 0 = Questões de Gramática ENEM, 1 = Redação Cronometrada
    var localRealtimeTheme by remember {
        mutableStateOf(
            com.example.network.GeminiClient.generateInstantThemePreview(
                category = "Educação",
                examType = "ENEM",
                customFocus = "",
                difficultyStyle = "Padrão INEP"
            )
        )
    }

    LaunchedEffect(latestGeneratedTheme?.id) {
        if (latestGeneratedTheme != null) {
            selectedCategory = latestGeneratedTheme.category
            expandedThemeId = latestGeneratedTheme.id
            localRealtimeTheme = latestGeneratedTheme
        }
    }

    val quickSuggestionsByAxis = remember(selectedCategory) {
        when (selectedCategory) {
            "Educação" -> listOf("IA nas escolas públicas", "Evasão no ensino médio", "Inclusão neurodivergente")
            "Saúde" -> listOf("Saúde mental juvenil", "Filas no SUS", "Desinformação médica")
            "Tecnologia" -> listOf("Algoritmos e polarização", "Deepfakes e democracia", "Vício em telas")
            "Meio Ambiente" -> listOf("Eventos climáticos extremos", "Racismo ambiental", "Lixo eletrônico")
            "Segurança Pública" -> listOf("Cultura de paz nas escolas", "Golpes cibernéticos", "Ressocialização")
            "Cultura" -> listOf("Patrimônio imaterial", "Acesso ao cinema no interior", "Cultura periférica")
            "Cidadania" -> listOf("Sub-registro civil", "Letramento digital idosos", "Acessibilidade urbana")
            "Trabalho" -> listOf("Uberização do trabalho", "Síndrome de Burnout", "Jovem aprendiz")
            else -> listOf("Políticas públicas em $selectedCategory", "Desigualdade regional em $selectedCategory", "Inovação social em $selectedCategory")
        }
    }

    val activeTheme = selectedTheme ?: themes.firstOrNull()
    var essayTitle by remember(activeTheme) {
        mutableStateOf("Reflexões sobre ${activeTheme?.category ?: "Cidadania"} no Brasil")
    }
    var essayBody by remember(activeTheme) {
        mutableStateOf(
            "Promulgada em 1988, a Constituição Cidadã assegura direitos sociais plenos a todos os brasileiros. Contudo, ao analisar a questão de ${activeTheme?.title?.lowercase() ?: "desafios sociais"}, observa-se uma lacuna entre a lei e a realidade prática, motivada pela omissão estatal e pela invisibilidade social.\n\nEm primeira análise, cabe pontuar que a insuficiência de políticas públicas agrava o problema. Sob a ótica de Gilberto Dimenstein em 'Cidadãos de Papel', muitos preceitos constitucionais permanecem restritos ao papel. Por conseguinte, a população vulnerável sofre com a precarização dos serviços essenciais.\n\nOutrossim, a falta de debate crítico nas escolas e na mídia perpetua estigmas históricos. Segundo Hannah Arendt, a banalização das injustiças impede o avanço democrático.\n\nPortanto, cabe ao Governo Federal, em parceria com o Ministério da Educação — órgãos responsáveis pela gestão pública e formação cidadã —, ampliar investimentos estruturais e promover campanhas pedagógicas permanentes, por meio de verbas orçamentárias e debates nas escolas, a fim de superar esse entrave e efetivar a Constituição de 1988."
        )
    }

    // Timer state for Simulados
    var simuladoSeconds by remember { mutableIntStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning) {
            delay(1000L)
            simuladoSeconds += 1
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        PrimaryScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            subTabs.forEachIndexed { idx, title ->
                Tab(
                    selected = selectedTab == idx,
                    onClick = { onSelectTab(idx) },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 1,
                            softWrap = false
                        )
                    },
                    modifier = Modifier.testTag("practice_tab_$idx")
                )
            }
        }

        when (selectedTab) {
            // TAB 0: MÓDULO DE TEMAS (ORGANIZADO E LIMPO)
            0 -> {
                val filteredThemes = themes.filter { it.category == selectedCategory }
                val visibleCategories = if (showAllCategories) {
                    InitialSeedData.themeCategories
                } else {
                    InitialSeedData.themeCategories.take(6)
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("themes_screen_list"),
                    contentPadding = PaddingValues(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "Eixos Temáticos",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "Escolha um eixo temático e gere propostas inéditas por IA em tempo real.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            visibleCategories.forEach { cat ->
                                FilterChip(
                                    selected = selectedCategory == cat,
                                    onClick = {
                                        selectedCategory = cat
                                        localRealtimeTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                            category = cat,
                                            examType = selectedExamType,
                                            customFocus = customFocusText,
                                            difficultyStyle = selectedDifficultyStyle
                                        )
                                    },
                                    label = { Text(cat) },
                                    modifier = Modifier.testTag("theme_category_$cat")
                                )
                            }
                            AssistChip(
                                onClick = { showAllCategories = !showAllCategories },
                                label = { Text(if (showAllCategories) "Menos eixos" else "+${InitialSeedData.themeCategories.size - 6} eixos") }
                            )
                        }
                    }

                    // GERADOR DE TEMA POR IA EM TEMPO REAL NA PÁGINA DOS EIXOS
                    item {
                        val effectiveGeneratedTheme = latestGeneratedTheme ?: localRealtimeTheme
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.65f),
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .testTag("realtime_ai_theme_generator_card"),
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                                                text = "Gerador de Tema por IA em Tempo Real",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Eixo ativo: $selectedCategory • Banca $selectedExamType",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                    }
                                    TextButton(
                                        onClick = { showGeneratorOptions = !showGeneratorOptions },
                                        modifier = Modifier.testTag("toggle_ai_generator_options")
                                    ) {
                                        Text(if (showGeneratorOptions) "Ocultar" else "Banca")
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // PREVIEW EM TEMPO REAL DO TEMA GERADO PELA IA
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.45f),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .testTag("realtime_generated_theme_preview"),
                                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.28f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "TEMA EM TEMPO REAL • ${effectiveGeneratedTheme.category.uppercase()} (${effectiveGeneratedTheme.examType})",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.secondary,
                                                fontWeight = FontWeight.Bold
                                            )
                                            TextButton(
                                                onClick = { showGeneratedThemeDetails = !showGeneratedThemeDetails }
                                            ) {
                                                Text(
                                                    text = if (showGeneratedThemeDetails) "Recolher" else "Ver apoio",
                                                    style = MaterialTheme.typography.labelSmall
                                                )
                                            }
                                        }
                                        Text(
                                            text = effectiveGeneratedTheme.title,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        AnimatedVisibility(visible = showGeneratedThemeDetails) {
                                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                                ThemeSectionBox("Textos Motivadores", effectiveGeneratedTheme.motivatingText)
                                                Spacer(modifier = Modifier.height(6.dp))
                                                ThemeSectionBox("Dados Estatísticos Verificados", effectiveGeneratedTheme.statisticalData)
                                                Spacer(modifier = Modifier.height(6.dp))
                                                ThemeSectionBox("Sugestões de Repertório", effectiveGeneratedTheme.repertoireSuggestions)
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Button(
                                                onClick = { onSelectThemeForWriting(effectiveGeneratedTheme, false) },
                                                shape = RoundedCornerShape(10.dp),
                                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .testTag("write_generated_theme_button")
                                            ) {
                                                Icon(Icons.Default.EditNote, contentDescription = null, modifier = Modifier.size(16.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "Redigir Tema",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    maxLines = 1,
                                                    softWrap = false
                                                )
                                            }
                                            OutlinedButton(
                                                onClick = { onSelectThemeForWriting(effectiveGeneratedTheme, true) },
                                                shape = RoundedCornerShape(10.dp),
                                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                                                modifier = Modifier.weight(1f)
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

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = customFocusText,
                                    onValueChange = {
                                        customFocusText = it
                                        localRealtimeTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                            category = selectedCategory,
                                            examType = selectedExamType,
                                            customFocus = it,
                                            difficultyStyle = selectedDifficultyStyle
                                        )
                                    },
                                    label = { Text("Recorte em tempo real ($selectedCategory)") },
                                    placeholder = { Text("Ex.: ${quickSuggestionsByAxis.firstOrNull() ?: "Desafios contemporâneos"}") },
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("ai_theme_custom_focus_input")
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    quickSuggestionsByAxis.forEachIndexed { idx, suggestion ->
                                        AssistChip(
                                            onClick = {
                                                customFocusText = suggestion
                                                localRealtimeTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                                    category = selectedCategory,
                                                    examType = selectedExamType,
                                                    customFocus = suggestion,
                                                    difficultyStyle = selectedDifficultyStyle
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = suggestion,
                                                    style = MaterialTheme.typography.labelSmall
                                                )
                                            },
                                            modifier = Modifier.testTag("ai_quick_suggestion_$idx")
                                        )
                                    }
                                }

                                AnimatedVisibility(visible = showGeneratorOptions) {
                                    Column(modifier = Modifier.padding(top = 8.dp)) {
                                        FlowRow(
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            listOf("ENEM", "FUVEST", "UNICAMP", "Concursos").forEach { exam ->
                                                FilterChip(
                                                    selected = selectedExamType == exam,
                                                    onClick = {
                                                        selectedExamType = exam
                                                        localRealtimeTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                                            category = selectedCategory,
                                                            examType = exam,
                                                            customFocus = customFocusText,
                                                            difficultyStyle = selectedDifficultyStyle
                                                        )
                                                    },
                                                    label = { Text(exam) }
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        FlowRow(
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            listOf("Padrão INEP", "Atualidades 2026", "Desafio Nota 1000").forEach { style ->
                                                FilterChip(
                                                    selected = selectedDifficultyStyle == style,
                                                    onClick = {
                                                        selectedDifficultyStyle = style
                                                        localRealtimeTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                                            category = selectedCategory,
                                                            examType = selectedExamType,
                                                            customFocus = customFocusText,
                                                            difficultyStyle = style
                                                        )
                                                    },
                                                    label = { Text(style) }
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        localRealtimeTheme = com.example.network.GeminiClient.generateInstantThemePreview(
                                            category = selectedCategory,
                                            examType = selectedExamType,
                                            customFocus = customFocusText,
                                            difficultyStyle = selectedDifficultyStyle
                                        )
                                        onGenerateThemeWithAi(
                                            selectedCategory,
                                            selectedExamType,
                                            customFocusText,
                                            selectedDifficultyStyle
                                        )
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("generate_theme_ai_button")
                                ) {
                                    if (isAiBusy) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(16.dp),
                                            strokeWidth = 2.dp,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Gerando tema inédito por IA...")
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.AutoAwesome,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Gerar Tema Imediatamente por IA ($selectedCategory)")
                                    }
                                }
                            }
                        }
                    }

                    items(filteredThemes, key = { it.id }) { theme ->
                        val isExpanded = expandedThemeId == theme.id
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandedThemeId = if (isExpanded) -1 else theme.id }
                                .testTag("theme_card_${theme.id}")
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
                                            text = "${theme.category} • ${theme.examType}",
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (theme.isExclusivePremium) {
                                            Surface(
                                                color = MaterialTheme.colorScheme.secondaryContainer,
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text(
                                                    text = "Exclusivo Premium",
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Surface(
                                            color = ClickableIconBlueBg,
                                            shape = RoundedCornerShape(8.dp),
                                            border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                                    contentDescription = "Expandir detalhes do tema",
                                                    tint = ClickableIconBlueTint,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = theme.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                AnimatedVisibility(visible = isExpanded) {
                                    Column(modifier = Modifier.padding(top = 12.dp)) {
                                        ThemeSectionBox("Textos Motivadores", theme.motivatingText)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        ThemeSectionBox("Proposta de Redação", theme.proposalPrompt)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        ThemeSectionBox("Dados Estatísticos Verificados", theme.statisticalData)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        ThemeSectionBox("Sugestões de Repertório Sociocultural", theme.repertoireSuggestions)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        ThemeSectionBox("Perguntas Orientadoras (Projeto de Texto)", theme.guidingQuestions)
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { onSelectThemeForWriting(theme, false) },
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("write_theme_button_${theme.id}")
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
                                        onClick = { onSelectThemeForWriting(theme, true) },
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("simulado_theme_button_${theme.id}")
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

            // TAB 1: EDITOR DE REDAÇÃO & CORREÇÃO COM IA
            1 -> {
                val wordsCount = essayBody.trim().split(Regex("\\s+")).filter { it.isNotBlank() }.size
                val estimatedLines = (essayBody.length / 65).coerceAtLeast(1)
                val reportEssay = latestCorrectedEssay ?: essays.firstOrNull()

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "TEMA SELECIONADO (${activeTheme?.category ?: "Geral"} • ${activeTheme?.examType ?: "ENEM"})",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = activeTheme?.title ?: "Escolha um tema na aba Temas",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = essayTitle,
                            onValueChange = { essayTitle = it },
                            label = { Text("Título da Redação (Opcional no ENEM)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("essay_title_input"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Inserção Rápida de Conectivos, Tópico Frasal e Citações:",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                "Em primeira análise, é imperioso destacar que " to "Tópico Frasal D1",
                                "Outrossim, cabe pontuar que " to "Tópico Frasal D2",
                                "Sob a ótica de Byung-Chul Han em 'Sociedade do Cansaço', " to "Filósofo: Han",
                                "Segundo Gilberto Dimenstein em 'Cidadãos de Papel', " to "Sociólogo: Dimenstein",
                                "Na canção 'Construção', de Chico Buarque, " to "Música: Construção",
                                "No documentário 'O Dilema das Redes', " to "Filme/Doc",
                                "Por conseguinte, " to "Consequência",
                                "Portanto, cabe ao Ministério " to "Conclusão C5"
                            ).forEach { (snippet, chipLabel) ->
                                AssistChip(
                                    onClick = { essayBody = "$essayBody $snippet" },
                                    label = { Text(chipLabel) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = essayBody,
                            onValueChange = { essayBody = it },
                            label = { Text("Texto Dissertativo-Argumentativo ($wordsCount palavras • ~$estimatedLines linhas)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("essay_body_input"),
                            minLines = 10
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                onSubmitEssayForAi(activeTheme, essayTitle, essayBody, 45, false, null)
                            },
                            enabled = !isAiBusy && essayBody.isNotBlank(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("submit_essay_ai_button")
                        ) {
                            if (isAiBusy) {
                                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                            } else {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Corrigir Redação com IA (Nota 0 a 1000)", fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(id = R.string.security_warning_prototype),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (reportEssay != null) {
                        item {
                            AiCorrectionReportCard(
                                essay = reportEssay,
                                onRewriteClick = { onSelectEssayToRewrite(reportEssay) }
                            )
                        }
                    }
                }
            }

            // TAB 2: ÁREA DE REESCRITA & COMPARAÇÃO DE VERSÕES
            2 -> {
                val targetEssay = essayBeingRewritten ?: essays.firstOrNull()
                var rewriteBody by remember(targetEssay) {
                    mutableStateOf(targetEssay?.content ?: "")
                }
                val rewrittenVersions = essays.filter { it.parentEssayId != null }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            text = "Área de Reescrita e Comparação de Versões",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Compare versões corrigidas, aplique as melhorias da IA e acompanhe o salto de pontuação.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (rewrittenVersions.isNotEmpty()) {
                        item {
                            val rewritten = rewrittenVersions.first()
                            val original = essays.find { it.id == rewritten.parentEssayId }
                            if (original != null) {
                                ElevatedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("version_comparison_card")
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    Icons.AutoMirrored.Filled.CompareArrows,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = "Evolução Comprovada no Texto",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Surface(
                                                color = ScoreExcellent.copy(alpha = 0.18f),
                                                shape = CircleShape
                                            ) {
                                                Text(
                                                    text = "${original.totalScore} ➔ ${rewritten.totalScore} (+${rewritten.totalScore - original.totalScore} pts)",
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                                    style = MaterialTheme.typography.labelMedium,
                                                    color = ScoreExcellent,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = "Versão 1 (${original.totalScore} pts • ${original.wordCount} palavras):",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = original.content.take(230) + "...",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Versão 2 Reescrita (${rewritten.totalScore} pts • ${rewritten.wordCount} palavras):",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = ScoreExcellent,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = rewritten.content.take(260) + "...",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (targetEssay != null) {
                        item {
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Reescrevendo: ${targetEssay.title} (Versão ${targetEssay.versionNumber} • Nota Atual: ${targetEssay.totalScore})",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Pontos a melhorar nesta reescrita: ${targetEssay.improvements.replace("|", " • ")}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedTextField(
                                        value = rewriteBody,
                                        onValueChange = { rewriteBody = it },
                                        label = { Text("Edite e aprimore sua redação para gerar a Versão ${targetEssay.versionNumber + 1}") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("rewrite_editor_input"),
                                        minLines = 8
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = {
                                            onSubmitEssayForAi(
                                                activeTheme,
                                                "${targetEssay.title} (Reescrita V${targetEssay.versionNumber + 1})",
                                                rewriteBody,
                                                35,
                                                false,
                                                targetEssay
                                            )
                                        },
                                        enabled = !isAiBusy && rewriteBody.isNotBlank(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("submit_rewrite_button")
                                    ) {
                                        Icon(Icons.Default.AutoFixHigh, contentDescription = null)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Avaliar Nova Versão Reescrita com IA")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // TAB 3: SIMULADOS (GRAMÁTICA ENEM & CRONÔMETRO DE REDAÇÃO)
            3 -> {
                val mins = simuladoSeconds / 60
                val secs = simuladoSeconds % 60
                val formattedTime = "%02d:%02d".format(mins, secs)

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = simuladoSubMode == 0,
                                onClick = { simuladoSubMode = 0 },
                                label = { Text("Questões Gramática ENEM", fontWeight = FontWeight.Bold) },
                                leadingIcon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp)) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("tab_simulado_gramatica")
                            )
                            FilterChip(
                                selected = simuladoSubMode == 1,
                                onClick = { simuladoSubMode = 1 },
                                label = { Text("Redação Cronometrada", fontWeight = FontWeight.Bold) },
                                leadingIcon = { Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(16.dp)) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("tab_simulado_redacao")
                            )
                        }
                    }

                    if (simuladoSubMode == 0) {
                        item {
                            EnemGrammarSimuladoSection()
                        }
                    } else {
                        item {
                            ElevatedCard(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.elevatedCardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "SIMULADO OFICIAL CRONOMETRADO ($selectedExamType)",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = formattedTime,
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.testTag("simulado_timer_display")
                                    )
                                    Text(
                                        text = "Tempo ideal recomendado: 45 a 60 minutos",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Button(
                                            onClick = { isTimerRunning = !isTimerRunning },
                                            modifier = Modifier.testTag("simulado_start_pause_button")
                                        ) {
                                            Icon(
                                                imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                                contentDescription = null
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(if (isTimerRunning) "Pausar" else "Iniciar Cronômetro")
                                        }
                                        OutlinedButton(
                                            onClick = {
                                                isTimerRunning = false
                                                simuladoSeconds = 0
                                            }
                                        ) {
                                            Icon(Icons.Default.RestartAlt, contentDescription = null)
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Zerar")
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Modalidade do Simulado:",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                FilledTonalButton(
                                    onClick = {
                                        val randomCategory = InitialSeedData.themeCategories.random()
                                        onGenerateThemeWithAi(randomCategory, selectedExamType, "", "Padrão INEP")
                                    },
                                    enabled = !isAiBusy
                                ) {
                                    Icon(Icons.Default.Shuffle, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Sortear Tema IA")
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("ENEM", "FUVEST / UNICAMP", "Concursos").forEach { mode ->
                                    FilterChip(
                                        selected = selectedExamType == mode,
                                        onClick = { selectedExamType = mode },
                                        label = { Text(mode) }
                                    )
                                }
                            }
                        }

                        item {
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Tema do Simulado: ${activeTheme?.title ?: ""}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = activeTheme?.motivatingText ?: "",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    OutlinedTextField(
                                        value = essayBody,
                                        onValueChange = { essayBody = it },
                                        label = { Text("Folha Oficial do Simulado") },
                                        modifier = Modifier.fillMaxWidth(),
                                        minLines = 8
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Button(
                                        onClick = {
                                            isTimerRunning = false
                                            val spent = (simuladoSeconds / 60).coerceAtLeast(35)
                                            onSubmitEssayForAi(
                                                activeTheme,
                                                "Simulado $selectedExamType: ${activeTheme?.category ?: "Geral"}",
                                                essayBody,
                                                spent,
                                                true,
                                                null
                                            )
                                            onSelectTab(1)
                                        },
                                        enabled = !isAiBusy && essayBody.isNotBlank(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("finish_simulado_button")
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Finalizar Simulado e Corrigir Automaticamente")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // TAB 4: SCANNER ÓPTICO DE REDAÇÃO NO PAPEL (FOTO -> NOTA ENEM C1-C5 + ONDE MELHORAR)
            4 -> {
                EssayPaperScannerSection(
                    themes = themes,
                    selectedTheme = activeTheme,
                    latestScannedEssay = latestScannedEssay,
                    scannedHistory = essays,
                    isAiBusy = isAiBusy,
                    onScanHandwrittenEssay = onScanHandwrittenEssay,
                    onOpenInRewrite = { scanned ->
                        onSelectEssayToRewrite(scanned)
                    }
                )
            }
        }
    }
}

@Composable
private fun ThemeSectionBox(title: String, body: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = body, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun AiCorrectionReportCard(
    essay: EssaySubmissionEntity,
    onRewriteClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("ai_correction_report_card"),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "LAUDO DE CORREÇÃO INTELIGENTE IA",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = essay.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${essay.wordCount} palavras • Versão ${essay.versionNumber} • ${essay.status}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    color = ScoreExcellent.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${essay.totalScore}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = ScoreExcellent,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "NOTA ENEM",
                            style = MaterialTheme.typography.labelSmall,
                            color = ScoreExcellent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Desempenho por Competência (0 a 200 pts)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            CompetencyScoreBar("Competência 1 • Norma Padrão e Gramática", essay.c1Score)
            CompetencyScoreBar("Competência 2 • Tema e Repertório Sociocultural", essay.c2Score)
            CompetencyScoreBar("Competência 3 • Projeto de Texto e Argumentação", essay.c3Score)
            CompetencyScoreBar("Competência 4 • Coesão e Conectivos", essay.c4Score)
            CompetencyScoreBar("Competência 5 • Proposta de Intervenção (5 Elementos)", essay.c5Score)

            Spacer(modifier = Modifier.height(12.dp))
            ReportBulletSection("Pontos Fortes", essay.strengths.split("|"), ScoreExcellent)
            Spacer(modifier = Modifier.height(8.dp))
            ReportBulletSection("Erros e Desvios Encontrados", essay.errorsFound.split("|"), ScoreNeedsWork)
            Spacer(modifier = Modifier.height(8.dp))
            ReportBulletSection("Pontos Fracos / Lacunas", essay.weaknesses.split("|"), ScoreWarning)
            Spacer(modifier = Modifier.height(8.dp))
            ReportBulletSection("Sugestões Práticas de Melhoria", essay.improvements.split("|"), MaterialTheme.colorScheme.primary)

            if (essay.highlightedExcerpts.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Trechos Destacados no Seu Texto",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                essay.highlightedExcerpts.split("|").filter { it.isNotBlank() }.forEach { raw ->
                    val parts = raw.split("::")
                    val snippet = parts.getOrNull(0) ?: raw
                    val tag = parts.getOrNull(1) ?: "DESTAQUE"
                    val comment = parts.getOrNull(2) ?: ""
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "[$tag] \"$snippet\"",
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (comment.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "↳ $comment",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            ReportBulletSection("Plano Personalizado de Evolução", essay.evolutionPlan.split("|"), MaterialTheme.colorScheme.tertiary)

            if (essay.teacherFeedback.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Parecer da Mentoria / Prof. João Batista:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = essay.teacherFeedback,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = onRewriteClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.EditNote, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Levar para Área de Reescrita e Subir Nota")
            }
        }
    }
}

@Composable
private fun CompetencyScoreBar(title: String, score: Int) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.bodySmall)
            Text(
                text = "$score / 200",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        LinearProgressIndicator(
            progress = { (score / 200f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun ReportBulletSection(
    title: String,
    items: List<String>,
    accentColor: androidx.compose.ui.graphics.Color
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = accentColor,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        items.filter { it.isNotBlank() }.forEach { line ->
            Text(
                text = "• $line",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}
