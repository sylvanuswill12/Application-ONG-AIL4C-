package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.AppFooter
import com.example.ui.components.SectionHeader
import com.example.ui.components.SocialFeedSection
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenContainer
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenLight
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.Ail4cViewModel

@Composable
fun ContactScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successSubmitted by remember { mutableStateOf(false) }

    fun dialPhone(number: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            context.startActivity(intent)
        } catch (_: Exception) {}
    }

    fun openWhatsApp(number: String) {
        try {
            val url = "https://wa.me/${number.replace("+", "").replace(" ", "")}?text=Bonjour%20ONG-AIL4C"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (_: Exception) {}
    }

    fun sendEmail(address: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$address")
                putExtra(Intent.EXTRA_SUBJECT, "Prise de contact ONG-AIL4C")
            }
            context.startActivity(intent)
        } catch (_: Exception) {}
    }

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
                        text = "ÉCHANGE & ENGAGEMENT",
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentOrange,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Contactez l'ONG-AIL4C",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Association Ivoirienne de Lutte Contre le Changement Climatique et le Chômage. Notre équipe est à votre entière écoute.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0)
                    )
                }
            }
        }

        // Official Contact Directory Cards
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                SectionHeader(
                    tagline = "Coordonnées directes",
                    title = "Nos lignes téléphoniques et adresse",
                    subtitle = "Cliquez directement pour appeler ou envoyer un message WhatsApp"
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Phone Line 1
                OfficialContactCard(
                    title = "Ligne Principale & WhatsApp",
                    contactValue = "+225 07 89 97 63 23",
                    icon = Icons.Default.Phone,
                    onCallClick = { dialPhone("+2250789976323") },
                    onWhatsAppClick = { openWhatsApp("+2250789976323") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Phone Line 2
                OfficialContactCard(
                    title = "Ligne Coordination Projets",
                    contactValue = "+225 07 08 06 46 86",
                    icon = Icons.Default.Phone,
                    onCallClick = { dialPhone("+2250708064686") },
                    onWhatsAppClick = { openWhatsApp("+2250708064686") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Phone Line 3
                OfficialContactCard(
                    title = "Ligne Secrétariat & Bénévoles",
                    contactValue = "+225 07 87 60 33 53",
                    icon = Icons.Default.Phone,
                    onCallClick = { dialPhone("+2250787603353") },
                    onWhatsAppClick = { openWhatsApp("+2250787603353") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Official Email Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AccentOrange.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null,
                                    tint = AccentOrange,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Courrier Électronique Officiel",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "ail4c03@gmail.com",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Button(
                            onClick = { sendEmail("ail4c03@gmail.com") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentOrange,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Écrire")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Location Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(ForestGreenPrimary.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = ForestGreenPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Siège & Pôle Opérationnel Principal",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Bouaké, Côte d'Ivoire (Campus UAO & Siège Régional)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // Contact Form
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                SectionHeader(
                    tagline = "Formulaire institutionnel",
                    title = "Envoyez-nous un message",
                    subtitle = "Remplissez les informations ci-dessous pour nous faire part de vos questions, demandes de partenariat ou d'adhésion."
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        if (successSubmitted) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = ForestGreenPrimary,
                                        modifier = Modifier.size(54.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Message transmis avec succès !",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Nous vous remercions pour votre intérêt pour l'ONG-AIL4C. Nos équipes vous répondront sous 24h à 48h.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    OutlinedButton(onClick = {
                                        successSubmitted = false
                                        fullName = ""
                                        email = ""
                                        phone = ""
                                        subject = ""
                                        message = ""
                                    }) {
                                        Text("Envoyer un autre message")
                                    }
                                }
                            }
                        } else {
                            if (errorMessage != null) {
                                Text(
                                    text = errorMessage!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(bottom = 10.dp)
                                )
                            }

                            OutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                label = { Text("Nom et Prénom(s) *") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("contact_fullname_input"),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Adresse Email *") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("contact_email_input"),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = phone,
                                onValueChange = { phone = it },
                                label = { Text("Numéro de téléphone / WhatsApp") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("contact_phone_input"),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = subject,
                                onValueChange = { subject = it },
                                label = { Text("Objet du message / Thématique") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("contact_subject_input"),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = message,
                                onValueChange = { message = it },
                                label = { Text("Votre message détaillé *") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .testTag("contact_message_input"),
                                maxLines = 6
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    viewModel.submitContact(
                                        fullName = fullName,
                                        email = email,
                                        phone = phone,
                                        subject = subject,
                                        message = message,
                                        onSuccess = { successSubmitted = true },
                                        onError = { errorMessage = it }
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("contact_submit_btn"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ForestGreenPrimary,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Envoyer mon message",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Social Channels Section
        item {
            SocialFeedSection()
        }

        // Footer
        item {
            Spacer(modifier = Modifier.height(20.dp))
            AppFooter(onNavigate = { viewModel.navigateTo(it) })
        }
    }
}

@Composable
private fun OfficialContactCard(
    title: String,
    contactValue: String,
    icon: ImageVector,
    onCallClick: () -> Unit,
    onWhatsAppClick: () -> Unit
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
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
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
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = contactValue,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onCallClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ForestGreenPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Appeler",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onWhatsAppClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Message,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "WhatsApp",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
