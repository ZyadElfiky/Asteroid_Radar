package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.util.Constants.DELETE_WORKER_NAME
import com.udacity.asteroidradar.util.Constants.REFRESH_WORKER_NAME
import com.udacity.asteroidradar.worker.DeleteWorker
import com.udacity.asteroidradar.worker.RefreshWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()

        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {

        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRefreshWorker = PeriodicWorkRequestBuilder<RefreshWorker>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constrains)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            REFRESH_WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRefreshWorker
        )
        val repeatingDeleteRequest = PeriodicWorkRequestBuilder<DeleteWorker>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constrains)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            DELETE_WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingDeleteRequest
        )
    }
}























