package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.UserProfile
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenContainer
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.AdminConfig
import com.example.ui.viewmodel.Ail4cViewModel

enum class AuthStage {
    ONBOARDING,
    LOGIN,
    SIGNUP
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: Ail4cViewModel,
    modifier: Modifier = Modifier
) {
    var stage by remember { mutableStateOf(AuthStage.LOGIN) }
    var onboardingStep by remember { mutableIntStateOf(0) }

    // Form inputs
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("+225 ") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var city by remember { mutableStateOf("Abidjan") }
    var roleInterest by remember { mutableStateOf("Bénévole Climat & Environnement") }
    var cityExpanded by remember { mutableStateOf(false) }
    var roleExpanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val cities = listOf("Abidjan", "Bouaké (UAO)", "Yamoussoukro", "Grand-Bassam", "San Pedro", "Korhogo", "Daloa", "Autre / Diaspora")
    val roles = listOf(
        "Bénévole Climat & Environnement",
        "Jeune / Étudiant en quête de formation",
        "Éco-Citoyen & Sympathisant",
        "Donateur / Partenaire Institutionnel",
        "Membre Adhérent Actif"
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFFFFFFF)
    ) {
        AnimatedContent(
            targetState = stage,
            transitionSpec = {
                (fadeIn(animationSpec = androidx.compose.animation.core.tween(300)))
                    .togetherWith(fadeOut(animationSpec = androidx.compose.animation.core.tween(200)))
            },
            label = "AuthStageAnimation"
        ) { currentStage ->
            when (currentStage) {
                AuthStage.ONBOARDING -> {
                    // Modern iOS style Onboarding from picture
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Top Logo
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(ForestGreenContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_launcher_custom_logo_1787715328062),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AIL4C",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                color = ForestGreenPrimary
                            )
                        }

                        // Center Hero Illustration / Photo
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Card(
                                shape = RoundedCornerShape(28.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(260.dp)
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = if (onboardingStep == 0) R.drawable.img_hero_community else R.drawable.img_formation_vert
                                    ),
                                    contentDescription = "Illustration Onboarding",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = if (onboardingStep == 0)
                                    "L'Action Climatique\nAccessible à Tous"
                                else
                                    "Autonomisez Votre Avenir\nGrâce aux Métiers Verts",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                color = ForestGreenDark,
                                lineHeight = 30.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = if (onboardingStep == 0)
                                    "Rejoignez les leaders du climat à Bouaké et Abidjan pour préserver notre environnement ivoirien."
                                else
                                    "Bénéficiez de formations certifiantes gratuites en énergie solaire, recyclage et agroécologie.",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF64748B),
                                lineHeight = 20.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Indicator dots
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(width = if (onboardingStep == 0) 24.dp else 8.dp, height = 8.dp)
                                        .clip(CircleShape)
                                        .background(if (onboardingStep == 0) ForestGreenPrimary else Color(0xFFE2E8F0))
                                )
                                Box(
                                    modifier = Modifier
                                        .size(width = if (onboardingStep == 1) 24.dp else 8.dp, height = 8.dp)
                                        .clip(CircleShape)
                                        .background(if (onboardingStep == 1) ForestGreenPrimary else Color(0xFFE2E8F0))
                                )
                            }
                        }

                        // Bottom Actions
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    if (onboardingStep == 0) {
                                        onboardingStep = 1
                                    } else {
                                        stage = AuthStage.LOGIN
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .testTag("onboarding_next_button")
                            ) {
                                Text(
                                    text = if (onboardingStep == 0) "Suivant" else "Commencer",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            TextButton(
                                onClick = { stage = AuthStage.LOGIN }
                            ) {
                                Text(
                                    text = "Passer l'introduction",
                                    color = Color(0xFF94A3B8),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                AuthStage.LOGIN -> {
                    // Modern iOS Login Screen (Screen 4 in picture)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        // Header with Back arrow & title
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = { stage = AuthStage.ONBOARDING },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF1F5F9))
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Retour",
                                    tint = Color(0xFF1E293B)
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Connexion",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.size(40.dp)) // balance
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "Bienvenue sur AIL4C",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ForestGreenDark
                        )
                        Text(
                            text = "Connectez-vous pour accéder à vos actions et formations.",
                            fontSize = 14.sp,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                        )

                        errorMessage?.let { err ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            ) {
                                Text(
                                    text = err,
                                    color = Color(0xFFDC2626),
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }

                        // Email Field
                        Text(
                            text = "Adresse Email",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF334155),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("votre.email@gmail.com", color = Color(0xFF94A3B8)) },
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = ForestGreenPrimary) },
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_email_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ForestGreenPrimary,
                                unfocusedBorderColor = Color(0xFFE2E8F0),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Password Field
                        Text(
                            text = "Mot de passe",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF334155),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = { Text("••••••••", color = Color(0xFF94A3B8)) },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = ForestGreenPrimary) },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = null,
                                        tint = Color(0xFF94A3B8)
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_password_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ForestGreenPrimary,
                                unfocusedBorderColor = Color(0xFFE2E8F0),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Emerald Pill Button Login
                        Button(
                            onClick = {
                                if (email.isBlank()) {
                                    errorMessage = "Veuillez renseigner votre adresse email."
                                    return@Button
                                }
                                if (password.isBlank()) {
                                    errorMessage = "Veuillez saisir un mot de passe."
                                    return@Button
                                }

                                val cleanEmail = email.trim().lowercase()
                                val isAdminEmail = AdminConfig.isAuthorizedEmail(cleanEmail)

                                if (isAdminEmail && password.trim() != AdminConfig.ADMIN_PASSWORD) {
                                    errorMessage = "Mot de passe administrateur incorrect (Requis : AIL4CCI)."
                                    return@Button
                                }

                                val namePart = when {
                                    cleanEmail == "atchouyaosylvain59@gmail.com" -> "Sylvain Atchouyao"
                                    cleanEmail == "ail4c03@gmail.com" -> "Direction ONG-AIL4C"
                                    cleanEmail.contains("@") -> cleanEmail.substringBefore("@").replaceFirstChar { it.uppercase() }
                                    else -> "Membre AIL4C"
                                }

                                viewModel.authenticateUser(
                                    UserProfile(
                                        id = "user_default",
                                        fullName = namePart,
                                        email = cleanEmail,
                                        phone = if (isAdminEmail) "+225 07 77 12 34 56" else "+225 07 00 00 00 00",
                                        city = if (isAdminEmail) "Siège ONG-AIL4C (Bouaké / Abidjan)" else "Abidjan / Bouaké",
                                        roleInterest = if (isAdminEmail) "Administrateur Exécutif ONG-AIL4C" else "Membre Engagé AIL4C",
                                        isMember = true
                                    ),
                                    isAutoAdminUnlock = isAdminEmail
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .shadow(8.dp, shape = RoundedCornerShape(16.dp), spotColor = ForestGreenPrimary)
                                .testTag("login_submit_button")
                        ) {
                            Text(
                                text = "Se connecter",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Switch to Sign up link
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Vous n'avez pas de compte ? ",
                                color = Color(0xFF64748B),
                                fontSize = 14.sp
                            )
                            Text(
                                text = "S'inscrire",
                                color = ForestGreenPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .clickable {
                                        errorMessage = null
                                        stage = AuthStage.SIGNUP
                                    }
                                    .testTag("switch_to_signup_button")
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Divider OR
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE2E8F0))
                            Text(
                                text = " OU ",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF94A3B8),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE2E8F0))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Social Fast Sign-in Pill Buttons (as in picture)
                        OutlinedButton(
                            onClick = {
                                viewModel.authenticateUser(
                                    UserProfile(
                                        id = "user_default",
                                        fullName = "Administrateur ONG-AIL4C",
                                        email = "ail4c03@gmail.com",
                                        phone = "+225 07 48 37 32 50",
                                        city = "Bouaké & Abidjan",
                                        roleInterest = "Administration & Coordination ONG-AIL4C",
                                        isMember = true
                                    ),
                                    isAutoAdminUnlock = true
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("login_admin_shortcut_btn"),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ForestGreenDark)
                        ) {
                            Icon(
                                Icons.Default.AdminPanelSettings,
                                contentDescription = null,
                                tint = ForestGreenPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Connexion Rapide Administrateur",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = {
                                viewModel.authenticateUser(
                                    UserProfile(
                                        id = "user_default",
                                        fullName = "Éco-Citoyen Invité",
                                        email = "visiteur@ong-ail4c.ci",
                                        phone = "+225 00 00 00 00",
                                        city = "Côte d'Ivoire",
                                        roleInterest = "Visiteur Découverte",
                                        isMember = false
                                    )
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("login_guest_button"),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF334155))
                        ) {
                            Text(
                                text = "Découvrir sans compte (Mode Invité)",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                AuthStage.SIGNUP -> {
                    // Modern iOS Sign Up Screen (Screen 5 in picture)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        // Header with Back arrow & title
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = { stage = AuthStage.LOGIN },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF1F5F9))
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Retour",
                                    tint = Color(0xFF1E293B)
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Inscription",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.size(40.dp))
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Créer votre profil",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ForestGreenDark
                        )
                        Text(
                            text = "Rejoignez la communauté AIL4C et participez à nos projets.",
                            fontSize = 14.sp,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                        )

                        errorMessage?.let { err ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            ) {
                                Text(
                                    text = err,
                                    color = Color(0xFFDC2626),
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }

                        // Full Name
                        Text(
                            text = "Nom et Prénoms",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF334155),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            placeholder = { Text("Ex: Koffi Emmanuel", color = Color(0xFF94A3B8)) },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = ForestGreenPrimary) },
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("signup_fullname_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ForestGreenPrimary,
                                unfocusedBorderColor = Color(0xFFE2E8F0),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Phone
                        Text(
                            text = "Numéro de téléphone",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF334155),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            placeholder = { Text("+225 07 00 00 00 00", color = Color(0xFF94A3B8)) },
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = ForestGreenPrimary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("signup_phone_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ForestGreenPrimary,
                                unfocusedBorderColor = Color(0xFFE2E8F0),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Email
                        Text(
                            text = "Adresse Email",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF334155),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("votre.email@gmail.com", color = Color(0xFF94A3B8)) },
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = ForestGreenPrimary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("signup_email_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ForestGreenPrimary,
                                unfocusedBorderColor = Color(0xFFE2E8F0),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Password
                        Text(
                            text = "Mot de passe",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF334155),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = { Text("Au moins 4 caractères", color = Color(0xFF94A3B8)) },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = ForestGreenPrimary) },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = null,
                                        tint = Color(0xFF94A3B8)
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("signup_password_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ForestGreenPrimary,
                                unfocusedBorderColor = Color(0xFFE2E8F0),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Sign Up Button
                        Button(
                            onClick = {
                                if (fullName.isBlank()) {
                                    errorMessage = "Veuillez entrer votre nom."
                                    return@Button
                                }
                                if (email.isBlank() || !email.contains("@")) {
                                    errorMessage = "Veuillez entrer un email valide."
                                    return@Button
                                }
                                if (password.length < 4) {
                                    errorMessage = "Mot de passe trop court (min 4 caractères)."
                                    return@Button
                                }

                                val cleanEmail = email.trim().lowercase()
                                val isAdminEmail = AdminConfig.isAuthorizedEmail(cleanEmail)
                                if (isAdminEmail && password.trim() != AdminConfig.ADMIN_PASSWORD) {
                                    errorMessage = "Mot de passe administrateur incorrect (Requis : AIL4CCI)."
                                    return@Button
                                }

                                viewModel.authenticateUser(
                                    UserProfile(
                                        id = "user_default",
                                        fullName = fullName.trim(),
                                        email = cleanEmail,
                                        phone = phone.trim(),
                                        city = city,
                                        roleInterest = if (isAdminEmail) "Administrateur Exécutif ONG-AIL4C" else roleInterest,
                                        isMember = true
                                    ),
                                    isAutoAdminUnlock = isAdminEmail
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .shadow(8.dp, shape = RoundedCornerShape(16.dp), spotColor = ForestGreenPrimary)
                                .testTag("signup_submit_button")
                        ) {
                            Text(
                                text = "Créer mon compte",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Vous avez déjà un compte ? ",
                                color = Color(0xFF64748B),
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Connexion",
                                color = ForestGreenPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .clickable {
                                        errorMessage = null
                                        stage = AuthStage.LOGIN
                                    }
                                    .testTag("switch_to_login_button")
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}
