package com.adl.appizin.utility

import android.location.Location

interface MyLocationListener {
    fun onLocationChanged(location: Location)
}