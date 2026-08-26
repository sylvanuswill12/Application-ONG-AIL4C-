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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
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

        // Notre Histoire & Genèse
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                SectionHeader(
                    tagline = "Genèse & Fondation",
                    title = "Notre Histoire & Vocation",
                    subtitle = "Née à Bouaké d'une recherche universitaire engagée pour le climat et la jeunesse"
                )

                // Key Facts Badges
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ForestGreenPrimary.copy(alpha = 0.12f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = ForestGreenPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "Création",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "23 Septembre 2023",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ForestGreenDark
                                )
                            }
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = AccentOrange.copy(alpha = 0.12f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = AccentOrange,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "Siège Social",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Broukro, Bouaké",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF7C2D12)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "L'ONG AIL4C (Association Ivoirienne de Lutte contre le Changement Climatique et le Chômage) est une organisation de la société civile basée à Bouaké, dans le district de la Vallée du Bandama, en Côte d’Ivoire.\n\nElle a été créée le 23 septembre 2023 par trois jeunes diplômés en sociologie (Sénin, Aka et Remy), à la suite de leur mémoire de master portant sur le changement climatique et l'analyse de ses impacts socio-environnementaux. Son siège social est établi dans le quartier de Broukro à Bouaké.\n\nL'AIL4C a pour ambition première de susciter une véritable prise de conscience collective au sein des populations de Bouaké et de toute la Côte d'Ivoire, où beaucoup ignorent encore l'ampleur des défis climatiques et leurs conséquences quotidiennes sur la vie communautaire.",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Gouvernance & Leadership
                SectionHeader(
                    tagline = "Structure & Dirigeants",
                    title = "Gouvernance de l'AIL4C",
                    subtitle = "Une équipe de sociologues et d'experts humanitaires engagés"
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Président actuel
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
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(ForestGreenPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "ST",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "SENIN TCHOUMOU ESDRAS GEMIEL",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = ForestGreenDark
                                )
                                Text(
                                    text = "Président en exercice de l'ONG AIL4C • Cofondateur",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AccentOrange,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "« Les jeunes ne doivent plus être de simples spectateurs face aux bouleversements écologiques. Ils détiennent l'énergie, l'ingéniosité et la force d'action nécessaires pour bâtir une Côte d'Ivoire propre, verte et prospère. À l'AIL4C, nous transformons cette volonté citoyenne en emplois d'avenir, en sensibilisation communautaire et en solutions de terrain durables. »",
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Autres membres clés de la gouvernance
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(ForestGreenPrimary.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = ForestGreenPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Fondateur",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ForestGreenPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ezéchiel Aka Koffi",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Président-fondateur, consultant humanitaire & ambassadeur YOMA.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(AccentOrange.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Groups,
                                        contentDescription = null,
                                        tint = AccentOrange,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Coordination",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AccentOrange
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Vice-Président & Coordinateur",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Pilotage opérationnel, déploiement des chantiers et relations institutionnelles.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // Section Les 4 Missions Principales
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                SectionHeader(
                    tagline = "Piliers d'Intervention",
                    title = "Nos 4 Missions Fondamentales",
                    subtitle = "Une réponse holistique aux urgences climatiques, sociales et humaines"
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Pilier 1: Climat
                MissionPillarCard(
                    number = "01",
                    title = "Lutter contre le changement climatique",
                    description = "Sensibilisation continue des populations et des jeunes aux causes réelles et conséquences directes du réchauffement planétaire, afin d'induire un changement pérenne des comportements.",
                    icon = Icons.Default.Eco,
                    iconBgColor = ForestGreenContainer,
                    iconTint = ForestGreenDark
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Pilier 2: Chômage des jeunes
                MissionPillarCard(
                    number = "02",
                    title = "Combattre le chômage des jeunes",
                    description = "Création d'opportunités économiques durables, formations certifiantes gratuites aux métiers verts (solaire, recyclage, agroécologie) et accompagnement vers l'insertion socio-économique.",
                    icon = Icons.Default.Work,
                    iconBgColor = Color(0xFFFFEDD5),
                    iconTint = AccentOrange
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Pilier 3: VBG & Santé Reproductive
                MissionPillarCard(
                    number = "03",
                    title = "Sensibilisation VBG & Santé Reproductive",
                    description = "Lutte contre les Violences Basées sur le Genre (VBG), éducation à la santé sexuelle et reproductive et promotion active de la planification familiale auprès des ménages et sur les campus.",
                    icon = Icons.Default.Favorite,
                    iconBgColor = Color(0xFFFCE7F3),
                    iconTint = Color(0xFFBE185D)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Pilier 4: Environnement & Salubrité
                MissionPillarCard(
                    number = "04",
                    title = "Préservation de l'environnement & Salubrité",
                    description = "Organisation de grandes campagnes de salubrité publique, curage écologique des caniveaux, opérations intensives de plantation d'arbres et éducation civique environnementale.",
                    icon = Icons.Default.Park,
                    iconBgColor = Color(0xFFDCFCE7),
                    iconTint = Color(0xFF15803D)
                )
            }
        }

        // Activités et Projets Marquants
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                SectionHeader(
                    tagline = "Réalisations & Impact",
                    title = "Activités & Projets Marquants",
                    subtitle = "Des actions phares concrètes menées sur le terrain à Bouaké et en Côte d'Ivoire"
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Semaine de la population 2024
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = ForestGreenPrimary
                            ) {
                                Text(
                                    text = "20–27 Juillet 2024",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Partenariat UNFPA",
                                color = AccentOrange,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Semaine de la Population à Bouaké",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenDark
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Une série d’actions d'envergure de sensibilisation dans les quartiers de Bouaké portant sur la lutte contre les VBG, la santé de la reproduction et la préservation de l'environnement, déployée avec l'appui institutionnel du Fonds des Nations Unies pour la Population (UNFPA).",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 19.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Journée de sensibilisation UAO
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF1E3A8A)
                            ) {
                                Text(
                                    text = "27 Juillet 2024",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Campus 2 UAO",
                                color = Color(0xFF2563EB),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Sensibilisation à l’Université Alassane Ouattara (UAO)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Campagne intensive sur la santé de la reproduction et la protection de l’environnement au Campus 2 de l’UAO de Bouaké, touchant des centaines d'étudiants, enseignants et personnels universitaires.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 19.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Actions de terrain permanentes
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.VolunteerActivism,
                                contentDescription = null,
                                tint = ForestGreenPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Actions de terrain régulières",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreenDark
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "• Campagnes de salubrité publique et curage de caniveaux\n• Plantations d’arbres et restauration du couvert végétal\n• Visites de ménages et causeries éducatives sur les VBG et le planning familial",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp
                        )
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

        // Section Partenaires Officiels
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                SectionHeader(
                    tagline = "Partenaires & Reconnaissance",
                    title = "Nos Partenaires Stratégiques",
                    subtitle = "Des alliances institutionnelles pour démultiplier l'impact sur le terrain"
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Grille des partenaires officiels mentionnés
                val partnersList = listOf(
                    Pair("UNFPA", "Fonds des Nations Unies pour la population • Appui Semaine de la Population & Santé/VBG"),
                    Pair("AIESEC", "Organisation internationale de jeunesse • Développement du leadership et volontariat"),
                    Pair("BLUE", "Initiative partenaire pour la durabilité et l'impact écologique"),
                    Pair("ONG LA MAIN SUR LE COEUR", "Organisation humanitaire partenaire pour la solidarité communautaire"),
                    Pair("Université Alassane Ouattara (UAO)", "Partenaire académique • Campus 2 de Bouaké pour les formations & sensibilisations")
                )

                partnersList.forEach { (name, description) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(ForestGreenPrimary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Handshake,
                                    contentDescription = null,
                                    tint = ForestGreenPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = ForestGreenDark,
                                    fontSize = 14.5.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ForestGreenContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Vous souhaitez devenir partenaire de l'AIL4C ?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenDark,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Rejoignez nos programmes en faveur du climat, de la jeunesse et de l'égalité des genres en Côte d'Ivoire.",
                            style = MaterialTheme.typography.bodySmall,
                            color = ForestGreenDark.copy(alpha = 0.85f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { viewModel.navigateTo(AppDestination.CONTACT) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ForestGreenDark,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("become_partner_btn")
                        ) {
                            Text("Prendre contact avec l'AIL4C")
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
private fun MissionPillarCard(
    number: String,
    title: String,
    description: String,
    icon: ImageVector,
    iconBgColor: Color,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PILIER $number",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = iconTint,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 19.sp,
                    fontSize = 13.sp
                )
            }
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
