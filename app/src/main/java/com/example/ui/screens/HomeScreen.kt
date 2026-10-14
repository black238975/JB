package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.EssaySubmissionEntity
import com.example.data.ExerciseAttemptEntity
import com.example.data.StudyTaskEntity
import com.example.data.UserProfileEntity
import com.example.ui.theme.ClickableIconBlueBg
import com.example.ui.theme.ClickableIconBlueBorder
import com.example.ui.theme.ClickableIconBlueSoftBg
import com.example.ui.theme.ClickableIconBlueTint
import com.example.ui.theme.DeepRoyalHero
import com.example.ui.theme.GoldBadge
import com.example.ui.theme.ScoreExcellent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    user: UserProfileEntity,
    essays: List<EssaySubmissionEntity>,
    studyTasks: List<StudyTaskEntity>,
    exerciseAttempts: List<ExerciseAttemptEntity>,
    isAiBusy: Boolean,
    onStartNowClick: () -> Unit,
    onInspectEssay: (EssaySubmissionEntity) -> Unit,
    onRewriteEssay: (EssaySubmissionEntity) -> Unit,
    onToggleStudyTask: (StudyTaskEntity) -> Unit,
    onRegenerateStudyPlan: () -> Unit,
    onOpenSimulado: () -> Unit,
    onOpenScanner: () -> Unit = {},
    onOpenVideoLessons: () -> Unit = {}
) {
    val latestEssay = essays.firstOrNull()
    val avgScore = remember(essays) {
        if (essays.isNotEmpty()) essays.map { it.totalScore }.average().toInt() else 0
    }
    val completedTasks = remember(studyTasks) { studyTasks.count { it.isCompleted } }
    val totalTasks = remember(studyTasks) { studyTasks.size.coerceAtLeast(1) }

    // Progressive disclosure: separates "Visão Geral" from "Plano do Dia" so the screen stays calm and uncluttered
    var selectedHomeSection by remember { mutableIntStateOf(0) }
    var showCompetencyBreakdown by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen_list"),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Minimal Editorial Hero Card (Discreet Graphite & Champagne Gold)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = DeepRoyalHero),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color.White.copy(alpha = 0.08f),
                            shape = CircleShape
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = "Ofensiva diária",
                                    tint = GoldBadge,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "${user.streakDays} dias • Nível ${user.level}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }

                        Text(
                            text = "${user.xp} XP",
                            style = MaterialTheme.typography.labelMedium,
                            color = GoldBadge
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Olá, ${user.name.substringBefore(" ")}",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "Ambiente de escrita analítica e preparação estruturada para o ENEM.",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.92f)
                        )
                    }

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onStartNowClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = DeepRoyalHero
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                            modifier = Modifier.testTag("start_now_button")
                        ) {
                            Surface(
                                color = ClickableIconBlueBg,
                                shape = RoundedCornerShape(7.dp),
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = ClickableIconBlueTint,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.start_now),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                softWrap = false
                            )
                        }

                        OutlinedButton(
                            onClick = onOpenSimulado,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.45f)),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                            modifier = Modifier.testTag("quick_simulado_button")
                        ) {
                            Surface(
                                color = ClickableIconBlueBg,
                                shape = RoundedCornerShape(7.dp),
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = null,
                                        tint = ClickableIconBlueTint,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Simulado",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                    }
                }
            }
        }

        // 2. Clean Section Switcher to reduce information density on a single view
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = selectedHomeSection == 0,
                    onClick = { selectedHomeSection = 0 },
                    label = { Text("Visão Geral", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("home_tab_overview")
                )
                FilterChip(
                    selected = selectedHomeSection == 1,
                    onClick = { selectedHomeSection = 1 },
                    label = { Text("Plano Diário ($completedTasks/${studyTasks.size})", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("home_tab_study_plan")
                )
            }
        }

        if (selectedHomeSection == 0) {
            // 2.1 Ferramentas Principais: Scanner ENEM & Vídeo Aulas (Clean Bordered Cards)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { onOpenScanner() }
                            .testTag("quick_scanner_button"),
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                color = ClickableIconBlueBg,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.DocumentScanner,
                                        contentDescription = "Scanner de Redação",
                                        tint = ClickableIconBlueTint,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Scanner ENEM",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Foto da folha • Nota C1–C5",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { onOpenVideoLessons() }
                            .testTag("quick_video_lessons_button"),
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                color = ClickableIconBlueBg,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.LiveTv,
                                        contentDescription = "Vídeo Aulas",
                                        tint = ClickableIconBlueTint,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Vídeo Aulas",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Gravadas e ao vivo",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // 2.2 Indicadores Essenciais (Minimalist 3-column metrics)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SummaryStatCard(
                        modifier = Modifier.weight(1f),
                        label = "Média",
                        value = "$avgScore",
                        subLabel = "Meta ${user.targetScore}",
                        accentColor = MaterialTheme.colorScheme.onSurface
                    )
                    SummaryStatCard(
                        modifier = Modifier.weight(1f),
                        label = "Redações",
                        value = "${essays.size}",
                        subLabel = "${essays.count { it.parentEssayId != null }} reescritas",
                        accentColor = MaterialTheme.colorScheme.onSurface
                    )
                    SummaryStatCard(
                        modifier = Modifier.weight(1f),
                        label = "Estudo",
                        value = "${user.studyMinutes / 60}h",
                        subLabel = "${exerciseAttempts.size + 6} treinos",
                        accentColor = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // 2.3 Última Redação Realizada (Compact & Refined)
            if (latestEssay != null) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("latest_essay_card"),
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "ÚLTIMA REDAÇÃO • ${latestEssay.category.uppercase()}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = latestEssay.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text(
                                        text = "${latestEssay.totalScore} pts",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            Text(
                                text = if (showCompetencyBreakdown) "Ocultar notas por competência" else "Ver notas C1 a C5",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clickable { showCompetencyBreakdown = !showCompetencyBreakdown }
                            )

                            AnimatedVisibility(visible = showCompetencyBreakdown) {
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    CompetencyMiniPill("C1", latestEssay.c1Score)
                                    CompetencyMiniPill("C2", latestEssay.c2Score)
                                    CompetencyMiniPill("C3", latestEssay.c3Score)
                                    CompetencyMiniPill("C4", latestEssay.c4Score)
                                    CompetencyMiniPill("C5", latestEssay.c5Score)
                                }
                            }

                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                FilledTonalButton(
                                    onClick = { onInspectEssay(latestEssay) },
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("view_latest_correction_button")
                                ) {
                                    Text(
                                        text = "Ver Correção",
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                OutlinedButton(
                                    onClick = { onRewriteEssay(latestEssay) },
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("rewrite_latest_essay_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.EditNote,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Reescrever",
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // ABA 2: Plano de Estudos Diário & Metas Semanais (Clean & Focused)
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Roteiro do Dia",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "$completedTasks de ${studyTasks.size} concluídas",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            FilledTonalButton(
                                onClick = onRegenerateStudyPlan,
                                enabled = !isAiBusy,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.testTag("regenerate_study_plan_button")
                            ) {
                                if (isAiBusy) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(14.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Adaptar")
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { completedTasks.toFloat() / totalTasks.toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }

            items(studyTasks, key = { it.id }) { task ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onToggleStudyTask(task) }
                        .testTag("study_task_card_${task.id}"),
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { onToggleStudyTask(task) }) {
                            Surface(
                                color = if (task.isCompleted) ScoreExcellent else ClickableIconBlueBg,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (task.isCompleted) ScoreExcellent else ClickableIconBlueBorder
                                ),
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                        contentDescription = "Alternar tarefa ${task.title}",
                                        tint = ClickableIconBlueTint,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${task.competenciaFocus} • ${task.durationMinutes} min",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Text(
                            text = "+${task.xpReward} XP",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                    }
                }
            }

            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Metas Semanais",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        WeeklyGoalProgressRow(
                            title = "Redações na semana",
                            current = essays.size.coerceAtMost(3),
                            target = 3
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        WeeklyGoalProgressRow(
                            title = "Exercícios concluídos",
                            current = (exerciseAttempts.size + 4).coerceAtMost(6),
                            target = 6
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryStatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    subLabel: String,
    accentColor: Color
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = accentColor,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subLabel,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CompetencyMiniPill(label: String, score: Int) {
    Surface(
        color = if (score == 200) ScoreExcellent.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = "$label · $score",
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = if (score == 200) ScoreExcellent else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun WeeklyGoalProgressRow(title: String, current: Int, target: Int) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$current/$target",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        LinearProgressIndicator(
            progress = { (current.toFloat() / target.toFloat()).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(CircleShape)
        )
    }
}
