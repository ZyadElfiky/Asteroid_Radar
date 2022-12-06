package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.local.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepo
import retrofit2.HttpException

class RefreshWorker(appContext: Context,parameters: WorkerParameters):
CoroutineWorker(appContext,parameters){
    override suspend fun doWork(): Result {
        val database=AsteroidDatabase.getDatabaseInstance(applicationContext)
        val repository=AsteroidRepo(database)

        return try {
            repository.refreshAsteroidsData()
            Result.success()
        }catch (e:HttpException){
            Result.retry()
        }
    }

}