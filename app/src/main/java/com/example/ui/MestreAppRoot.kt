package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.UserProfileEntity
import com.example.ui.screens.AccountAndAuthDialog
import com.example.ui.screens.CommunityAndHubScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnAndExercisesScreen
import com.example.ui.screens.NotificationsDialog
import com.example.ui.screens.PerformanceAndProfileScreen
import com.example.ui.screens.PracticeAndAiScreen
import com.example.ui.theme.ClickableIconBlueBg
import com.example.ui.theme.ClickableIconBlueBorder
import com.example.ui.theme.ClickableIconBlueSoftBg
import com.example.ui.theme.ClickableIconBlueTint

data class NavBarItemConfig(
    val destination: MainDestination,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MestreAppRoot(viewModel: MestreViewModel) {
    val currentDestination by viewModel.currentDestination.collectAsStateWithLifecycle()
    val practiceSubTab by viewModel.practiceSubTab.collectAsStateWithLifecycle()
    val learnSubTab by viewModel.learnSubTab.collectAsStateWithLifecycle()
    val hubSubTab by viewModel.hubSubTab.collectAsStateWithLifecycle()
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()

    val userState by viewModel.userProfile.collectAsStateWithLifecycle()
    val user = userState ?: UserProfileEntity()
    val themes by viewModel.themes.collectAsStateWithLifecycle()
    val essays by viewModel.essays.collectAsStateWithLifecycle()
    val progressHistory by viewModel.progressHistory.collectAsStateWithLifecycle()
    val studyTasks by viewModel.studyTasks.collectAsStateWithLifecycle()
    val exerciseAttempts by viewModel.exerciseAttempts.collectAsStateWithLifecycle()
    val communityPosts by viewModel.communityPosts.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val videoLessons by viewModel.videoLessons.collectAsStateWithLifecycle()

    val selectedTheme by viewModel.selectedThemeForWriting.collectAsStateWithLifecycle()
    val essayBeingRewritten by viewModel.essayBeingRewritten.collectAsStateWithLifecycle()
    val latestCorrectedEssay by viewModel.latestCorrectedEssay.collectAsStateWithLifecycle()
    val latestScannedEssay by viewModel.latestScannedEssay.collectAsStateWithLifecycle()
    val latestGeneratedTheme by viewModel.latestGeneratedTheme.collectAsStateWithLifecycle()
    val isAiBusy by viewModel.isAiBusy.collectAsStateWithLifecycle()
    val bannerMessage by viewModel.statusBannerMessage.collectAsStateWithLifecycle()

    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showAccountDialog by remember { mutableStateOf(false) }

    if (currentDestination != MainDestination.HOME) {
        BackHandler {
            viewModel.navigateTo(MainDestination.HOME)
        }
    }

    val navItems = remember {
        listOf(
            NavBarItemConfig(
                MainDestination.HOME,
                R.string.nav_home,
                Icons.Filled.Home,
                Icons.Outlined.Home,
                "nav_item_home"
            ),
            NavBarItemConfig(
                MainDestination.LEARN,
                R.string.nav_learn,
                Icons.AutoMirrored.Filled.MenuBook,
                Icons.AutoMirrored.Outlined.MenuBook,
                "nav_item_learn"
            ),
            NavBarItemConfig(
                MainDestination.PRACTICE,
                R.string.nav_practice,
                Icons.Filled.EditNote,
                Icons.Outlined.EditNote,
                "nav_item_practice"
            ),
            NavBarItemConfig(
                MainDestination.PERFORMANCE,
                R.string.nav_performance,
                Icons.Filled.Analytics,
                Icons.Outlined.Analytics,
                "nav_item_performance"
            ),
            NavBarItemConfig(
                MainDestination.HUB,
                R.string.nav_hub,
                Icons.Filled.Groups,
                Icons.Outlined.Groups,
                "nav_item_hub"
            )
        )
    }

    val unreadCount = notifications.count { !it.isRead }

    if (showNotificationsDialog) {
        NotificationsDialog(
            notifications = notifications,
            onMarkAllRead = { viewModel.markNotificationsRead() },
            onDismiss = { showNotificationsDialog = false }
        )
    }

    if (showAccountDialog) {
        AccountAndAuthDialog(
            user = user,
            onDismiss = { showAccountDialog = false },
            onSaveProfile = { name, email, targetScore, role, provider ->
                viewModel.updateAccountAndProfile(name, email, targetScore, role, provider)
            },
            onPasswordReset = { email ->
                viewModel.requestPasswordRecovery(email)
            }
        )
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = maxWidth >= 680.dp

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Surface(
                                    color = ClickableIconBlueSoftBg,
                                    shape = CircleShape,
                                    border = BorderStroke(1.dp, ClickableIconBlueBg.copy(alpha = 0.45f))
                                ) {
                                    Text(
                                        text = "Meta ${user.targetScore}+",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = ClickableIconBlueBorder,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = { viewModel.toggleDarkTheme() },
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .testTag("theme_toggle_button")
                            ) {
                                Surface(
                                    color = ClickableIconBlueBg,
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                            contentDescription = stringResource(id = R.string.theme_toggle_desc),
                                            tint = ClickableIconBlueTint,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }

                            IconButton(
                                onClick = { showNotificationsDialog = true },
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .testTag("notifications_button")
                            ) {
                                BadgedBox(
                                    badge = {
                                        if (unreadCount > 0) {
                                            Badge { Text("$unreadCount", fontWeight = FontWeight.Bold) }
                                        }
                                    }
                                ) {
                                    Surface(
                                        color = ClickableIconBlueBg,
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(1.dp, ClickableIconBlueBorder),
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.Notifications,
                                                contentDescription = stringResource(id = R.string.notifications_desc),
                                                tint = ClickableIconBlueTint,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Surface(
                                modifier = Modifier
                                    .padding(end = 14.dp, start = 4.dp)
                                    .size(36.dp)
                                    .clickable { showAccountDialog = true }
                                    .testTag("account_profile_button"),
                                shape = RoundedCornerShape(10.dp),
                                color = ClickableIconBlueBg,
                                border = BorderStroke(1.dp, ClickableIconBlueBorder)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = user.avatarInitials,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = ClickableIconBlueTint,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                    androidx.compose.material3.HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            },
            bottomBar = {
                if (!isWideScreen) {
                    Column {
                        androidx.compose.material3.HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 0.dp
                        ) {
                            navItems.forEach { item ->
                                val selected = currentDestination == item.destination
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { viewModel.navigateTo(item.destination) },
                                    icon = {
                                        Surface(
                                            color = if (selected) ClickableIconBlueBg else ClickableIconBlueSoftBg,
                                            shape = RoundedCornerShape(10.dp),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = if (selected) ClickableIconBlueBorder else ClickableIconBlueBg.copy(alpha = 0.45f)
                                            ),
                                            modifier = Modifier.size(width = 40.dp, height = 32.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                                    contentDescription = stringResource(id = item.labelRes),
                                                    tint = if (selected) ClickableIconBlueTint else ClickableIconBlueBorder,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(id = item.labelRes),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Bold,
                                            color = if (selected) ClickableIconBlueBorder else MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            softWrap = false
                                        )
                                    },
                                    modifier = Modifier.testTag(item.testTag)
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (isWideScreen) {
                    NavigationRail(modifier = Modifier.fillMaxHeight()) {
                        navItems.forEach { item ->
                            val selected = currentDestination == item.destination
                            NavigationRailItem(
                                selected = selected,
                                onClick = { viewModel.navigateTo(item.destination) },
                                icon = {
                                    Surface(
                                        color = if (selected) ClickableIconBlueBg else ClickableIconBlueSoftBg,
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = if (selected) ClickableIconBlueBorder else ClickableIconBlueBg.copy(alpha = 0.45f)
                                        ),
                                        modifier = Modifier.size(width = 40.dp, height = 32.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                                contentDescription = stringResource(id = item.labelRes),
                                                tint = if (selected) ClickableIconBlueTint else ClickableIconBlueBorder,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = item.labelRes),
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                modifier = Modifier.testTag(item.testTag)
                            )
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    AnimatedVisibility(visible = bannerMessage != null) {
                        bannerMessage?.let { msg ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = MaterialTheme.shapes.small
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = msg,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(
                                        onClick = { viewModel.clearBannerMessage() },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Surface(
                                            color = ClickableIconBlueBg,
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Fechar aviso",
                                                    tint = ClickableIconBlueTint,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        when (currentDestination) {
                            MainDestination.HOME -> HomeScreen(
                                user = user,
                                essays = essays,
                                studyTasks = studyTasks,
                                exerciseAttempts = exerciseAttempts,
                                isAiBusy = isAiBusy,
                                onStartNowClick = {
                                    viewModel.navigateTo(MainDestination.PRACTICE)
                                    viewModel.selectPracticeSubTab(0)
                                },
                                onInspectEssay = { viewModel.inspectEssayCorrection(it) },
                                onRewriteEssay = { viewModel.startRewriteForEssay(it) },
                                onToggleStudyTask = { viewModel.toggleStudyTask(it) },
                                onRegenerateStudyPlan = { viewModel.regenerateStudyPlanWithAi() },
                                onOpenSimulado = {
                                    viewModel.navigateTo(MainDestination.PRACTICE)
                                    viewModel.selectPracticeSubTab(3)
                                },
                                onOpenScanner = {
                                    viewModel.navigateTo(MainDestination.PRACTICE)
                                    viewModel.selectPracticeSubTab(4)
                                },
                                onOpenVideoLessons = {
                                    viewModel.navigateTo(MainDestination.LEARN)
                                    viewModel.selectLearnSubTab(5)
                                }
                            )

                            MainDestination.LEARN -> LearnAndExercisesScreen(
                                selectedTab = learnSubTab,
                                onSelectTab = { viewModel.selectLearnSubTab(it) },
                                exerciseAttempts = exerciseAttempts,
                                videoLessons = videoLessons,
                                latestGeneratedTheme = latestGeneratedTheme,
                                isAiBusy = isAiBusy,
                                onSubmitObjectiveExercise = { ex, opt ->
                                    viewModel.submitObjectiveExercise(ex, opt)
                                },
                                onSubmitDiscursiveExercise = { ex, text ->
                                    viewModel.submitDiscursiveExercise(ex, text)
                                },
                                onPracticePastEnemTheme = { pastTheme, isSim ->
                                    viewModel.practicePastEnemTheme(pastTheme, isSim)
                                },
                                onGenerateImmediateAiTheme = { cat, exam, focus, style ->
                                    viewModel.generateNewThemeWithAi(cat, exam, focus, style)
                                },
                                onWriteGeneratedTheme = { theme, isSim ->
                                    viewModel.startWritingWithTheme(theme, isSim)
                                },
                                onPostVideoLesson = { title, desc, inst, cat, isLive, status, sched, dur, url, topics, mat ->
                                    viewModel.postNewVideoLesson(title, desc, inst, cat, isLive, status, sched, dur, url, topics, mat)
                                },
                                onToggleVideoWatched = { lesson ->
                                    viewModel.toggleVideoLessonWatched(lesson)
                                },
                                onCycleVideoLiveStatus = { lesson ->
                                    viewModel.cycleVideoLessonLiveStatus(lesson)
                                },
                                onSendVideoChatMessage = { lesson, msg ->
                                    viewModel.sendVideoLessonChatMessage(lesson, msg)
                                },
                                onDeleteVideoLesson = { id ->
                                    viewModel.deleteVideoLesson(id)
                                }
                            )

                            MainDestination.PRACTICE -> PracticeAndAiScreen(
                                selectedTab = practiceSubTab,
                                onSelectTab = { viewModel.selectPracticeSubTab(it) },
                                themes = themes,
                                essays = essays,
                                selectedTheme = selectedTheme,
                                essayBeingRewritten = essayBeingRewritten,
                                latestCorrectedEssay = latestCorrectedEssay,
                                latestScannedEssay = latestScannedEssay,
                                latestGeneratedTheme = latestGeneratedTheme,
                                isAiBusy = isAiBusy,
                                onSelectThemeForWriting = { theme, isSim ->
                                    viewModel.startWritingWithTheme(theme, isSim)
                                },
                                onGenerateThemeWithAi = { cat, exType, focus, style ->
                                    viewModel.generateNewThemeWithAi(cat, exType, focus, style)
                                },
                                onSubmitEssayForAi = { theme, title, content, mins, isSim, parent ->
                                    viewModel.submitEssayForAiCorrection(theme, title, content, mins, isSim, parent)
                                },
                                onSelectEssayToRewrite = { essay ->
                                    viewModel.startRewriteForEssay(essay)
                                },
                                onScanHandwrittenEssay = { b64, theme, customTheme, cat, title, hint ->
                                    viewModel.scanHandwrittenEssayFromPhoto(b64, theme, customTheme, cat, title, hint)
                                }
                            )

                            MainDestination.PERFORMANCE -> PerformanceAndProfileScreen(
                                user = user,
                                essays = essays,
                                progressHistory = progressHistory,
                                studyTasks = studyTasks,
                                onOpenAccountDialog = { showAccountDialog = true },
                                onInspectEssay = { viewModel.inspectEssayCorrection(it) },
                                onRecordHistoricalScore = { title, cat, exam, c1, c2, c3, c4, c5, note ->
                                    viewModel.recordHistoricalEssayScore(title, cat, exam, c1, c2, c3, c4, c5, note)
                                },
                                onDeleteEssay = { viewModel.deleteSavedEssay(it) },
                                onAdvanceMetalRankXp = { viewModel.advanceUserXpForMetalEvolution(350) }
                            )

                            MainDestination.HUB -> CommunityAndHubScreen(
                                selectedTab = hubSubTab,
                                onSelectTab = { viewModel.selectHubSubTab(it) },
                                user = user,
                                posts = communityPosts,
                                essays = essays,
                                themes = themes,
                                onCreatePost = { cat, title, content, quote ->
                                    viewModel.createNewCommunityPost(cat, title, content, quote)
                                },
                                onToggleLike = { viewModel.toggleLikeOnPost(it) },
                                onAddComment = { post, comment ->
                                    viewModel.addCommentOnPost(post, comment)
                                },
                                onSaveTeacherCorrection = { essay, c1, c2, c3, c4, c5, notes ->
                                    viewModel.saveTeacherManualCorrection(essay, c1, c2, c3, c4, c5, notes)
                                },
                                onCreateManualTheme = { title, cat, ex, mot, stat, rep, guid, excl ->
                                    viewModel.createManualTheme(title, cat, ex, mot, stat, rep, guid, excl)
                                },
                                onDeleteTheme = { viewModel.deleteThemeById(it) },
                                onTogglePremium = { viewModel.togglePremiumSubscription() }
                            )
                        }
                    }
                }
            }
        }
    }
}
