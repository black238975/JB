package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.CertificateInfo
import com.example.data.EssaySubmissionEntity
import com.example.data.InitialSeedData
import com.example.data.MetalRankTier
import com.example.data.ProgressHistoryEntity
import com.example.data.RepertoireAndGuidesCatalog
import com.example.data.StudyTaskEntity
import com.example.data.UserProfileEntity
import com.example.ui.theme.AmberSecondaryDark
import com.example.ui.theme.ClickableIconBlueBg
import com.example.ui.theme.ClickableIconBlueBorder
import com.example.ui.theme.ClickableIconBlueTint
import com.example.ui.theme.GoldBadge
import com.example.ui.theme.ScoreExcellent
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PerformanceAndProfileScreen(
    user: UserProfileEntity,
    essays: List<EssaySubmissionEntity>,
    progressHistory: List<ProgressHistoryEntity>,
    studyTasks: List<StudyTaskEntity>,
    onOpenAccountDialog: () -> Unit,
    onInspectEssay: (EssaySubmissionEntity) -> Unit,
    onRecordHistoricalScore: (String, String, String, Int, Int, Int, Int, Int, String) -> Unit,
    onDeleteEssay: (Int) -> Unit,
    onAdvanceMetalRankXp: () -> Unit = {}
) {
    val chronologicalEssays = remember(essays) { essays.sortedBy { it.timestamp } }
    val chartScores = remember(progressHistory, chronologicalEssays) {
        if (progressHistory.isNotEmpty()) {
            progressHistory.map { it.totalScore }
        } else {
            chronologicalEssays.map { it.totalScore }
        }
    }
    val chartLabels = remember(progressHistory, chronologicalEssays) {
        if (progressHistory.isNotEmpty()) {
            progressHistory.map { it.dateLabel }
        } else {
            chronologicalEssays.mapIndexed { idx, _ -> "Red. ${idx + 1}" }
        }
    }

    val avgTotal = remember(essays) {
        if (essays.isNotEmpty()) essays.map { it.totalScore }.average().toInt() else 0
    }
    val highestScore = remember(essays) {
        essays.maxOfOrNull { it.totalScore } ?: 0
    }
    val avgC1 = remember(essays) {
        if (essays.isNotEmpty()) essays.map { it.c1Score }.average().toInt() else 0
    }
    val avgC2 = remember(essays) {
        if (essays.isNotEmpty()) essays.map { it.c2Score }.average().toInt() else 0
    }
    val avgC3 = remember(essays) {
        if (essays.isNotEmpty()) essays.map { it.c3Score }.average().toInt() else 0
    }
    val avgC4 = remember(essays) {
        if (essays.isNotEmpty()) essays.map { it.c4Score }.average().toInt() else 0
    }
    val avgC5 = remember(essays) {
        if (essays.isNotEmpty()) essays.map { it.c5Score }.average().toInt() else 0
    }

    val currentMetalTier = remember(user.level, user.xp) {
        RepertoireAndGuidesCatalog.getMetalTierForUser(user.level, user.xp)
    }
    val metalColorPrimary = Color(currentMetalTier.primaryColorHex)
    val metalColorSecondary = Color(currentMetalTier.secondaryColorHex)

    var selectedCertificate by remember { mutableStateOf<CertificateInfo?>(null) }
    var showAddHistoryDialog by remember { mutableStateOf(false) }
    var showMetalScaleDetails by remember { mutableStateOf(false) }
    var selectedPerformanceSection by remember { mutableIntStateOf(0) } // 0 = Competências & Redações, 1 = Linha do Tempo, 2 = Conquistas & Ranking
    var chartVisualMode by remember { mutableIntStateOf(0) } // 0 = Curva Animada, 1 = Barras Animadas
    var selectedPointIndex by remember(chartScores.size) {
        mutableIntStateOf((chartScores.size - 1).coerceAtLeast(0))
    }

    val chartAnimProgress = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(chartScores, chartVisualMode, currentMetalTier.tierIndex) {
        chartAnimProgress.snapTo(0f)
        chartAnimProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1050, easing = FastOutSlowInEasing)
        )
    }

    if (selectedCertificate != null) {
        val cert = selectedCertificate!!
        AlertDialog(
            onDismissRequest = { selectedCertificate = null },
            icon = {
                Icon(
                    imageVector = Icons.Default.WorkspacePremium,
                    contentDescription = null,
                    tint = GoldBadge,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = cert.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Certificamos que ${user.name} (Patente: ${currentMetalTier.metalName}) concluiu com êxito os requisitos acadêmicos de evolução textual na plataforma Mestre da Redação.")
                    Text(
                        text = "• Carga Horária: ${cert.hours} horas\n• Critério: ${cert.requirementDescription}\n• Média Atual: $avgTotal/1000 pts",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedCertificate = null }) {
                    Text("Fechar Certificado")
                }
            }
        )
    }

    if (showAddHistoryDialog) {
        RecordHistoricalScoreDialog(
            onDismiss = { showAddHistoryDialog = false },
            onSave = { title, category, examType, c1, c2, c3, c4, c5, note ->
                onRecordHistoricalScore(title, category, examType, c1, c2, c3, c4, c5, note)
                showAddHistoryDialog = false
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("performance_screen_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Perfil do Aluno & Sistema de Patentes de Metais Nobres (Bronze -> Prata -> Ouro -> Platina -> Titânio -> Paládio -> Ródio/Diamante)
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("metal_rank_profile_card"),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(metalColorPrimary, metalColorSecondary)
                                    )
                                )
                                .border(2.dp, Color.White.copy(alpha = 0.7f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = user.avatarInitials,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Nv.${user.level}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = user.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Perfil Verificado",
                                    tint = metalColorPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Surface(
                                color = metalColorPrimary.copy(alpha = 0.18f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.padding(vertical = 3.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Shield,
                                        contentDescription = null,
                                        tint = metalColorPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Patente: ${currentMetalTier.metalName} • ${currentMetalTier.titleBadge}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = metalColorPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "Meta: ${user.targetScore} pts • Recorde: $highestScore pts",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        FilledTonalButton(
                            onClick = onOpenAccountDialog,
                            modifier = Modifier.testTag("edit_profile_button")
                        ) {
                            Icon(Icons.Default.ManageAccounts, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Conta")
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    // Barra Animada de Progresso Metálico
                    val tierSpan = (currentMetalTier.nextTierXp - currentMetalTier.minXp).coerceAtLeast(100)
                    val tierProgressRaw = ((user.xp - currentMetalTier.minXp).toFloat() / tierSpan.toFloat()).coerceIn(0.08f, 1f)
                    val animatedXpProgress by animateFloatAsState(
                        targetValue = tierProgressRaw,
                        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
                        label = "xp_bar_anim"
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nível ${user.level} • ${currentMetalTier.metalName}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${user.xp} / ${currentMetalTier.nextTierXp} XP",
                            style = MaterialTheme.typography.labelMedium,
                            color = metalColorPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { animatedXpProgress },
                        color = metalColorPrimary,
                        trackColor = metalColorPrimary.copy(alpha = 0.2f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Bônus da Patente: ${currentMetalTier.unlockedPerks}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { showMetalScaleDetails = !showMetalScaleDetails }) {
                            Text(if (showMetalScaleDetails) "Ocultar Escala de Metais" else "Ver Escala de Metais Nobres")
                        }
                        OutlinedButton(
                            onClick = onAdvanceMetalRankXp,
                            modifier = Modifier.testTag("advance_metal_rank_button")
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("+350 XP (Treino)")
                        }
                    }

                    if (showMetalScaleDetails) {
                        Spacer(modifier = Modifier.height(6.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            RepertoireAndGuidesCatalog.metalRankTiers.forEach { tier ->
                                val isCurrent = tier.tierIndex == currentMetalTier.tierIndex
                                val isUnlocked = user.xp >= tier.minXp || user.level >= tier.minLevel
                                val chipColor = Color(tier.primaryColorHex)
                                Surface(
                                    color = if (isCurrent) chipColor.copy(alpha = 0.22f)
                                    else if (isUnlocked) chipColor.copy(alpha = 0.10f)
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.border(
                                        width = if (isCurrent) 1.5.dp else 0.5.dp,
                                        color = if (isCurrent) chipColor else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                ) {
                                    Text(
                                        text = "${tier.tierIndex}. ${tier.metalName} (Nv.${tier.minLevel}+)",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isUnlocked) chipColor else MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Modelo de Gráfico Animado de Evolução (Curva Animada / Barras Animadas + Cor do Metal Atual)
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("animated_evolution_chart_card"),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                    contentDescription = null,
                                    tint = metalColorPrimary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Gráfico Animado de Evolução (${currentMetalTier.metalName})",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Média: $avgTotal pts • Meta: ${user.targetScore} pts • Recorde: $highestScore pts",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        FilledTonalButton(
                            onClick = { showAddHistoryDialog = true },
                            modifier = Modifier.testTag("add_historical_score_button")
                        ) {
                            Icon(Icons.Default.AddChart, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Registrar Nota")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = chartVisualMode == 0,
                                onClick = { chartVisualMode = 0 },
                                label = { Text("Curva Animada") },
                                modifier = Modifier.testTag("chart_mode_curve")
                            )
                            FilterChip(
                                selected = chartVisualMode == 1,
                                onClick = { chartVisualMode = 1 },
                                label = { Text("Barras Metálicas") },
                                modifier = Modifier.testTag("chart_mode_bars")
                            )
                        }
                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    chartAnimProgress.snapTo(0f)
                                    chartAnimProgress.animateTo(
                                        targetValue = 1f,
                                        animationSpec = tween(durationMillis = 1050, easing = FastOutSlowInEasing)
                                    )
                                }
                            },
                            modifier = Modifier.testTag("replay_chart_animation_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Animar")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    AnimatedScoreEvolutionCanvasChart(
                        scores = chartScores,
                        labels = chartLabels,
                        targetScore = user.targetScore,
                        primaryMetalColor = metalColorPrimary,
                        secondaryMetalColor = metalColorSecondary,
                        animationProgress = chartAnimProgress.value,
                        visualMode = chartVisualMode,
                        selectedPointIndex = selectedPointIndex
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Toque em um marco para inspecionar a nota no gráfico:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val displayScores = if (chartScores.isNotEmpty()) chartScores else listOf(820, 880, 960, 920)
                        displayScores.forEachIndexed { index, score ->
                            val lbl = chartLabels.getOrNull(index) ?: "M${index + 1}"
                            FilterChip(
                                selected = selectedPointIndex == index,
                                onClick = { selectedPointIndex = index },
                                label = { Text("$lbl: $score pts") }
                            )
                        }
                    }
                }
            }
        }

        // Seletor de Seção para organizar informações sem poluição visual
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedPerformanceSection == 0,
                    onClick = { selectedPerformanceSection = 0 },
                    label = { Text("Competências & Banco") },
                    modifier = Modifier.testTag("perf_section_competencies")
                )
                FilterChip(
                    selected = selectedPerformanceSection == 1,
                    onClick = { selectedPerformanceSection = 1 },
                    label = { Text("Linha do Tempo") },
                    modifier = Modifier.testTag("perf_section_timeline")
                )
                FilterChip(
                    selected = selectedPerformanceSection == 2,
                    onClick = { selectedPerformanceSection = 2 },
                    label = { Text("Conquistas & Ranking") },
                    modifier = Modifier.testTag("perf_section_awards")
                )
            }
        }

        if (selectedPerformanceSection == 0) {
            // 3. Evolução Animada por Competência (C1 a C5)
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Domínio Médio por Competência ENEM",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        CompetencyAvgRow("C1 • Modalidade Escrita Formal", avgC1, metalColorPrimary)
                        CompetencyAvgRow("C2 • Compreensão do Tema & Repertório", avgC2, metalColorPrimary)
                        CompetencyAvgRow("C3 • Projeto de Texto & Argumentação", avgC3, metalColorPrimary)
                        CompetencyAvgRow("C4 • Coesão e Operadores Argumentativos", avgC4, metalColorPrimary)
                        CompetencyAvgRow("C5 • Proposta de Intervenção (5 Elementos)", avgC5, metalColorPrimary)
                    }
                }
            }

            // 8. Banco de Redações Armazenadas (Room EssaySubmissionEntity)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Storage,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Redações Salvas (${essays.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            items(essays, key = { it.id }) { essay ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onInspectEssay(essay) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${essay.category} • ${essay.examType} • Versão ${essay.versionNumber}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = essay.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "C1:${essay.c1Score} C2:${essay.c2Score} C3:${essay.c3Score} C4:${essay.c4Score} C5:${essay.c5Score}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${essay.totalScore} pts",
                                style = MaterialTheme.typography.titleMedium,
                                color = ScoreExcellent,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(
                                onClick = { onDeleteEssay(essay.id) },
                                modifier = Modifier.testTag("delete_essay_${essay.id}")
                            ) {
                                Surface(
                                    color = ClickableIconBlueBg,
                                    shape = RoundedCornerShape(9.dp),
                                    border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.DeleteOutline,
                                            contentDescription = "Remover redação do banco",
                                            tint = ClickableIconBlueTint,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (selectedPerformanceSection == 1) {
            // 4. Linha do Tempo de Progresso Histórico (Room ProgressHistoryEntity)
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = metalColorPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Histórico Cronológico Consolidado",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Marcos de notas, médias móveis e patente metálica",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        progressHistory.reversed().forEach { snap ->
                            val snapTier = RepertoireAndGuidesCatalog.getMetalTierForUser(
                                level = (snap.xpCumulative / 350) + 1,
                                xp = snap.xpCumulative
                            )
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${snap.dateLabel} • ${snap.category} (${snap.examType}) • Metal: ${snapTier.metalName}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(snapTier.primaryColorHex),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = snap.essayTitle,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (snap.milestoneNote.isNotBlank()) {
                                            Text(
                                                text = snap.milestoneNote,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Text(
                                            text = "Média Móvel: ${snap.rollingAverageScore} pts • Estudo: ${snap.studyMinutesCumulative} min",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${snap.totalScore} pts",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = ScoreExcellent,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // 5. Gamificação: Medalhas e Conquistas
            item {
                Text(
                    text = "Medalhas e Conquistas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    InitialSeedData.medals.forEach { medal ->
                        val unlocked = user.xp >= medal.xpRequired
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (unlocked) {
                                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f)
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                                }
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MilitaryTech,
                                    contentDescription = null,
                                    tint = if (unlocked) GoldBadge else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = medal.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = medal.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = if (unlocked) "Conquistada" else "${medal.xpRequired} XP",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (unlocked) ScoreExcellent else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // 6. Ranking de Alunos com Patentes Metálicas
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Leaderboard,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Ranking Nacional Mestre da Redação",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        InitialSeedData.studentRanking.forEach { row ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        color = if (row.rank <= 3) AmberSecondaryDark.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surfaceVariant,
                                        shape = CircleShape
                                    ) {
                                        Text(
                                            text = "#${row.rank}",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "${row.name} (${row.stateCode})",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = "${row.badge} • ${row.xp} XP",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                Text(
                                    text = "Média ${row.avgScore}",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = ScoreExcellent,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // 7. Certificados de Evolução
            item {
                Text(
                    text = "Certificados de Evolução",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    InitialSeedData.certificates.forEach { cert ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = cert.isUnlocked) { selectedCertificate = cert }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WorkspacePremium,
                                    contentDescription = null,
                                    tint = if (cert.isUnlocked) GoldBadge else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${cert.title} (${cert.hours}h)",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = cert.requirementDescription,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                if (cert.isUnlocked) {
                                    TextButton(onClick = { selectedCertificate = cert }) {
                                        Text("Emitir")
                                    }
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
private fun AnimatedScoreEvolutionCanvasChart(
    scores: List<Int>,
    labels: List<String>,
    targetScore: Int,
    primaryMetalColor: Color,
    secondaryMetalColor: Color,
    animationProgress: Float,
    visualMode: Int,
    selectedPointIndex: Int
) {
    val plotScores = if (scores.size >= 2) scores else listOf(820, 880, 920, 960)
    val pulseRadiusMultiplier = 1.25f

    val safeSelectedIdx = selectedPointIndex.coerceIn(0, plotScores.lastIndex)
    val selectedScore = plotScores[safeSelectedIdx]
    val selectedLabel = labels.getOrNull(safeSelectedIdx) ?: "Marco ${safeSelectedIdx + 1}"

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                    RoundedCornerShape(14.dp)
                )
                .padding(18.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val minScore = 600f
                val maxScore = 1000f

                // Horizontal grid lines (700, 800, 900, 1000)
                listOf(700f, 800f, 900f, 1000f).forEach { gridVal ->
                    val gy = h - ((gridVal - minScore) / (maxScore - minScore)) * h
                    drawLine(
                        color = primaryMetalColor.copy(alpha = 0.14f),
                        start = Offset(0f, gy),
                        end = Offset(w, gy),
                        strokeWidth = 2f
                    )
                }

                // Target score line
                val targetY = h - ((targetScore.coerceIn(600, 1000) - minScore) / (maxScore - minScore)) * h
                drawLine(
                    color = ScoreExcellent.copy(alpha = 0.65f),
                    start = Offset(0f, targetY),
                    end = Offset(w * animationProgress, targetY),
                    strokeWidth = 3.5f
                )

                val stepX = if (plotScores.size > 1) w / (plotScores.size - 1) else w

                if (visualMode == 0) {
                    // CURVA ANIMADA + SOMBRA DE ÁREA METÁLICA
                    val linePath = Path()
                    val fillPath = Path()
                    val points = plotScores.mapIndexed { idx, sc ->
                        val x = idx * stepX
                        val norm = ((sc.coerceIn(600, 1000) - minScore) / (maxScore - minScore)) * animationProgress
                        val y = h - (norm * h)
                        Offset(x, y)
                    }

                    points.forEachIndexed { idx, pt ->
                        if (idx == 0) {
                            linePath.moveTo(pt.x, pt.y)
                            fillPath.moveTo(pt.x, h)
                            fillPath.lineTo(pt.x, pt.y)
                        } else {
                            val prev = points[idx - 1]
                            val ctrlX = (prev.x + pt.x) / 2f
                            linePath.cubicTo(ctrlX, prev.y, ctrlX, pt.y, pt.x, pt.y)
                            fillPath.cubicTo(ctrlX, prev.y, ctrlX, pt.y, pt.x, pt.y)
                        }
                    }
                    if (points.isNotEmpty()) {
                        fillPath.lineTo(points.last().x, h)
                        fillPath.close()
                        drawPath(
                            path = fillPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    primaryMetalColor.copy(alpha = 0.36f * animationProgress),
                                    secondaryMetalColor.copy(alpha = 0.03f)
                                )
                            )
                        )
                    }

                    drawPath(
                        path = linePath,
                        brush = Brush.horizontalGradient(
                            colors = listOf(secondaryMetalColor, primaryMetalColor)
                        ),
                        style = Stroke(width = 7f, cap = StrokeCap.Round)
                    )

                    points.forEachIndexed { idx, pt ->
                        val isSelected = idx == safeSelectedIdx
                        if (isSelected) {
                            drawCircle(
                                color = primaryMetalColor.copy(alpha = 0.30f),
                                radius = 16f * pulseRadiusMultiplier,
                                center = pt
                            )
                        }
                        drawCircle(
                            color = if (isSelected) ScoreExcellent else primaryMetalColor,
                            radius = if (isSelected) 12f else 9f,
                            center = pt
                        )
                    }
                } else {
                    // BARRAS METÁLICAS ANIMADAS
                    val barWidth = (w / (plotScores.size * 2.2f)).coerceIn(18f, 48f)
                    plotScores.forEachIndexed { idx, sc ->
                        val xCenter = idx * stepX
                        val norm = ((sc.coerceIn(600, 1000) - minScore) / (maxScore - minScore)) * animationProgress
                        val barHeight = (norm * h).coerceAtLeast(8f)
                        val topY = h - barHeight
                        val isSelected = idx == safeSelectedIdx
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                colors = if (isSelected) {
                                    listOf(ScoreExcellent, primaryMetalColor)
                                } else {
                                    listOf(primaryMetalColor, secondaryMetalColor)
                                }
                            ),
                            topLeft = Offset((xCenter - barWidth / 2f).coerceIn(0f, w - barWidth), topY),
                            size = Size(barWidth, barHeight),
                            cornerRadius = CornerRadius(10f, 10f)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Marco Selecionado: $selectedLabel — Nota: $selectedScore / 1000 pts",
                style = MaterialTheme.typography.labelMedium,
                color = primaryMetalColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Linha Verde = Meta (${targetScore} pts)",
                style = MaterialTheme.typography.labelSmall,
                color = ScoreExcellent,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun RecordHistoricalScoreDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String, Int, Int, Int, Int, Int, String) -> Unit
) {
    var title by remember { mutableStateOf("Simulado ENEM — Desafios da Saúde Pública") }
    var category by remember { mutableStateOf("Saúde") }
    var examType by remember { mutableStateOf("ENEM") }
    var c1 by remember { mutableIntStateOf(180) }
    var c2 by remember { mutableIntStateOf(200) }
    var c3 by remember { mutableIntStateOf(180) }
    var c4 by remember { mutableIntStateOf(180) }
    var c5 by remember { mutableIntStateOf(200) }
    var note by remember { mutableStateOf("Evolução consistente no uso de conectivos interparágrafos.") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Registrar Nota e Redação no Histórico",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título ou Tema da Redação") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("history_title_input"),
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Eixo Temático") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = examType,
                        onValueChange = { examType = it },
                        label = { Text("Banca") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
                Text(
                    text = "Notas por Competência (Total: ${c1 + c2 + c3 + c4 + c5} / 1000 pts)",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(
                        "C1" to c1,
                        "C2" to c2,
                        "C3" to c3,
                        "C4" to c4,
                        "C5" to c5
                    ).forEachIndexed { idx, pair ->
                        Surface(
                            modifier = Modifier.clickable {
                                val next = if (pair.second >= 200) 120 else pair.second + 20
                                when (idx) {
                                    0 -> c1 = next
                                    1 -> c2 = next
                                    2 -> c3 = next
                                    3 -> c4 = next
                                    4 -> c5 = next
                                }
                            },
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "${pair.first}:${pair.second}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Observação de Evolução") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(title, category, examType, c1, c2, c3, c4, c5, note) },
                modifier = Modifier.testTag("save_history_score_button")
            ) {
                Text("Salvar no Banco Room")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun CompetencyAvgRow(label: String, avg: Int, accentColor: Color) {
    val animatedAvgProgress by animateFloatAsState(
        targetValue = (avg / 200f).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 850, easing = FastOutSlowInEasing),
        label = "comp_avg_anim"
    )
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodySmall)
            Text(
                text = "$avg / 200 pts",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        LinearProgressIndicator(
            progress = { animatedAvgProgress },
            color = accentColor,
            trackColor = accentColor.copy(alpha = 0.2f),
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(CircleShape)
        )
    }
}
