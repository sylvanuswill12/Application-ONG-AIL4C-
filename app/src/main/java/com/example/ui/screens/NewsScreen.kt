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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import com.example.ui.components.AppFooter
import com.example.ui.components.NewsCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.SocialFeedSection
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.Ail4cViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    val filteredNews by viewModel.filteredNews.collectAsStateWithLifecycle()
    val searchQuery by viewModel.newsSearchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.newsCategoryFilter.collectAsStateWithLifecycle()

    val categories = listOf(
        "Toutes",
        "Jeunesse & Emploi",
        "Salubrité publique",
        "Climat & Forêts",
        "Institutionnel"
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
                        text = "COMMUNICATION & MÉDIAS",
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentOrange,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Actualités & Communiqués",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Restez informé des initiatives, événements et rapports d'impact de l'ONG-AIL4C.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0)
                    )
                }
            }
        }

        // Search Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.newsSearchQuery.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("news_search_input"),
                    placeholder = { Text("Rechercher un article, un mot-clé...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Rechercher",
                            tint = ForestGreenPrimary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.newsSearchQuery.value = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Effacer la recherche"
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        // Filter chips
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = cat == selectedCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.newsCategoryFilter.value = cat },
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
                            modifier = Modifier.testTag("news_filter_${cat.lowercase().replace(" ", "_")}")
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
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredNews.size} article(s) trouvé(s)",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Articles list
        items(filteredNews, key = { it.id }) { article ->
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                NewsCard(
                    article = article,
                    onReadClick = { viewModel.selectedNews.value = article }
                )
            }
        }

        // Social Feed Section
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SocialFeedSection()
        }

        // Footer
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AppFooter(onNavigate = { viewModel.navigateTo(it) })
        }
    }
    }
}
