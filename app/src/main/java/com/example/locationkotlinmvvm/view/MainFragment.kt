package com.example.locationkotlinmvvm.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.locationkotlinmvvm.R
import com.example.locationkotlinmvvm.model.LocationViewModel
import com.example.locationkotlinmvvm.model.MainViewModelFactory
import com.example.locationkotlinmvvm.model.SPVM
import com.example.locationkotlinmvvm.repository.Repository

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var retrofitVM: SPVM
    private lateinit var locationVM: LocationViewModel
    private lateinit var getDistanceButton: Button
    private val repository = Repository()
    private val viewModelFactory = MainViewModelFactory(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retrofitVM =
            ViewModelProvider(this, viewModelFactory).get(SPVM::class.java)
        locationVM = ViewModelProvider(this).get(LocationViewModel::class.java)
        context?.let { locationVM.setLocationRepository(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDistanceButton = requireView().findViewById(R.id.button_coordinates)
        getDistanceButton.setOnClickListener { getDeviceLocation() }

        setUpObservers()

    }

    private fun setUpObservers() {
        val locationObserver = Observer<Location> { location ->
            location?.let {
                Toast.makeText(activity, "${it.latitude} oraz ${it.longitude}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        locationVM.location?.observe(this, locationObserver)
    }

    private fun getDeviceLocation() {
        if (isPermissionGranted()) {
            locationVM.enableLocationServices()
            locationVM.locationRepository?.let {
                if (!it.hasObservers()) {
                    it.observe(this, Observer<Location?> { location ->
                        location?.let { locationVM.location?.value = it }
                    })
                }
            }
        } else requestPermission()
    }

    private fun isPermissionGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            context as Activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            context as Activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        if (!isPermissionGranted()) {
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 1001
            )
        }
    }
}