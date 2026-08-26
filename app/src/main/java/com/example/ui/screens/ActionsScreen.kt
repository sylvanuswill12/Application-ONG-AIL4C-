package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ActionCard
import com.example.ui.components.AppFooter
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenContainer
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.Ail4cViewModel
import com.example.ui.viewmodel.AppDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionsScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    val filteredActions by viewModel.filteredActions.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.actionsCategoryFilter.collectAsStateWithLifecycle()

    val categories = listOf(
        "Toutes",
        "Salubrité publique",
        "Protection environnement",
        "Formations certifiantes",
        "Sensibilisation communautaire",
        "Insertion des jeunes"
    )

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() },
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
        // Banner
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
                    Text(
                        text = "ENGAGEMENT COMMUNAUTAIRE",
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentOrange,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Nos Actions de Terrain",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Salubrité, reboisement, formations certifiantes et mobilisations éco-citoyennes en Côte d'Ivoire.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0)
                    )
                }
            }
        }

        // Category Filter Chips
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null,
                        tint = ForestGreenPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Filtrer par type d'action :",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = cat == selectedCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.actionsCategoryFilter.value = cat },
                            label = {
                                Text(
                                    text = cat,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ForestGreenPrimary,
                                selectedLabelColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier.testTag("action_filter_${cat.lowercase().replace(" ", "_")}")
                        )
                    }
                }
            }
        }

        // Results count
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredActions.size} action(s) répertoriée(s)",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // List of Actions
        items(filteredActions, key = { it.id }) { action ->
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                ActionCard(
                    action = action,
                    onDetailsClick = { viewModel.selectedAction.value = action }
                )
            }
        }

        // Propose or Participate Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ForestGreenContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.VolunteerActivism,
                        contentDescription = null,
                        tint = ForestGreenPrimary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Envie de participer à la prochaine action ?",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenDark,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Rejoignez nos équipes de volontaires sur le terrain lors des prochaines journées de salubrité ou de reboisement.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ForestGreenDark.copy(alpha = 0.9f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = { viewModel.openVolunteerDialog("Prochaines actions de terrain AIL4C") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ForestGreenPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("S'inscrire comme bénévole")
                    }
                }
            }
        }

        // Footer
        item {
            AppFooter(onNavigate = { viewModel.navigateTo(it) })
        }
    }
    }
}
