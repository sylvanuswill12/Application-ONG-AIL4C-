package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Co2
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Waves
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.AppFooter
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenContainer
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.Ail4cViewModel
import com.example.ui.viewmodel.AppDestination

@Composable
fun ClimateEnvironmentScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Banner
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
                        text = "URGENCE ÉCOLOGIQUE & SOLUTIONS",
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentOrange,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Climat & Environnement",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Protéger nos écosystèmes, assainir nos villes et bâtir une résilience durable pour la Côte d'Ivoire.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0)
                    )
                }
            }
        }

        // Hero Image
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
                        painter = painterResource(id = R.drawable.img_reboisement),
                        contentDescription = "Reboisement et protection environnementale en Côte d'Ivoire",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SectionHeader(
                    tagline = "État des lieux en Côte d'Ivoire",
                    title = "Les défis climatiques majeurs auxquels nous faisons face",
                    subtitle = "Comprendre les menaces pour agir avec pertinence et efficacité"
                )

                Text(
                    text = "La Côte d'Ivoire subit de plein fouet les effets du changement climatique : perturbation des régimes pluviométriques impactant l'agriculture, montée du niveau de la mer entraînant l'érosion des côtes littorales (Grand-Bassam, San-Pédro), et intensification des inondations urbaines dues à l'engorgement des réseaux d'évacuation par les déchets plastiques.\n\nL'ONG-AIL4C déploie une approche holistique combinant restauration des puits de carbone, gestion des déchets solides et mobilisation communautaire.",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 5 Pôles d'action écologique
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                SectionHeader(
                    tagline = "Interventions",
                    title = "Nos réponses concrètes sur le terrain",
                    subtitle = "Des programmes d'action ciblés pour chaque enjeu écologique"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ClimateEnjeuCard(
                    title = "1. Lutte contre le changement climatique & Adaptation",
                    desc = "Sensibilisation massive des populations aux dérèglements climatiques, vulgarisation des pratiques agro-écologiques résilientes face à la sécheresse et reboisement communautaire.",
                    icon = Icons.Default.DeviceThermostat,
                    highlight = "2 500+ arbres plantés"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ClimateEnjeuCard(
                    title = "2. Salubrité publique & Lutte contre la pollution",
                    desc = "Organisation régulière de grandes journées de salubrité éco-citoyennes pour désensabler les caniveaux, éradiquer les décharges sauvages et prévenir les risques sanitaires et d'inondations.",
                    icon = Icons.Default.CleaningServices,
                    highlight = "8+ tonnes de déchets collectés"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ClimateEnjeuCard(
                    title = "3. Gestion des déchets & Économie circulaire",
                    desc = "Mise en place de circuits de tri sélectif participatif, valorisation locale des déchets plastiques recyclables et formation de jeunes collecteurs et transformateurs.",
                    icon = Icons.Default.Recycling,
                    highlight = "Filières plastiques valorisées"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ClimateEnjeuCard(
                    title = "4. Préservation des forêts et zones côtières",
                    desc = "Restauration des écosystèmes dégradés, protection des mangroves littorales contre l'érosion marine et reboisement de ceintures vertes autour des agglomérations.",
                    icon = Icons.Default.Forest,
                    highlight = "Zones littorales protégées"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ClimateEnjeuCard(
                    title = "5. Résilience communautaire & Éco-citoyenneté",
                    desc = "Formation d'éco-délégués dans les écoles, lycées et quartiers pour ancrer durablement la culture de la propreté et la gestion responsable de l'eau et de l'énergie.",
                    icon = Icons.Default.Shield,
                    highlight = "1 500+ élèves formés"
                )
            }
        }

        // CTA
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
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
                        text = "Agissez avec nous pour la planète",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreenDark,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Participez à nos campagnes de reboisement et d'assainissement en Côte d'Ivoire.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ForestGreenDark.copy(alpha = 0.9f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = { viewModel.navigateTo(AppDestination.ACTIONS) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ForestGreenPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Découvrir le calendrier des actions")
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
private fun ClimateEnjeuCard(
    title: String,
    desc: String,
    icon: ImageVector,
    highlight: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ForestGreenPrimary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = ForestGreenPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(AccentOrange.copy(alpha = 0.12f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "🌱 Impact clé : $highlight",
                    style = MaterialTheme.typography.labelMedium,
                    color = AccentOrange,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp
                )
            }
        }
    }
}
