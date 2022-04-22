package com.adl.appizin.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.adl.appizin.background.LocationService

class BootDeviceRecievers : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            ContextCompat.startForegroundService(it,Intent(it, LocationService::class.java))
        }
    }
}