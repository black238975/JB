package com.example.ui.screens

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.EssaySubmissionEntity
import com.example.data.EssayThemeEntity
import com.example.data.InitialSeedData
import com.example.data.VideoLessonEntity
import com.example.ui.theme.ClickableIconBlueBg
import com.example.ui.theme.ClickableIconBlueBorder
import com.example.ui.theme.ClickableIconBlueTint
import com.example.ui.theme.DeepRoyalHero
import com.example.ui.theme.GoldBadge
import com.example.ui.theme.ScoreExcellent
import com.example.ui.theme.ScoreNeedsWork
import com.example.ui.theme.ScoreWarning
import java.io.ByteArrayOutputStream

private fun Bitmap.toBase64Jpeg(): String {
    val outputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 82, outputStream)
    return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EssayPaperScannerSection(
    themes: List<EssayThemeEntity>,
    selectedTheme: EssayThemeEntity?,
    latestScannedEssay: EssaySubmissionEntity?,
    scannedHistory: List<EssaySubmissionEntity>,
    isAiBusy: Boolean,
    onScanHandwrittenEssay: (
        imageBase64: String?,
        theme: EssayThemeEntity?,
        customThemeTitle: String,
        category: String,
        essayTitle: String,
        transcriptHint: String
    ) -> Unit,
    onOpenInRewrite: (EssaySubmissionEntity) -> Unit
) {
    val context = LocalContext.current

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var captureSourceLabel by remember { mutableStateOf("Folha Oficial 30 Linhas Pronta para Escaneamento") }
    var isDemoSheetLoaded by remember { mutableStateOf(true) }

    var selectedCategory by remember { mutableStateOf(selectedTheme?.category ?: "Educação") }
    var customThemeTitle by remember(selectedTheme) {
        mutableStateOf(
            selectedTheme?.title
                ?: "Desafios para a valorização do magistério e combate à evasão escolar no Brasil"
        )
    }
    var scannedTitle by remember { mutableStateOf("Redação Manuscrita Escaneada (Folha ENEM)") }
    var ocrTranscriptText by remember {
        mutableStateOf(
            "Promulgada em 1988, a Constituição Cidadã assegura a educação e a dignidade como direitos inalienáveis no Brasil. Contudo, ao analisar a problemática no contexto nacional, verifica-se um grave abismo entre a teoria constitucional e a realidade cotidiana, motivado pela negligência estatal e pelo silenciamento social.\n\nEm primeira análise, cabe pontuar que a escassez de investimentos direcionados impede a consolidação plena da cidadania. Sob a ótica do jornalista Gilberto Dimenstein, na obra 'Cidadãos de Papel', o país detém um arcabouço legal moderno que, todavia, não se materializa na prática. Dessa forma, parcelas vulneráveis da população permanecem desassistidas.\n\nAdemais, a ausência de debates críticos nas escolas e nos meios de comunicação perpetua estigmas históricos. Segundo a filósofa Hannah Arendt, a banalização das injustiças enfraquece o espaço público democrático e naturaliza desigualdades.\n\nPortanto, urge que o Governo Federal, em ação conjunta com o Ministério da Educação — órgãos responsáveis pela administração pública e formação cidadã —, amplie as verbas estruturais e institua projetos pedagógicos permanentes nas escolas, por meio do Plano Nacional de Educação e oficinas comunitárias, a fim de erradicar esse entrave e efetivar a Carta Magna de 1988."
        )
    }
    var showTranscriptEditor by remember { mutableStateOf(false) }

    val cameraPreviewLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            capturedBitmap = bitmap
            isDemoSheetLoaded = false
            captureSourceLabel = "Foto capturada pela câmera (${bitmap.width}x${bitmap.height}px)"
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted: Boolean ->
        if (granted) {
            cameraPreviewLauncher.launch(null)
        } else {
            isDemoSheetLoaded = true
            captureSourceLabel = "Permissão de câmera negada — Modo Folha Digital 30 Linhas ativo"
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            try {
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    val decoded = BitmapFactory.decodeStream(stream)
                    if (decoded != null) {
                        capturedBitmap = decoded
                        isDemoSheetLoaded = false
                        captureSourceLabel = "Foto importada da galeria (${decoded.width}x${decoded.height}px)"
                    }
                }
            } catch (_: Exception) {
                captureSourceLabel = "Imagem selecionada da galeria para análise OCR"
            }
        }
    }

    val laserProgress by animateFloatAsState(
        targetValue = if (isAiBusy) 0.85f else 0.42f,
        animationSpec = tween(durationMillis = 600),
        label = "laser_y"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("scanner_screen_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Hero Header of the Scanner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scanner_header_card"),
                colors = CardDefaults.cardColors(containerColor = DeepRoyalHero),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            color = GoldBadge.copy(alpha = 0.22f),
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.DocumentScanner,
                                contentDescription = "Scanner de Redação",
                                tint = GoldBadge,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(26.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Scanner Óptico de Redação no Papel",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "Tire uma foto da sua folha manuscrita • OCR + Nota C1 a C5 + Onde Melhorar (Padrão ENEM)",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.88f)
                            )
                        }
                    }
                }
            }
        }

        // 2. Capture Controls & 30-Line Paper Sheet Visualizer
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scanner_capture_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1. Capturar Folha Manuscrita (30 Linhas)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = if (capturedBitmap != null) "Foto Real Pronta" else "Folha 30 Linhas",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Text(
                        text = captureSourceLabel,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Action buttons: Camera, Gallery, Demo 30-line sheet
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            },
                            modifier = Modifier.testTag("scanner_camera_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Tirar Foto (Câmera)")
                        }

                        FilledTonalButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.testTag("scanner_gallery_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Importar da Galeria")
                        }

                        OutlinedButton(
                            onClick = {
                                capturedBitmap = null
                                isDemoSheetLoaded = true
                                captureSourceLabel = "Folha Oficial 30 Linhas (Modelo Manuscrito Carregado)"
                            },
                            modifier = Modifier.testTag("scanner_load_demo_sheet_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Folha Exemplo 30L")
                        }
                    }

                    // Visual Preview of the Scanned Paper Sheet with Laser Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFFFDFBF7))
                    ) {
                        val bmp = capturedBitmap
                        if (bmp != null) {
                            Image(
                                bitmap = bmp.asImageBitmap(),
                                contentDescription = "Foto da Redação no Papel",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            // Authentic ENEM 30-Line Ruled Sheet Canvas Preview
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height
                                val marginLeft = 42.dp.toPx()
                                val marginRight = w - 18.dp.toPx()

                                // Left & Right vertical red/blue margins like official ENEM sheet
                                drawLine(
                                    color = Color(0xFFE57373).copy(alpha = 0.65f),
                                    start = Offset(marginLeft, 0f),
                                    end = Offset(marginLeft, h),
                                    strokeWidth = 2f
                                )
                                drawLine(
                                    color = Color(0xFFE57373).copy(alpha = 0.45f),
                                    start = Offset(marginRight, 0f),
                                    end = Offset(marginRight, h),
                                    strokeWidth = 1.5f
                                )

                                val totalVisibleLines = 14
                                val lineSpacing = h / (totalVisibleLines + 1)
                                for (i in 1..totalVisibleLines) {
                                    val y = i * lineSpacing
                                    drawLine(
                                        color = Color(0xFF90CAF9).copy(alpha = 0.65f),
                                        start = Offset(0f, y),
                                        end = Offset(w, y),
                                        strokeWidth = 1.2f
                                    )
                                    // Simulated handwritten ink waves on the ruled lines
                                    val indent = if (i == 1 || i == 5 || i == 9 || i == 12) 28.dp.toPx() else 4.dp.toPx()
                                    val lineEnd = if (i == 4 || i == 8 || i == 11 || i == 14) marginRight * 0.78f else marginRight - 8f
                                    drawLine(
                                        color = Color(0xFF1A237E).copy(alpha = 0.55f),
                                        start = Offset(marginLeft + indent, y - 4f),
                                        end = Offset(lineEnd, y - 4f),
                                        strokeWidth = 3.2f
                                    )
                                }
                            }
                        }

                        // Animated Green/Gold Optical Scanner Beam
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val beamY = size.height * laserProgress
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        ScoreExcellent.copy(alpha = 0.32f),
                                        GoldBadge.copy(alpha = 0.75f),
                                        ScoreExcellent.copy(alpha = 0.32f),
                                        Color.Transparent
                                    ),
                                    startY = (beamY - 24f).coerceAtLeast(0f),
                                    endY = (beamY + 24f).coerceAtMost(size.height)
                                )
                            )
                            drawLine(
                                color = ScoreExcellent,
                                start = Offset(0f, beamY),
                                end = Offset(size.width, beamY),
                                strokeWidth = 3f
                            )
                        }

                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(10.dp),
                            color = DeepRoyalHero.copy(alpha = 0.86f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "FOLHA OFICIAL ENEM • 30 LINHAS • OCR ATIVO",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }

                    // Theme & Category configuration for the Scanner
                    Text(
                        text = "2. Eixo e Tema da Redação Escaneada:",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        InitialSeedData.themeCategories.take(8).forEach { cat ->
                            FilterChip(
                                selected = selectedCategory == cat,
                                onClick = {
                                    selectedCategory = cat
                                    val matching = themes.firstOrNull { it.category == cat }
                                    if (matching != null) {
                                        customThemeTitle = matching.title
                                    }
                                },
                                label = { Text(cat, style = MaterialTheme.typography.labelSmall) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = customThemeTitle,
                        onValueChange = { customThemeTitle = it },
                        label = { Text("Frase Temática da Redação Feita no Papel") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("scanner_theme_input"),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Revisar/Editar Texto Transcrito pelo OCR (Opcional)",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { showTranscriptEditor = !showTranscriptEditor }
                        )
                        FilledTonalButton(
                            onClick = { showTranscriptEditor = !showTranscriptEditor },
                            modifier = Modifier.testTag("toggle_ocr_editor_button")
                        ) {
                            Text(if (showTranscriptEditor) "Ocultar OCR" else "Ver/Editar OCR")
                        }
                    }

                    AnimatedVisibility(visible = showTranscriptEditor) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = scannedTitle,
                                onValueChange = { scannedTitle = it },
                                label = { Text("Título na Folha (Opcional)") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = ocrTranscriptText,
                                onValueChange = { ocrTranscriptText = it },
                                label = { Text("Transcrição Óptica da Folha Manuscrita") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(170.dp)
                                    .testTag("scanner_ocr_text_input")
                            )
                        }
                    }

                    Button(
                        onClick = {
                            val b64 = capturedBitmap?.toBase64Jpeg()
                            onScanHandwrittenEssay(
                                b64,
                                selectedTheme,
                                customThemeTitle,
                                selectedCategory,
                                scannedTitle,
                                ocrTranscriptText
                            )
                        },
                        enabled = !isAiBusy,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("scan_paper_essay_button")
                    ) {
                        if (isAiBusy) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Escaneando Folha e Calculando Nota ENEM...")
                        } else {
                            Icon(Icons.Default.DocumentScanner, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Escanear Redação e Gerar Nota ENEM (C1 a C5)")
                        }
                    }
                }
            }
        }

        // 3. Detailed ENEM Scanner Evaluation Report (Scores C1-C5 + Specific Comments Where to Improve)
        val activeScanned = latestScannedEssay ?: scannedHistory.firstOrNull { it.isScannedFromPaper } ?: scannedHistory.firstOrNull()
        if (activeScanned != null) {
            item {
                ScannedEssayEnemReportCard(
                    essay = activeScanned,
                    onOpenInRewrite = { onOpenInRewrite(activeScanned) }
                )
            }
        }
    }
}

