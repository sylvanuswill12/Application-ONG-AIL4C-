package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VolunteerActivism
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.DomainCategory
import com.example.ui.components.AppFooter
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
fun AboutScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Institutional Top Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ForestGreenDark
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(6.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_logo_ail4c),
                            contentDescription = "Logo ONG-AIL4C",
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "À PROPOS DE L'ORGANISATION",
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentOrange,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "ONG-AIL4C",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Association Ivoirienne de Lutte Contre le Changement Climatique et le Chômage",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFE2E8F0),
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Notre Histoire, Mission, Vision
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                SectionHeader(
                    tagline = "Fondements",
                    title = "Notre Histoire & Vocation",
                    subtitle = "Une mobilisation citoyenne et environnementale en Côte d'Ivoire"
                )

                Text(
                    text = "L'ONG-AIL4C (Association Ivoirienne de Lutte Contre le Changement Climatique et le Chômage) est née de la volonté d'acteurs engagés de la société civile ivoirienne de répondre simultanément à deux urgences majeures du XXIe siècle : le dérèglement climatique qui frappe nos communautés et nos écosystèmes, et le défi de l'emploi des jeunes.\n\nImplantée activement à Bouaké (notamment à l'Université Alassane Ouattara - UAO), à Abidjan et dans plusieurs régions de Côte d'Ivoire, l'ONG déploie des actions concrètes : formations certifiantes gratuites, caravanes de salubrité publique, reboisement communautaire, éducation civique et sensibilisation sur la santé reproductive et les VBG avec l'appui de partenaires comme l'UNFPA.",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Mot du Président-Fondateur Ezékiel Aka
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ForestGreenLight.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(ForestGreenPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "EA",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Ezékiel Aka",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = ForestGreenDark
                                )
                                Text(
                                    text = "Président-Fondateur de l'ONG-AIL4C",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AccentOrange,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "« Les jeunes ne doivent plus être de simples spectateurs face aux bouleversements écologiques. Ils détiennent l'énergie, l'ingéniosité et la force d'action nécessaires pour bâtir une Côte d'Ivoire propre, verte et prospère. À l'AIL4C, nous transformons cette volonté citoyenne en emplois d'avenir et en solutions locales. »",
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Mission & Vision Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = ForestGreenContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = null,
                                tint = ForestGreenDark,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Notre Mission",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreenDark
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Sensibiliser, former et insérer les populations et les jeunes dans des initiatives durables de protection environnementale et de résilience climatique.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = ForestGreenDark.copy(alpha = 0.9f),
                                fontSize = 12.5.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEDD5))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = null,
                                tint = AccentOrange,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Notre Vision",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7C2D12)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Bâtir une Côte d'Ivoire verte et solidaire où chaque jeune devient un champion de la durabilité et accède à un emploi vert épanouissant.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF7C2D12).copy(alpha = 0.9f),
                                fontSize = 12.5.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }
        }

        // Section Nos Valeurs
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                SectionHeader(
                    tagline = "Éthique & Principes",
                    title = "Nos valeurs fondamentales",
                    subtitle = "Ce qui guide chacune de nos interventions au quotidien"
                )

                Spacer(modifier = Modifier.height(12.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    maxItemsInEachRow = 2
                ) {
                    ValueCard(
                        title = "Engagement",
                        desc = "Dévouement total au service des communautés locales et de la planète.",
                        icon = Icons.Default.VolunteerActivism,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    ValueCard(
                        title = "Responsabilité",
                        desc = "Rigueur, transparence et exemplarité dans la conduite des projets.",
                        icon = Icons.Default.Shield,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    ValueCard(
                        title = "Solidarité",
                        desc = "Entraide intergénérationnelle et inclusion des couches vulnérables.",
                        icon = Icons.Default.Diversity1,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    ValueCard(
                        title = "Innovation",
                        desc = "Valorisation des solutions écologiques créatives et adaptées.",
                        icon = Icons.Default.Lightbulb,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    ValueCard(
                        title = "Citoyenneté",
                        desc = "Promotion de l'éco-responsabilité et du civisme actif.",
                        icon = Icons.Default.FactCheck,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    ValueCard(
                        title = "Protection",
                        desc = "Préservation inconditionnelle de la biodiversité ivoirienne.",
                        icon = Icons.Default.Eco,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                }
            }
        }

        // Section Nos Partenaires
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                SectionHeader(
                    tagline = "Collaboration & Synergies",
                    title = "Nos partenaires",
                    subtitle = "Construisons ensemble un avenir résilient et durable"
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Handshake,
                            contentDescription = null,
                            tint = ForestGreenPrimary,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Espace Partenariats & Alliances",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "L'ONG-AIL4C coopère avec les institutions publiques, les ministères sectoriels, les collectivités territoriales, les entreprises éco-citoyennes, les universités et les bailleurs internationaux pour démultiplier l'impact sur le terrain.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "[Emplacements dédiés aux logos des partenaires officiels]",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Ministères • Collectivités • Bailleurs • Entreprises RSE • ONG alliées",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ForestGreenPrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = { viewModel.navigateTo(AppDestination.CONTACT) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ForestGreenPrimary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("become_partner_btn")
                        ) {
                            Text("Devenir partenaire de l'ONG-AIL4C")
                        }
                    }
                }
            }
        }

        // Social Feed Section
        item {
            SocialFeedSection(
                onNavigateToAllNews = { viewModel.navigateTo(AppDestination.NEWS) }
            )
        }

        // Footer
        item {
            AppFooter(onNavigate = { viewModel.navigateTo(it) })
        }
    }
}

@Composable
private fun ValueCard(
    title: String,
    desc: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(150.dp),
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
                    .background(AccentOrange.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AccentOrange,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = desc,
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
