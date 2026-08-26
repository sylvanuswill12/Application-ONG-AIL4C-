package com.example.data.api

import android.util.Log
import com.example.BuildConfig
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class GeminiPart(
    val text: String? = null
)

@JsonClass(generateAdapter = true)
data class GeminiContent(
    val role: String? = null,
    val parts: List<GeminiPart>
)

@JsonClass(generateAdapter = true)
data class GeminiGenerationConfig(
    val temperature: Float? = 0.7f,
    val topP: Float? = 0.95f,
    val topK: Int? = 40,
    val maxOutputTokens: Int? = 1000
)

@JsonClass(generateAdapter = true)
data class GeminiGenerateRequest(
    val contents: List<GeminiContent>,
    val systemInstruction: GeminiContent? = null,
    val generationConfig: GeminiGenerationConfig? = null
)

@JsonClass(generateAdapter = true)
data class GeminiCandidate(
    val content: GeminiContent?
)

@JsonClass(generateAdapter = true)
data class GeminiGenerateResponse(
    val candidates: List<GeminiCandidate>?
)

interface GeminiRestApi {
    @POST("v1beta/models/gemini-2.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiGenerateRequest
    ): GeminiGenerateResponse
}

object GeminiApiClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        }
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    val service: GeminiRestApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        retrofit.create(GeminiRestApi::class.java)
    }
}

class GeminiAiService {

    companion object {
        const val SYSTEM_PROMPT = """
Tu es l'Assistant Écologique & Citoyen Officiel de l'ONG-AIL4C (Association Ivoirienne de Lutte contre le Changement Climatique et le Chômage).

Informations officielles sur l'organisation :
- **Création & Fondation** : Créée le 23 septembre 2023 à Bouaké (district de la Vallée du Bandama, Côte d'Ivoire) par trois jeunes diplômés en sociologie (Sénin, Aka et Remy), à la suite de leur mémoire de master sur le changement climatique et l'impact socio-environnemental.
- **Siège social** : Situé à Broukro, un quartier de Bouaké.
- **Gouvernance** :
  - Président actuel en exercice : SENIN TCHOUMOU ESDRAS GEMIEL (Sociologue et cofondateur).
  - Président-fondateur : Ezéchiel Aka Koffi (Consultant humanitaire et ambassadeur YOMA).
  - Vice-président & Direction de la coordination des activités de terrain.
- **Les 4 Missions Fondamentales** :
  1. Lutter contre le changement climatique à travers la sensibilisation continue des populations et la prise de conscience collective.
  2. Combattre le chômage des jeunes en créant des opportunités d'insertion socio-économique et des formations certifiantes gratuites aux métiers verts.
  3. Sensibiliser sur les Violences Basées sur le Genre (VBG), la santé sexuelle et reproductive ainsi que la planification familiale.
  4. Promouvoir la préservation de l'environnement (campagnes de salubrité publique, curage des caniveaux, reboisement et éducation environnementale).
- **Activités marquantes** :
  - Semaine de la population (20–27 juillet 2024) : Sensibilisation dans les quartiers de Bouaké sur les VBG, la santé reproductive et l'environnement, avec l'appui de l'UNFPA.
  - Journée de sensibilisation à l'Université Alassane Ouattara (27 juillet 2024) au Campus 2 de l'UAO de Bouaké.
  - Actions régulières de salubrité, reboisement et visites de ménages.
- **Partenaires officiels** : UNFPA (Fonds des Nations Unies pour la Population), AIESEC, BLUE, ONG LA MAIN SUR LE COEUR, Université Alassane Ouattara (UAO).

Tes missions en tant qu'assistant :
1. Accueillir chaleureusement les utilisateurs, bénévoles, étudiants, donateurs et partenaires avec bienveillance et enthousiasme.
2. Répondre avec exactitude aux questions sur l'historique, la gouvernance, les missions, les projets et les actions de l'AIL4C.
3. Exprimer le respect et citer fidèlement la vision du Président SENIN TCHOUMOU ESDRAS GEMIEL et des fondateurs.
4. Répondre en français dans un style clair, structuré, encourageant, avec des emojis écologiques pertinents (🌿, 🌳, 🇨🇮, 💡, 🤝, 🏛️).
"""
    }