@Composable
private fun ScannedEssayEnemReportCard(
    essay: EssaySubmissionEntity,
    onOpenInRewrite: () -> Unit
) {
    val parsedCompetencyComments = remember(essay.competencyCommentsRaw, essay.c1Score, essay.c2Score, essay.c3Score, essay.c4Score, essay.c5Score) {
        val rawMap = essay.competencyCommentsRaw
            .split("|")
            .mapNotNull { entry ->
                val parts = entry.split("::")
                if (parts.size >= 2) parts[0].trim() to parts[1].trim() else null
            }
            .toMap()

        listOf(
            Triple(
                "Competência 1 • Domínio da Norma-Padrão & Caligrafia",
                essay.c1Score,
                rawMap["C1"] ?: "Revise desvios de acentuação, regência, crase e atenção à separação silábica (translineação) na margem direita da folha."
            ),
            Triple(
                "Competência 2 • Compreensão do Tema & Repertório",
                essay.c2Score,
                rawMap["C2"] ?: "Abordagem completa do tema com repertório sociocultural produtivo. Garanta conexão direta entre o autor citado e a tese."
            ),
            Triple(
                "Competência 3 • Projeto de Texto, Tópico Frasal & Argumentação",
                essay.c3Score,
                rawMap["C3"] ?: "Tópico frasal claro na abertura dos parágrafos. Aprofunde o fechamento crítico do D1 e D2 para evitar lacunas argumentativas."
            ),
            Triple(
                "Competência 4 • Conectivos Inter e Intraparágrafos",
                essay.c4Score,
                rawMap["C4"] ?: "Empregue conectivos interparágrafos no início do D1, D2 e Conclusão e diversifique os operadores intraparágrafos."
            ),
            Triple(
                "Competência 5 • Proposta de Intervenção (5 Elementos)",
                essay.c5Score,
                rawMap["C5"] ?: "Apresente explicitamente Agente, Ação, Meio/Modo, Efeito e Detalhamento (preferencialmente entre travessões)."
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("scanner_evaluation_result_card"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        color = ScoreExcellent.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "DIAGNÓSTICO DO SCANNER • PADRÃO ENEM",
                            style = MaterialTheme.typography.labelSmall,
                            color = ScoreExcellent,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = essay.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tema: ${essay.themeTitle}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    color = DeepRoyalHero,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${essay.totalScore}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = GoldBadge,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "de 1000 pts",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }
            }

            // Sheet legibility & line count metadata
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Leitura Óptica da Folha: ~${essay.scannedLinesEstimate}/30 linhas • ${essay.wordCount} palavras reconhecidas",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = essay.legibilityComment.ifBlank {
                            "Folha pautada analisada com sucesso: caligrafia legível, margens respeitadas e estrutura em 4 parágrafos."
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            HorizontalDivider()

            // Competency-by-Competency Scores + Comments on Where to Improve
            Text(
                text = "Nota e Comentários por Competência (C1 a C5):",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold
            )

            parsedCompetencyComments.forEachIndexed { index, (compTitle, score, comment) ->
                val badgeColor = when {
                    score >= 180 -> ScoreExcellent
                    score >= 140 -> ScoreWarning
                    else -> ScoreNeedsWork
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("scanner_comp_card_${index + 1}"),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = compTitle,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                color = badgeColor.copy(alpha = 0.16f),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = "$score / 200",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = badgeColor,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                        LinearProgressIndicator(
                            progress = { (score / 200f).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape),
                            color = badgeColor
                        )
                        Text(
                            text = "💡 Comentário & Onde Melhorar: $comment",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // General Strengths, Errors on Paper & Actionable Improvements
            HorizontalDivider()

            Text(
                text = "Pontos Fortes Identificados na Folha:",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = ScoreExcellent
            )
            essay.strengths.split("|").filter { it.isNotBlank() }.forEach { item ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = ScoreExcellent,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = item, style = MaterialTheme.typography.bodySmall)
                }
            }

            Text(
                text = "Onde Melhorar para Atingir 960–1000 Pontos:",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = ScoreNeedsWork
            )
            (essay.errorsFound.split("|") + essay.improvements.split("|"))
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .distinct()
                .forEach { tip ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            tint = ScoreNeedsWork,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(text = tip, style = MaterialTheme.typography.bodySmall)
                    }
                }

            // Transcribed OCR Text Box
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Texto Transcrito Automaticamente pelo Scanner:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = essay.content,
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            FilledTonalButton(
                onClick = onOpenInRewrite,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scanner_rewrite_button")
            ) {
                Icon(Icons.Default.AutoFixHigh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Aperfeiçoar Redação Escaneada na Área de Reescrita")
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VideoLessonsSessionSection(
    videoLessons: List<VideoLessonEntity>,
    onPostVideoLesson: (
        title: String,
        description: String,
        instructorName: String,
        category: String,
        isLive: Boolean,
        liveStatus: String,
        scheduledDateTime: String,
        durationMinutes: Int,
        videoUrl: String,
        keyTopics: String,
        supportMaterial: String
    ) -> Unit,
    onToggleWatched: (VideoLessonEntity) -> Unit,
    onCycleLiveStatus: (VideoLessonEntity) -> Unit,
    onSendChatMessage: (VideoLessonEntity, String) -> Unit,
    onDeleteLesson: (Int) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    var selectedFilter by remember { mutableStateOf("TODAS") } // TODAS, AO_VIVO, GRAVADAS, CONCLUIDAS
    var activeLessonId by remember(videoLessons) {
        mutableIntStateOf(videoLessons.firstOrNull()?.id ?: 0)
    }
    var showPostLessonForm by remember { mutableStateOf(false) }

    // Form state for posting a new Recorded or Live Video Lesson
    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    var newInstructor by remember { mutableStateOf("Prof. João Batista") }
    var newCategory by remember { mutableStateOf("Aulão Ao Vivo") }
    var newModality by remember { mutableStateOf("AO_VIVO_AGORA") } // AO_VIVO_AGORA, AGENDADA, GRAVADA
    var newSchedule by remember { mutableStateOf("Hoje às 19:30 • Transmissão Ao Vivo") }
    var newDuration by remember { mutableStateOf("45") }
    var newVideoUrl by remember { mutableStateOf("https://youtube.com/live/mestre-redacao-enem") }
    var newKeyTopics by remember {
        mutableStateOf("00:00 Abertura e Contextualização|12:00 Estrutura de Parágrafo Padrão ENEM|28:00 Correção de Redações e Dúvidas")
    }
    var newSupportMaterial by remember {
        mutableStateOf("Material de apoio em ficha: resumo dos conectivos, repertórios da aula e checklist das 5 competências.")
    }

    // Interactive player state
    var isPlaying by remember { mutableStateOf(false) }
    var playbackProgress by remember { mutableFloatStateOf(0.25f) }
    var selectedChapterIndex by remember { mutableIntStateOf(0) }
    var chatInput by remember { mutableStateOf("") }

    val filteredLessons = remember(videoLessons, selectedFilter) {
        when (selectedFilter) {
            "AO_VIVO" -> videoLessons.filter { it.isLive || it.liveStatus != "GRAVADA" }
            "GRAVADAS" -> videoLessons.filter { !it.isLive && it.liveStatus == "GRAVADA" }
            "CONCLUIDAS" -> videoLessons.filter { it.isWatched }
            else -> videoLessons
        }
    }

    val selectedLesson = videoLessons.find { it.id == activeLessonId } ?: filteredLessons.firstOrNull() ?: videoLessons.firstOrNull()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("video_lessons_screen_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header Banner + Teacher "Postar Aula Gravada / Ao Vivo" Button
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("video_lessons_header_card"),
                colors = CardDefaults.cardColors(containerColor = DeepRoyalHero),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            color = Color(0xFFD32F2F).copy(alpha = 0.25f),
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.LiveTv,
                                contentDescription = "Vídeo Aulas",
                                tint = GoldBadge,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(26.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Central de Vídeo Aulas (Gravadas & Ao Vivo)",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "Assista às aulas gravadas passo a passo, participe das transmissões ao vivo e publique novas aulas.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.88f)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val liveCount = videoLessons.count { it.isLive }
                        val recordedCount = videoLessons.count { !it.isLive }
                        Text(
                            text = "🔴 $liveCount Ao Vivo/Agendadas • 🎬 $recordedCount Gravadas",
                            style = MaterialTheme.typography.labelMedium,
                            color = GoldBadge,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = { showPostLessonForm = !showPostLessonForm },
                            modifier = Modifier.testTag("open_post_video_lesson_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.VideoCall,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (showPostLessonForm) "Fechar Estúdio" else "Postar Aula (Gravada/Live)")
                        }
                    }
                }
            }
        }

        // 2. Collapsible Teacher Studio Form to Post Recorded or Live Video Lessons
        if (showPostLessonForm) {
            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("post_video_lesson_form_card")
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Estúdio do Professor • Publicar Nova Vídeo Aula",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Publique aulas gravadas por módulo ou agende/inicie transmissões ao vivo para os alunos.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Modality selector: AO_VIVO_AGORA, AGENDADA, GRAVADA
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = newModality == "AO_VIVO_AGORA",
                                onClick = {
                                    newModality = "AO_VIVO_AGORA"
                                    newSchedule = "AO VIVO AGORA • Transmissão Aberta"
                                },
                                label = { Text("🔴 Ao Vivo Agora") },
                                modifier = Modifier.testTag("modality_live_now_chip")
                            )
                            FilterChip(
                                selected = newModality == "AGENDADA",
                                onClick = {
                                    newModality = "AGENDADA"
                                    newSchedule = "Sábado às 15:00 (Horário de Brasília)"
                                },
                                label = { Text("📅 Live Agendada") },
                                modifier = Modifier.testTag("modality_scheduled_chip")
                            )
                            FilterChip(
                                selected = newModality == "GRAVADA",
                                onClick = {
                                    newModality = "GRAVADA"
                                    newSchedule = "Aula Gravada • Disponível sob demanda"
                                },
                                label = { Text("🎬 Aula Gravada") },
                                modifier = Modifier.testTag("modality_recorded_chip")
                            )
                        }

                        // Category chips
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                "Aulão Ao Vivo",
                                "Passo a Passo",
                                "Competência 1",
                                "Competência 2",
                                "Competência 3",
                                "Competência 4",
                                "Competência 5",
                                "Análise Nota 1000"
                            ).forEach { cat ->
                                FilterChip(
                                    selected = newCategory == cat,
                                    onClick = { newCategory = cat },
                                    label = { Text(cat, style = MaterialTheme.typography.labelSmall) }
                                )
                            }
                        }

                        OutlinedTextField(
                            value = newTitle,
                            onValueChange = { newTitle = it },
                            label = { Text("Título da Aula Gravada ou Ao Vivo") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("video_lesson_title_input"),
                            singleLine = true
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = newInstructor,
                                onValueChange = { newInstructor = it },
                                label = { Text("Professor(a)") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = newDuration,
                                onValueChange = { newDuration = it },
                                label = { Text("Duração (min)") },
                                modifier = Modifier.width(120.dp),
                                singleLine = true
                            )
                        }

                        OutlinedTextField(
                            value = newSchedule,
                            onValueChange = { newSchedule = it },
                            label = { Text("Horário da Live ou Módulo da Gravação") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = newVideoUrl,
                            onValueChange = { newVideoUrl = it },
                            label = { Text("Link da Vídeo Aula / Live (YouTube, Meet, Drive)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("video_lesson_url_input"),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = newDescription,
                            onValueChange = { newDescription = it },
                            label = { Text("Resumo / Descrição da Aula") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = newKeyTopics,
                            onValueChange = { newKeyTopics = it },
                            label = { Text("Capítulos da Aula (separados por |)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = newSupportMaterial,
                            onValueChange = { newSupportMaterial = it },
                            label = { Text("Material de Apoio / Resumo em Ficha") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                val resolvedTitle = newTitle.ifBlank {
                                    if (newModality == "GRAVADA") "Aula Gravada: Dominando $newCategory no ENEM"
                                    else "Aulão Ao Vivo: Estratégias 960+ em $newCategory"
                                }
                                onPostVideoLesson(
                                    resolvedTitle,
                                    newDescription,
                                    newInstructor,
                                    newCategory,
                                    newModality != "GRAVADA",
                                    newModality,
                                    newSchedule,
                                    newDuration.toIntOrNull() ?: 45,
                                    newVideoUrl,
                                    newKeyTopics,
                                    newSupportMaterial
                                )
                                newTitle = ""
                                newDescription = ""
                                showPostLessonForm = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("submit_video_lesson_button")
                        ) {
                            Icon(Icons.Default.AddCircle, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Publicar Aula na Sessão de Vídeo Aulas")
                        }
                    }
                }
            }
        }

        // 3. Active Video Lesson / Live Stream Player Studio
        if (selectedLesson != null) {
            item {
                val chapters = remember(selectedLesson.keyTopics) {
                    selectedLesson.keyTopics.split("|").map { it.trim() }.filter { it.isNotBlank() }
                }
                val chatMessages = remember(selectedLesson.chatMessagesRaw) {
                    selectedLesson.chatMessagesRaw.split("|").mapNotNull { raw ->
                        val parts = raw.split("::")
                        if (parts.size >= 2) parts[0].trim() to parts[1].trim() else null
                    }
                }

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("active_video_player_card")
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Video Screen Canvas Simulation
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(205.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF0F172A),
                                            DeepRoyalHero,
                                            Color(0xFF1E1B4B)
                                        )
                                    )
                                )
                                .padding(16.dp)
                        ) {
                            // Top status badges inside player
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopStart),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = when (selectedLesson.liveStatus) {
                                        "AO_VIVO_AGORA" -> Color(0xFFD32F2F)
                                        "AGENDADA" -> ScoreWarning
                                        else -> ScoreExcellent
                                    },
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (selectedLesson.isLive) Icons.Default.RadioButtonChecked else Icons.Default.VideoLibrary,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = when (selectedLesson.liveStatus) {
                                                "AO_VIVO_AGORA" -> "AO VIVO AGORA"
                                                "AGENDADA" -> "LIVE AGENDADA"
                                                else -> "AULA GRAVADA HD"
                                            },
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    }
                                }

                                Surface(
                                    color = Color.Black.copy(alpha = 0.45f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "${selectedLesson.instructorName} • ${selectedLesson.durationMinutes} min • ${selectedLesson.viewsCount} visualizações",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            // Center Blackboard Slide Content
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(horizontal = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = selectedLesson.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                val currentTopic = chapters.getOrNull(selectedChapterIndex.coerceIn(0, (chapters.size - 1).coerceAtLeast(0)))
                                    ?: "Tópico Principal: ${selectedLesson.category}"
                                Surface(
                                    color = GoldBadge.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Em reprodução: $currentTopic",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = GoldBadge,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            // Bottom Player Controls Bar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        isPlaying = !isPlaying
                                        if (isPlaying && playbackProgress < 0.9f) {
                                            playbackProgress = (playbackProgress + 0.15f).coerceAtMost(1f)
                                        }
                                    },
                                    modifier = Modifier
                                        .size(38.dp)
                                        .testTag("video_player_play_pause_button")
                                ) {
                                    Surface(
                                        color = ClickableIconBlueBg,
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                                contentDescription = "Reproduzir ou Pausar",
                                                tint = ClickableIconBlueTint,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }

                                Slider(
                                    value = playbackProgress,
                                    onValueChange = { playbackProgress = it },
                                    modifier = Modifier.weight(1f)
                                )

                                AssistChip(
                                    onClick = {
                                        try {
                                            uriHandler.openUri(selectedLesson.videoUrl)
                                        } catch (_: Exception) {
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = "Abrir Link",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                            contentDescription = null,
                                            tint = GoldBadge,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                )
                            }
                        }

                        // Lesson Description & Metadata
                        Text(
                            text = selectedLesson.description,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        // Interactive Chapter Markers
                        if (chapters.isNotEmpty()) {
                            Text(
                                text = "Capítulos e Tópicos da Aula:",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                chapters.forEachIndexed { idx, chapter ->
                                    FilterChip(
                                        selected = selectedChapterIndex == idx,
                                        onClick = {
                                            selectedChapterIndex = idx
                                            isPlaying = true
                                            playbackProgress = ((idx + 1).toFloat() / chapters.size.coerceAtLeast(1)).coerceIn(0.1f, 1f)
                                        },
                                        label = { Text(chapter, style = MaterialTheme.typography.labelSmall) }
                                    )
                                }
                            }
                        }

                        // Support Material Box
                        if (selectedLesson.supportMaterial.isNotBlank()) {
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "📚 Material de Apoio & Resumo da Aula:",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Text(
                                        text = selectedLesson.supportMaterial,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }

                        // Student & Teacher Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onToggleWatched(selectedLesson) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("mark_video_watched_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (selectedLesson.isWatched) "Aula Assistida ✅" else "Concluir Aula (+90 XP)")
                            }

                            OutlinedButton(
                                onClick = { onCycleLiveStatus(selectedLesson) },
                                modifier = Modifier.testTag("cycle_live_status_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LiveTv,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    when (selectedLesson.liveStatus) {
                                        "AGENDADA" -> "Iniciar Live 🔴"
                                        "AO_VIVO_AGORA" -> "Salvar Gravação"
                                        else -> "Reabrir Live"
                                    }
                                )
                            }
                        }

                        HorizontalDivider()

                        // Interactive Live Chat / Class Q&A
                        Text(
                            text = "💬 Chat da Aula & Plantão de Dúvidas (${chatMessages.size}):",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )

                        chatMessages.forEach { (author, msg) ->
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "$author:",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = msg,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = chatInput,
                                onValueChange = { chatInput = it },
                                label = { Text("Envie sua dúvida ou comentário na aula...") },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("video_lesson_chat_input"),
                                singleLine = true
                            )
                            IconButton(
                                onClick = {
                                    if (chatInput.isNotBlank()) {
                                        onSendChatMessage(selectedLesson, chatInput)
                                        chatInput = ""
                                    }
                                },
                                modifier = Modifier.testTag("send_video_chat_button")
                            ) {
                                Surface(
                                    color = ClickableIconBlueBg,
                                    shape = RoundedCornerShape(9.dp),
                                    border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.Send,
                                            contentDescription = "Enviar mensagem na aula",
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

        // 4. Filter Bar for Video Lessons Catalog
        item {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "TODAS" to "Todas (${videoLessons.size})",
                    "AO_VIVO" to "Ao Vivo & Agendadas 🔴",
                    "GRAVADAS" to "Aulas Gravadas 🎬",
                    "CONCLUIDAS" to "Assistidas ✅"
                ).forEach { (key, label) ->
                    FilterChip(
                        selected = selectedFilter == key,
                        onClick = { selectedFilter = key },
                        label = { Text(label, fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("video_filter_$key")
                    )
                }
            }
        }

        // 5. Catalog List of Recorded & Live Video Lessons
        items(filteredLessons, key = { it.id }) { lesson ->
            val isSelected = selectedLesson?.id == lesson.id
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activeLessonId = lesson.id
                        selectedChapterIndex = 0
                    }
                    .testTag("video_lesson_card_${lesson.id}"),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    }
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        color = ClickableIconBlueBg,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, ClickableIconBlueBorder),
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (lesson.isLive) Icons.Default.LiveTv else Icons.Default.PlayCircleFilled,
                                contentDescription = null,
                                tint = ClickableIconBlueTint,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = when (lesson.liveStatus) {
                                    "AO_VIVO_AGORA" -> Color(0xFFD32F2F)
                                    "AGENDADA" -> ScoreWarning
                                    else -> ScoreExcellent
                                },
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = when (lesson.liveStatus) {
                                        "AO_VIVO_AGORA" -> "AO VIVO 🔴"
                                        "AGENDADA" -> "AGENDADA 📅"
                                        else -> "GRAVADA 🎬"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "${lesson.category} • ${lesson.durationMinutes} min",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = lesson.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${lesson.instructorName} • ${lesson.scheduledDateTime}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (videoLessons.size > 1) {
                        IconButton(onClick = { onDeleteLesson(lesson.id) }) {
                            Surface(
                                color = ClickableIconBlueBg,
                                shape = RoundedCornerShape(9.dp),
                                border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.DeleteOutline,
                                        contentDescription = "Remover vídeo aula",
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
}
