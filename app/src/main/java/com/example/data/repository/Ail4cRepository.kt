package com.example.data.repository

import com.example.data.api.GeminiAiService
import com.example.data.cloud.FirestoreSyncService
import com.example.data.local.Ail4cDao
import com.example.data.model.ActionItem
import com.example.data.model.ContactMessage
import com.example.data.model.NewsArticle
import com.example.data.model.OpportunityItem
import com.example.data.model.ProjectItem
import com.example.data.model.UserProfile
import com.example.data.model.VolunteerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Ail4cRepository(
    private val dao: Ail4cDao,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    val syncService = FirestoreSyncService(dao, scope)
    val aiService = GeminiAiService()

    val publishedActions: Flow<List<ActionItem>> = dao.getPublishedActions()
    val allActions: Flow<List<ActionItem>> = dao.getAllActions()

    val publishedProjects: Flow<List<ProjectItem>> = dao.getPublishedProjects()
    val allProjects: Flow<List<ProjectItem>> = dao.getAllProjects()

    val publishedNews: Flow<List<NewsArticle>> = dao.getPublishedNews()
    val allNews: Flow<List<NewsArticle>> = dao.getAllNews()

    val publishedOpportunities: Flow<List<OpportunityItem>> = dao.getPublishedOpportunities()
    val allOpportunities: Flow<List<OpportunityItem>> = dao.getAllOpportunities()

    val allContactMessages: Flow<List<ContactMessage>> = dao.getAllContactMessages()
    val allVolunteerApplications: Flow<List<VolunteerApplication>> = dao.getAllVolunteerApplications()

    val currentUserProfile: Flow<UserProfile?> = dao.getUserProfile()

    // User Profile Authentication / Session
    suspend fun saveUserProfile(profile: UserProfile) {
        dao.saveUserProfile(profile)
    }

    suspend fun clearUserSession() {
        dao.clearUserProfiles()
    }

    suspend fun getCurrentUserProfileDirect(): UserProfile? {
        return dao.getCurrentUserProfileDirect()
    }

    // Actions CRUD with realtime Cloud & Room sync
    suspend fun insertAction(action: ActionItem) {
        dao.insertAction(action)
        syncService.syncAction(action)
    }

    suspend fun updateAction(action: ActionItem) {
        dao.updateAction(action)
        syncService.syncAction(action)
    }

    suspend fun deleteAction(action: ActionItem) {
        dao.deleteAction(action)
        syncService.deleteAction(action.id)
    }

    // Projects CRUD with realtime Cloud & Room sync
    suspend fun insertProject(project: ProjectItem) {
        dao.insertProject(project)
        syncService.syncProject(project)
    }

    suspend fun updateProject(project: ProjectItem) {
        dao.updateProject(project)
        syncService.syncProject(project)
    }

    suspend fun deleteProject(project: ProjectItem) {
        dao.deleteProject(project)
        syncService.deleteProject(project.id)
    }

    // News CRUD with realtime Cloud & Room sync
    suspend fun insertNews(news: NewsArticle) {
        dao.insertNews(news)
        syncService.syncNews(news)
    }

    suspend fun updateNews(news: NewsArticle) {
        dao.updateNews(news)
        syncService.syncNews(news)
    }

    suspend fun deleteNews(news: NewsArticle) {
        dao.deleteNews(news)
        syncService.deleteNews(news.id)
    }

    // Opportunities CRUD with realtime Cloud & Room sync
    suspend fun insertOpportunity(opp: OpportunityItem) {
        dao.insertOpportunity(opp)
        syncService.syncOpportunity(opp)
    }

    suspend fun updateOpportunity(opp: OpportunityItem) {
        dao.updateOpportunity(opp)
        syncService.syncOpportunity(opp)
    }

    suspend fun deleteOpportunity(opp: OpportunityItem) {
        dao.deleteOpportunity(opp)
        syncService.deleteOpportunity(opp.id)
    }

    // Messages & Applications
    suspend fun submitContactMessage(message: ContactMessage) {
        dao.insertContactMessage(message)
        syncService.submitContactToCloud(message)
    }

    suspend fun deleteContactMessage(message: ContactMessage) = dao.deleteContactMessage(message)

    suspend fun submitVolunteerApplication(app: VolunteerApplication) {
        dao.insertVolunteerApplication(app)
        syncService.submitVolunteerToCloud(app)
    }

    suspend fun deleteVolunteerApplication(app: VolunteerApplication) = dao.deleteVolunteerApplication(app)

    suspend fun askGeminiAi(
        prompt: String,
        history: List<Pair<String, Boolean>>,
        userName: String
    ): String {
        return aiService.sendMessage(prompt, history, userName)
    }

    suspend fun ensureDefaultDataPopulated() {
        if (dao.getActionsCount() == 0 || dao.getProjectsCount() == 0 || dao.getNewsCount() == 0 || dao.getOpportunitiesCount() == 0) {
            com.example.data.local.Ail4cDatabase.populateInitialData(dao)
        }
    }
}
