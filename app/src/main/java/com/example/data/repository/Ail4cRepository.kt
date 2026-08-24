package com.example.data.repository

import com.example.data.local.Ail4cDao
import com.example.data.model.ActionItem
import com.example.data.model.ContactMessage
import com.example.data.model.NewsArticle
import com.example.data.model.OpportunityItem
import com.example.data.model.ProjectItem
import com.example.data.model.VolunteerApplication
import kotlinx.coroutines.flow.Flow

class Ail4cRepository(private val dao: Ail4cDao) {
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

    // Mutators
    suspend fun insertAction(action: ActionItem) = dao.insertAction(action)
    suspend fun updateAction(action: ActionItem) = dao.updateAction(action)
    suspend fun deleteAction(action: ActionItem) = dao.deleteAction(action)

    suspend fun insertProject(project: ProjectItem) = dao.insertProject(project)
    suspend fun updateProject(project: ProjectItem) = dao.updateProject(project)
    suspend fun deleteProject(project: ProjectItem) = dao.deleteProject(project)

    suspend fun insertNews(news: NewsArticle) = dao.insertNews(news)
    suspend fun updateNews(news: NewsArticle) = dao.updateNews(news)
    suspend fun deleteNews(news: NewsArticle) = dao.deleteNews(news)

    suspend fun insertOpportunity(opp: OpportunityItem) = dao.insertOpportunity(opp)
    suspend fun updateOpportunity(opp: OpportunityItem) = dao.updateOpportunity(opp)
    suspend fun deleteOpportunity(opp: OpportunityItem) = dao.deleteOpportunity(opp)

    suspend fun submitContactMessage(message: ContactMessage) = dao.insertContactMessage(message)
    suspend fun deleteContactMessage(message: ContactMessage) = dao.deleteContactMessage(message)

    suspend fun submitVolunteerApplication(app: VolunteerApplication) = dao.insertVolunteerApplication(app)
    suspend fun deleteVolunteerApplication(app: VolunteerApplication) = dao.deleteVolunteerApplication(app)

    suspend fun ensureDefaultDataPopulated() {
        if (dao.getActionsCount() == 0 || dao.getProjectsCount() == 0 || dao.getNewsCount() == 0 || dao.getOpportunitiesCount() == 0) {
            com.example.data.local.Ail4cDatabase.populateInitialData(dao)
        }
    }
}
