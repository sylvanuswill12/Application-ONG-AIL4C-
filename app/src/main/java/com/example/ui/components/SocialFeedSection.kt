package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.SocialFeedPost
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenLight
import com.example.ui.theme.ForestGreenPrimary

// Official verified links provided by ONG-AIL4C
const val OFFICIAL_INSTAGRAM_URL = "https://www.instagram.com/ongail4c?igsi=MW8wMG45anFpM2M1Mw=="
const val OFFICIAL_FACEBOOK_URL = "https://www.facebook.com/share/1Gg8rzSWhm/"
const val OFFICIAL_WHATSAPP_URL = "https://wa.me/2250789976323?text=Bonjour%20ONG-AIL4C"

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SocialFeedSection(
    modifier: Modifier = Modifier,
    onNavigateToAllNews: (() -> Unit)? = null
) {
    val context = LocalContext.current

    val officialPosts = remember {
        listOf(
            SocialFeedPost(
                id = "post_ig_1",
                platform = "Instagram",
                authorName = "ONG-AIL4C Officiel",
                authorHandle = "@ongail4c",
                date = "Février 2026",
                content = "Bilan vibrant de la Semaine de la Population à Bouaké en collaboration avec le Fonds des Nations unies pour la population (UNFPA) et l'UAO ! Des centaines de jeunes mobilisés pour la santé de la reproduction, la lutte contre les VBG et la préservation de l'environnement face au réchauffement climatique. 🌍🌱",
                tags = listOf("#AIL4C", "#UNFPA", "#Bouaké", "#ClimatCI", "#SantéJeunesse", "#ÉcoCitoyen"),
                url = OFFICIAL_INSTAGRAM_URL,
                imageRes = R.drawable.img_hero_community,
                likesCount = "342",
                commentsCount = "58"
            ),
            SocialFeedPost(
                id = "post_fb_1",
                platform = "Facebook",
                authorName = "ONG AIL4C",
                authorHandle = "facebook.com/ongail4c",
                date = "Février 2026",
                content = "« L'engagement de la jeunesse ivoirienne pour un développement durable » 📚🌿 ! L'ONG-AIL4C a dispensé une session de formation certifiante gratuite à l'Université Alassane Ouattara (UAO) de Bouaké sur les enjeux climatiques, l'énergie solaire et l'économie circulaire. Bravo à tous les participants !",
                tags = listOf("#FormationGratuite", "#UAO", "#Bouaké", "#EmploiVert", "#AIL4C"),
                url = OFFICIAL_FACEBOOK_URL,
                imageRes = R.drawable.img_formation_vert,
                likesCount = "415",
                commentsCount = "72"
            ),
            SocialFeedPost(
                id = "post_ig_2",
                platform = "Instagram",
                authorName = "ONG-AIL4C Officiel",
                authorHandle = "@ongail4c",
                date = "Janvier 2026",
                content = "« Face aux impacts du dérèglement climatique, la jeunesse ne doit pas être spectatrice mais le fer de lance de la transition écologique en Côte d'Ivoire. » — Mot du Président-Fondateur Ezékiel Aka lors du Forum Climat Jeunesse. 🇨🇮✊",
                tags = listOf("#EzékielAka", "#VisionAIL4C", "#ChangementClimatique", "#CôteDIvoire"),
                url = OFFICIAL_INSTAGRAM_URL,
                imageRes = R.drawable.img_hero_community,
                likesCount = "289",
                commentsCount = "34"
            ),
            SocialFeedPost(
                id = "post_fb_2",
                platform = "Facebook",
                authorName = "ONG AIL4C",
                authorHandle = "facebook.com/ongail4c",
                date = "Janvier 2026",
                content = "Grande Caravane Citoyenne « Salubrité Urbaine & Caniveaux Sains » : Opération désensablement, curage et tri sélectif des plastiques. Plus de 5 tonnes collectées et acheminées vers le recyclage. Mobilisons-nous pour nos quartiers !",
                tags = listOf("#SalubritéPublique", "#VillesDurables", "#AIL4C", "#TriPlastique", "#Bouaké"),
                url = OFFICIAL_FACEBOOK_URL,
                imageRes = R.drawable.img_salubrite_ville,
                likesCount = "520",
                commentsCount = "89"
            ),
            SocialFeedPost(
                id = "post_ig_3",
                platform = "Instagram",
                authorName = "ONG-AIL4C Officiel",
                authorHandle = "@ongail4c",
                date = "Décembre 2025",
                content = "Restauration des écosystèmes et ceintures vertes : 3 000 arbres plantés avec les éco-clubs scolaires et les chefferies traditionnelles. Chaque arbre planté est un pas de plus pour la résilience de notre pays. 🌳✨",
                tags = listOf("#Reboisement", "#Biodiversité", "#ForêtIvoirienne", "#AIL4C"),
                url = OFFICIAL_INSTAGRAM_URL,
                imageRes = R.drawable.img_reboisement,
                likesCount = "378",
                commentsCount = "41"
            )
        )
    }

    fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Impossible d'ouvrir le lien", Toast.LENGTH_SHORT).show()
        }
    }

    fun copyToClipboard(label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Lien copié dans le presse-papier !", Toast.LENGTH_SHORT).show()
    }

    fun sharePost(post: SocialFeedPost) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "${post.content}\n\nRetrouvez l'ONG-AIL4C sur ${post.platform} : ${post.url}"
            )
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Partager la publication AIL4C")
        context.startActivity(shareIntent)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        // Section Title & Badge
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(AccentOrange)
                )
                Text(
                    text = "COMMUNAUTÉ & RÉSEAUX SOCIAUX",
                    style = MaterialTheme.typography.labelMedium,
                    color = AccentOrange,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Suivez-nous sur Instagram & Facebook",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Découvrez en direct nos publications, reportages de terrain à Bouaké, Abidjan et l'Université Alassane Ouattara (UAO).",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
            )
        }

        // Direct Social Hub Banner Cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Instagram Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { openUrl(OFFICIAL_INSTAGRAM_URL) }
                    .testTag("btn_open_instagram_hub"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF2F4)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFBCFE8)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            Color(0xFF833AB4),
                                            Color(0xFFFD1D1D),
                                            Color(0xFFF77737)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "IG",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.OpenInNew,
                            contentDescription = "Ouvrir Instagram",
                            tint = Color(0xFFE1306C),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Instagram",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF831843)
                    )
                    Text(
                        text = "@ongail4c",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFE1306C)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF833AB4), Color(0xFFE1306C))
                                )
                            )
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "S'abonner",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Facebook Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { openUrl(OFFICIAL_FACEBOOK_URL) }
                    .testTag("btn_open_facebook_hub"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1877F2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "f",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.OpenInNew,
                            contentDescription = "Ouvrir Facebook",
                            tint = Color(0xFF1877F2),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Facebook",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3A8A)
                    )
                    Text(
                        text = "ONG AIL4C",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1877F2)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF1877F2))
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Rejoindre la page",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Horizontal Feed Carousel
        Text(
            text = "DERNIÈRES PUBLICATIONS & REPORTAGES",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(officialPosts) { post ->
                SocialPostCard(
                    post = post,
                    onOpenUrl = { openUrl(post.url) },
                    onShare = { sharePost(post) },
                    onCopyLink = { copyToClipboard("${post.platform} AIL4C", post.url) }
                )
            }
        }
    }
}

