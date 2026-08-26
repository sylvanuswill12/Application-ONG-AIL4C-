package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ActionItem
import com.example.data.model.ContactMessage
import com.example.data.model.NewsArticle
import com.example.data.model.OpportunityItem
import com.example.data.model.ProjectItem
import com.example.data.model.VolunteerApplication
import kotlinx.coroutines.flow.Flow

@Dao
interface Ail4cDao {
    // Actions
    @Query("SELECT * FROM actions WHERE isPublished = 1 ORDER BY id DESC")
    fun getPublishedActions(): Flow<List<ActionItem>>

    @Query("SELECT * FROM actions ORDER BY id DESC")
    fun getAllActions(): Flow<List<ActionItem>>

    @Query("SELECT COUNT(*) FROM actions")
    suspend fun getActionsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAction(action: ActionItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActions(actions: List<ActionItem>)

    @Update
    suspend fun updateAction(action: ActionItem)

    @Delete
    suspend fun deleteAction(action: ActionItem)

    // Projects
    @Query("SELECT * FROM projects WHERE isPublished = 1 ORDER BY id DESC")
    fun getPublishedProjects(): Flow<List<ProjectItem>>

    @Query("SELECT * FROM projects ORDER BY id DESC")
    fun getAllProjects(): Flow<List<ProjectItem>>

    @Query("SELECT COUNT(*) FROM projects")
    suspend fun getProjectsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectItem>)

    @Update
    suspend fun updateProject(project: ProjectItem)

    @Delete
    suspend fun deleteProject(project: ProjectItem)

    // News
    @Query("SELECT * FROM news WHERE isPublished = 1 ORDER BY id DESC")
    fun getPublishedNews(): Flow<List<NewsArticle>>

    @Query("SELECT * FROM news ORDER BY id DESC")
    fun getAllNews(): Flow<List<NewsArticle>>

    @Query("SELECT COUNT(*) FROM news")
    suspend fun getNewsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsList(newsList: List<NewsArticle>)

    @Update
    suspend fun updateNews(news: NewsArticle)

    @Delete
    suspend fun deleteNews(news: NewsArticle)

    // Opportunities
    @Query("SELECT * FROM opportunities WHERE isPublished = 1 ORDER BY id DESC")
    fun getPublishedOpportunities(): Flow<List<OpportunityItem>>

    @Query("SELECT * FROM opportunities ORDER BY id DESC")
    fun getAllOpportunities(): Flow<List<OpportunityItem>>

    @Query("SELECT COUNT(*) FROM opportunities")
    suspend fun getOpportunitiesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpportunity(opportunity: OpportunityItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpportunities(opportunities: List<OpportunityItem>)

    @Update
    suspend fun updateOpportunity(opportunity: OpportunityItem)

    @Delete
    suspend fun deleteOpportunity(opportunity: OpportunityItem)

    // Contact Messages
    @Query("SELECT * FROM contact_messages ORDER BY timestamp DESC")
    fun getAllContactMessages(): Flow<List<ContactMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactMessage(message: ContactMessage)

    @Delete
    suspend fun deleteContactMessage(message: ContactMessage)

    // Volunteer Applications
    @Query("SELECT * FROM volunteer_applications ORDER BY timestamp DESC")
    fun getAllVolunteerApplications(): Flow<List<VolunteerApplication>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVolunteerApplication(application: VolunteerApplication)

    @Delete
    suspend fun deleteVolunteerApplication(application: VolunteerApplication)

    // User Profile
    @Query("SELECT * FROM user_profiles ORDER BY registeredAt DESC LIMIT 1")
    fun getUserProfile(): Flow<com.example.data.model.UserProfile?>

    @Query("SELECT * FROM user_profiles ORDER BY registeredAt DESC LIMIT 1")
    suspend fun getCurrentUserProfileDirect(): com.example.data.model.UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: com.example.data.model.UserProfile)

    @Query("DELETE FROM user_profiles")
    suspend fun clearUserProfiles()
}
