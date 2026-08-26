package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ActionDetailModal
import com.example.ui.components.AiAssistantModal
import com.example.ui.components.AppDrawer
import com.example.ui.components.AppHeader
import com.example.ui.components.AppUpdateDialog
import com.example.ui.components.FullScreenImageViewer
import com.example.ui.components.ModernBottomNavBar
import com.example.ui.components.NewsDetailModal
import com.example.ui.components.OpportunityDetailModal
import com.example.ui.components.ProjectDetailModal
import com.example.ui.components.VolunteerApplicationDialog
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.ActionsScreen
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ClimateEnvironmentScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.GalleryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NewsScreen
import com.example.ui.screens.ObjectivesScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProjectsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.YouthEmploymentScreen
import com.example.ui.theme.Ail4cTheme
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.Ail4cViewModel
import com.example.ui.viewmodel.Ail4cViewModelFactory
import com.example.ui.viewmodel.AppDestination
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: Ail4cViewModel by viewModels {
        Ail4cViewModelFactory((application as Ail4cApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ail4cTheme {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: Ail4cViewModel) {
    var isSplashFinished by rememberSaveable { mutableStateOf(false) }

    // Display Animated 3D Splash Video sequence before showing login/main content
    if (!isSplashFinished) {
        SplashScreen(
            onSplashFinished = { isSplashFinished = true }
        )
        return
    }

    val currentUserProfile by viewModel.currentUserProfile.collectAsStateWithLifecycle()
    val isCurrentUserAdmin by viewModel.isCurrentUserAdmin.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentDestination by viewModel.currentDestination.collectAsStateWithLifecycle()
    val selectedAction by viewModel.selectedAction.collectAsStateWithLifecycle()
    val selectedProject by viewModel.selectedProject.collectAsStateWithLifecycle()
    val selectedNews by viewModel.selectedNews.collectAsStateWithLifecycle()
    val selectedOpportunity by viewModel.selectedOpportunity.collectAsStateWithLifecycle()
    val fullscreenGalleryItem by viewModel.fullscreenGalleryItem.collectAsStateWithLifecycle()
    val showVolunteerDialog by viewModel.showVolunteerDialog.collectAsStateWithLifecycle()
    val volunteerTargetTitle by viewModel.volunteerTargetTitle.collectAsStateWithLifecycle()
    val showAiAssistant by viewModel.showAiAssistant.collectAsStateWithLifecycle()
    val showUpdateDialog by viewModel.showUpdateDialog.collectAsStateWithLifecycle()
    val appUpdateInfo by viewModel.appUpdateInfo.collectAsStateWithLifecycle()
    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()

    // Handle toast messages via snackbar
    LaunchedEffect(toastMessage) {
        toastMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearToast()
        }
    }

    // MANDATORY AUTHENTICATION GATE: If not logged in, show AuthScreen first!
    if (currentUserProfile == null) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding ->
            AuthScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(padding)
            )
        }
        return
    }

    // Handle back button: return to Home first if inside another screen, or close drawer if open
    BackHandler(enabled = drawerState.isOpen || currentDestination != AppDestination.HOME) {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else if (currentDestination != AppDestination.HOME) {
            viewModel.navigateTo(AppDestination.HOME)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentDestination = currentDestination,
                userProfile = currentUserProfile,
                onDestinationSelected = { dest ->
                    viewModel.navigateTo(dest)
                },
                onOpenAiAssistant = {
                    viewModel.openAiAssistant()
                },
                onCheckUpdates = {
                    viewModel.checkForUpdates(isUserTriggered = true)
                },
                onLogout = {
                    viewModel.logout()
                },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                },
                isAdmin = isCurrentUserAdmin
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppHeader(
                    onMenuClick = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    },
                    onContactClick = {
                        viewModel.navigateTo(AppDestination.CONTACT)
                    },
                    onAdminClick = {
                        viewModel.navigateTo(AppDestination.ADMIN)
                    },
                    onLogoClick = {
                        viewModel.navigateTo(AppDestination.HOME)
                    },
                    currentDestination = currentDestination,
                    isAdmin = isCurrentUserAdmin
                )
            },
            bottomBar = {
                ModernBottomNavBar(
                    currentDestination = currentDestination,
                    onNavigate = { dest ->
                        viewModel.navigateTo(dest)
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.openAiAssistant() },
                    containerColor = ForestGreenPrimary,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .testTag("fab_ai_assistant")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Assistant IA Écologique",
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            contentWindowInsets = WindowInsets.safeDrawing
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AnimatedContent(
                    targetState = currentDestination,
                    transitionSpec = {
                        (fadeIn(animationSpec = androidx.compose.animation.core.tween(300)) +
                                androidx.compose.animation.scaleIn(initialScale = 0.98f, animationSpec = androidx.compose.animation.core.tween(300)))
                            .togetherWith(
                                fadeOut(animationSpec = androidx.compose.animation.core.tween(250)) +
                                        androidx.compose.animation.scaleOut(targetScale = 0.98f, animationSpec = androidx.compose.animation.core.tween(250))
                            )
                    },
                    label = "ScreenTransition"
                ) { destination ->
                    when (destination) {
                        AppDestination.HOME -> HomeScreen(viewModel = viewModel)
                        AppDestination.ABOUT -> AboutScreen(viewModel = viewModel)
                        AppDestination.OBJECTIVES -> ObjectivesScreen(viewModel = viewModel)
                        AppDestination.ACTIONS -> ActionsScreen(viewModel = viewModel)
                        AppDestination.PROJECTS -> ProjectsScreen(viewModel = viewModel)
                        AppDestination.YOUTH_EMPLOYMENT -> YouthEmploymentScreen(viewModel = viewModel)
                        AppDestination.CLIMATE_ENVIRONMENT -> ClimateEnvironmentScreen(viewModel = viewModel)
                        AppDestination.NEWS -> NewsScreen(viewModel = viewModel)
                        AppDestination.GALLERY -> GalleryScreen(viewModel = viewModel)
                        AppDestination.CONTACT -> ContactScreen(viewModel = viewModel)
                        AppDestination.ADMIN -> AdminScreen(viewModel = viewModel)
                        AppDestination.PROFILE -> ProfileScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }

    // AI Assistant Modal Bottom Sheet
    if (showAiAssistant) {
        AiAssistantModal(
            viewModel = viewModel,
            userName = currentUserProfile?.fullName ?: "Membre AIL4C",
            onDismiss = { viewModel.closeAiAssistant() }
        )
    }

    // Modal Dialogs
    selectedAction?.let { action ->
        ActionDetailModal(
            action = action,
            onDismiss = { viewModel.selectedAction.value = null },
            onVolunteerClick = {
                viewModel.selectedAction.value = null
                viewModel.openVolunteerDialog(action.title)
            }
        )
    }

    selectedProject?.let { project ->
        ProjectDetailModal(
            project = project,
            onDismiss = { viewModel.selectedProject.value = null },
            onPartnerClick = {
                viewModel.selectedProject.value = null
                viewModel.navigateTo(AppDestination.CONTACT)
            }
        )
    }

    selectedNews?.let { news ->
        NewsDetailModal(
            article = news,
            onDismiss = { viewModel.selectedNews.value = null }
        )
    }

    selectedOpportunity?.let { opp ->
        OpportunityDetailModal(
            opportunity = opp,
            onDismiss = { viewModel.selectedOpportunity.value = null },
            onApplyClick = {
                viewModel.selectedOpportunity.value = null
                viewModel.openVolunteerDialog(opp.title)
            }
        )
    }

    fullscreenGalleryItem?.let { galleryItem ->
        FullScreenImageViewer(
            item = galleryItem,
            onDismiss = { viewModel.fullscreenGalleryItem.value = null }
        )
    }

    if (showVolunteerDialog) {
        VolunteerApplicationDialog(
            initialOpportunityTitle = volunteerTargetTitle.ifBlank { "Volontariat & Engagement Général" },
            onDismiss = { viewModel.showVolunteerDialog.value = false },
            onSubmit = { fullName, email, phone, city, domain, motivation ->
                viewModel.submitVolunteerApplication(
                    fullName = fullName,
                    email = email,
                    phone = phone,
                    city = city,
                    opportunityTitle = volunteerTargetTitle,
                    domainOfInterest = domain,
                    motivation = motivation,
                    onSuccess = {
                        viewModel.showVolunteerDialog.value = false
                    },
                    onError = { err ->
                        viewModel.toastMessage.value = err
                    }
                )
            }
        )
    }

    if (showUpdateDialog && appUpdateInfo != null) {
        AppUpdateDialog(
            updateInfo = appUpdateInfo!!,
            currentVersionCode = viewModel.currentAppVersionCode,
            currentVersionName = viewModel.currentAppVersionName,
            onDismiss = { viewModel.dismissUpdateDialog() }
        )
    }
}
