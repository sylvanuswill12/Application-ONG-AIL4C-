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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.ui.components.AppFooter
import com.example.ui.components.OpportunityCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenContainer
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.Ail4cViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun YouthEmploymentScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    val publishedOpportunities by viewModel.publishedOpportunities.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
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
                        text = "PÔLE INSERTION & JEUNESSE",
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentOrange,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Jeunesse, Emploi & Métiers Verts",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Accompagner la jeunesse ivoirienne vers des emplois durables, des formations qualifiantes et l'éco-entrepreneuriat.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0)
                    )
                }
            }
        }

        // Hero Image & Introduction
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_formation_vert),
                        contentDescription = "Formation certifiante aux métiers verts ONG-AIL4C",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SectionHeader(
                    tagline = "Stratégie Jeunesse",
                    title = "Transformer le défi de l'emploi en opportunité écologique",
                    subtitle = "Des parcours complets pour révéler le potentiel de chaque jeune"
                )

                Text(
                    text = "En Côte d'Ivoire, les jeunes représentent plus de 60% de la population. L'ONG-AIL4C s'engage activement pour qu'ils soient au cœur de la transition écologique en créant des programmes de formation certifiante, d'insertion professionnelle directe et d'incubation entrepreneuriale.",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 6 Piliers Métiers Verts
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                SectionHeader(
                    tagline = "Dispositifs",
                    title = "Nos 6 piliers d'accompagnement",
                    subtitle = "Un écosystème conçu pour accompagner de l'apprentissage à l'emploi stable"
                )

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    maxItemsInEachRow = 2
                ) {
                    YouthPillarCard(
                        title = "Formations pratiques",
                        desc = "Modules certifiants en énergie solaire, agroécologie et valorisation des déchets.",
                        icon = Icons.Default.School,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    YouthPillarCard(
                        title = "Emploi vert",
                        desc = "Passerelles directes avec les entreprises du secteur de la transition durable.",
                        icon = Icons.Default.Work,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    YouthPillarCard(
                        title = "Éco-Entrepreneuriat",
                        desc = "Incubation, coaching d'affaires et bourses d'amorçage pour startups vertes.",
                        icon = Icons.Default.Lightbulb,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    YouthPillarCard(
                        title = "Engagement citoyen",
                        desc = "Mobilisation des jeunes comme ambassadeurs du climat et volontaires de terrain.",
                        icon = Icons.Default.VolunteerActivism,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    YouthPillarCard(
                        title = "Insertion durable",
                        desc = "Suivi post-formation et intégration dans des coopératives éco-responsables.",
                        icon = Icons.Default.Engineering,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                    YouthPillarCard(
                        title = "Mentorat & Coaching",
                        desc = "Accompagnement personnalisé par des experts et professionnels reconnus.",
                        icon = Icons.Default.SupportAgent,
                        modifier = Modifier.fillMaxWidth(0.48f)
                    )
                }
            }
        }

        // Opportunités ouvertes
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                SectionHeader(
                    tagline = "Appels & Inscriptions",
                    title = "Opportunités ouvertes aux jeunes",
                    subtitle = "Postulez dès aujourd'hui à nos programmes gratuits et certifiants"
                )
            }
        }

        // List of Opportunities
        items(publishedOpportunities, key = { it.id }) { opp ->
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                OpportunityCard(
                    opportunity = opp,
                    onApplyClick = {
                        viewModel.openVolunteerDialog(opp.title)
                    },
                    onDetailsClick = {
                        viewModel.selectedOpportunity.value = opp
                    }
                )
            }
        }

        // Footer
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AppFooter(onNavigate = { viewModel.navigateTo(it) })
        }
    }
}

@Composable
private fun YouthPillarCard(
    title: String,
    desc: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(160.dp),
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
                    imageVector = icon,
                    contentDescription = null,
                    tint = ForestGreenPrimary,
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
                Spacer(modifier = Modifier.height(3.dp))
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
