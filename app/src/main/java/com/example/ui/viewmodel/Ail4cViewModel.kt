package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.model.ActionItem
import com.example.data.model.AppUpdateInfo
import com.example.data.model.ContactMessage
import com.example.data.model.GalleryItem
import com.example.data.model.NewsArticle
import com.example.data.model.OpportunityItem
import com.example.data.model.ProjectItem
import com.example.data.model.UserProfile
import com.example.data.model.VolunteerApplication
import com.example.data.repository.Ail4cRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

object AdminConfig {
    val AUTHORIZED_ADMIN_EMAILS = setOf(
        "atchouyaosylvain59@gmail.com",
        "ail4c03@gmail.com"
    )
    const val ADMIN_PASSWORD = "AIL4CCI"

    fun isAuthorizedEmail(email: String?): Boolean {
        if (email.isNullOrBlank()) return false
        val clean = email.trim().lowercase()
        return AUTHORIZED_ADMIN_EMAILS.contains(clean)
    }
}

enum class AppDestination(val title: String, val route: String) {
    HOME("Accueil", "home"),
    ABOUT("À propos", "about"),
    OBJECTIVES("Nos objectifs", "objectives"),
    ACTIONS("Nos actions", "actions"),
    PROJECTS("Nos projets", "projects"),
    YOUTH_EMPLOYMENT("Jeunesse & Emploi", "youth_employment"),
    CLIMATE_ENVIRONMENT("Climat & Environnement", "climate_environment"),
    NEWS("Actualités", "news"),
    GALLERY("Galerie", "gallery"),
    CONTACT("Contact", "contact"),
    ADMIN("Administration", "admin"),
    PROFILE("Mon Profil", "profile")
}

