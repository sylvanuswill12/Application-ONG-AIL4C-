package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.AppDestination

@Composable
fun AppDrawer(
    currentDestination: AppDestination,
    onDestinationSelected: (AppDestination) -> Unit,
    onCloseDrawer: () -> Unit,
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
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .padding(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_logo_ail4c),
                            contentDescription = "Logo ONG-AIL4C",
                            modifier = Modifier.size(46.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "ONG-AIL4C",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Association Ivoirienne de Lutte Contre le Changement Climatique et le Chômage",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                title = "Jeunesse & Emploi vert",
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

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
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
