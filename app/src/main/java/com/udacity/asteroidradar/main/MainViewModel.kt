package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.AsteroidApiService
import com.udacity.asteroidradar.api.AsteroidApiStatus
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday

import com.udacity.asteroidradar.local.AsteroidDatabase.Companion.getDatabaseInstance
import com.udacity.asteroidradar.local.entities.Asteroid
import com.udacity.asteroidradar.network.AsteroidResponse
import com.udacity.asteroidradar.network.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class MainViewModel (application: Application): AndroidViewModel(application) {

    private val database = getDatabaseInstance(application)
    private val asteroidRepository = AsteroidRepo(database)

    private var _asteroids=MutableLiveData<List<Asteroid>>()
    val asteroids:LiveData<List<Asteroid>> get() = _asteroids

    private val _picture=MutableLiveData<PictureOfDay>()
    val picture:LiveData<PictureOfDay> get() = _picture

    private val _navigateToDetailFragment = MutableLiveData<Asteroid>()
    val navigateToDetailFragment: LiveData<Asteroid>
        get() = _navigateToDetailFragment

    private val _asteroidStatus=MutableLiveData<AsteroidApiStatus>()
    val asteroidApiStatus:LiveData<AsteroidApiStatus> get()=_asteroidStatus

    init{
        _asteroidStatus.value=AsteroidApiStatus.LOADING
        viewModelScope.launch {

            try {
                asteroidRepository.refreshAsteroidsData()
                displayWeekData()
                setPictureOfDay()

                _asteroidStatus.value=AsteroidApiStatus.DONE


            }catch (e : Exception){
                _asteroidStatus.value=AsteroidApiStatus.ERROR
            }

        }
    }



    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetailFragment.value = asteroid
    }

    fun doneNavigating() {
        _navigateToDetailFragment.value = null
    }

    private suspend fun setPictureOfDay(){
        _picture.value=asteroidRepository.getPictureOfDay()
    }


    fun displayWeekData() {
        viewModelScope.launch {

            database.asteroidDao.getAsteroidsWithSpecificData(
                getToday(),
                getSeventhDay()
            ) .collect {
                _asteroids.value=it
            }

        }

    }

    fun displayToData() {
        viewModelScope.launch {

            database.asteroidDao.getAsteroidsWithSpecificData(
                getToday(),
                getToday()
            ) .collect {
                _asteroids.value=it
            }

        }
    }
    fun displayAllData(){
        viewModelScope.launch {
            database.asteroidDao.getAllAsteroids().collect { _asteroids.value=it }
        }
    }
}