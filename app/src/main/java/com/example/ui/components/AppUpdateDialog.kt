package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppUpdateInfo
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenPrimary

@Composable
fun AppUpdateDialog(
    updateInfo: AppUpdateInfo,
    currentVersionCode: Int,
    currentVersionName: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val hasUpdate = updateInfo.latestVersionCode > currentVersionCode

    AlertDialog(
        onDismissRequest = {
            if (!updateInfo.isMandatory) {
                onDismiss()
            }
        },
        title = null,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(if (hasUpdate) ForestGreenPrimary.copy(alpha = 0.15f) else AccentOrange.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (hasUpdate) Icons.Default.SystemUpdate else Icons.Default.Info,
                        contentDescription = "Mise à jour",
                        tint = if (hasUpdate) ForestGreenPrimary else AccentOrange,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (hasUpdate) "Mise à jour disponible !" else "Application à jour",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = if (hasUpdate) {
                        "Version installée : $currentVersionName ➔ Nouvelle : ${updateInfo.latestVersionName}"
                    } else {
                        "Vous utilisez la version la plus récente ($currentVersionName)."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )

                if (hasUpdate) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Nouveautés & Améliorations :",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = ForestGreenPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = updateInfo.releaseNotes,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "ℹ️ Aucun besoin de désinstaller : vos données et connexions sont entièrement conservées lors de la mise à jour.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF2E7D32),
                        fontSize = 11.sp
                    )
                }
            }
        },
        confirmButton = {
            if (hasUpdate) {
                Button(
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateInfo.downloadUrl))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Fallback
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("btn_download_update")
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Télécharger la mise à jour", fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("D'accord")
                }
            }
        },
        dismissButton = {
            if (hasUpdate && !updateInfo.isMandatory) {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("btn_dismiss_update")
                ) {
                    Text("Plus tard", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.surface
    )
}
