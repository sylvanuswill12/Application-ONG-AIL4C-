package com.example

import android.app.Application
import com.example.data.local.Ail4cDatabase
import com.example.data.repository.Ail4cRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class Ail4cApp : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val database by lazy { Ail4cDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { Ail4cRepository(database.ail4cDao()) }
}
