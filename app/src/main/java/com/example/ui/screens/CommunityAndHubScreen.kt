package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.CommunityPostEntity
import com.example.data.CurriculumCatalog
import com.example.data.EssaySubmissionEntity
import com.example.data.EssayThemeEntity
import com.example.data.InitialSeedData
import com.example.data.UserProfileEntity
import com.example.ui.theme.ClickableIconBlueBg
import com.example.ui.theme.ClickableIconBlueBorder
import com.example.ui.theme.ClickableIconBlueTint
import com.example.ui.theme.GoldBadge
import com.example.ui.theme.ScoreExcellent

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CommunityAndHubScreen(
    selectedTab: Int,
    onSelectTab: (Int) -> Unit,
    user: UserProfileEntity,
    posts: List<CommunityPostEntity>,
    essays: List<EssaySubmissionEntity>,
    themes: List<EssayThemeEntity>,
    onCreatePost: (String, String, String, String) -> Unit,
    onToggleLike: (CommunityPostEntity) -> Unit,
    onAddComment: (CommunityPostEntity, String) -> Unit,
    onSaveTeacherCorrection: (EssaySubmissionEntity, Int, Int, Int, Int, Int, String) -> Unit,
    onCreateManualTheme: (String, String, String, String, String, String, String, Boolean) -> Unit,
    onDeleteTheme: (Int) -> Unit,
    onTogglePremium: () -> Unit
) {
    val subTabs = listOf(
        "Comunidade",
        "Professor",
        "Admin",
        "Premium"
    )

    // Community new post state
    var showNewPostForm by remember { mutableStateOf(false) }
    var postCategory by remember { mutableStateOf("Repertório") }
    var postTitle by remember { mutableStateOf("") }
    var postContent by remember { mutableStateOf("") }
    var postQuote by remember { mutableStateOf("") }
    val commentDrafts = remember { mutableStateMapOf<Int, String>() }

    // Teacher manual grading state
    var selectedEssayIndex by remember { mutableIntStateOf(0) }
    val targetEssay = essays.getOrNull(selectedEssayIndex) ?: essays.firstOrNull()
    var c1Slider by remember(targetEssay) { mutableFloatStateOf((targetEssay?.c1Score ?: 180).toFloat()) }
    var c2Slider by remember(targetEssay) { mutableFloatStateOf((targetEssay?.c2Score ?: 200).toFloat()) }
    var c3Slider by remember(targetEssay) { mutableFloatStateOf((targetEssay?.c3Score ?: 180).toFloat()) }
    var c4Slider by remember(targetEssay) { mutableFloatStateOf((targetEssay?.c4Score ?: 180).toFloat()) }
    var c5Slider by remember(targetEssay) { mutableFloatStateOf((targetEssay?.c5Score ?: 200).toFloat()) }
    var teacherFeedbackInput by remember(targetEssay) {
        mutableStateOf(
            targetEssay?.teacherFeedback?.ifBlank {
                "Excelente progressão temática e domínio dos 5 elementos na conclusão. Continue refinando os conectivos intraparágrafos."
            } ?: ""
        )
    }

    // Teacher new theme state
    var newThemeTitle by remember { mutableStateOf("") }
    var newThemeCategory by remember { mutableStateOf("Educação") }
    var newThemeMotivator by remember { mutableStateOf("") }
    var newThemeRepertoire by remember { mutableStateOf("") }
    var newThemeExclusive by remember { mutableStateOf(false) }

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
                    modifier = Modifier.testTag("hub_tab_$idx")
                )
            }
        }

        when (selectedTab) {
            // TAB 0: COMUNIDADE (FÓRUM, REPERTÓRIOS, CURTIDAS E COMENTÁRIOS)
            0 -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Mural de Repertórios",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Text(
                                    text = "Teses, citações e análises compartilhadas.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            FilledTonalButton(
                                onClick = { showNewPostForm = !showNewPostForm },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.testTag("toggle_new_post_form_button")
                            ) {
                                Icon(Icons.Default.AddCircleOutline, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (showNewPostForm) "Fechar" else "Publicar")
                            }
                        }
                    }

                    if (showNewPostForm) {
                        item {
                            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Nova Publicação",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        listOf("Repertório", "Tese & Argumento", "Discussão", "Dúvida").forEach { cat ->
                                            FilterChip(
                                                selected = postCategory == cat,
                                                onClick = { postCategory = cat },
                                                label = { Text(cat) }
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = postTitle,
                                        onValueChange = { postTitle = it },
                                        label = { Text("Título da Discussão ou Repertório") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("community_post_title_input"),
                                        singleLine = true
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = postContent,
                                        onValueChange = { postContent = it },
                                        label = { Text("Explique como aplicar na redação...") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("community_post_content_input"),
                                        minLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = postQuote,
                                        onValueChange = { postQuote = it },
                                        label = { Text("Citação / Frase Filosófica (Opcional)") },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = {
                                            onCreatePost(postCategory, postTitle, postContent, postQuote)
                                            postTitle = ""
                                            postContent = ""
                                            postQuote = ""
                                            showNewPostForm = false
                                        },
                                        enabled = postTitle.isNotBlank() && postContent.isNotBlank(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("publish_community_post_button")
                                    ) {
                                        Icon(Icons.Default.Forum, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Publicar (+40 XP)")
                                    }
                                }
                            }
                        }
                    }

                    items(posts, key = { it.id }) { post ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("community_post_card_${post.id}")
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "${post.authorName} • Nível ${post.authorLevel}",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = post.authorBadge,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Surface(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = post.category,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = post.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = post.content,
                                    style = MaterialTheme.typography.bodySmall
                                )

                                if (post.repertoireQuote.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Surface(
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(
                                            text = post.repertoireQuote,
                                            modifier = Modifier.padding(10.dp),
                                            style = MaterialTheme.typography.bodySmall,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    FilledTonalButton(
                                        onClick = { onToggleLike(post) },
                                        modifier = Modifier.testTag("like_post_button_${post.id}")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ThumbUp,
                                            contentDescription = "Curtir publicação",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("${post.likesCount} Curtidas")
                                    }
                                }

                                // Comments list
                                val comments = post.commentsRaw.split("|").filter { it.isNotBlank() }
                                if (comments.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    HorizontalDivider()
                                    Spacer(modifier = Modifier.height(6.dp))
                                    comments.forEach { rawComment ->
                                        val parts = rawComment.split("::")
                                        val cAuthor = parts.getOrNull(0) ?: "Aluno"
                                        val cBody = parts.getOrNull(1) ?: rawComment
                                        Text(
                                            text = "$cAuthor: $cBody",
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }

                                // Add comment row
                                Spacer(modifier = Modifier.height(8.dp))
                                val draft = commentDrafts[post.id] ?: ""
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = draft,
                                        onValueChange = { commentDrafts[post.id] = it },
                                        placeholder = { Text("Comentar repertório...") },
                                        modifier = Modifier.weight(1f),
                                        singleLine = true
                                    )
                                    IconButton(
                                        onClick = {
                                            if (draft.isNotBlank()) {
                                                onAddComment(post, draft)
                                                commentDrafts[post.id] = ""
                                            }
                                        }
                                    ) {
                                        Surface(
                                            color = ClickableIconBlueBg,
                                            shape = RoundedCornerShape(9.dp),
                                            border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                            modifier = Modifier.size(34.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                                    contentDescription = "Enviar comentário",
                                                    tint = ClickableIconBlueTint,
                                                    modifier = Modifier.size(17.dp)
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

            // TAB 1: ÁREA DO PROFESSOR (DASHBOARD, CORREÇÕES MANUAIS, CRIAÇÃO DE TEMAS)
            1 -> {
                val avgClass = if (essays.isNotEmpty()) essays.map { it.totalScore }.average().toInt() else 900
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Dashboard do Prof. João Batista & Mentoria",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Docente & Mentor Oficial: Prof. João Batista",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Redações Recebidas: ${essays.size}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Média da Turma: $avgClass pts", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                                    Text("Temas Ativos: ${themes.size}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }

                    if (targetEssay != null) {
                        item {
                            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Mesa de Correção Manual — Prof. João Batista",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        essays.forEachIndexed { idx, item ->
                                            FilterChip(
                                                selected = selectedEssayIndex == idx,
                                                onClick = { selectedEssayIndex = idx },
                                                label = { Text(item.title.take(24)) }
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Redação Selecionada: ${targetEssay.title}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = targetEssay.content.take(280) + "...",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))

                                    TeacherScoreSlider("Competência 1 (Norma Padrão)", c1Slider) { c1Slider = it }
                                    TeacherScoreSlider("Competência 2 (Tema & Repertório)", c2Slider) { c2Slider = it }
                                    TeacherScoreSlider("Competência 3 (Argumentação)", c3Slider) { c3Slider = it }
                                    TeacherScoreSlider("Competência 4 (Coesão)", c4Slider) { c4Slider = it }
                                    TeacherScoreSlider("Competência 5 (Intervenção)", c5Slider) { c5Slider = it }

                                    val totalManual = c1Slider.toInt() + c2Slider.toInt() + c3Slider.toInt() + c4Slider.toInt() + c5Slider.toInt()
                                    Text(
                                        text = "Nota Final Atribuída pelo Prof. João Batista: $totalManual / 1000 pts",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = ScoreExcellent,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = teacherFeedbackInput,
                                        onValueChange = { teacherFeedbackInput = it },
                                        label = { Text("Parecer Pedagógico e Orientações ao Aluno") },
                                        modifier = Modifier.fillMaxWidth(),
                                        minLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = {
                                            onSaveTeacherCorrection(
                                                targetEssay,
                                                c1Slider.toInt(),
                                                c2Slider.toInt(),
                                                c3Slider.toInt(),
                                                c4Slider.toInt(),
                                                c5Slider.toInt(),
                                                teacherFeedbackInput
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("save_teacher_correction_button")
                                    ) {
                                        Icon(Icons.Default.RateReview, contentDescription = null)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Salvar Correção Manual e Notificar Aluno")
                                    }
                                }
                            }
                        }
                    }

                    // Criação de temas pelo professor
                    item {
                        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Publicar Novo Tema de Redação",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = newThemeTitle,
                                    onValueChange = { newThemeTitle = it },
                                    label = { Text("Frase Temática Oficial") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = newThemeMotivator,
                                    onValueChange = { newThemeMotivator = it },
                                    label = { Text("Texto Motivador e Dados Estatísticos") },
                                    modifier = Modifier.fillMaxWidth(),
                                    minLines = 2
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = newThemeRepertoire,
                                    onValueChange = { newThemeRepertoire = it },
                                    label = { Text("Sugestões de Repertório Sociocultural") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        onCreateManualTheme(
                                            newThemeTitle,
                                            newThemeCategory,
                                            "ENEM",
                                            newThemeMotivator,
                                            newThemeMotivator,
                                            newThemeRepertoire,
                                            "1. Qual a causa central? 2. Qual a intervenção ideal?",
                                            newThemeExclusive
                                        )
                                        newThemeTitle = ""
                                        newThemeMotivator = ""
                                        newThemeRepertoire = ""
                                    },
                                    enabled = newThemeTitle.isNotBlank(),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.AddCircleOutline, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Publicar Tema no Catálogo")
                                }
                            }
                        }
                    }
                }
            }

            // TAB 2: ÁREA ADMINISTRATIVA (USUÁRIOS, CONTEÚDOS, TEMAS, ASSINATURAS, RELATÓRIOS)
            2 -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Painel Administrativo Geral",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text("• Usuários Ativos na Plataforma: 1.480 alunos e 24 professores corretores")
                                Text("• Conteúdos Publicados: ${CurriculumCatalog.fundamentosLessons.size + CurriculumCatalog.enemCompetencias.size} módulos + ${CurriculumCatalog.libraryAssets.size} materiais de biblioteca")
                                Text("• Temas Cadastrados nos 10 Eixos: ${themes.size} propostas completas")
                                Text("• Assinaturas Mestre Diamante Ativas: 89% de retenção mensal")
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Gestão do Catálogo de Temas (${themes.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(themes.take(6), key = { it.id }) { theme ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${theme.category} • ${theme.examType}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = theme.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                IconButton(onClick = { onDeleteTheme(theme.id) }) {
                                    Surface(
                                        color = ClickableIconBlueBg,
                                        shape = RoundedCornerShape(9.dp),
                                        border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.DeleteOutline,
                                                contentDescription = "Remover tema ${theme.title}",
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
            }

            // TAB 3: RECURSOS PREMIUM (MESTRE DIAMANTE 1000)
            3 -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.WorkspacePremium,
                                        contentDescription = null,
                                        tint = GoldBadge,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "Mestre Diamante 1000 • Experiência Premium",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Status Atual: ${user.planName}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                listOf(
                                    "Correções ilimitadas com Inteligência Artificial nas 5 Competências",
                                    "Temas exclusivos semanais baseados em tendências do INEP e Vestibulares",
                                    "Simulados avançados cronometrados com diagnóstico de tempo por parágrafo",
                                    "Relatórios detalhados de evolução e comparação de versões na Reescrita",
                                    "Mentoria personalizada com revisão humana de professores especialistas"
                                ).forEach { benefit ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = ScoreExcellent,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = benefit, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = onTogglePremium,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("toggle_premium_button")
                                ) {
                                    Text(
                                        text = if (user.isPremium) "Plano Diamante Ativo (Alternar para Demonstração)" else "Ativar Assinatura Mestre Diamante 1000",
                                        fontWeight = FontWeight.Bold
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

@Composable
private fun TeacherScoreSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.labelMedium)
            Text(text = "${value.toInt()} pts", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        }
        Slider(
            value = value,
            onValueChange = { raw ->
                val snapped = ((raw / 40f).toInt() * 40).coerceIn(0, 200).toFloat()
                onValueChange(snapped)
            },
            valueRange = 0f..200f,
            steps = 4
        )
    }
}
