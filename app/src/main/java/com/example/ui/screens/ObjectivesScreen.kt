package com.example.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppFooter
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenContainer
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenLight
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.Ail4cViewModel
import com.example.ui.viewmodel.AppDestination

data class StrategicObjective(
    val number: Int,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val target: String,
    val actions: List<String>
)

@Composable
fun ObjectivesScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    val objectives = listOf(
        StrategicObjective(
            number = 1,
            title = "Sensibiliser les populations aux enjeux du changement climatique",
            description = "Faire comprendre la réalité du dérèglement climatique en Côte d'Ivoire, les conséquences sur l'agriculture et les zones côtières, et promouvoir les gestes éco-responsables auprès de tous les ménages.",
            icon = Icons.Default.Campaign,
            target = "50 000+ citoyens sensibilisés d'ici 2027",
            actions = listOf(
                "Caravanes écologiques et causeries citoyennes de quartier",
                "Clubs environnementaux dans les écoles primaires et secondaires",
                "Campagnes médiatiques et diffusion de guides de bonnes pratiques"
            )
        ),
        StrategicObjective(
            number = 2,
            title = "Former et mobiliser les jeunes pour le développement durable",
            description = "Transformer l'énergie de la jeunesse ivoirienne en un puissant moteur de transition écologique en renforçant leurs compétences techniques et citoyennes.",
            icon = Icons.Default.Diversity3,
            target = "10 000+ jeunes formés et mobilisés",
            actions = listOf(
                "Ateliers d'apprentissage des techniques éco-responsables",
                "Réseau national d'ambassadeurs climat dans les communes",
                "Formations certifiantes en écocitoyenneté et gestion de crise"
            )
        ),
        StrategicObjective(
            number = 3,
            title = "Promouvoir l'emploi vert et l'entrepreneuriat climatique",
            description = "Créer des passerelles directes entre la transition écologique et l'insertion professionnelle durable des jeunes diplômés ou en quête de reconversion.",
            icon = Icons.Default.Work,
            target = "1 200+ jeunes insérés dans des métiers verts",
            actions = listOf(
                "Formations certifiantes en énergie solaire, agroécologie et recyclage",
                "Incubateur pour jeunes éco-entrepreneurs porteurs de projets",
                "Mise en relation avec les entreprises du secteur vert et durable"
            )
        ),
        StrategicObjective(
            number = 4,
            title = "Contribuer à la protection de l'environnement & salubrité",
            description = "Agir directement sur le terrain pour restaurer la biodiversité, lutter contre la déforestation et éliminer les points noirs de déchets urbains.",
            icon = Icons.Default.Forest,
            target = "25 000+ arbres plantés et 50T de déchets valorisés",
            actions = listOf(
                "Campagnes communautaires de reboisement et création de pépinières",
                "Journées de salubrité publique et curage citoyen des caniveaux",
                "Mise en place de circuits de tri sélectif et valorisation du plastique"
            )
        ),
        StrategicObjective(
            number = 5,
            title = "Renforcer la résilience des communautés vulnérables",
            description = "Accompagner les populations les plus exposées aux inondations, à la sécheresse et à l'érosion marine pour développer des capacités d'adaptation pérennes.",
            icon = Icons.Default.Shield,
            target = "20+ localités et quartiers vulnérables accompagnés",
            actions = listOf(
                "Diagnostic communautaire des risques climatiques locaux",
                "Restauration naturelle des mangroves et digues végétalisées",
                "Comités de vigilance citoyenne et systèmes d'alerte précoce"
            )
        )
    )

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
                        text = "FEUILLE DE ROUTE STRATÉGIQUE",
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentOrange,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Nos 5 Objectifs Majeurs",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Des engagements clairs et mesurables pour répondre aux défis du climat et du chômage en Côte d'Ivoire.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0)
                    )
                }
            }
        }

        // Objectives List
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                objectives.forEach { obj ->
                    ObjectiveCard(objective = obj)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // CTA Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ForestGreenContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Vous souhaitez soutenir nos objectifs ?",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenDark,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Que vous soyez bénévole, institution, entreprise ou collectivité, votre engagement compte.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ForestGreenDark.copy(alpha = 0.9f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.navigateTo(AppDestination.CONTACT) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ForestGreenPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Rejoindre le mouvement")
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

@Composable
private fun ObjectiveCard(objective: StrategicObjective) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("objective_card_${objective.number}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(ForestGreenPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${objective.number}",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "OBJECTIF STRATÉGIQUE ${objective.number}",
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentOrange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                    Text(
                        text = objective.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = objective.description,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Target pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(AccentOrange.copy(alpha = 0.12f))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "🎯 Cible : ${objective.target}",
                    style = MaterialTheme.typography.labelMedium,
                    color = AccentOrange,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Moyens d'action prioritaires :",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            objective.actions.forEach { action ->
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = ForestGreenPrimary,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = action,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
