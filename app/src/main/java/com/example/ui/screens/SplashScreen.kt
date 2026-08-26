package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Animated 3D Splash Screen matching the ONG-AIL4C official video animation:
 * - 3D Spherical ribbon globe assembly with orange & green spirals
 * - Light flare sweep & orbiting energy particles
 * - Title "ONG-AIL4C" with bold 3D green & charcoal gradient
 * - Subtitle "Association Ivoirienne de Lutte Contre le Changement Climatique et le Chômage"
 * - Realistic glossy floor mirror reflection
 * - Smooth transition into the login/registration screen
 */
@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animation progress controllers
    val logoScale = remember { Animatable(0.2f) }
    val logoRotationY = remember { Animatable(-180f) }
    val logoRotationZ = remember { Animatable(-30f) }
    val logoAlpha = remember { Animatable(0f) }

    val textAlpha = remember { Animatable(0f) }
    val textSlideY = remember { Animatable(30f) }

    val lightBeamSweep = remember { Animatable(-1f) }
    val reflectionAlpha = remember { Animatable(0f) }

    val overallAlpha = remember { Animatable(1f) }

    // Infinite gentle breathing / shimmer animation once assembled
    val infiniteTransition = rememberInfiniteTransition(label = "splash_infinite")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val flarePosition by infiniteTransition.animateFloat(
        initialValue = -0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "flare_position"
    )

    // Master animation timeline matching the video flow
    LaunchedEffect(Unit) {
        // Phase 1: Globe 3D Spiral In & Spin (0ms -> 1400ms)
        launch {
            logoAlpha.animateTo(1f, tween(700, easing = FastOutSlowInEasing))
        }
        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        launch {
            logoRotationY.animateTo(0f, tween(1400, easing = FastOutSlowInEasing))
        }
        launch {
            logoRotationZ.animateTo(0f, tween(1400, easing = FastOutSlowInEasing))
        }

        // Phase 2: Light flare beam sweep (800ms -> 2000ms)
        delay(600)
        launch {
            lightBeamSweep.animateTo(1.5f, tween(1200, easing = FastOutSlowInEasing))
        }

        // Phase 3: Text & Reflection Reveal (1100ms -> 2400ms)
        delay(500)
        launch {
            textAlpha.animateTo(1f, tween(800, easing = FastOutSlowInEasing))
        }
        launch {
            textSlideY.animateTo(0f, tween(800, easing = FastOutSlowInEasing))
        }
        launch {
            reflectionAlpha.animateTo(0.7f, tween(1000, easing = FastOutSlowInEasing))
        }

        // Phase 4: Hold in full beauty then seamlessly transition to Auth
        delay(2200)
        overallAlpha.animateTo(0f, tween(500, easing = FastOutSlowInEasing))
        onSplashFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .alpha(overallAlpha.value)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFF7FAF7),
                        Color(0xFFEAEFEA),
                        Color(0xFFDCE4DD)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Ambient background studio lights and particles
        SplashAtmosphereCanvas(
            lightProgress = lightBeamSweep.value,
            flareProgress = flarePosition
        )

        // Skip Button top right
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 44.dp, end = 20.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Surface(
                onClick = { onSplashFinished() },
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.85f),
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Passer",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenPrimary
                        )
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Passer l'animation",
                        tint = ForestGreenPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Central 3D Logo Composition
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 3D Animated Logo Emblem Box
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = logoScale.value * pulseScale
                        scaleY = logoScale.value * pulseScale
                        rotationY = logoRotationY.value
                        rotationZ = logoRotationZ.value
                        alpha = logoAlpha.value
                        cameraDistance = 16f * density
                    },
                contentAlignment = Alignment.Center
            ) {
                // High-Resolution 3D Render Banner/Logo matching the video
                Image(
                    painter = painterResource(id = R.drawable.img_splash_video_logo_1787743648079),
                    contentDescription = "Logo ONG-AIL4C 3D",
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit
                )

                // Shimmering flare overlay passing across the logo
                if (lightBeamSweep.value in -0.5f..1.5f) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .height(200.dp)
                    ) {
                        val flareX = size.width * lightBeamSweep.value
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.8f),
                                    Color(0xFFFFB74D).copy(alpha = 0.4f),
                                    Color(0xFF81C784).copy(alpha = 0.2f),
                                    Color.Transparent
                                ),
                                center = Offset(flareX, size.height * 0.45f),
                                radius = size.width * 0.35f
                            ),
                            center = Offset(flareX, size.height * 0.45f),
                            radius = size.width * 0.35f
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle & Organization slogan with glossy entrance
            Column(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = textAlpha.value
                        translationY = textSlideY.value
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Slogan Chips / Pillars (Climat & Emploi)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Surface(
                        color = ForestGreenPrimary.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            ForestGreenPrimary.copy(alpha = 0.3f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Park,
                                contentDescription = null,
                                tint = ForestGreenPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Action Climat",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ForestGreenPrimary
                                )
                            )
                        }
                    }

                    Surface(
                        color = AccentOrange.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            AccentOrange.copy(alpha = 0.3f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Work,
                                contentDescription = null,
                                tint = AccentOrange,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Emploi Verts",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AccentOrange
                                )
                            )
                        }
                    }
                }

                Text(
                    text = "Côte d'Ivoire • Climat • Jeunesse • Avenir",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF556B2F),
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.2.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Studio Floor Mirror Reflection
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(60.dp)
                    .graphicsLayer {
                        alpha = reflectionAlpha.value
                        scaleY = -0.45f // Mirror reflection inverted
                        cameraDistance = 12f * density
                    }
                    .blur(2.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_splash_video_logo_1787743648079),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    contentScale = ContentScale.Fit
                )

                // Floor gradient mask to fade reflection naturally
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFFEAEFEA).copy(alpha = 0.7f),
                                    Color(0xFFEAEFEA)
                                )
                            )
                        )
                )
            }
        }

        // Bottom Brand Signature
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ONG-AIL4C • APPLICATION OFFICIELLE",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = ForestGreenPrimary.copy(alpha = 0.8f)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Version 1.2.0 • 2026",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.Gray,
                    fontSize = 10.sp
                )
            )
        }
    }
}

