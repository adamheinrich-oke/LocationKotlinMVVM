package com.example.locationkotlinmvvm.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationkotlinmvvm.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class SPVM(private val repository: Repository) : ViewModel() {

    private val distanceLiveData: MutableLiveData<Response<GetDistance>> = MutableLiveData()
    private val coordinatesLiveData: MutableLiveData<Response<Coordinates>> = MutableLiveData()

    fun sendCoordinates(data: Coordinates) {
        viewModelScope.launch {
            val response: Response<Coordinates> = repository.pushCoordinates(data)
            coordinatesLiveData.value = response
        }
    }

    fun getDistance() {
        viewModelScope.launch {
            val response: Response<GetDistance> = repository.getDistance()
            distanceLiveData.value = response
        }
    }
}