package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.network.AsteroidResponse
import com.udacity.asteroidradar.util.Constants
import com.udacity.asteroidradar.network.PictureOfDay
import com.udacity.asteroidradar.util.Resource
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

enum class AsteroidApiStatus { LOADING, ERROR, DONE }
interface AsteroidApi {
    @GET("neo/rest/v1/feed")
   suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): String

    @GET("planetary/apod")
   suspend fun getPictureOfDay(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): PictureOfDay

}