/**
 * Animated Particle and Light Sweep Canvas
 */
@Composable
private fun SplashAtmosphereCanvas(
    lightProgress: Float,
    flareProgress: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Subtle studio radial spotlight in the background
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White,
                    Color(0xFFF1F8F1).copy(alpha = 0.9f),
                    Color(0xFFE5ECE5).copy(alpha = 0.5f),
                    Color.Transparent
                ),
                center = Offset(w * 0.5f, h * 0.45f),
                radius = w * 0.75f
            ),
            center = Offset(w * 0.5f, h * 0.45f),
            radius = w * 0.75f
        )

        // Orbiting glowing spark particles in Orange & Green
        val particleCount = 18
        for (i in 0 until particleCount) {
            val angle = (flareProgress * 2 * PI + (i * 2 * PI / particleCount)).toFloat()
            val radiusX = w * (0.28f + (i % 3) * 0.06f)
            val radiusY = h * (0.12f + (i % 2) * 0.04f)
            val px = w * 0.5f + cos(angle) * radiusX
            val py = h * 0.42f + sin(angle) * radiusY

            val color = if (i % 2 == 0) {
                Color(0xFFFF7A00).copy(alpha = (0.35f + 0.35f * sin(angle)).coerceIn(0.1f, 0.8f))
            } else {
                Color(0xFF1B5E20).copy(alpha = (0.35f + 0.35f * cos(angle)).coerceIn(0.1f, 0.8f))
            }

            drawCircle(
                color = color,
                radius = 3.5f + (i % 4) * 1.5f,
                center = Offset(px, py)
            )
        }
    }
}
