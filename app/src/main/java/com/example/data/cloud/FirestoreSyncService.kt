package com.example.data.cloud

import android.util.Log
import com.example.data.local.Ail4cDao
import com.example.data.model.ActionItem
import com.example.data.model.ContactMessage
import com.example.data.model.NewsArticle
import com.example.data.model.OpportunityItem
import com.example.data.model.ProjectItem
import com.example.data.model.VolunteerApplication
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirestoreSyncService(
    private val dao: Ail4cDao,
    private val scope: CoroutineScope
) {
    private val TAG = "FirestoreSyncService"
    private var firestoreInstance: FirebaseFirestore? = null
    private val listeners = mutableListOf<ListenerRegistration>()

    init {
        try {
            firestoreInstance = FirebaseFirestore.getInstance()
            Log.d(TAG, "Firebase Firestore initialized successfully.")
            startRealtimeListeners()
        } catch (e: Exception) {
            Log.w(TAG, "Firestore initialization skipped/deferred: ${e.message}")
        }
    }

    private fun startRealtimeListeners() {
        val firestore = firestoreInstance ?: return

        try {
            // Listen to remote actions
            val actionListener = firestore.collection("actions")
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        Log.w(TAG, "Listen failed for actions: ${error.message}")
                        return@addSnapshotListener
                    }
                    if (snapshots != null && !snapshots.isEmpty) {
                        scope.launch(Dispatchers.IO) {
                            val items = snapshots.documents.mapNotNull { doc ->
                                try {
                                    val id = doc.getLong("id")?.toInt() ?: doc.id.hashCode()
                                    val title = doc.getString("title") ?: return@mapNotNull null
                                    val shortDescription = doc.getString("shortDescription") ?: ""
                                    val fullStory = doc.getString("fullStory") ?: ""
                                    val category = doc.getString("category") ?: "Sensibilisation"
                                    val date = doc.getString("date") ?: ""
                                    val location = doc.getString("location") ?: "Côte d'Ivoire"
                                    val beneficiariesCount = doc.getString("beneficiariesCount") ?: "100+"
                                    val isPublished = doc.getBoolean("isPublished") ?: true
                                    ActionItem(
                                        id = id,
                                        title = title,
                                        shortDescription = shortDescription,
                                        fullStory = fullStory,
                                        category = category,
                                        date = date,
                                        location = location,
                                        beneficiariesCount = beneficiariesCount,
                                        isPublished = isPublished
                                    )
                                } catch (e: Exception) {
                                    null
                                }
                            }
                            if (items.isNotEmpty()) {
                                dao.insertActions(items)
                            }
                        }
                    }
                }
            listeners.add(actionListener)

            // Listen to remote projects
            val projectListener = firestore.collection("projects")
                .addSnapshotListener { snapshots, error ->
                    if (error != null) return@addSnapshotListener
                    if (snapshots != null && !snapshots.isEmpty) {
                        scope.launch(Dispatchers.IO) {
                            val items = snapshots.documents.mapNotNull { doc ->
                                try {
                                    val id = doc.getLong("id")?.toInt() ?: doc.id.hashCode()
                                    val title = doc.getString("title") ?: return@mapNotNull null
                                    val domain = doc.getString("domain") ?: "Climat"
                                    val shortDescription = doc.getString("shortDescription") ?: ""
                                    val fullDescription = doc.getString("fullDescription") ?: ""
                                    val status = doc.getString("status") ?: "En cours"
                                    val dateRange = doc.getString("dateRange") ?: ""
                                    val location = doc.getString("location") ?: ""
                                    val progressPercent = doc.getLong("progressPercent")?.toInt() ?: 50
                                    val targetBeneficiaries = doc.getString("targetBeneficiaries") ?: "5 000"
                                    val partnersMention = doc.getString("partnersMention") ?: ""
                                    val isPublished = doc.getBoolean("isPublished") ?: true
                                    ProjectItem(
                                        id = id,
                                        title = title,
                                        domain = domain,
                                        shortDescription = shortDescription,
                                        fullDescription = fullDescription,
                                        status = status,
                                        dateRange = dateRange,
                                        location = location,
                                        progressPercent = progressPercent,
                                        targetBeneficiaries = targetBeneficiaries,
                                        partnersMention = partnersMention,
                                        isPublished = isPublished
                                    )
                                } catch (e: Exception) {
                                    null
                                }
                            }
                            if (items.isNotEmpty()) {
                                dao.insertProjects(items)
                            }
                        }
                    }
                }
            listeners.add(projectListener)

            // Listen to remote news
            val newsListener = firestore.collection("news")
                .addSnapshotListener { snapshots, error ->
                    if (error != null) return@addSnapshotListener
                    if (snapshots != null && !snapshots.isEmpty) {
                        scope.launch(Dispatchers.IO) {
                            val items = snapshots.documents.mapNotNull { doc ->
                                try {
                                    val id = doc.getLong("id")?.toInt() ?: doc.id.hashCode()
                                    val title = doc.getString("title") ?: return@mapNotNull null
                                    val category = doc.getString("category") ?: "Actualité"
                                    val date = doc.getString("date") ?: ""
                                    val summary = doc.getString("summary") ?: ""
                                    val fullContent = doc.getString("fullContent") ?: ""
                                    val author = doc.getString("author") ?: "AIL4C"
                                    val readTime = doc.getString("readTime") ?: "3 min"
                                    val isFeatured = doc.getBoolean("isFeatured") ?: false
                                    val isPublished = doc.getBoolean("isPublished") ?: true
                                    NewsArticle(
                                        id = id,
                                        title = title,
                                        category = category,
                                        date = date,
                                        summary = summary,
                                        fullContent = fullContent,
                                        author = author,
                                        readTime = readTime,
                                        isFeatured = isFeatured,
                                        isPublished = isPublished
                                    )
                                } catch (e: Exception) {
                                    null
                                }
                            }
                            if (items.isNotEmpty()) {
                                dao.insertNewsList(items)
                            }
                        }
                    }
                }
            listeners.add(newsListener)
        } catch (e: Exception) {
            Log.w(TAG, "Error starting Firestore listeners: ${e.message}")
        }
    }

    // Cloud CRUD mutations
    fun syncAction(action: ActionItem) {
        val firestore = firestoreInstance ?: return
        try {
            val data = hashMapOf(
                "id" to action.id,
                "title" to action.title,
                "shortDescription" to action.shortDescription,
                "fullStory" to action.fullStory,
                "category" to action.category,
                "date" to action.date,
                "location" to action.location,
                "beneficiariesCount" to action.beneficiariesCount,
                "isPublished" to action.isPublished,
                "updatedAt" to System.currentTimeMillis()
            )
            firestore.collection("actions").document(action.id.toString()).set(data)
                .addOnSuccessListener { Log.d(TAG, "Action synced to Firestore: ${action.id}") }
                .addOnFailureListener { e -> Log.w(TAG, "Failed syncing action: ${e.message}") }
        } catch (e: Exception) {
            Log.w(TAG, "Firestore syncAction exception: ${e.message}")
        }
    }

    fun deleteAction(actionId: Int) {
        val firestore = firestoreInstance ?: return
        try {
            firestore.collection("actions").document(actionId.toString()).delete()
                .addOnSuccessListener { Log.d(TAG, "Action deleted from Firestore: $actionId") }
        } catch (e: Exception) {
            Log.w(TAG, "Firestore deleteAction exception: ${e.message}")
        }
    }

    fun syncProject(project: ProjectItem) {
        val firestore = firestoreInstance ?: return
        try {
            val data = hashMapOf(
                "id" to project.id,
                "title" to project.title,
                "domain" to project.domain,
                "shortDescription" to project.shortDescription,
                "fullDescription" to project.fullDescription,
                "status" to project.status,
                "dateRange" to project.dateRange,
                "location" to project.location,
                "progressPercent" to project.progressPercent,
                "targetBeneficiaries" to project.targetBeneficiaries,
                "partnersMention" to project.partnersMention,
                "isPublished" to project.isPublished,
                "updatedAt" to System.currentTimeMillis()
            )
            firestore.collection("projects").document(project.id.toString()).set(data)
        } catch (e: Exception) {
            Log.w(TAG, "Firestore syncProject exception: ${e.message}")
        }
    }

    fun deleteProject(projectId: Int) {
        val firestore = firestoreInstance ?: return
        try {
            firestore.collection("projects").document(projectId.toString()).delete()
        } catch (e: Exception) {
            Log.w(TAG, "Firestore deleteProject exception: ${e.message}")
        }
    }

    fun syncNews(news: NewsArticle) {
        val firestore = firestoreInstance ?: return
        try {
            val data = hashMapOf(
                "id" to news.id,
                "title" to news.title,
                "category" to news.category,
                "date" to news.date,
                "summary" to news.summary,
                "fullContent" to news.fullContent,
                "author" to news.author,
                "readTime" to news.readTime,
                "isFeatured" to news.isFeatured,
                "isPublished" to news.isPublished,
                "updatedAt" to System.currentTimeMillis()
            )
            firestore.collection("news").document(news.id.toString()).set(data)
        } catch (e: Exception) {
            Log.w(TAG, "Firestore syncNews exception: ${e.message}")
        }
    }

    fun deleteNews(newsId: Int) {
        val firestore = firestoreInstance ?: return
        try {
            firestore.collection("news").document(newsId.toString()).delete()
        } catch (e: Exception) {
            Log.w(TAG, "Firestore deleteNews exception: ${e.message}")
        }
    }

    fun syncOpportunity(opp: OpportunityItem) {
        val firestore = firestoreInstance ?: return
        try {
            val data = hashMapOf(
                "id" to opp.id,
                "title" to opp.title,
                "type" to opp.type,
                "category" to opp.category,
                "location" to opp.location,
                "deadline" to opp.deadline,
                "description" to opp.description,
                "requirements" to opp.requirements,
                "benefits" to opp.benefits,
                "placesAvailable" to opp.placesAvailable,
                "isPublished" to opp.isPublished,
                "updatedAt" to System.currentTimeMillis()
            )
            firestore.collection("opportunities").document(opp.id.toString()).set(data)
        } catch (e: Exception) {
            Log.w(TAG, "Firestore syncOpportunity exception: ${e.message}")
        }
    }

    fun deleteOpportunity(oppId: Int) {
        val firestore = firestoreInstance ?: return
        try {
            firestore.collection("opportunities").document(oppId.toString()).delete()
        } catch (e: Exception) {
            Log.w(TAG, "Firestore deleteOpportunity exception: ${e.message}")
        }
    }

    fun submitContactToCloud(msg: ContactMessage) {
        val firestore = firestoreInstance ?: return
        try {
            val data = hashMapOf(
                "id" to msg.id,
                "fullName" to msg.fullName,
                "email" to msg.email,
                "phone" to msg.phone,
                "subject" to msg.subject,
                "message" to msg.message,
                "timestamp" to msg.timestamp
            )
            firestore.collection("contacts").add(data)
        } catch (e: Exception) {
            Log.w(TAG, "Firestore submitContact exception: ${e.message}")
        }
    }

    fun submitVolunteerToCloud(app: VolunteerApplication) {
        val firestore = firestoreInstance ?: return
        try {
            val data = hashMapOf(
                "id" to app.id,
                "fullName" to app.fullName,
                "email" to app.email,
                "phone" to app.phone,
                "city" to app.city,
                "opportunityTitle" to app.opportunityTitle,
                "domainOfInterest" to app.domainOfInterest,
                "motivation" to app.motivation,
                "timestamp" to app.timestamp
            )
            firestore.collection("volunteer_applications").add(data)
        } catch (e: Exception) {
            Log.w(TAG, "Firestore submitVolunteer exception: ${e.message}")
        }
    }

    fun cleanup() {
        listeners.forEach { it.remove() }
        listeners.clear()
    }
}