    suspend fun sendMessage(
        userMessage: String,
        conversationHistory: List<Pair<String, Boolean>> = emptyList(), // Pair(text, isUser)
        userName: String = "Membre AIL4C"
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        // Try remote Gemini API if key is available and valid
        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val contentsList = mutableListOf<GeminiContent>()

                // Add prior conversation history (limited to last 6 messages to keep context concise)
                conversationHistory.takeLast(6).forEach { (msg, isUser) ->
                    contentsList.add(
                        GeminiContent(
                            role = if (isUser) "user" else "model",
                            parts = listOf(GeminiPart(text = msg))
                        )
                    )
                }

                // Add current user prompt
                contentsList.add(
                    GeminiContent(
                        role = "user",
                        parts = listOf(GeminiPart(text = "L'utilisateur s'appelle $userName. Message de l'utilisateur : $userMessage"))
                    )
                )

                val request = GeminiGenerateRequest(
                    contents = contentsList,
                    systemInstruction = GeminiContent(
                        parts = listOf(GeminiPart(text = SYSTEM_PROMPT))
                    ),
                    generationConfig = GeminiGenerationConfig(
                        temperature = 0.7f,
                        maxOutputTokens = 800
                    )
                )

                val response = GeminiApiClient.service.generateContent(apiKey, request)
                val responseText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                if (!responseText.isNullOrBlank()) {
                    return@withContext responseText.trim()
                }
            } catch (e: Exception) {
                Log.w("GeminiAiService", "Gemini API remote error, switching to smart local response", e)
            }
        }

        // Smart Knowledge Base Fallback Engine (100% reliable offline/prototype)
        return@withContext generateSmartLocalResponse(userMessage, userName)
    }

    private fun generateSmartLocalResponse(query: String, userName: String): String {
        val lower = query.lowercase().trim()

        return when {
            lower.contains("bonjour") || lower.contains("salut") || lower.contains("bienvenue") || lower.contains("hello") -> {
                "Bonjour $userName ! 🌿 Bienvenue au sein de l'ONG-AIL4C.\n\nLe Président SENIN TCHOUMOU ESDRAS GEMIEL et l'ensemble de notre équipe vous saluent chaleureusement. Je suis votre assistant écologique intelligent. Comment puis-je vous accompagner aujourd'hui dans votre engagement pour le climat et l'avenir de la jeunesse ivoirienne ? 🇨🇮✨"
            }
            lower.contains("histoire") || lower.contains("création") || lower.contains("creation") || lower.contains("origine") || lower.contains("broukro") || lower.contains("bouaké") || lower.contains("bouake") -> {
                "🏛️ **Histoire & Genèse de l'AIL4C** :\n\nL'ONG **AIL4C** (Association Ivoirienne de Lutte contre le Changement Climatique et le Chômage) est une organisation de la société civile basée à **Bouaké** (district de la Vallée du Bandama, Côte d'Ivoire), avec son siège dans le quartier de **Broukro**.\n\nElle a été créée le **23 septembre 2023** par trois jeunes diplômés en sociologie (**Sénin, Aka et Remy**), à la suite de leur mémoire de master sur le changement climatique. L'AIL4C s'engage à susciter une véritable prise de conscience collective face au réchauffement climatique et au chômage des jeunes. 🇨🇮🌱"
            }
            lower.contains("président") || lower.contains("president") || lower.contains("senin") || lower.contains("esdras") || lower.contains("tchoumou") || lower.contains("dirigeant") || lower.contains("fondateur") || lower.contains("aka") || lower.contains("ezéchiel") || lower.contains("ezechiel") -> {
                "👨‍💼 **Gouvernance de l'ONG-AIL4C** :\n\n• **Président en exercice** : **SENIN TCHOUMOU ESDRAS GEMIEL** (Sociologue et cofondateur).\n• **Président-fondateur** : **Ezéchiel Aka Koffi** (Consultant humanitaire et ambassadeur YOMA).\n• **Cofondateurs** : Sénin, Aka et Remy (sociologues diplômés de Bouaké).\n\nL'équipe dirigeante œuvre au quotidien avec les universités et partenaires pour déployer des actions concrètes de terrain. 🤝✨"
            }
            lower.contains("mission") || lower.contains("objectif") || lower.contains("pilier") || lower.contains("but") -> {
                "🎯 **Les 4 Missions Fondamentales de l'AIL4C** :\n\n1. 🌿 **Lutter contre le changement climatique** : Sensibilisation continue et éveil des consciences.\n2. 💼 **Combattre le chômage des jeunes** : Formations gratuites certifiantes aux métiers verts et insertion socio-économique.\n3. 🕊️ **Sensibilisation VBG & Santé Reproductive** : Lutte contre les violences basées sur le genre et planification familiale.\n4. 🌳 **Préservation de l'environnement** : Salubrité publique, curage des caniveaux et plantations d'arbres."
            }
            lower.contains("partenaire") || lower.contains("unfpa") || lower.contains("aiesec") || lower.contains("blue") || lower.contains("main sur le coeur") || lower.contains("semaine de la population") -> {
                "🤝 **Nos Partenaires & Actions Phares** :\n\nL'AIL4C collabore avec des organisations de premier plan :\n• **UNFPA** (Fonds des Nations Unies pour la Population) : Appui lors de la **Semaine de la Population** à Bouaké (juillet 2024).\n• **AIESEC** : Leadership des jeunes et engagement bénévole.\n• **BLUE** & **ONG LA MAIN SUR LE COEUR** : Projets durables et solidarité.\n• **Université Alassane Ouattara (UAO)** : Formations et sensibilisations sur le campus 2 de Bouaké."
            }
            lower.contains("bénévole") || lower.contains("benevole") || lower.contains("volontaire") || lower.contains("rejoindre") || lower.contains("adhérer") || lower.contains("adherer") || lower.contains("inscription") -> {
                "✋ **Devenir Bénévole AIL4C** :\n\nVous êtes au bon endroit ! Pour vous engager :\n1. Ouvrez le menu latéral ou l'onglet « Nos Actions ».\n2. Choisissez une activité terrain ou une formation.\n3. Cliquez sur « Rejoindre cette action » ou remplissez le formulaire de volontariat.\n\nVotre motivation sera directement transmise à notre bureau de coordination !"
            }
            lower.contains("don") || lower.contains("financer") || lower.contains("partenaire") || lower.contains("aide") || lower.contains("contact") -> {
                "💳 **Soutenir l'AIL4C** :\n\nVos contributions permettent de fournir du matériel de reboisement, d'outiller les brigades de salubrité et d'offrir des formations gratuites aux jeunes.\n\nContactez notre équipe via l'onglet « Contact » ou écrivez-nous sur contact@ong-ail4c.ci / WhatsApp au +225 07 00 00 00 00."
            }
            else -> {
                "🌿 **Éco-Conseil AIL4C** :\n\nMerci pour votre question $userName ! L'ONG-AIL4C, présidée par **SENIN TCHOUMOU ESDRAS GEMIEL**, œuvre chaque jour sur le terrain pour la justice climatique et l'autonomisation de la jeunesse en Côte d'Ivoire.\n\nVous pouvez explorer nos rubriques :\n• 🌳 **Nos Actions** : Participez aux chantiers écologiques de proximité.\n• 💡 **Nos Projets** : Découvrez nos initiatives à long terme.\n• 🎓 **Jeunesse & Emploi** : Accédez à nos formations certifiantes gratuites.\n• 📰 **Actualités & Social** : Suivez nos publications en direct.\n\nN'hésitez pas à me poser une autre question spécifique !"
            }
        }
    }
}
