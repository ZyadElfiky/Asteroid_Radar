package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.local.AsteroidDatabase
import com.udacity.asteroidradar.network.PictureOfDay
import com.udacity.asteroidradar.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject

class AsteroidRepo(private val db: AsteroidDatabase) {

    suspend fun refreshAsteroidsData(
        startDate: String = getToday(), endDate: String = getSeventhDay()
    ) {

        withContext(Dispatchers.IO) {
            val result= AsteroidApiService
                .retrofitService
                .getAsteroids(
                    startDate, endDate, Constants.API_KEY
                )
            val objectResult = parseAsteroidsJsonResult(JSONObject(result))
            db.asteroidDao.insertAllAsteroid(*objectResult.toTypedArray())
        }
    }
    suspend fun deletePreviousDayAsteroids() {
        withContext(Dispatchers.IO) {
            db.asteroidDao.deletePreviousDayAsteroids(getToday())
        }
    }
    suspend fun getPictureOfDay(): PictureOfDay? {
        var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = AsteroidApiService.retrofitService.getPictureOfDay()
        }
        if (pictureOfDay.mediaType == "image") {
            return pictureOfDay
        }
        return null
    }
}