class Ail4cViewModel(private val repository: Ail4cRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.ensureDefaultDataPopulated()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Active user session & authentication
    val currentUserProfile: StateFlow<UserProfile?> = repository.currentUserProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Pull-to-refresh state
    val isRefreshing = MutableStateFlow(false)

    fun refreshData() {
        viewModelScope.launch {
            isRefreshing.value = true
            try {
                // Ensure default data & sync fresh data
                repository.ensureDefaultDataPopulated()
                kotlinx.coroutines.delay(650) // Smooth tactile feel for user feedback
                toastMessage.value = "Page et données actualisées ! 🌿"
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isRefreshing.value = false
            }
        }
    }

    // Admin authorization check: only atchouyaosylvain59@gmail.com and ail4c03@gmail.com
    val isCurrentUserAdmin: StateFlow<Boolean> = repository.currentUserProfile
        .map { profile -> AdminConfig.isAuthorizedEmail(profile?.email) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // Admin unlocked state (with password AIL4CCI)
    val isAdminUnlocked = MutableStateFlow(false)

    fun verifyAdminPassword(inputPassword: String): Boolean {
        val isValid = inputPassword.trim() == AdminConfig.ADMIN_PASSWORD
        if (isValid) {
            isAdminUnlocked.value = true
        }
        return isValid
    }

    fun authenticateUser(profile: UserProfile, isAutoAdminUnlock: Boolean = false) {
        viewModelScope.launch {
            if (AdminConfig.isAuthorizedEmail(profile.email) && isAutoAdminUnlock) {
                isAdminUnlocked.value = true
            }
            repository.saveUserProfile(profile)
            if (AdminConfig.isAuthorizedEmail(profile.email)) {
                toastMessage.value = "Bienvenue Administrateur ${profile.fullName} ! 🛡️"
            } else {
                toastMessage.value = "Bienvenue ${profile.fullName} sur l'application AIL4C ! 🌿"
            }
        }
    }

    fun updateUserProfile(updatedProfile: UserProfile) {
        viewModelScope.launch {
            repository.saveUserProfile(updatedProfile)
            toastMessage.value = "Profil mis à jour avec succès ! ✅"
        }
    }

    fun logout() {
        viewModelScope.launch {
            isAdminUnlocked.value = false
            repository.clearUserSession()
            toastMessage.value = "Vous avez été déconnecté."
        }
    }

    // Active screen navigation
    private val _currentDestination = MutableStateFlow(AppDestination.HOME)
    val currentDestination: StateFlow<AppDestination> = _currentDestination.asStateFlow()

    fun navigateTo(destination: AppDestination) {
        _currentDestination.value = destination
    }

    // Modal / Lightbox / AI assistant states
    val selectedAction = MutableStateFlow<ActionItem?>(null)
    val selectedProject = MutableStateFlow<ProjectItem?>(null)
    val selectedNews = MutableStateFlow<NewsArticle?>(null)
    val selectedOpportunity = MutableStateFlow<OpportunityItem?>(null)
    val fullscreenGalleryItem = MutableStateFlow<GalleryItem?>(null)
    val showVolunteerDialog = MutableStateFlow(false)
    val volunteerTargetTitle = MutableStateFlow("")
    val showAiAssistant = MutableStateFlow(false)

    // In-App Update states
    val appUpdateInfo = MutableStateFlow<AppUpdateInfo?>(null)
    val showUpdateDialog = MutableStateFlow(false)
    val currentAppVersionCode = 1
    val currentAppVersionName = "v1.0.0"

    fun checkForUpdates(isUserTriggered: Boolean = false) {
        viewModelScope.launch {
            // Simulated / Cloud server update check logic
            val latestInfo = AppUpdateInfo(
                latestVersionCode = 2,
                latestVersionName = "v2.0.0",
                releaseNotes = "• Nouveau logo officiel ONG-AIL4C en haute définition\n• Système de mise à jour transparente sans désinstallation\n• Optimisation de la fluidité et synchronisation des formulaires",
                downloadUrl = "https://github.com/atchouyaosylvain59/ong-ail4c-android/releases",
                isMandatory = false
            )
            appUpdateInfo.value = latestInfo
            showUpdateDialog.value = true
            if (isUserTriggered) {
                toastMessage.value = "Vérification des mises à jour terminée !"
            }
        }
    }

    fun dismissUpdateDialog() {
        showUpdateDialog.value = false
    }

    // Notification / Toast
    val toastMessage = MutableStateFlow<String?>(null)

    // Data streams from Room & Cloud sync
    val publishedActions = repository.publishedActions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allActions = repository.allActions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val publishedProjects = repository.publishedProjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allProjects = repository.allProjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val publishedNews = repository.publishedNews
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allNews = repository.allNews
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val publishedOpportunities = repository.publishedOpportunities
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allOpportunities = repository.allOpportunities
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val contactMessages = repository.allContactMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val volunteerApplications = repository.allVolunteerApplications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filters
    val actionsCategoryFilter = MutableStateFlow("Toutes")
    val projectsStatusFilter = MutableStateFlow("Tous")
    val projectsDomainFilter = MutableStateFlow("Tous")
    val newsSearchQuery = MutableStateFlow("")
    val newsCategoryFilter = MutableStateFlow("Toutes")
    val galleryCategoryFilter = MutableStateFlow("Toutes")
    val opportunitiesTypeFilter = MutableStateFlow("Toutes")

    // Filtered lists
    val filteredActions: StateFlow<List<ActionItem>> = combine(publishedActions, actionsCategoryFilter) { actions, cat ->
        if (cat == "Toutes") actions else actions.filter { it.category.contains(cat, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredProjects: StateFlow<List<ProjectItem>> = combine(
        publishedProjects,
        projectsStatusFilter,
        projectsDomainFilter
    ) { projects, status, domain ->
        projects.filter {
            (status == "Tous" || it.status.equals(status, ignoreCase = true)) &&
            (domain == "Tous" || it.domain.contains(domain, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredNews: StateFlow<List<NewsArticle>> = combine(
        publishedNews,
        newsSearchQuery,
        newsCategoryFilter
    ) { newsList, query, cat ->
        newsList.filter {
            val matchesQuery = query.isBlank() ||
                    it.title.contains(query, ignoreCase = true) ||
                    it.summary.contains(query, ignoreCase = true) ||
                    it.fullContent.contains(query, ignoreCase = true)
            val matchesCat = cat == "Toutes" || it.category.contains(cat, ignoreCase = true)
            matchesQuery && matchesCat
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredOpportunities: StateFlow<List<OpportunityItem>> = combine(
        publishedOpportunities,
        opportunitiesTypeFilter
    ) { list, type ->
        if (type == "Toutes") list else list.filter { it.type.contains(type, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Static Gallery items
    val staticGalleryItems = listOf(
        GalleryItem(
            id = 1,
            title = "Volontaires AIL4C sur le terrain",
            category = "Actions terrain",
            location = "Abidjan, Côte d'Ivoire",
            date = "Février 2026",
            imageRes = R.drawable.img_hero_community,
            description = "Équipe de jeunes dynamiques engagés dans la préservation de l'environnement urbain et l'éco-citoyenneté."
        ),
        GalleryItem(
            id = 2,
            title = "Reboisement et Plantation d'arbres",
            category = "Actions terrain",
            location = "Littoral de Grand-Bassam",
            date = "Janvier 2026",
            imageRes = R.drawable.img_reboisement,
            description = "Mise en terre de plants pour restaurer le couvert végétal et renforcer la résilience côtière."
        ),
        GalleryItem(
            id = 3,
            title = "Formation technique en énergie solaire",
            category = "Formations",
            location = "Centre de formation AIL4C, Abidjan",
            date = "Janvier 2026",
            imageRes = R.drawable.img_formation_vert,
            description = "Apprentissage des techniques d'installation et de maintenance photovoltaïque pour les jeunes ivoiriens."
        ),
        GalleryItem(
            id = 4,
            title = "Grande journée de salubrité et tri sélectif",
            category = "Salubrité & Climat",
            location = "District d'Abidjan",
            date = "Février 2026",
            imageRes = R.drawable.img_salubrite_ville,
            description = "Collecte sélective et recyclage des déchets plastiques pour assainir les quartiers et prévenir les inondations."
        ),
        GalleryItem(
            id = 5,
            title = "Mobilisation des jeunes pour les ODD",
            category = "Communautés",
            location = "Yamoussoukro",
            date = "Décembre 2025",
            imageRes = R.drawable.img_hero_community,
            description = "Sensibilisation et structuration de clubs écologiques dans les communautés scolaires et universitaires."
        ),
        GalleryItem(
            id = 6,
            title = "Atelier de sensibilisation des éco-délégués",
            category = "Formations",
            location = "Bouaké",
            date = "Novembre 2025",
            imageRes = R.drawable.img_formation_vert,
            description = "Renforcement des capacités des ambassadeurs climat sur la gestion durable des ressources et l'économie circulaire."
        )
    )

    val filteredGallery: StateFlow<List<GalleryItem>> = galleryCategoryFilter.combine(
        MutableStateFlow(staticGalleryItems)
    ) { cat, items ->
        if (cat == "Toutes") items else items.filter { it.category.contains(cat, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), staticGalleryItems)

    // Contact form submission with cloud sync
    fun submitContact(
        fullName: String,
        email: String,
        phone: String,
        subject: String,
        message: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (fullName.isBlank() || email.isBlank() || message.isBlank()) {
            onError("Veuillez renseigner votre nom, email et message.")
            return
        }
        viewModelScope.launch {
            try {
                repository.submitContactMessage(
                    ContactMessage(
                        fullName = fullName.trim(),
                        email = email.trim(),
                        phone = phone.trim(),
                        subject = subject.ifBlank { "Demande générale ONG-AIL4C" }.trim(),
                        message = message.trim()
                    )
                )
                toastMessage.value = "Votre message a été transmis avec succès à l'équipe ONG-AIL4C."
                onSuccess()
            } catch (e: Exception) {
                onError("Une erreur est survenue lors de l'envoi. Veuillez réessayer.")
            }
        }
    }

    // Volunteer application submission with cloud sync
    fun submitVolunteerApplication(
        fullName: String,
        email: String,
        phone: String,
        city: String,
        opportunityTitle: String,
        domainOfInterest: String,
        motivation: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (fullName.isBlank() || email.isBlank() || phone.isBlank()) {
            onError("Veuillez renseigner votre nom complet, email et numéro de téléphone.")
            return
        }
        viewModelScope.launch {
            try {
                repository.submitVolunteerApplication(
                    VolunteerApplication(
                        fullName = fullName.trim(),
                        email = email.trim(),
                        phone = phone.trim(),
                        city = city.ifBlank { "Abidjan" }.trim(),
                        opportunityTitle = opportunityTitle.ifBlank { "Engagement bénévole général" },
                        domainOfInterest = domainOfInterest.ifBlank { "Environnement & Climat" },
                        motivation = motivation.trim()
                    )
                )
                toastMessage.value = "Candidature enregistrée avec succès ! L'ONG-AIL4C vous contactera prochainement."
                onSuccess()
            } catch (e: Exception) {
                onError("Erreur lors de l'enregistrement de votre candidature.")
            }
        }
    }

    // AI Assistant call
    suspend fun askAi(prompt: String, history: List<Pair<String, Boolean>>, userName: String): String {
        return repository.askGeminiAi(prompt, history, userName)
    }

    // Admin CRUD Operations with Realtime Room + Cloud sync
    fun addAction(action: ActionItem) {
        viewModelScope.launch {
            repository.insertAction(action)
            toastMessage.value = "Action enregistrée et synchronisée en temps réel !"
        }
    }

    fun updateAction(action: ActionItem) {
        viewModelScope.launch {
            repository.updateAction(action)
            toastMessage.value = "Action mise à jour en temps réel."
        }
    }

    fun deleteAction(action: ActionItem) {
        viewModelScope.launch {
            repository.deleteAction(action)
            toastMessage.value = "Action supprimée en temps réel."
        }
    }

    fun addProject(project: ProjectItem) {
        viewModelScope.launch {
            repository.insertProject(project)
            toastMessage.value = "Projet enregistré et synchronisé en temps réel !"
        }
    }

    fun updateProject(project: ProjectItem) {
        viewModelScope.launch {
            repository.updateProject(project)
            toastMessage.value = "Projet mis à jour en temps réel."
        }
    }

    fun deleteProject(project: ProjectItem) {
        viewModelScope.launch {
            repository.deleteProject(project)
            toastMessage.value = "Projet supprimé en temps réel."
        }
    }

    fun addNews(news: NewsArticle) {
        viewModelScope.launch {
            repository.insertNews(news)
            toastMessage.value = "Actualité publiée et synchronisée en temps réel !"
        }
    }

    fun updateNews(news: NewsArticle) {
        viewModelScope.launch {
            repository.updateNews(news)
            toastMessage.value = "Actualité mise à jour en temps réel."
        }
    }

    fun deleteNews(news: NewsArticle) {
        viewModelScope.launch {
            repository.deleteNews(news)
            toastMessage.value = "Actualité supprimée en temps réel."
        }
    }

    fun addOpportunity(opp: OpportunityItem) {
        viewModelScope.launch {
            repository.insertOpportunity(opp)
            toastMessage.value = "Opportunité publiée et synchronisée en temps réel !"
        }
    }

    fun updateOpportunity(opp: OpportunityItem) {
        viewModelScope.launch {
            repository.updateOpportunity(opp)
            toastMessage.value = "Opportunité mise à jour en temps réel."
        }
    }

    fun deleteOpportunity(opp: OpportunityItem) {
        viewModelScope.launch {
            repository.deleteOpportunity(opp)
            toastMessage.value = "Opportunité supprimée en temps réel."
        }
    }

    fun deleteContactMessage(msg: ContactMessage) {
        viewModelScope.launch {
            repository.deleteContactMessage(msg)
            toastMessage.value = "Message supprimé."
        }
    }

    fun deleteVolunteerApplication(app: VolunteerApplication) {
        viewModelScope.launch {
            repository.deleteVolunteerApplication(app)
            toastMessage.value = "Candidature supprimée."
        }
    }

    fun openVolunteerDialog(targetTitle: String = "Bénévolat & Engagement AIL4C") {
        volunteerTargetTitle.value = targetTitle
        showVolunteerDialog.value = true
    }

    fun openAiAssistant() {
        showAiAssistant.value = true
    }

    fun closeAiAssistant() {
        showAiAssistant.value = false
    }

    fun clearToast() {
        toastMessage.value = null
    }
}

class Ail4cViewModelFactory(private val repository: Ail4cRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Ail4cViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return Ail4cViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
