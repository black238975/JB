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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EnemGrammarQuestion
import com.example.data.EnemPortugueseSimuladoCatalog
import com.example.ui.theme.ScoreExcellent
import com.example.ui.theme.ScoreNeedsWork

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EnemGrammarSimuladoSection(
    modifier: Modifier = Modifier
) {
    val allQuestions = remember { EnemPortugueseSimuladoCatalog.questions }
    var selectedTopic by remember { mutableStateOf("TODOS") }

    // Map storing student's chosen option index (0..4) for each question id
    val selectedAnswers = remember { mutableStateMapOf<String, Int>() }

    // State for omitted answer key (gabarito omitido por padrão)
    var isGabaritoRevealed by remember { mutableStateOf(false) }

    // Set of individually revealed question IDs
    val individuallyRevealedIds = remember { mutableStateMapOf<String, Boolean>() }

    val filteredQuestions = remember(selectedTopic) {
        if (selectedTopic == "TODOS") {
            allQuestions
        } else {
            allQuestions.filter { it.topic == selectedTopic }
        }
    }

    // Performance statistics
    val totalQuestions = filteredQuestions.size
    val answeredCount = filteredQuestions.count { selectedAnswers.containsKey(it.id) }
    val correctCount = filteredQuestions.count { q ->
        selectedAnswers[q.id] == q.correctIndex
    }
    val scorePercentage = if (totalQuestions > 0) {
        (correctCount * 100) / totalQuestions
    } else 0

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. BANNER DE CABEÇALHO DO SIMULADO DE PORTUGUÊS ENEM
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("simulado_gramatica_banner"),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "SIMULADO ENEM • PORTUGUÊS & GRAMÁTICA",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Questões de múltipla escolha 100% no padrão INEP/ENEM",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Pratique regência, concordância, crase, pontuação, variação linguística, funções da linguagem, figuras de linguagem e coesão. Marque suas alternativas e confira seu desempenho com o gabarito oficial comentado pelo Prof. João Batista.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                // Stats and Controls Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Progresso: $answeredCount de $totalQuestions respondidas",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        if (isGabaritoRevealed) {
                            Text(
                                text = "Acertos: $correctCount de $totalQuestions ($scorePercentage%)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (scorePercentage >= 70) ScoreExcellent else ScoreNeedsWork
                            )
                        } else {
                            Text(
                                text = "Gabarito Omitido (resolva antes de conferir)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (!isGabaritoRevealed) {
                            Button(
                                onClick = { isGabaritoRevealed = true },
                                modifier = Modifier.testTag("ver_gabarito_button"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Ver gabarito",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            OutlinedButton(
                                onClick = {
                                    isGabaritoRevealed = false
                                    individuallyRevealedIds.clear()
                                },
                                modifier = Modifier.testTag("ocultar_gabarito_button")
                            ) {
                                Icon(Icons.Default.VisibilityOff, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Ocultar gabarito")
                            }
                        }

                        TextButton(
                            onClick = {
                                selectedAnswers.clear()
                                isGabaritoRevealed = false
                                individuallyRevealedIds.clear()
                            }
                        ) {
                            Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Refazer")
                        }
                    }
                }
            }
        }

        // 2. PAINEL DE RESULTADO QUANDO O GABARITO FOR REVELADO
        if (isGabaritoRevealed) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("resultado_gabarito_card"),
                colors = CardDefaults.cardColors(
                    containerColor = if (scorePercentage >= 70) {
                        ScoreExcellent.copy(alpha = 0.12f)
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    }
                ),
                border = BorderStroke(
                    1.dp,
                    if (scorePercentage >= 70) ScoreExcellent.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📊 Resultado do Simulado de Gramática",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (scorePercentage >= 70) ScoreExcellent else ScoreNeedsWork
                        ) {
                            Text(
                                text = "$correctCount / $totalQuestions ACERTOS",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { if (totalQuestions > 0) correctCount.toFloat() / totalQuestions else 0f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = if (scorePercentage >= 70) ScoreExcellent else MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = when {
                            scorePercentage >= 85 -> "🎉 Excelente desempenho! Você demonstrou domínio expressivo da norma-padrão (Competência 1) e dos padrões de análise textual do ENEM."
                            scorePercentage >= 65 -> "👍 Bom resultado! Para cravar os 200 pontos na Competência 1 e acertar mais questões de Linguagens, revise as regras de regência, crase e concordância explicadas abaixo."
                            else -> "📚 Continue treinando! Leia com atenção os comentários detalhados do Prof. João Batista em cada questão para assimilar os distratores e a lógica do ENEM."
                        },
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // 3. FILTROS DE TÓPICOS DE PORTUGUÊS ENEM
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Filtrar por Tema de Gramática & Linguagens:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EnemPortugueseSimuladoCatalog.topics.forEach { topic ->
                    FilterChip(
                        selected = selectedTopic == topic,
                        onClick = { selectedTopic = topic },
                        label = {
                            val count = if (topic == "TODOS") allQuestions.size else allQuestions.count { it.topic == topic }
                            Text("$topic ($count)")
                        }
                    )
                }
            }
        }

        // 4. LISTA DAS QUESTÕES DE MÚLTIPLA ESCOLHA
        filteredQuestions.forEachIndexed { index, question ->
            val userSelectedOption = selectedAnswers[question.id]
            val isQuestionRevealed = isGabaritoRevealed || individuallyRevealedIds[question.id] == true

            EnemQuestionCard(
                index = index + 1,
                total = totalQuestions,
                question = question,
                selectedOption = userSelectedOption,
                isGabaritoRevealed = isQuestionRevealed,
                onSelectOption = { chosenIdx ->
                    if (!isQuestionRevealed) {
                        selectedAnswers[question.id] = chosenIdx
                    }
                },
                onToggleQuestionGabarito = {
                    val current = individuallyRevealedIds[question.id] ?: false
                    individuallyRevealedIds[question.id] = !current
                }
            )
        }

        // Botão de rodapé "Ver gabarito" para facilitar quando o aluno terminar
        if (!isGabaritoRevealed) {
            Button(
                onClick = { isGabaritoRevealed = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ver_gabarito_bottom_button")
                    .padding(vertical = 8.dp)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ver gabarito de todas as questões",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun EnemQuestionCard(
    index: Int,
    total: Int,
    question: EnemGrammarQuestion,
    selectedOption: Int?,
    isGabaritoRevealed: Boolean,
    onSelectOption: (Int) -> Unit,
    onToggleQuestionGabarito: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("enem_question_${question.id}"),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Badges superiores: Ano do ENEM, Tópico e Número da Questão
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = question.enemYear,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = question.topic,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                Text(
                    text = "Questão $index de $total",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = question.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Texto Base Oficial do ENEM
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "TEXTO BASE:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = question.baseText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Fonte: ${question.textSource}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Enunciado da questão do ENEM
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = question.prompt,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 5 Alternativas de Múltipla Escolha (A, B, C, D, E)
            val optionLetters = listOf("A", "B", "C", "D", "E")
            question.options.forEachIndexed { optIndex, optionText ->
                val isSelected = selectedOption == optIndex
                val isCorrect = optIndex == question.correctIndex

                val cardBgColor = when {
                    isGabaritoRevealed && isCorrect -> ScoreExcellent.copy(alpha = 0.12f)
                    isGabaritoRevealed && isSelected && !isCorrect -> ScoreNeedsWork.copy(alpha = 0.12f)
                    isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                    else -> MaterialTheme.colorScheme.surface
                }

                val borderStroke = when {
                    isGabaritoRevealed && isCorrect -> BorderStroke(2.dp, ScoreExcellent)
                    isGabaritoRevealed && isSelected && !isCorrect -> BorderStroke(2.dp, ScoreNeedsWork)
                    isSelected -> BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                    else -> BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(enabled = !isGabaritoRevealed) {
                            onSelectOption(optIndex)
                        },
                    color = cardBgColor,
                    shape = RoundedCornerShape(10.dp),
                    border = borderStroke
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = when {
                                isGabaritoRevealed && isCorrect -> ScoreExcellent
                                isGabaritoRevealed && isSelected && !isCorrect -> ScoreNeedsWork
                                isSelected -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = optionLetters.getOrElse(optIndex) { "?" },
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected || (isGabaritoRevealed && isCorrect)) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isGabaritoRevealed && isCorrect) FontWeight.Bold else FontWeight.Normal
                            )

                            // Status tags if gabarito is revealed
                            if (isGabaritoRevealed) {
                                Spacer(modifier = Modifier.height(4.dp))
                                when {
                                    isCorrect && isSelected -> {
                                        Text(
                                            text = "✓ Gabarito Oficial (Você acertou!)",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = ScoreExcellent
                                        )
                                    }
                                    isCorrect -> {
                                        Text(
                                            text = "✓ Gabarito Oficial (Alternativa Correta)",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = ScoreExcellent
                                        )
                                    }
                                    isSelected -> {
                                        Text(
                                            text = "✕ Sua Resposta (Incorreta)",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = ScoreNeedsWork
                                        )
                                    }
                                }
                            }
                        }

                        if (!isGabaritoRevealed) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { onSelectOption(optIndex) }
                            )
                        } else {
                            if (isCorrect) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = ScoreExcellent,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = ScoreNeedsWork,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botão individual "Ver gabarito" / "Ocultar gabarito" para esta questão
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedOption != null) {
                    Text(
                        text = "Sua opção: Letra ${optionLetters.getOrElse(selectedOption) { "" }}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "Nenhuma alternativa marcada ainda",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                TextButton(
                    onClick = onToggleQuestionGabarito,
                    modifier = Modifier.testTag("toggle_gabarito_q_${question.id}")
                ) {
                    Icon(
                        imageVector = if (isGabaritoRevealed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isGabaritoRevealed) "Ocultar gabarito" else "Ver gabarito")
                }
            }

            // SEÇÃO DO GABARITO E EXPLICAÇÃO PEDAGÓGICA (REVELADA PELO BOTÃO)
            if (isGabaritoRevealed) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Gabarito: Letra ${optionLetters.getOrElse(question.correctIndex) { "" }}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                if (selectedOption != null) {
                                    val isHit = selectedOption == question.correctIndex
                                    Surface(
                                        color = if (isHit) ScoreExcellent else ScoreNeedsWork,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = if (isHit) "Você acertou!" else "Você errou",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = question.explanation,
                                style = MaterialTheme.typography.bodySmall,
                                lineHeight = 19.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
