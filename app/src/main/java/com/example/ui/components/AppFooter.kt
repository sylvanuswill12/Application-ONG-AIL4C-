package com.example.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenLight
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.AppDestination

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppFooter(
    onNavigate: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Variables for official social links
    val socialFacebookUrl = "https://www.facebook.com/share/1Gg8rzSWhm/"
    val socialWhatsAppPhone = "+2250789976323"
    val socialInstagramUrl = "https://www.instagram.com/ongail4c?igsi=MW8wMG45anFpM2M1Mw=="
    val socialTikTokUrl = "https://tiktok.com"
    val socialLinkedInUrl = "https://linkedin.com"

    fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (_: Exception) {}
    }

    fun dialPhone(number: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            context.startActivity(intent)
        } catch (_: Exception) {}
    }

    fun sendEmail(email: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, "Contact ONG-AIL4C")
            }
            context.startActivity(intent)
        } catch (_: Exception) {}
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color(0xFF0F1B13) // Deep eco-charcoal
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 28.dp)
        ) {
            // Top branding
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_logo_ail4c),
                        contentDescription = "Logo ONG-AIL4C",
                        modifier = Modifier.size(46.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "ONG-AIL4C",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "« Agir aujourd'hui pour un avenir durable »",
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentOrange,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Text(
                text = "Association Ivoirienne de Lutte Contre le Changement Climatique et le Chômage. Organisation non gouvernementale engagée pour le climat, l'insertion socio-professionnelle des jeunes et le développement durable en Côte d'Ivoire.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF94A3B8),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            HorizontalDivider(color = Color(0xFF1E293B), thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))

            // Navigation Links
            Text(
                text = "NAVIGATION RAPIDE",
                style = MaterialTheme.typography.labelMedium,
                color = ForestGreenLight,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FooterNavLink("Accueil") { onNavigate(AppDestination.HOME) }
                FooterNavLink("À propos") { onNavigate(AppDestination.ABOUT) }
                FooterNavLink("Nos objectifs") { onNavigate(AppDestination.OBJECTIVES) }
                FooterNavLink("Nos actions") { onNavigate(AppDestination.ACTIONS) }
                FooterNavLink("Nos projets") { onNavigate(AppDestination.PROJECTS) }
                FooterNavLink("Jeunesse & Emploi") { onNavigate(AppDestination.YOUTH_EMPLOYMENT) }
                FooterNavLink("Climat & Environnement") { onNavigate(AppDestination.CLIMATE_ENVIRONMENT) }
                FooterNavLink("Actualités") { onNavigate(AppDestination.NEWS) }
                FooterNavLink("Galerie") { onNavigate(AppDestination.GALLERY) }
                FooterNavLink("Contact") { onNavigate(AppDestination.CONTACT) }
                FooterNavLink("Administration") { onNavigate(AppDestination.ADMIN) }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color(0xFF1E293B), thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))

            // Official Contacts
            Text(
                text = "COORDONNÉES OFFICIELLES",
                style = MaterialTheme.typography.labelMedium,
                color = ForestGreenLight,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Phone 1
            ContactLineItem(
                icon = Icons.Default.Phone,
                text = "+225 07 89 97 63 23",
                onClick = { dialPhone("+2250789976323") }
            )
            // Phone 2
            ContactLineItem(
                icon = Icons.Default.Phone,
                text = "+225 07 08 06 46 86",
                onClick = { dialPhone("+2250708064686") }
            )
            // Phone 3
            ContactLineItem(
                icon = Icons.Default.Phone,
                text = "+225 07 87 60 33 53",
                onClick = { dialPhone("+2250787603353") }
            )
            // Email
            ContactLineItem(
                icon = Icons.Default.Email,
                text = "ail4c03@gmail.com",
                onClick = { sendEmail("ail4c03@gmail.com") }
            )
            // Location
            ContactLineItem(
                icon = Icons.Default.LocationOn,
                text = "Abidjan, Côte d'Ivoire",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Social Networks
            Text(
                text = "RÉSEAUX SOCIAUX & COMMUNICATION",
                style = MaterialTheme.typography.labelMedium,
                color = ForestGreenLight,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialBadge("WhatsApp", AccentOrange) {
                    openUrl("https://wa.me/2250789976323?text=Bonjour%20ONG-AIL4C")
                }
                SocialBadge("Facebook", ForestGreenPrimary) { openUrl(socialFacebookUrl) }
                SocialBadge("Instagram", Color(0xFFE1306C)) { openUrl(socialInstagramUrl) }
                SocialBadge("TikTok", Color(0xFF000000)) { openUrl(socialTikTokUrl) }
                SocialBadge("LinkedIn", Color(0xFF0A66C2)) { openUrl(socialLinkedInUrl) }
            }

            Spacer(modifier = Modifier.height(28.dp))
            HorizontalDivider(color = Color(0xFF1E293B), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // Official Copyright Notice
            Text(
                text = "© 2026 ONG-AIL4C — Association Ivoirienne de Lutte Contre le Changement Climatique et le Chômage. Tous droits réservés.",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 11.5.sp,
                color = Color(0xFF64748B),
                lineHeight = 16.sp,
                modifier = Modifier.testTag("footer_copyright")
            )
        }
    }
}

@Composable
private fun FooterNavLink(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp, horizontal = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = ForestGreenLight,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFE2E8F0),
            fontSize = 13.sp
        )
    }
}

@Composable
private fun ContactLineItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AccentOrange,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFCBD5E1)
        )
    }
}

@Composable
private fun SocialBadge(
    label: String,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(badgeColor.copy(alpha = 0.25f))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.5.sp
        )
    }
}
