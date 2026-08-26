package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.UserProfile
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenLight
import com.example.ui.theme.ForestGreenPrimary
import java.text.NumberFormat
import java.util.Locale
import kotlin.random.Random

enum class PaymentMethodType(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val badgeColor: Color
) {
    WAVE("Wave Côte d'Ivoire", "Numéro: +225 07 48 37 32 50 (Sans frais)", Icons.Default.PhoneAndroid, Color(0xFF1DA1F2)),
    ORANGE_MONEY("Orange Money CI", "Numéro: +225 07 48 37 32 50 (#144#)", Icons.Default.PhoneAndroid, Color(0xFFFF7900)),
    MTN_MOMO("MTN Mobile Money", "Numéro: +225 05 54 88 77 12 (*133#)", Icons.Default.PhoneAndroid, Color(0xFFFFCC00)),
    MOOV_MONEY("Moov Money CI", "Numéro: +225 01 02 03 04 05 (*155#)", Icons.Default.PhoneAndroid, Color(0xFF006699)),
    BANK_TRANSFER("Virement Bancaire (RIB)", "Banque Atlantique Côte d'Ivoire (BACI)", Icons.Default.AccountBalance, ForestGreenPrimary),
    CREDIT_CARD("Carte Bancaire (Visa / Mastercard)", "Paiement international sécurisé", Icons.Default.CreditCard, Color(0xFF6366F1))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DonationDialog(
    userProfile: UserProfile?,
    onDismiss: () -> Unit,
    onDonationSuccess: (amount: Int, project: String, method: String) -> Unit
) {
    val context = LocalContext.current

    var isMonthly by remember { mutableStateOf(false) }
    var selectedPresetAmount by remember { mutableIntStateOf(5000) }
    var customAmountText by remember { mutableStateOf("") }
    var isCustomAmount by remember { mutableStateOf(false) }

    var selectedCause by remember { mutableStateOf("Toutes les actions prioritaires de l'ONG-AIL4C") }
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethodType.WAVE) }

    var donorName by remember { mutableStateOf(userProfile?.fullName ?: "") }
    var donorEmail by remember { mutableStateOf(userProfile?.email ?: "") }
    var donorPhone by remember { mutableStateOf(userProfile?.phone ?: "") }
    var isAnonymous by remember { mutableStateOf(false) }
    var requestReceipt by remember { mutableStateOf(true) }

    var isSuccess by remember { mutableStateOf(false) }
    var donationReference by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val presetAmounts = listOf(2500, 5000, 10000, 25000, 50000)

    val causes = listOf(
        "Toutes les actions prioritaires de l'ONG-AIL4C",
        "Reforestation & Pépinières Scolaires",
        "Énergie Solaire & Transition Énergétique",
        "Salubrité Urbaine & Éco-Citoyenneté à Bouaké & Abidjan",
        "Bourses d'Insertion aux Métiers Verts"
    )

    val effectiveAmount = remember(isCustomAmount, customAmountText, selectedPresetAmount) {
        if (isCustomAmount) {
            customAmountText.toIntOrNull() ?: 0
        } else {
            selectedPresetAmount
        }
    }

    val impactDescription = remember(effectiveAmount) {
        when {
            effectiveAmount <= 3000 -> "🌱 Finance la mise en terre de 5 arbres indigènes et le matériel de reboisement."
            effectiveAmount <= 7000 -> "🌳 Permet de planter 10 arbres avec un suivi de croissance et d'entretien sur 1 an."
            effectiveAmount <= 15000 -> "💡 Finance le kit d'outillage pour un jeune en formation aux métiers du solaire photovoltaïque."
            effectiveAmount <= 35000 -> "☀️ Finance un kit solaire d'éclairage complet pour une classe ou un foyer rural."
            else -> "🏆 Parraine un lot complet de pépinière communautaire et forme 5 jeunes aux techniques agro-écologiques."
        }
    }

    fun copyToClipboard(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label copié dans le presse-papiers !", Toast.LENGTH_SHORT).show()
    }

    fun shareDonationReceipt() {
        val formattedAmount = NumberFormat.getNumberInstance(Locale.FRENCH).format(effectiveAmount)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "💚 J'ai soutenu l'ONG-AIL4C avec un don de $formattedAmount FCFA pour « $selectedCause » ! " +
                        "Rejoignez-nous pour protéger le climat et bâtir les métiers verts en Côte d'Ivoire 🇨🇮. " +
                        "Réf: $donationReference"
            )
        }
        context.startActivity(Intent.createChooser(shareIntent, "Partager mon reçu de don AIL4C"))
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(horizontal = 16.dp, vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.94f)
                    .shadow(elevation = 20.dp, shape = RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(ForestGreenDark, ForestGreenPrimary)
                                )
                            )
                            .padding(22.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.VolunteerActivism,
                                        contentDescription = null,
                                        tint = AccentOrange,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "SOUTIEN & MÉCÉNAT CLIMAT 🇨🇮",
                                        fontSize = 11.sp,
                                        color = AccentOrange,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 0.6.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Faire un Don à l'ONG-AIL4C",
                                    fontSize = 21.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = "Chaque franc CFA planté fait germer une Côte d'Ivoire plus verte",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }

                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Fermer",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    if (isSuccess) {
                        // SUCCESS CONFIRMATION VIEW
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(ForestGreenLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = ForestGreenPrimary,
                                    modifier = Modifier.size(44.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Merci infiniment pour votre générosité !",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0F172A),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Votre contribution soutient directement nos actions sur le terrain en Côte d'Ivoire.",
                                fontSize = 13.sp,
                                color = Color(0xFF64748B),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Official receipt summary card
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Column(modifier = Modifier.padding(18.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Reçu d'intention de don",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = ForestGreenPrimary
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = ForestGreenLight
                                        ) {
                                            Text(
                                                text = "VALIDE",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = ForestGreenDark,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))
                                    HorizontalDivider(color = Color(0xFFE2E8F0))
                                    Spacer(modifier = Modifier.height(12.dp))

                                    ReceiptRow(label = "Référence :", value = donationReference)
                                    ReceiptRow(label = "Montant :", value = "${NumberFormat.getNumberInstance(Locale.FRENCH).format(effectiveAmount)} FCFA")
                                    ReceiptRow(label = "Fréquence :", value = if (isMonthly) "Don Mensuel Récurrent" else "Don Ponctuel")
                                    ReceiptRow(label = "Affectation :", value = selectedCause)
                                    ReceiptRow(label = "Moyen sélectionné :", value = selectedPaymentMethod.title)
                                    ReceiptRow(label = "Donateur :", value = if (isAnonymous) "Donateur Anonyme" else donorName)

                                    if (requestReceipt && donorEmail.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "📧 Une attestation officielle sera transmise à $donorEmail",
                                            fontSize = 11.sp,
                                            color = ForestGreenDark,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { shareDonationReceipt() },
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                ) {
                                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Partager", fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = onDismiss,
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                ) {
                                    Text("Fermer", fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                        }
                    } else {
                        // DONATION FORM
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(22.dp)
                        ) {
                            // 1. Frequency Switch (Ponctuel vs Mensuel)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFF1F5F9))
                                    .padding(4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (!isMonthly) ForestGreenPrimary else Color.Transparent)
                                        .clickable { isMonthly = false }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Don Ponctuel",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (!isMonthly) Color.White else Color(0xFF64748B)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isMonthly) ForestGreenPrimary else Color.Transparent)
                                        .clickable { isMonthly = true }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Don Mensuel",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = if (isMonthly) Color.White else Color(0xFF64748B)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = AccentOrange
                                        ) {
                                            Text(
                                                text = "Fidélité",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // 2. Preset Amounts Grid
                            Text(
                                text = "Choisissez votre montant (FCFA)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                presetAmounts.forEach { amount ->
                                    val isSelected = !isCustomAmount && selectedPresetAmount == amount
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = if (isSelected) ForestGreenPrimary else Color(0xFFF8FAFC),
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.5.dp,
                                            if (isSelected) ForestGreenPrimary else Color(0xFFE2E8F0)
                                        ),
                                        modifier = Modifier
                                            .clickable {
                                                isCustomAmount = false
                                                selectedPresetAmount = amount
                                            }
                                    ) {
                                        Text(
                                            text = "${NumberFormat.getNumberInstance(Locale.FRENCH).format(amount)} F",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else Color(0xFF1E293B),
                                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (isCustomAmount) ForestGreenPrimary else Color(0xFFF8FAFC),
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.5.dp,
                                        if (isCustomAmount) ForestGreenPrimary else Color(0xFFE2E8F0)
                                    ),
                                    modifier = Modifier
                                        .clickable {
                                            isCustomAmount = true
                                        }
                                ) {
                                    Text(
                                        text = "Autre montant",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isCustomAmount) Color.White else Color(0xFF1E293B),
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                                    )
                                }
                            }

                            if (isCustomAmount) {
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = customAmountText,
                                    onValueChange = { customAmountText = it.filter { ch -> ch.isDigit() } },
                                    label = { Text("Montant personnalisé en FCFA") },
                                    placeholder = { Text("Ex: 15000") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = ForestGreenPrimary,
                                        focusedLabelColor = ForestGreenPrimary
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Concrete Impact Preview
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = ForestGreenLight),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = ForestGreenDark,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "Impact direct de votre don :",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = ForestGreenDark
                                        )
                                        Text(
                                            text = impactDescription,
                                            fontSize = 12.5.sp,
                                            color = ForestGreenDark,
                                            fontWeight = FontWeight.SemiBold,
                                            lineHeight = 17.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // 3. Selection of cause / project
                            Text(
                                text = "Projet ou cause soutenue",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                causes.forEach { cause ->
                                    val isSelected = selectedCause == cause
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (isSelected) Color(0xFFEFF6FF) else Color(0xFFF8FAFC))
                                            .border(
                                                1.dp,
                                                if (isSelected) Color(0xFF3B82F6) else Color(0xFFE2E8F0),
                                                RoundedCornerShape(12.dp)
                                            )
                                            .clickable { selectedCause = cause }
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(CircleShape)
                                                .border(2.dp, if (isSelected) Color(0xFF3B82F6) else Color(0xFF94A3B8), CircleShape)
                                                .background(if (isSelected) Color(0xFF3B82F6) else Color.Transparent)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = cause,
                                            fontSize = 12.5.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) Color(0xFF1E3A8A) else Color(0xFF334155)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(22.dp))

                            // 4. Payment Method
                            Text(
                                text = "Mode de paiement sécurisé (Côte d'Ivoire & International)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                PaymentMethodType.values().forEach { method ->
                                    val isSelected = selectedPaymentMethod == method
                                    Card(
                                        shape = RoundedCornerShape(14.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (isSelected) method.badgeColor.copy(alpha = 0.08f) else Color(0xFFF8FAFC)
                                        ),
                                        border = androidx.compose.foundation.BorderStroke(
                                            if (isSelected) 1.5.dp else 1.dp,
                                            if (isSelected) method.badgeColor else Color(0xFFE2E8F0)
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { selectedPaymentMethod = method }
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(36.dp)
                                                    .clip(CircleShape)
                                                    .background(method.badgeColor.copy(alpha = 0.15f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = method.icon,
                                                    contentDescription = null,
                                                    tint = method.badgeColor,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(12.dp))

                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = method.title,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF0F172A)
                                                )
                                                Text(
                                                    text = method.subtitle,
                                                    fontSize = 11.sp,
                                                    color = Color(0xFF64748B)
                                                )
                                            }

                                            if (isSelected) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = method.badgeColor,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // Payment instructions details card
                            Spacer(modifier = Modifier.height(12.dp))
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    when (selectedPaymentMethod) {
                                        PaymentMethodType.WAVE -> {
                                            Text(
                                                text = "Instructions Wave Côte d'Ivoire :",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF0F172A)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "Effectuez un transfert direct sans frais vers le numéro officiel de la trésorerie ONG-AIL4C :",
                                                fontSize = 11.5.sp,
                                                color = Color(0xFF475569)
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "+225 07 48 37 32 50",
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = Color(0xFF1DA1F2)
                                                )
                                                OutlinedButton(
                                                    onClick = { copyToClipboard("+2250748373250", "Numéro Wave") },
                                                    shape = RoundedCornerShape(10.dp),
                                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                                ) {
                                                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(13.dp))
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text("Copier", fontSize = 11.sp)
                                                }
                                            }
                                        }
                                        PaymentMethodType.ORANGE_MONEY -> {
                                            Text(
                                                text = "Instructions Orange Money CI :",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF0F172A)
                                            )
                                            Text(
                                                text = "Composez #144*1*1# puis transférez au +225 07 48 37 32 50 (ONG-AIL4C)",
                                                fontSize = 11.5.sp,
                                                color = Color(0xFF475569)
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            OutlinedButton(
                                                onClick = { copyToClipboard("+2250748373250", "Numéro Orange Money") },
                                                shape = RoundedCornerShape(10.dp),
                                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                            ) {
                                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(13.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Copier le numéro", fontSize = 11.sp)
                                            }
                                        }
                                        PaymentMethodType.MTN_MOMO -> {
                                            Text(
                                                text = "Instructions MTN Mobile Money :",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF0F172A)
                                            )
                                            Text(
                                                text = "Composez *133# puis effectuez le transfert vers +225 05 54 88 77 12",
                                                fontSize = 11.5.sp,
                                                color = Color(0xFF475569)
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            OutlinedButton(
                                                onClick = { copyToClipboard("+2250554887712", "Numéro MTN MoMo") },
                                                shape = RoundedCornerShape(10.dp),
                                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                            ) {
                                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(13.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Copier le numéro", fontSize = 11.sp)
                                            }
                                        }
                                        PaymentMethodType.MOOV_MONEY -> {
                                            Text(
                                                text = "Instructions Moov Money CI :",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF0F172A)
                                            )
                                            Text(
                                                text = "Composez *155# puis effectuez le transfert vers +225 01 02 03 04 05",
                                                fontSize = 11.5.sp,
                                                color = Color(0xFF475569)
                                            )
                                        }
                                        PaymentMethodType.BANK_TRANSFER -> {
                                            Text(
                                                text = "Coordonnées Bancaires Officielles (BACI) :",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF0F172A)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "Bénéficiaire : ALLIANCE IVOIRIENNE LEADERS CLIMAT\nRIB : CI092 01001 02345678901 23\nBanque : Banque Atlantique Côte d'Ivoire",
                                                fontSize = 11.5.sp,
                                                color = Color(0xFF475569),
                                                lineHeight = 16.sp
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            OutlinedButton(
                                                onClick = { copyToClipboard("CI092010010234567890123", "RIB Banque Atlantique") },
                                                shape = RoundedCornerShape(10.dp),
                                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                            ) {
                                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(13.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Copier le RIB complet", fontSize = 11.sp)
                                            }
                                        }
                                        PaymentMethodType.CREDIT_CARD -> {
                                            Text(
                                                text = "Paiement International par Carte :",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF0F172A)
                                            )
                                            Text(
                                                text = "Transaction sécurisée SSL 256 bits avec 3D-Secure pour les cartes Visa & Mastercard.",
                                                fontSize = 11.5.sp,
                                                color = Color(0xFF475569)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // 5. Donor Info
                            Text(
                                text = "Coordonnées du donateur",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = donorName,
                                onValueChange = { donorName = it },
                                label = { Text("Nom & Prénoms") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                enabled = !isAnonymous
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = donorEmail,
                                onValueChange = { donorEmail = it },
                                label = { Text("Adresse Email") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = donorPhone,
                                onValueChange = { donorPhone = it },
                                label = { Text("Numéro de Téléphone (Mobile Money)") },
                                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Checkboxes
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isAnonymous = !isAnonymous }
                            ) {
                                Checkbox(
                                    checked = isAnonymous,
                                    onCheckedChange = { isAnonymous = it },
                                    colors = CheckboxDefaults.colors(checkedColor = ForestGreenPrimary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Je souhaite que ce don reste anonyme",
                                    fontSize = 12.5.sp,
                                    color = Color(0xFF334155)
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { requestReceipt = !requestReceipt }
                            ) {
                                Checkbox(
                                    checked = requestReceipt,
                                    onCheckedChange = { requestReceipt = it },
                                    colors = CheckboxDefaults.colors(checkedColor = ForestGreenPrimary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Recevoir une attestation / reçu fiscal par email",
                                    fontSize = 12.5.sp,
                                    color = Color(0xFF334155)
                                )
                            }

                            errorMessage?.let { err ->
                                Spacer(modifier = Modifier.height(10.dp))
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFFFEE2E2),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = err,
                                        color = Color(0xFFDC2626),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Submit Button
                            Button(
                                onClick = {
                                    if (effectiveAmount < 500) {
                                        errorMessage = "Le montant minimum de don est de 500 FCFA."
                                        return@Button
                                    }
                                    if (!isAnonymous && donorName.isBlank()) {
                                        errorMessage = "Veuillez renseigner votre nom ou choisir l'option don anonyme."
                                        return@Button
                                    }
                                    if (donorEmail.isBlank() || !donorEmail.contains("@")) {
                                        errorMessage = "Veuillez renseigner une adresse email valide pour recevoir la confirmation."
                                        return@Button
                                    }

                                    errorMessage = null
                                    val randomCode = (10000..99999).random()
                                    donationReference = "AIL4C-DON-2026-$randomCode"
                                    isSuccess = true
                                    onDonationSuccess(effectiveAmount, selectedCause, selectedPaymentMethod.title)
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .testTag("donation_submit_button")
                            ) {
                                Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Valider mon don de ${NumberFormat.getNumberInstance(Locale.FRENCH).format(effectiveAmount)} FCFA",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReceiptRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF64748B),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 12.5.sp,
            color = Color(0xFF0F172A),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}