@Composable
fun SocialPostCard(
    post: SocialFeedPost,
    onOpenUrl: () -> Unit,
    onShare: () -> Unit,
    onCopyLink: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isInstagram = post.platform == "Instagram"
    val platformColor = if (isInstagram) Color(0xFFE1306C) else Color(0xFF1877F2)
    val platformBg = if (isInstagram) Color(0xFFFDF2F4) else Color(0xFFEFF6FF)

    Card(
        modifier = modifier
            .width(300.dp)
            .testTag("social_post_${post.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Post Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, Color(0xFFE2E8F0), CircleShape)
                        .padding(3.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_logo_ail4c),
                        contentDescription = "Logo AIL4C",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = post.authorName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Vérifié",
                            tint = if (isInstagram) Color(0xFFE1306C) else Color(0xFF1877F2),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "${post.authorHandle} • ${post.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }

                // Platform Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(platformBg)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = post.platform,
                        style = MaterialTheme.typography.labelSmall,
                        color = platformColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }

            // Post Visual Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Image(
                    painter = painterResource(id = post.imageRes),
                    contentDescription = post.content,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Post Caption
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text(
                    text = post.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Hashtags
                Text(
                    text = post.tags.joinToString(" "),
                    style = MaterialTheme.typography.bodySmall,
                    color = platformColor,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(8.dp))

                // Interactions & Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isInstagram) Icons.Default.Favorite else Icons.Default.ThumbUp,
                                contentDescription = "Likes",
                                tint = if (isInstagram) Color(0xFFE1306C) else Color(0xFF1877F2),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = post.likesCount,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ModeComment,
                                contentDescription = "Commentaires",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = post.commentsCount,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onShare,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Partager",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Button(
                            onClick = onOpenUrl,
                            colors = ButtonDefaults.buttonColors(containerColor = platformColor),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Voir",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
