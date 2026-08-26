package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.UserProfile
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.AdminConfig
import com.example.ui.viewmodel.AppDestination

@Composable
fun AppDrawer(
    currentDestination: AppDestination,
    userProfile: UserProfile?,
    onDestinationSelected: (AppDestination) -> Unit,
    onOpenAiAssistant: () -> Unit,
    onCheckUpdates: () -> Unit = {},
    onLogout: () -> Unit,
    onCloseDrawer: () -> Unit,
    isAdmin: Boolean = false,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ForestGreenDark)
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .padding(4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_custom_logo_1787715328062),
                                contentDescription = "Logo ONG-AIL4C",
                                modifier = Modifier.size(42.dp)
                            )
                        }

                        // Connected User Avatar Badge
                        userProfile?.let { user ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0x33000000))
                                    .clickable {
                                        onDestinationSelected(AppDestination.PROFILE)
                                        onCloseDrawer()
                                    }
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = user.fullName.split(" ").firstOrNull() ?: "Profil",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "ONG-AIL4C",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Alliance Internationale des Leaders pour le Climat",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Président : SENIN TCHOUMOU ESDRAS GEMIEL",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFFDE68A),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp
                    )
                }
            }

            // User Info Banner
            userProfile?.let { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(ForestGreenPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.fullName.take(1).uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = user.fullName,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreenDark
                            )
                            Text(
                                text = user.email.ifBlank { user.phone },
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B),
                                fontSize = 11.sp
                            )
                        }
                        IconButton(
                            onClick = onLogout,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.ExitToApp,
                                contentDescription = "Déconnexion",
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // AI Chat Assistant Button
            NavigationDrawerItem(
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Assistant IA AIL4C",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(AccentOrange)
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text("IA", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Assistant IA",
                        tint = ForestGreenPrimary
                    )
                },
                selected = false,
                onClick = {
                    onOpenAiAssistant()
                    onCloseDrawer()
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color(0xFFECFDF5)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .testTag("nav_item_assistant_ia")
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Navigation Items
            DrawerItemRow(
                title = "Accueil",
                icon = Icons.Default.Home,
                selected = currentDestination == AppDestination.HOME,
                onClick = {
                    onDestinationSelected(AppDestination.HOME)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "À propos de l'ONG",
                icon = Icons.Default.Info,
                selected = currentDestination == AppDestination.ABOUT,
                onClick = {
                    onDestinationSelected(AppDestination.ABOUT)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "Nos objectifs",
                icon = Icons.Default.TrackChanges,
                selected = currentDestination == AppDestination.OBJECTIVES,
                onClick = {
                    onDestinationSelected(AppDestination.OBJECTIVES)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "Nos actions de terrain",
                icon = Icons.Default.VolunteerActivism,
                selected = currentDestination == AppDestination.ACTIONS,
                onClick = {
                    onDestinationSelected(AppDestination.ACTIONS)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "Nos projets",
                icon = Icons.Default.FolderSpecial,
                selected = currentDestination == AppDestination.PROJECTS,
                onClick = {
                    onDestinationSelected(AppDestination.PROJECTS)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "Jeunesse & Emploi",
                icon = Icons.Default.School,
                selected = currentDestination == AppDestination.YOUTH_EMPLOYMENT,
                onClick = {
                    onDestinationSelected(AppDestination.YOUTH_EMPLOYMENT)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "Climat & Environnement",
                icon = Icons.Default.Eco,
                selected = currentDestination == AppDestination.CLIMATE_ENVIRONMENT,
                onClick = {
                    onDestinationSelected(AppDestination.CLIMATE_ENVIRONMENT)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "Actualités & Presse",
                icon = Icons.Default.Newspaper,
                selected = currentDestination == AppDestination.NEWS,
                onClick = {
                    onDestinationSelected(AppDestination.NEWS)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "Galerie photos",
                icon = Icons.Default.Collections,
                selected = currentDestination == AppDestination.GALLERY,
                onClick = {
                    onDestinationSelected(AppDestination.GALLERY)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "Contact & Coordonnées",
                icon = Icons.Default.Call,
                selected = currentDestination == AppDestination.CONTACT,
                onClick = {
                    onDestinationSelected(AppDestination.CONTACT)
                    onCloseDrawer()
                }
            )

            DrawerItemRow(
                title = "Mon Profil",
                icon = Icons.Default.Person,
                selected = currentDestination == AppDestination.PROFILE,
                onClick = {
                    onDestinationSelected(AppDestination.PROFILE)
                    onCloseDrawer()
                }
            )

            // Only users with authorized admin emails can see the Admin option
            if (isAdmin) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                DrawerItemRow(
                    title = "Espace Administration",
                    icon = Icons.Default.AdminPanelSettings,
                    selected = currentDestination == AppDestination.ADMIN,
                    onClick = {
                        onDestinationSelected(AppDestination.ADMIN)
                        onCloseDrawer()
                    },
                    accent = true
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            DrawerItemRow(
                title = "Vérifier Mise à jour",
                icon = Icons.Default.SystemUpdate,
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onCheckUpdates()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DrawerItemRow(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    accent: Boolean = false
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) {
                    if (accent) AccentOrange else ForestGreenPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        },
        selected = selected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = if (accent) AccentOrange.copy(alpha = 0.12f) else ForestGreenPrimary.copy(alpha = 0.12f),
            selectedTextColor = if (accent) AccentOrange else ForestGreenPrimary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .testTag("nav_item_${title.lowercase().replace(" ", "_")}")
    )
}
