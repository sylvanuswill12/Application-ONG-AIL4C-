package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val id: String = "user_default",
    val fullName: String,
    val email: String,
    val phone: String,
    val city: String = "Abidjan, Côte d'Ivoire",
    val roleInterest: String = "Bénévole Climat",
    val isMember: Boolean = true,
    val profilePhotoUrl: String? = null,
    val registeredAt: Long = System.currentTimeMillis()
)
