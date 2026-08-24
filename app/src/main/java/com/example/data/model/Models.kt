package com.example.data.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.R

enum class DomainCategory(
    val title: String,
    val shortDesc: String,
    val iconName: String
) {
    CLIMATE_CHANGE(
        title = "Changement climatique",
        shortDesc = "Atténuation, adaptation et sensibilisation face aux aléas climatiques en Côte d'Ivoire.",
        iconName = "Thunderstorm"
    ),
    ENVIRONMENT_PROTECTION(
        title = "Protection de l'environnement",
        shortDesc = "Préservation des forêts, des cours d'eau, des mangroves et de la biodiversité.",
        iconName = "Park"
    ),
    YOUTH_SUSTAINABILITY(
        title = "Jeunesse et développement durable",
        shortDesc = "Mobilisation civique, éco-citoyenneté et implication des jeunes dans les ODD.",
        iconName = "Groups"
    ),
    GREEN_JOBS(
        title = "Emploi vert",
        shortDesc = "Création et promotion de métiers pérennes liés à la transition écologique et énergétique.",
        iconName = "Work"
    ),
    CLIMATE_ENTREPRENEURSHIP(
        title = "Entrepreneuriat climatique",
        shortDesc = "Accompagnement de startups vertes, agroécologie et valorisation locale des ressources.",
        iconName = "Lightbulb"
    ),
    CITIZEN_ENGAGEMENT(
        title = "Engagement citoyen",
        shortDesc = "Campagnes de proximité, plaidoyers et animation de réseaux de bénévoles actifs.",
        iconName = "VolunteerActivism"
    ),
    PUBLIC_SANITATION(
        title = "Salubrité publique",
        shortDesc = "Opérations de propreté urbaine, lutte contre les dépôts sauvages et recyclage communautaire.",
        iconName = "Recycling"
    ),
    SOCIO_PROFESSIONAL_INSERTION(
        title = "Insertion socio-professionnelle",
        shortDesc = "Formations certifiantes, mentorat et stages d'insertion pour les jeunes diplômés et en reconversion.",
        iconName = "School"
    )
}

@Entity(tableName = "actions")
data class ActionItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val shortDescription: String,
    val fullStory: String,
    val category: String, // Salubrité, Reforestation, Formation, Citoyenneté, Insertion
    val date: String,
    val location: String, // ex: "Abidjan (Cocody / Yopougon)", "Grand-Bassam", "Yamoussoukro"
    @DrawableRes val imageRes: Int = R.drawable.img_hero_community,
    val beneficiariesCount: String = "150+ participants",
    val isPublished: Boolean = true
)

@Entity(tableName = "projects")
data class ProjectItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val domain: String,
    val shortDescription: String,
    val fullDescription: String,
    val status: String, // "En cours", "Terminé", "À venir"
    val dateRange: String,
    val location: String,
    val progressPercent: Int = 50,
    @DrawableRes val imageRes: Int = R.drawable.img_reboisement,
    val targetBeneficiaries: String = "5 000 jeunes",
    val partnersMention: String = "Collectivités locales & Communautés",
    val isPublished: Boolean = true
)

@Entity(tableName = "news")
data class NewsArticle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val category: String, // Climat, Jeunesse, Salubrité, Partenariats, Institutionnel
    val date: String,
    val summary: String,
    val fullContent: String,
    val author: String = "Bureau Exécutif ONG-AIL4C",
    val readTime: String = "3 min de lecture",
    @DrawableRes val imageRes: Int = R.drawable.img_formation_vert,
    val isFeatured: Boolean = false,
    val isPublished: Boolean = true,
    val socialPlatform: String? = null, // "Instagram", "Facebook", "Communiqué"
    val socialUrl: String? = null,
    val hashtags: String? = null
)

data class SocialFeedPost(
    val id: String,
    val platform: String, // "Instagram" or "Facebook"
    val authorName: String = "ONG AIL4C",
    val authorHandle: String = "@ongail4c",
    val date: String,
    val content: String,
    val tags: List<String>,
    val url: String,
    @DrawableRes val imageRes: Int,
    val likesCount: String = "240+",
    val commentsCount: String = "45+"
)

@Entity(tableName = "opportunities")
data class OpportunityItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val type: String, // Formation, Appel à candidatures, Bénévolat, Stage, Programme Jeunes
    val category: String,
    val location: String, // ex: Abidjan & En ligne
    val deadline: String,
    val description: String,
    val requirements: String,
    val benefits: String,
    val placesAvailable: String = "Places limitées",
    val isPublished: Boolean = true
)

@Entity(tableName = "contact_messages")
data class ContactMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val email: String,
    val phone: String,
    val subject: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

@Entity(tableName = "volunteer_applications")
data class VolunteerApplication(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val email: String,
    val phone: String,
    val city: String,
    val opportunityTitle: String,
    val domainOfInterest: String,
    val motivation: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class GalleryItem(
    val id: Int,
    val title: String,
    val category: String,
    val location: String,
    val date: String,
    @DrawableRes val imageRes: Int,
    val description: String
)

data class ObjectivePillar(
    val number: String,
    val title: String,
    val description: String,
    val keyActions: List<String>,
    val iconName: String
)
