package com.example.data.model

data class AppUpdateInfo(
    val latestVersionCode: Int = 2,
    val latestVersionName: String = "v2.0.0",
    val releaseNotes: String = "Nouvelle mise à jour ONG-AIL4C disponible avec des améliorations visuelles, sécurité renforcée et nouvelles fonctionnalités écologiques.",
    val downloadUrl: String = "https://github.com/atchouyaosylvain59/ong-ail4c-android/releases",
    val isMandatory: Boolean = false
)
