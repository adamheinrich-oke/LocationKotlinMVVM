package com.example.locationkotlinmvvm.model

import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {

    var location: MutableLiveData<Location>? = MutableLiveData<Location>()
    var locationRepository: LocationListener? = null

    fun setLocationRepository(context: Context) {
        locationRepository = LocationListener.getInstance(context)
    }

    fun enableLocationServices() = locationRepository?.startService()

}