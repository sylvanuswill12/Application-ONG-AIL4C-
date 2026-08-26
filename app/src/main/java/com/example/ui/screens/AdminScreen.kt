package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.ActionItem
import com.example.data.model.ContactMessage
import com.example.data.model.NewsArticle
import com.example.data.model.OpportunityItem
import com.example.data.model.ProjectItem
import com.example.data.model.VolunteerApplication
import com.example.ui.components.AppFooter
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenContainer
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenLight
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.AdminConfig
import com.example.ui.viewmodel.Ail4cViewModel
import com.example.ui.viewmodel.AppDestination

@Composable
fun AdminScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    val isCurrentUserAdmin by viewModel.isCurrentUserAdmin.collectAsStateWithLifecycle()
    val currentUserProfile by viewModel.currentUserProfile.collectAsStateWithLifecycle()
    val isAdminUnlocked by viewModel.isAdminUnlocked.collectAsStateWithLifecycle()

    var adminPasswordInput by remember { mutableStateOf("") }
    var adminPasswordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // SECURITY CHECK 1: Only atchouyaosylvain59@gmail.com and ail4c03@gmail.com
    if (!isCurrentUserAdmin) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFEF2F2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFFDC2626),
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Accès Restreint",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF991B1B)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "L'espace d'administration et les fonctionnalités de modification en temps réel sont strictement réservés à la Direction AIL4C.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Adresses e-mails autorisées :",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreenDark
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "• atchouyaosylvain59@gmail.com\n• ail4c03@gmail.com",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF334155),
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.navigateTo(AppDestination.HOME) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_restricted_back_home"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary)
                    ) {
                        Text(
                            text = "Retour à l'accueil",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { viewModel.logout() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("admin_restricted_relogin"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Se connecter avec un compte admin",
                            fontWeight = FontWeight.SemiBold,
                            color = ForestGreenDark
                        )
                    }
                }
            }
        }
        return
    }

    // SECURITY CHECK 2: Password check AIL4CCI
    if (!isAdminUnlocked) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(AccentOrange.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = AccentOrange,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Sécurité Administrateur AIL4C",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenDark,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Compte habilité : ${currentUserProfile?.email ?: ""}",
                        style = MaterialTheme.typography.labelMedium,
                        color = ForestGreenPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Veuillez saisir le mot de passe d'administration (AIL4CCI) pour déverrouiller l'accès aux modifications et ajouts en temps réel.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    OutlinedTextField(
                        value = adminPasswordInput,
                        onValueChange = {
                            adminPasswordInput = it
                            passwordError = null
                        },
                        label = { Text("Mot de passe d'administration") },
                        placeholder = { Text("AIL4CCI") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = ForestGreenPrimary) },
                        trailingIcon = {
                            IconButton(onClick = { adminPasswordVisible = !adminPasswordVisible }) {
                                Icon(
                                    imageVector = if (adminPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (adminPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        isError = passwordError != null,
                        supportingText = passwordError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_gate_password_input"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ForestGreenPrimary,
                            focusedLabelColor = ForestGreenPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            if (viewModel.verifyAdminPassword(adminPasswordInput)) {
                                passwordError = null
                            } else {
                                passwordError = "Mot de passe incorrect. Le mot de passe requis est AIL4CCI."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("admin_gate_unlock_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AccentOrange)
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Déverrouiller l'Espace Administrateur",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextButton(
                        onClick = { viewModel.navigateTo(AppDestination.HOME) }
                    ) {
                        Text(
                            text = "Annuler et retourner à l'accueil",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        return
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "Messages reçus",
        "Candidatures",
        "Actions terrain",
        "Projets d'impact",
        "Actualités & Presse",
        "Opportunités & Stages",
        "Mises à jour APK"
    )

    val contactMessages by viewModel.contactMessages.collectAsStateWithLifecycle()
    val volunteerApps by viewModel.volunteerApplications.collectAsStateWithLifecycle()
    val allActions by viewModel.allActions.collectAsStateWithLifecycle()
    val allProjects by viewModel.allProjects.collectAsStateWithLifecycle()
    val allNews by viewModel.allNews.collectAsStateWithLifecycle()
    val allOpportunities by viewModel.allOpportunities.collectAsStateWithLifecycle()

    // Dialog states for Add
    var showAddActionDialog by remember { mutableStateOf(false) }
    var showAddProjectDialog by remember { mutableStateOf(false) }
    var showAddNewsDialog by remember { mutableStateOf(false) }
    var showAddOpportunityDialog by remember { mutableStateOf(false) }

    // Dialog states for Edit
    var editingAction by remember { mutableStateOf<ActionItem?>(null) }
    var editingProject by remember { mutableStateOf<ProjectItem?>(null) }
    var editingNews by remember { mutableStateOf<NewsArticle?>(null) }
    var editingOpportunity by remember { mutableStateOf<OpportunityItem?>(null) }

    // Delete confirmation state
    var itemToDeleteDesc by remember { mutableStateOf<Pair<String, () -> Unit>?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Institutional Admin Banner
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ForestGreenDark
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(AccentOrange.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = null,
                                tint = AccentOrange,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "ESPACE ADMINISTRATION & GESTION INTÉGRALE",
                            style = MaterialTheme.typography.labelMedium,
                            color = AccentOrange,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Tableau de Bord ONG-AIL4C",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Gérez l'ensemble des informations, modifiez les textes, descriptions, lieux et visuels de vos actions, projets, articles et opportunités.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0),
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x33000000))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF4ADE80))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Cloud Firebase & Database Synchronisés en Temps Réel",
                                color = Color(0xFFD1FAE5),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(AccentOrange.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = AccentOrange,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Session Admin Active : ${currentUserProfile?.email ?: ""}",
                                color = Color(0xFFFFEDD5),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Quick Stats Summary Strip
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    AdminStatBadge(label = "Messages", count = contactMessages.size.toString())
                    AdminStatBadge(label = "Candidatures", count = volunteerApps.size.toString())
                    AdminStatBadge(label = "Actions", count = allActions.size.toString())
                    AdminStatBadge(label = "Projets", count = allProjects.size.toString())
                    AdminStatBadge(label = "News", count = allNews.size.toString())
                }
            }
        }

        // Navigation Tabs
        item {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = ForestGreenPrimary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedTab == index) ForestGreenPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }
        }

        // TAB 0 : Messages reçus
        if (selectedTab == 0) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = "Total : ${contactMessages.size} message(s) reçu(s)",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenPrimary
                    )
                }
            }

            if (contactMessages.isEmpty()) {
                item {
                    EmptyStateCard(text = "Aucun message reçu pour le moment via le formulaire de contact.")
                }
            } else {
                items(contactMessages, key = { it.id }) { msg ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = msg.fullName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                IconButton(
                                    onClick = {
                                        itemToDeleteDesc = Pair("le message de ${msg.fullName}") {
                                            viewModel.deleteContactMessage(msg)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Supprimer",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                            Text(
                                text = "📧 ${msg.email}  |  📞 ${msg.phone.ifBlank { "Non renseigné" }}",
                                style = MaterialTheme.typography.labelMedium,
                                color = AccentOrange,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Objet : ${msg.subject}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = msg.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // TAB 1 : Candidatures Bénévoles
        if (selectedTab == 1) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = "Total : ${volunteerApps.size} candidature(s) enregistrée(s)",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenPrimary
                    )
                }
            }

            if (volunteerApps.isEmpty()) {
                item {
                    EmptyStateCard(text = "Aucune candidature reçue pour le moment.")
                }
            } else {
                items(volunteerApps, key = { it.id }) { app ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = app.fullName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                IconButton(
                                    onClick = {
                                        itemToDeleteDesc = Pair("la candidature de ${app.fullName}") {
                                            viewModel.deleteVolunteerApplication(app)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Supprimer",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                            Text(
                                text = "🎯 Cible : ${app.opportunityTitle}",
                                style = MaterialTheme.typography.labelMedium,
                                color = ForestGreenPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "📧 ${app.email}  |  📞 ${app.phone}  |  📍 ${app.city}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (app.motivation.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Motivations : ${app.motivation}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }

        // TAB 2 : Actions Management (Ajout, Modification, Suppression)
        if (selectedTab == 2) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${allActions.size} action(s) terrain",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenPrimary
                        )
                        Text(
                            text = "Modifiez le titre, le lieu, la date ou le descriptif",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { showAddActionDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ajouter")
                    }
                }
            }

            items(allActions, key = { it.id }) { action ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = action.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "📍 ${action.location}  •  🗓 ${action.date}  •  🏷 ${action.category}",
                                style = MaterialTheme.typography.bodySmall,
                                color = ForestGreenPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = action.shortDescription,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { editingAction = action },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = ForestGreenContainer.copy(alpha = 0.5f)
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Modifier",
                                    tint = ForestGreenPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            IconButton(
                                onClick = {
                                    itemToDeleteDesc = Pair("l'action « ${action.title} »") {
                                        viewModel.deleteAction(action)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Supprimer",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // TAB 3 : Projects Management (Ajout, Modification, Progression)
        if (selectedTab == 3) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${allProjects.size} projet(s) structurant(s)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenPrimary
                        )
                        Text(
                            text = "Modifiez la progression (%), statut, lieu et description",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { showAddProjectDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ajouter")
                    }
                }
            }

            items(allProjects, key = { it.id }) { project ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = project.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Statut : ${project.status}  •  Progression : ${project.progressPercent}%  •  📍 ${project.location}",
                                style = MaterialTheme.typography.bodySmall,
                                color = ForestGreenPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = project.shortDescription,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { editingProject = project },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = ForestGreenContainer.copy(alpha = 0.5f)
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Modifier",
                                    tint = ForestGreenPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            IconButton(
                                onClick = {
                                    itemToDeleteDesc = Pair("le projet « ${project.title} »") {
                                        viewModel.deleteProject(project)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Supprimer",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // TAB 4 : News Management (Ajout, Modification, Suppression)
        if (selectedTab == 4) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${allNews.size} article(s) publié(s)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenPrimary
                        )
                        Text(
                            text = "Mettez à jour les communiqués et nouvelles",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { showAddNewsDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Publier")
                    }
                }
            }

            items(allNews, key = { it.id }) { news ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = news.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "🏷 ${news.category}  •  🗓 ${news.date}  •  ✍️ ${news.author}",
                                style = MaterialTheme.typography.bodySmall,
                                color = AccentOrange,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = news.summary,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { editingNews = news },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = ForestGreenContainer.copy(alpha = 0.5f)
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Modifier",
                                    tint = ForestGreenPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            IconButton(
                                onClick = {
                                    itemToDeleteDesc = Pair("l'actualité « ${news.title} »") {
                                        viewModel.deleteNews(news)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Supprimer",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // TAB 5 : Opportunities Management
        if (selectedTab == 5) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${allOpportunities.size} opportunité(s) & stage(s)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenPrimary
                        )
                        Text(
                            text = "Gérez les appels à projets, formations et stages",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { showAddOpportunityDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ajouter")
                    }
                }
            }

            items(allOpportunities, key = { it.id }) { opp ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = opp.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "🏷 Type : ${opp.type}  •  ⏳ Date limite : ${opp.deadline}  •  📍 ${opp.location}",
                                style = MaterialTheme.typography.bodySmall,
                                color = ForestGreenPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = opp.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { editingOpportunity = opp },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = ForestGreenContainer.copy(alpha = 0.5f)
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Modifier",
                                    tint = ForestGreenPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            IconButton(
                                onClick = {
                                    itemToDeleteDesc = Pair("l'opportunité « ${opp.title} »") {
                                        viewModel.deleteOpportunity(opp)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Supprimer",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // TAB 6 : Mises à jour & Diffusion APK
        if (selectedTab == 6) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(ForestGreenPrimary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SystemUpdate,
                                    contentDescription = null,
                                    tint = ForestGreenPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Diffusion & Mises à jour sans désinstallation",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Gestion des versions et notifications in-app",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = ForestGreenDark.copy(alpha = 0.05f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "🚀 Comment mettre à jour vos utilisateurs facilement ?",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = ForestGreenPrimary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "1. Lors de l'envoi d'un nouvel APK (via WhatsApp, Web ou GitHub Releases), vos utilisateurs cliquent simplement sur le fichier téléchargé.\n\n2. Android affiche automatiquement « Mettre à jour ». Vos utilisateurs n'ont jamais besoin de désinstaller l'application.\n\n3. Toutes leurs données, historiques et comptes restent intacts sur leurs smartphones.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 18.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.checkForUpdates(isUserTriggered = true)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tester l'alerte de mise à jour In-App", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Footer
        item {
            Spacer(modifier = Modifier.height(24.dp))
            AppFooter(onNavigate = { viewModel.navigateTo(it) })
        }
    }

    // CREATE ACTION DIALOG
    if (showAddActionDialog) {
        ActionEditorDialog(
            initialAction = null,
            onDismiss = { showAddActionDialog = false },
            onSave = {
                viewModel.addAction(it)
                showAddActionDialog = false
            }
        )
    }

    // EDIT ACTION DIALOG
    editingAction?.let { action ->
        ActionEditorDialog(
            initialAction = action,
            onDismiss = { editingAction = null },
            onSave = { updated ->
                viewModel.updateAction(updated)
                editingAction = null
            }
        )
    }

    // CREATE PROJECT DIALOG
    if (showAddProjectDialog) {
        ProjectEditorDialog(
            initialProject = null,
            onDismiss = { showAddProjectDialog = false },
            onSave = {
                viewModel.addProject(it)
                showAddProjectDialog = false
            }
        )
    }

    // EDIT PROJECT DIALOG
    editingProject?.let { project ->
        ProjectEditorDialog(
            initialProject = project,
            onDismiss = { editingProject = null },
            onSave = { updated ->
                viewModel.updateProject(updated)
                editingProject = null
            }
        )
    }

    // CREATE NEWS DIALOG
    if (showAddNewsDialog) {
        NewsEditorDialog(
            initialNews = null,
            onDismiss = { showAddNewsDialog = false },
            onSave = {
                viewModel.addNews(it)
                showAddNewsDialog = false
            }
        )
    }

    // EDIT NEWS DIALOG
    editingNews?.let { news ->
        NewsEditorDialog(
            initialNews = news,
            onDismiss = { editingNews = null },
            onSave = { updated ->
                viewModel.updateNews(updated)
                editingNews = null
            }
        )
    }

    // CREATE OPPORTUNITY DIALOG
    if (showAddOpportunityDialog) {
        OpportunityEditorDialog(
            initialOpportunity = null,
            onDismiss = { showAddOpportunityDialog = false },
            onSave = {
                viewModel.addOpportunity(it)
                showAddOpportunityDialog = false
            }
        )
    }

    // EDIT OPPORTUNITY DIALOG
    editingOpportunity?.let { opp ->
        OpportunityEditorDialog(
            initialOpportunity = opp,
            onDismiss = { editingOpportunity = null },
            onSave = { updated ->
                viewModel.updateOpportunity(updated)
                editingOpportunity = null
            }
        )
    }

    // DELETE CONFIRMATION DIALOG
    itemToDeleteDesc?.let { (desc, onConfirm) ->
        AlertDialog(
            onDismissRequest = { itemToDeleteDesc = null },
            title = { Text("Confirmer la suppression", fontWeight = FontWeight.Bold) },
            text = { Text("Êtes-vous sûr de vouloir supprimer définitivement $desc ? Cette action est irréversible.") },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        itemToDeleteDesc = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Supprimer")
                }
            },
            dismissButton = {
                TextButton(onClick = { itemToDeleteDesc = null }) {
                    Text("Annuler")
                }
            }
        )
    }
}

// ==========================================
// FORM DIALOGS (ADD & EDIT with Image selection)
// ==========================================

@Composable
private fun ActionEditorDialog(
    initialAction: ActionItem?,
    onDismiss: () -> Unit,
    onSave: (ActionItem) -> Unit
) {
    val isEditing = initialAction != null
    var title by remember { mutableStateOf(initialAction?.title ?: "") }
    var category by remember { mutableStateOf(initialAction?.category ?: "Salubrité publique") }
    var shortDesc by remember { mutableStateOf(initialAction?.shortDescription ?: "") }
    var fullStory by remember { mutableStateOf(initialAction?.fullStory ?: "") }
    var location by remember { mutableStateOf(initialAction?.location ?: "Bouaké, Côte d'Ivoire") }
    var date by remember { mutableStateOf(initialAction?.date ?: "Mars 2026") }
    var beneficiaries by remember { mutableStateOf(initialAction?.beneficiariesCount ?: "250+ participants") }
    var selectedImageRes by remember { mutableIntStateOf(initialAction?.imageRes ?: R.drawable.img_salubrite_ville) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxSize(0.9f)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEditing) "Modifier l'action de terrain" else "Ajouter une action de terrain",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenPrimary
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Fermer")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Titre de l'action *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Catégorie (ex: Salubrité, Reboisement, Formation...)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text("Lieu (ex: Bouaké)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = date,
                            onValueChange = { date = it },
                            label = { Text("Date (ex: Mars 2026)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = beneficiaries,
                        onValueChange = { beneficiaries = it },
                        label = { Text("Bénéficiaires / Impact (ex: 500+ participants)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Image / Visuel de l'action :",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ImageSelectorRow(
                        selectedRes = selectedImageRes,
                        onSelect = { selectedImageRes = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = shortDesc,
                        onValueChange = { shortDesc = it },
                        label = { Text("Description courte / Résumé *") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = fullStory,
                        onValueChange = { fullStory = it },
                        label = { Text("Récit complet et détails de l'action") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 6
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Annuler")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank() && shortDesc.isNotBlank()) {
                                onSave(
                                    ActionItem(
                                        id = initialAction?.id ?: 0,
                                        title = title.trim(),
                                        category = category.trim(),
                                        shortDescription = shortDesc.trim(),
                                        fullStory = fullStory.ifBlank { shortDesc }.trim(),
                                        location = location.trim(),
                                        date = date.trim(),
                                        beneficiariesCount = beneficiaries.trim(),
                                        imageRes = selectedImageRes,
                                        isPublished = true
                                    )
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary)
                    ) {
                        Text(if (isEditing) "Mettre à jour" else "Enregistrer l'action")
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectEditorDialog(
    initialProject: ProjectItem?,
    onDismiss: () -> Unit,
    onSave: (ProjectItem) -> Unit
) {
    val isEditing = initialProject != null
    var title by remember { mutableStateOf(initialProject?.title ?: "") }
    var domain by remember { mutableStateOf(initialProject?.domain ?: "Transition écologique") }
    var shortDesc by remember { mutableStateOf(initialProject?.shortDescription ?: "") }
    var fullDesc by remember { mutableStateOf(initialProject?.fullDescription ?: "") }
    var status by remember { mutableStateOf(initialProject?.status ?: "En cours") }
    var dateRange by remember { mutableStateOf(initialProject?.dateRange ?: "2026 - 2028") }
    var location by remember { mutableStateOf(initialProject?.location ?: "Bouaké & Régions") }
    var progressPercent by remember { mutableFloatStateOf(initialProject?.progressPercent?.toFloat() ?: 35f) }
    var targetBeneficiaries by remember { mutableStateOf(initialProject?.targetBeneficiaries ?: "5 000 jeunes") }
    var partnersMention by remember { mutableStateOf(initialProject?.partnersMention ?: "Partenaires AIL4C") }
    var selectedImageRes by remember { mutableIntStateOf(initialProject?.imageRes ?: R.drawable.img_reboisement) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxSize(0.9f)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEditing) "Modifier le projet" else "Ajouter un projet",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenPrimary
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Fermer")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Titre du projet *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = domain,
                            onValueChange = { domain = it },
                            label = { Text("Pôle / Domaine") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = status,
                            onValueChange = { status = it },
                            label = { Text("Statut (En cours / Terminé)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text("Lieu") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = dateRange,
                            onValueChange = { dateRange = it },
                            label = { Text("Période") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Progress Slider
                    Text(
                        text = "Progression du projet : ${progressPercent.toInt()}%",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenPrimary
                    )
                    Slider(
                        value = progressPercent,
                        onValueChange = { progressPercent = it },
                        valueRange = 0f..100f,
                        steps = 20,
                        colors = SliderDefaults.colors(
                            thumbColor = ForestGreenPrimary,
                            activeTrackColor = ForestGreenPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = targetBeneficiaries,
                            onValueChange = { targetBeneficiaries = it },
                            label = { Text("Bénéficiaires cibles") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = partnersMention,
                            onValueChange = { partnersMention = it },
                            label = { Text("Partenaires") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Image / Illustration du projet :",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ImageSelectorRow(
                        selectedRes = selectedImageRes,
                        onSelect = { selectedImageRes = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = shortDesc,
                        onValueChange = { shortDesc = it },
                        label = { Text("Résumé du projet *") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = fullDesc,
                        onValueChange = { fullDesc = it },
                        label = { Text("Description complète et livrables") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 6
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Annuler")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank() && shortDesc.isNotBlank()) {
                                onSave(
                                    ProjectItem(
                                        id = initialProject?.id ?: 0,
                                        title = title.trim(),
                                        domain = domain.trim(),
                                        shortDescription = shortDesc.trim(),
                                        fullDescription = fullDesc.ifBlank { shortDesc }.trim(),
                                        status = status.trim(),
                                        dateRange = dateRange.trim(),
                                        location = location.trim(),
                                        progressPercent = progressPercent.toInt(),
                                        imageRes = selectedImageRes,
                                        targetBeneficiaries = targetBeneficiaries.trim(),
                                        partnersMention = partnersMention.trim(),
                                        isPublished = true
                                    )
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary)
                    ) {
                        Text(if (isEditing) "Mettre à jour" else "Enregistrer le projet")
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsEditorDialog(
    initialNews: NewsArticle?,
    onDismiss: () -> Unit,
    onSave: (NewsArticle) -> Unit
) {
    val isEditing = initialNews != null
    var title by remember { mutableStateOf(initialNews?.title ?: "") }
    var category by remember { mutableStateOf(initialNews?.category ?: "Institutionnel") }
    var date by remember { mutableStateOf(initialNews?.date ?: "Mars 2026") }
    var author by remember { mutableStateOf(initialNews?.author ?: "Bureau Exécutif ONG-AIL4C") }
    var readTime by remember { mutableStateOf(initialNews?.readTime ?: "3 min de lecture") }
    var summary by remember { mutableStateOf(initialNews?.summary ?: "") }
    var fullContent by remember { mutableStateOf(initialNews?.fullContent ?: "") }
    var selectedImageRes by remember { mutableIntStateOf(initialNews?.imageRes ?: R.drawable.img_formation_vert) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxSize(0.9f)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEditing) "Modifier l'article" else "Publier une actualité",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenPrimary
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Fermer")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Titre de l'actualité *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("Catégorie") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = date,
                            onValueChange = { date = it },
                            label = { Text("Date") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = author,
                            onValueChange = { author = it },
                            label = { Text("Auteur / Source") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = readTime,
                            onValueChange = { readTime = it },
                            label = { Text("Temps de lecture") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Image de couverture de l'article :",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ImageSelectorRow(
                        selectedRes = selectedImageRes,
                        onSelect = { selectedImageRes = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = summary,
                        onValueChange = { summary = it },
                        label = { Text("Résumé / Chapeau *") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = fullContent,
                        onValueChange = { fullContent = it },
                        label = { Text("Contenu complet de l'article") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        maxLines = 8
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Annuler")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank() && summary.isNotBlank()) {
                                onSave(
                                    NewsArticle(
                                        id = initialNews?.id ?: 0,
                                        title = title.trim(),
                                        category = category.trim(),
                                        date = date.trim(),
                                        author = author.trim(),
                                        readTime = readTime.trim(),
                                        summary = summary.trim(),
                                        fullContent = fullContent.ifBlank { summary }.trim(),
                                        imageRes = selectedImageRes,
                                        isPublished = true
                                    )
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary)
                    ) {
                        Text(if (isEditing) "Mettre à jour" else "Publier l'actualité")
                    }
                }
            }
        }
    }
}

@Composable
private fun OpportunityEditorDialog(
    initialOpportunity: OpportunityItem?,
    onDismiss: () -> Unit,
    onSave: (OpportunityItem) -> Unit
) {
    val isEditing = initialOpportunity != null
    var title by remember { mutableStateOf(initialOpportunity?.title ?: "") }
    var type by remember { mutableStateOf(initialOpportunity?.type ?: "Formation") }
    var category by remember { mutableStateOf(initialOpportunity?.category ?: "Transition écologique") }
    var location by remember { mutableStateOf(initialOpportunity?.location ?: "Bouaké & En ligne") }
    var deadline by remember { mutableStateOf(initialOpportunity?.deadline ?: "30 Avril 2026") }
    var placesAvailable by remember { mutableStateOf(initialOpportunity?.placesAvailable ?: "Places limitées") }
    var description by remember { mutableStateOf(initialOpportunity?.description ?: "") }
    var requirements by remember { mutableStateOf(initialOpportunity?.requirements ?: "Motivation et engagement citoyen") }
    var benefits by remember { mutableStateOf(initialOpportunity?.benefits ?: "Attestation de fin de formation et insertion") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxSize(0.9f)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEditing) "Modifier l'opportunité" else "Ajouter une opportunité",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenPrimary
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Fermer")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Titre de l'opportunité *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = type,
                            onValueChange = { type = it },
                            label = { Text("Type (Formation, Bénévolat...)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("Domaine") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text("Lieu (ex: Bouaké & En ligne)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = deadline,
                            onValueChange = { deadline = it },
                            label = { Text("Date limite") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = placesAvailable,
                        onValueChange = { placesAvailable = it },
                        label = { Text("Places disponibles (ex: 50 places)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description de l'opportunité *") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = requirements,
                        onValueChange = { requirements = it },
                        label = { Text("Critères / Prérequis") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = benefits,
                        onValueChange = { benefits = it },
                        label = { Text("Avantages / Débouchés") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Annuler")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank() && description.isNotBlank()) {
                                onSave(
                                    OpportunityItem(
                                        id = initialOpportunity?.id ?: 0,
                                        title = title.trim(),
                                        type = type.trim(),
                                        category = category.trim(),
                                        location = location.trim(),
                                        deadline = deadline.trim(),
                                        placesAvailable = placesAvailable.trim(),
                                        description = description.trim(),
                                        requirements = requirements.trim(),
                                        benefits = benefits.trim(),
                                        isPublished = true
                                    )
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary)
                    ) {
                        Text(if (isEditing) "Mettre à jour" else "Enregistrer l'opportunité")
                    }
                }
            }
        }
    }
}

@Composable
private fun ImageSelectorRow(
    selectedRes: Int,
    onSelect: (Int) -> Unit
) {
    val availableImages = listOf(
        Pair(R.drawable.img_hero_community, "Mobilisation citoyenne"),
        Pair(R.drawable.img_formation_vert, "Formation verte"),
        Pair(R.drawable.img_reboisement, "Reboisement & Arbres"),
        Pair(R.drawable.img_salubrite_ville, "Salubrité urbaine")
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(availableImages) { (resId, name) ->
            val isSelected = resId == selectedRes
            Card(
                onClick = { onSelect(resId) },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) ForestGreenPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ),
                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, ForestGreenPrimary) else null
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isSelected) ForestGreenPrimary else Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Image,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = name,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) ForestGreenPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminStatBadge(label: String, count: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = ForestGreenPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun EmptyStateCard(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(36.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
