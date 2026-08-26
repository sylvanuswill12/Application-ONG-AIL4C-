package com.example

import android.app.Application
import android.util.Log
import com.example.data.local.Ail4cDatabase
import com.example.data.repository.Ail4cRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class Ail4cApp : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val database by lazy { Ail4cDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { Ail4cRepository(database.ail4cDao(), applicationScope) }

    override fun onCreate() {
        super.onCreate()
        // Safeguard against uncaught thread exceptions causing hard crash on physical devices
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("Ail4cApp", "Uncaught exception in thread ${thread.name}: ${throwable.message}", throwable)
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}
