package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.DomainCategory
import com.example.ui.components.ActionCard
import com.example.ui.components.AppFooter
import com.example.ui.components.NewsCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.SocialFeedSection
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenContainer
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenLight
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.Ail4cViewModel
import com.example.ui.viewmodel.AppDestination

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    val publishedActions by viewModel.publishedActions.collectAsStateWithLifecycle()
    val publishedNews by viewModel.publishedNews.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. HERO SECTION
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(440.dp)
            ) {
                // Background real community photo
                Image(
                    painter = painterResource(id = R.drawable.img_hero_community),
                    contentDescription = "Action communautaire environnementale ONG-AIL4C en Côte d'Ivoire",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark elegant gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.65f),
                                    Color.Black.copy(alpha = 0.45f),
                                    Color.Black.copy(alpha = 0.88f)
                                )
                            )
                        )
                )

                // Hero textual content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(AccentOrange)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "ONG-AIL4C • CÔTE D'IVOIRE",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Association Ivoirienne de Lutte Contre le Changement Climatique et le Chômage",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 30.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Engagement citoyen, préservation de l'environnement et autonomisation de la jeunesse en Côte d'Ivoire.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFF1F5F9),
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Two Hero Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { viewModel.navigateTo(AppDestination.ABOUT) },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("hero_discover_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ForestGreenPrimary,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Découvrir l'ONG",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = { viewModel.navigateTo(AppDestination.ACTIONS) },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("hero_actions_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentOrange,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Voir nos actions",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // 2. KEY METRICS STRIP
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ForestGreenDark
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ImpactMetricItem(count = "2 500+", label = "Arbres plantés")
                    ImpactMetricItem(count = "1 200+", label = "Jeunes formés")
                    ImpactMetricItem(count = "8+ Tonnes", label = "Déchets triés")
                    ImpactMetricItem(count = "100%", label = "Engagement")
                }
            }
        }

        // 3. NOTRE ENGAGEMENT SECTION
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                SectionHeader(
                    tagline = "À propos d'AIL4C",
                    title = "Notre engagement",
                    subtitle = "Une réponse concrète aux défis climatiques et à l'insertion des jeunes"
                )

                Text(
                    text = "L'Association Ivoirienne de Lutte Contre le Changement Climatique et le Chômage (ONG-AIL4C) est née d'une conviction profonde : la transition écologique est la plus formidable opportunité pour offrir des emplois dignes et pérennes à la jeunesse ivoirienne.\n\nNous agissons concrètement auprès des communautés, dans les quartiers urbains et les zones rurales, à travers des campagnes de salubrité publique, des formations aux métiers verts, le reboisement participatif et le soutien à l'éco-entrepreneuriat.",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ForestGreenContainer.copy(alpha = 0.5f)
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = ForestGreenPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Nos 3 piliers d'action :",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreenDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        PillarPoint(text = "1. Préservation de l'environnement & résilience climatique")
                        PillarPoint(text = "2. Formation pratique & insertion socio-professionnelle des jeunes")
                        PillarPoint(text = "3. Salubrité publique & dynamisation de l'économie circulaire")
                    }
                }
            }
        }

        // 4. NOS DOMAINES D'INTERVENTION (8 Cartes élégantes)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                SectionHeader(
                    tagline = "Expertise & Pôles",
                    title = "Nos domaines d'intervention",
                    subtitle = "8 domaines stratégiques pour transformer durablement la Côte d'Ivoire"
                )

                Spacer(modifier = Modifier.height(12.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    maxItemsInEachRow = 2
                ) {
                    DomainCategory.entries.forEach { domain ->
                        DomainCard(
                            domain = domain,
                            modifier = Modifier.fillMaxWidth(0.48f)
                        )
                    }
                }
            }
        }

        // 5. ACTIONS PHARES DU TERRAIN PREVIEW
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    SectionHeader(
                        tagline = "Impact réel",
                        title = "Actions récentes sur le terrain",
                        subtitle = "Découvrez nos interventions communautaires en Côte d'Ivoire"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(publishedActions.take(4)) { action ->
                        ActionCard(
                            action = action,
                            onDetailsClick = { viewModel.selectedAction.value = action },
                            modifier = Modifier.width(300.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedButton(
                        onClick = { viewModel.navigateTo(AppDestination.ACTIONS) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Voir toutes nos actions de terrain",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = ForestGreenPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // 6. SOCIAL MEDIA & REAL-TIME FEED (Instagram & Facebook)
        item {
            SocialFeedSection(
                onNavigateToAllNews = { viewModel.navigateTo(AppDestination.NEWS) }
            )
        }

        // 7. CALL TO ACTION BANNER : REJOIGNEZ-NOUS
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = AccentOrange)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.VolunteerActivism,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(44.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Devenir Bénévole ou Partenaire",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Rejoignez le réseau des acteurs du changement. Ensemble, préservons l'environnement et créons des opportunités pour chaque jeune.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.95f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { viewModel.openVolunteerDialog("Engagement Bénévole AIL4C") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = AccentOrange
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Devenir Bénévole",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        OutlinedButton(
                            onClick = { viewModel.navigateTo(AppDestination.CONTACT) },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Nous Contacter",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }

        // 7. ACTUALITÉS RÉCENTES PREVIEW
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                SectionHeader(
                    tagline = "Informations",
                    title = "Dernières actualités",
                    subtitle = "Suivez la vie institutionnelle et les temps forts d'AIL4C"
                )

                Spacer(modifier = Modifier.height(12.dp))

                publishedNews.take(2).forEach { article ->
                    NewsCard(
                        article = article,
                        onReadClick = { viewModel.selectedNews.value = article },
                        modifier = Modifier.padding(bottom = 14.dp)
                    )
                }

                OutlinedButton(
                    onClick = { viewModel.navigateTo(AppDestination.NEWS) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Consulter toutes les actualités",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenPrimary
                    )
                }
            }
        }

        // 8. FOOTER
        item {
            AppFooter(onNavigate = { viewModel.navigateTo(it) })
        }
    }
}

@Composable
private fun ImpactMetricItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count,
            style = MaterialTheme.typography.titleLarge,
            color = AccentOrange,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 11.sp
        )
    }
}

@Composable
private fun PillarPoint(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(vertical = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(AccentOrange)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun DomainCard(
    domain: DomainCategory,
    modifier: Modifier = Modifier
) {
    val domainIcon = when (domain) {
        DomainCategory.CLIMATE_CHANGE -> Icons.Default.Thunderstorm
        DomainCategory.ENVIRONMENT_PROTECTION -> Icons.Default.Forest
        DomainCategory.YOUTH_SUSTAINABILITY -> Icons.Default.Diversity3
        DomainCategory.GREEN_JOBS -> Icons.Default.Work
        DomainCategory.CLIMATE_ENTREPRENEURSHIP -> Icons.Default.Lightbulb
        DomainCategory.CITIZEN_ENGAGEMENT -> Icons.Default.VolunteerActivism
        DomainCategory.PUBLIC_SANITATION -> Icons.Default.Recycling
        DomainCategory.SOCIO_PROFESSIONAL_INSERTION -> Icons.Default.School
    }

    Card(
        modifier = modifier.height(180.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ForestGreenPrimary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = domainIcon,
                    contentDescription = null,
                    tint = ForestGreenPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = domain.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.5.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = domain.shortDesc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.5.sp,
                    lineHeight = 15.sp,
                    maxLines = 3
                )
            }
        }
    }
}
