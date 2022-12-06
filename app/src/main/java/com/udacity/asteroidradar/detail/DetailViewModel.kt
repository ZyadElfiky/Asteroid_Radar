package com.udacity.asteroidradar.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel: ViewModel(){

    private val _showDialog=MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> get() = _showDialog

    fun onDialogClicked(){
        _showDialog.value=true
    }
    fun onDialogClickDone(){
        _showDialog.value=false
    }

}