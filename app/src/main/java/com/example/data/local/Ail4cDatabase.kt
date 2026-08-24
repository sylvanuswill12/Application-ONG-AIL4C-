package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.R
import com.example.data.model.ActionItem
import com.example.data.model.ContactMessage
import com.example.data.model.NewsArticle
import com.example.data.model.OpportunityItem
import com.example.data.model.ProjectItem
import com.example.data.model.VolunteerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ActionItem::class,
        ProjectItem::class,
        NewsArticle::class,
        OpportunityItem::class,
        ContactMessage::class,
        VolunteerApplication::class
    ],
    version = 2,
    exportSchema = false
)
abstract class Ail4cDatabase : RoomDatabase() {
    abstract fun ail4cDao(): Ail4cDao

    companion object {
        @Volatile
        private var INSTANCE: Ail4cDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): Ail4cDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Ail4cDatabase::class.java,
                    "ong_ail4c_database"
                )
                .fallbackToDestructiveMigration()
                .addCallback(Ail4cDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class Ail4cDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateInitialData(database.ail4cDao())
                }
            }
        }

        suspend fun populateInitialData(dao: Ail4cDao) {
            // Pre-seed Actions enrichies depuis les publications Facebook et Instagram
            dao.insertActions(
                listOf(
                    ActionItem(
                        id = 1,
                        title = "Semaine de la Population à Bouaké : Santé, Climat & Lutte contre les VBG",
                        shortDescription = "Mobilisation historique à Bouaké en collaboration avec l'UNFPA et l'Université Alassane Ouattara (UAO).",
                        fullStory = "Sous le leadership de son Président-Fondateur Ezékiel Aka et avec l'appui du Fonds des Nations unies pour la population (UNFPA), l'ONG-AIL4C a organisé la Semaine de la Population à Bouaké. Cet événement majeur a réuni des centaines d'étudiants, de leaders communautaires et de jeunes pour des ateliers sur la santé de la reproduction, la lutte contre les violences basées sur le genre (VBG) et la préservation de l'environnement face au réchauffement climatique.",
                        category = "Sensibilisation communautaire",
                        date = "Juillet 2024 & Février 2026",
                        location = "Bouaké (Campus UAO & Quartiers)",
                        imageRes = R.drawable.img_hero_community,
                        beneficiariesCount = "500+ participants"
                    ),
                    ActionItem(
                        id = 2,
                        title = "Formations Certifiantes Gratuites aux Métiers Verts à l'UAO",
                        shortDescription = "Sessions intensives gratuites dispensées aux étudiants et jeunes pour accélérer la transition écologique.",
                        fullStory = "L'ONG-AIL4C a déployé à l'Université Alassane Ouattara de Bouaké et à Abidjan des cycles de formations certifiantes entièrement gratuites. Les modules portent sur les fondamentaux du changement climatique, la gestion et valorisation des déchets solides, l'énergie solaire et le montage de projets éco-responsables bancables.",
                        category = "Formations certifiantes",
                        date = "15 Janvier 2026",
                        location = "Bouaké & Abidjan",
                        imageRes = R.drawable.img_formation_vert,
                        beneficiariesCount = "180 jeunes certifiés"
                    ),
                    ActionItem(
                        id = 3,
                        title = "Caravane Nationale de Salubrité et Curage Écologique",
                        shortDescription = "Journées citoyennes de propreté urbaine et collecte sélective des déchets plastiques.",
                        fullStory = "Mobilisation des brigades bénévoles d'AIL4C dans les grandes artères et zones vulnérables aux inondations. Plus de 5 tonnes de déchets plastiques ont été collectées, triées et réacheminées vers des unités de recyclage partenaires pour encourager l'économie circulaire.",
                        category = "Salubrité publique",
                        date = "08 Février 2026",
                        location = "Abidjan & Bouaké",
                        imageRes = R.drawable.img_salubrite_ville,
                        beneficiariesCount = "150+ bénévoles mobilisés"
                    ),
                    ActionItem(
                        id = 4,
                        title = "Campagne Communautaire de Reboisement & Ceintures Vertes",
                        shortDescription = "Plantation de 3 000 plants d'arbres pour restaurer les écosystèmes et lutter contre l'érosion des sols.",
                        fullStory = "En partenariat avec les chefferies traditionnelles et les éco-clubs scolaires, les équipes d'AIL4C ont procédé à la mise en terre d'essences locales et d'arbres d'ombrage pour reboiser les zones dégradées et créer des îlots de fraîcheur contre la hausse des températures.",
                        category = "Protection environnement",
                        date = "28 Janvier 2026",
                        location = "Grand-Bassam & Région du Gbêkê",
                        imageRes = R.drawable.img_reboisement,
                        beneficiariesCount = "3 000 arbres plantés"
                    ),
                    ActionItem(
                        id = 5,
                        title = "Forum 2026 : Jeunesse Africaine en Action pour le Climat",
                        shortDescription = "Grande tribune d'échanges et de plaidoyer pour l'inclusion des jeunes dans les politiques climatiques.",
                        fullStory = "Ce forum a rassemblé des délégations de jeunes militants, des universitaires et des représentants institutionnels pour élaborer la feuille de route de l'engagement jeunesse pour l'adaptation climatique en Côte d'Ivoire.",
                        category = "Insertion des jeunes",
                        date = "05 Février 2026",
                        location = "Abidjan (Centre de Conférence)",
                        imageRes = R.drawable.img_hero_community,
                        beneficiariesCount = "350 délégués jeunesse"
                    )
                )
            )

            // Pre-seed Projects
            dao.insertProjects(
                listOf(
                    ProjectItem(
                        id = 1,
                        title = "Programme « 1 Jeune, 1 Emploi Vert »",
                        domain = "Emploi vert & Insertion",
                        shortDescription = "Programme global de formation technique et d'insertion professionnelle de 1 200 jeunes ivoiriens dans les filières de la transition écologique.",
                        fullDescription = "Ce projet phare d'AIL4C vise à transformer le défi du chômage des jeunes en levier pour la transition écologique. À travers des modules certifiants en énergie solaire, agroécologie résiliente, gestion des déchets et éco-construction, les apprenants bénéficient d'un accompagnement vers l'emploi direct ou la création d'entreprises vertes.",
                        status = "En cours",
                        dateRange = "2025 - 2027",
                        location = "Abidjan, Bouaké (UAO), San-Pédro",
                        progressPercent = 65,
                        imageRes = R.drawable.img_formation_vert,
                        targetBeneficiaries = "1 200 jeunes diplômés & décrocheurs",
                        partnersMention = "Collectivités, centres de formation & acteurs privés"
                    ),
                    ProjectItem(
                        id = 2,
                        title = "Initiative « Villes Vertes & Salubrité Participative »",
                        domain = "Salubrité & Climat",
                        shortDescription = "Création de brigades citoyennes de propreté et mise en place de circuits courts de recyclage plastique.",
                        fullDescription = "Déploiement d'un réseau communautaire pour l'assainissement régulier des quartiers, l'élimination des dépotoirs sauvages et le recyclage des plastiques avec valorisation socio-économique pour les jeunes collecteurs.",
                        status = "En cours",
                        dateRange = "2025 - 2026",
                        location = "Bouaké, Abidjan et communes partenaires",
                        progressPercent = 55,
                        imageRes = R.drawable.img_salubrite_ville,
                        targetBeneficiaries = "15 000+ résidents urbains",
                        partnersMention = "Municipalités et comités de riverains"
                    ),
                    ProjectItem(
                        id = 3,
                        title = "Restauration des Écosystèmes & Ceintures Côtières",
                        domain = "Protection environnementale",
                        shortDescription = "Reforestation intensive des zones dégradées et mangroves pour contrer la montée des eaux et l'érosion des sols.",
                        fullDescription = "Programme de réhabilitation écologique combinant pépinières communautaires gérées par des jeunes, replantation d'arbres stabilisateurs et sensibilisation active des populations riveraines.",
                        status = "En cours",
                        dateRange = "2024 - 2026",
                        location = "Grand-Bassam & Région du Gbêkê",
                        progressPercent = 75,
                        imageRes = R.drawable.img_reboisement,
                        targetBeneficiaries = "50 000 habitants protégés",
                        partnersMention = "Chefferies traditionnelles et coopératives"
                    ),
                    ProjectItem(
                        id = 4,
                        title = "Incubateur « Éco-Pépinière AIL4C »",
                        domain = "Entrepreneuriat climatique",
                        shortDescription = "Hub d'accélération pour 50 startups vertes et solutions locales d'adaptation au changement climatique.",
                        fullDescription = "Dispositif d'accompagnement complet comprenant hébergement, coaching technique, mentorat d'affaires et mise en relation avec des fonds d'impact pour les jeunes innovateurs environnementaux.",
                        status = "À venir",
                        dateRange = "2026 - 2028",
                        location = "Abidjan & Plateformes régionales",
                        progressPercent = 20,
                        imageRes = R.drawable.img_hero_community,
                        targetBeneficiaries = "50 startups & 250 emplois créés",
                        partnersMention = "Partenaires techniques et financiers"
                    )
                )
            )

            // Pre-seed News avec liens directs vers Instagram (@ongail4c) et Facebook
            dao.insertNewsList(
                listOf(
                    NewsArticle(
                        id = 1,
                        title = "Bilan vibrant de la Semaine de la Population à Bouaké avec l'UNFPA",
                        category = "Jeunesse & Emploi",
                        date = "18 Février 2026",
                        summary = "L'ONG-AIL4C et le Fonds des Nations unies pour la population (UNFPA) dressent un bilan remarquable sur la santé de la reproduction et l'éco-citoyenneté.",
                        fullContent = "Dans la dynamique de son engagement sur le terrain, l'ONG-AIL4C a réuni à Bouaké et sur le campus de l'Université Alassane Ouattara des centaines de jeunes lors de la « Semaine de la Population ». Soutenu par l'UNFPA, cet événement a permis de mener des causeries éducatives sur la santé sexuelle et reproductive, la lutte contre les violences basées sur le genre (VBG) et la résilience face au réchauffement climatique.\n\n« Nous croyons fermement qu'une jeunesse épanouie et informée est la clé pour relever simultanément les défis sanitaires, sociaux et climatiques de la Côte d'Ivoire », a affirmé Ezékiel Aka, Président-Fondateur de l'ONG-AIL4C.",
                        author = "Rédaction AIL4C / Instagram @ongail4c",
                        readTime = "4 min de lecture",
                        imageRes = R.drawable.img_hero_community,
                        isFeatured = true,
                        socialPlatform = "Instagram",
                        socialUrl = "https://www.instagram.com/ongail4c?igsi=MW8wMG45anFpM2M1Mw==",
                        hashtags = "#AIL4C #UNFPA #Bouaké #SantéJeunesse #ClimatCI #ÉcoCitoyen"
                    ),
                    NewsArticle(
                        id = 2,
                        title = "Formation Certifiante Gratuite à l'Université Alassane Ouattara (UAO)",
                        category = "Jeunesse & Emploi",
                        date = "10 Février 2026",
                        summary = "Nouvelle cohorte d'étudiants formés aux métiers de la transition écologique, de l'énergie solaire et de la valorisation des déchets.",
                        fullContent = "Dans le cadre de son programme d'autonomisation des jeunes, l'ONG-AIL4C a dispensé une session de formation certifiante gratuite à l'UAO de Bouaké. Les participants ont bénéficié de cours pratiques sur les énergies renouvelables, l'agroécologie et le montage de micro-projets durables.\n\nChaque participant a reçu une attestation officielle attestant de ses compétences en transition écologique.",
                        author = "Pôle Formation AIL4C / Facebook ONG AIL4C",
                        readTime = "3 min de lecture",
                        imageRes = R.drawable.img_formation_vert,
                        isFeatured = false,
                        socialPlatform = "Facebook",
                        socialUrl = "https://www.facebook.com/share/1Gg8rzSWhm/",
                        hashtags = "#FormationGratuite #UAO #Bouaké #EmploiVert #AIL4C"
                    ),
                    NewsArticle(
                        id = 3,
                        title = "Mot du Président Ezékiel Aka : « La jeunesse au cœur de l'action climatique »",
                        category = "Institutionnel",
                        date = "02 Février 2026",
                        summary = "Le Président-Fondateur réaffirme la vision d'AIL4C pour transformer l'éco-anxiété en force d'action et d'entrepreneuriat.",
                        fullContent = "« Face aux effets tangibles du changement climatique en Côte d'Ivoire — hausse des températures, inondations urbaines, déforestation —, notre rôle est de donner aux jeunes les outils intellectuels, techniques et financiers pour bâtir l'avenir. L'ONG-AIL4C continuera d'arpenter les campus et les communautés pour susciter des vocations écologiques et citoyennes. »",
                        author = "Cabinet du Président Ezékiel Aka",
                        readTime = "3 min de lecture",
                        imageRes = R.drawable.img_hero_community,
                        isFeatured = false,
                        socialPlatform = "Instagram",
                        socialUrl = "https://www.instagram.com/ongail4c?igsi=MW8wMG45anFpM2M1Mw==",
                        hashtags = "#EzékielAka #VisionAIL4C #ChangementClimatique #CôtedIvoire"
                    ),
                    NewsArticle(
                        id = 4,
                        title = "Opération « Villes Propres & Caniveaux Sains » à Abidjan et Bouaké",
                        category = "Salubrité publique",
                        date = "25 Janvier 2026",
                        summary = "Plus de 5 tonnes de déchets plastiques collectées lors des grandes journées de salubrité publique.",
                        fullContent = "Les brigades de volontaires d'AIL4C ont bravé la chaleur pour curer les caniveaux et débarrasser les espaces publics des dépotoirs anarchiques. Cette opération combinée à une sensibilisation de porte-à-porte a permis de conscientiser des centaines de foyers sur l'importance du tri des déchets plastiques.",
                        author = "Pôle Salubrité & Cadre de Vie",
                        readTime = "3 min de lecture",
                        imageRes = R.drawable.img_salubrite_ville,
                        isFeatured = false,
                        socialPlatform = "Facebook",
                        socialUrl = "https://www.facebook.com/share/1Gg8rzSWhm/",
                        hashtags = "#SalubritéPublique #VillesDurables #AIL4C #TriPlastique"
                    ),
                    NewsArticle(
                        id = 5,
                        title = "Restauration des écosystèmes : 3 000 plants mis en terre",
                        category = "Climat & Forêts",
                        date = "15 Janvier 2026",
                        summary = "Succès de la campagne de reboisement communautaire le long du littoral et dans le Gbêkê.",
                        fullContent = "Les équipes d'AIL4C, accompagnées par les chefferies et les éco-délégués scolaires, ont planté 3 000 arbres d'essences locales pour lutter contre la déforestation et préserver la biodiversité ivoirienne.",
                        author = "Département Biodiversité & Climat",
                        readTime = "2 min de lecture",
                        imageRes = R.drawable.img_reboisement,
                        isFeatured = false,
                        socialPlatform = "Instagram",
                        socialUrl = "https://www.instagram.com/ongail4c?igsi=MW8wMG45anFpM2M1Mw==",
                        hashtags = "#Reboisement #Biodiversité #ForêtIvoirienne #AIL4C"
                    )
                )
            )

            // Pre-seed Opportunities
            dao.insertOpportunities(
                listOf(
                    OpportunityItem(
                        id = 1,
                        title = "Formation Certifiante Gratuite en Énergie Solaire & Métiers Verts",
                        type = "Formation",
                        category = "Énergies Renouvelables",
                        location = "Bouaké (UAO) & Abidjan",
                        deadline = "15 Mars 2026",
                        description = "Programme pratique certifiant de 6 semaines intensives pour acquérir les compétences d'installation photovoltaïque et de maintenance.",
                        requirements = "Niveau BAC ou équivalent, motivation pour les métiers de la transition écologique, assiduité.",
                        benefits = "Certificat de compétence ONG-AIL4C, dotation kit outils de base, insertion dans le réseau d'entreprises partenaires.",
                        placesAvailable = "40 places"
                    ),
                    OpportunityItem(
                        id = 2,
                        title = "Recrutement des Ambassadeurs Climat & Éco-Citoyens 2026",
                        type = "Bénévolat",
                        category = "Engagement citoyen",
                        location = "Toutes les régions (Bouaké, Abidjan, Yamoussoukro, Korhogo, etc.)",
                        deadline = "Candidatures ouvertes en continu",
                        description = "Rejoignez le réseau national des volontaires AIL4C pour animer les actions de terrain, les caravanes de salubrité et les campagnes écologiques.",
                        requirements = "Avoir 16 ans ou plus, esprit d'équipe, passion pour la préservation de l'environnement et l'engagement associatif.",
                        benefits = "Attestation d'engagement bénévole, formation gratuite aux enjeux climatiques, gilet et badge officiel AIL4C.",
                        placesAvailable = "Ouvert à tous"
                    ),
                    OpportunityItem(
                        id = 3,
                        title = "Bourse d'Incubation pour Éco-Entrepreneurs Jeunes",
                        type = "Appel à candidatures",
                        category = "Entrepreneuriat vert",
                        location = "National (Côte d'Ivoire)",
                        deadline = "30 Avril 2026",
                        description = "Accompagnement technique et dotation financière d'amorçage pour les projets innovants dans la valorisation des déchets, l'agroécologie ou les énergies renouvelables.",
                        requirements = "Jeunes de 18 à 35 ans, porteurs d'un projet vert en phase pilote ou démarrage en Côte d'Ivoire.",
                        benefits = "6 mois d'incubation sur-mesure, mentorat d'experts de l'écosystème, mise en relation avec des bailleurs et investisseurs d'impact.",
                        placesAvailable = "15 projets lauréats"
                    ),
                    OpportunityItem(
                        id = 4,
                        title = "Stage d'Immersion : Assistant de Projets Environnement & VBG",
                        type = "Stage",
                        category = "Gestion de projet",
                        location = "Bouaké / Abidjan",
                        deadline = "25 Mars 2026",
                        description = "Stage formateur au sein de la coordination des programmes pour appuyer l'organisation des ateliers de sensibilisation et le suivi d'impact communautaire.",
                        requirements = "Étudiant ou jeune diplômé en sciences environnementales, sociologie, gestion de projet ou communication.",
                        benefits = "Indemnité de stage, formation continue, expérience de terrain concrète valorisable.",
                        placesAvailable = "3 postes"
                    )
                )
            )
        }
    }
}
