package com.adl.appizin.background

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.adl.appizin.R
import com.adl.appizin.model.GetIzinResponse
import com.adl.appizin.model.ResponseTracking
import com.adl.appizin.services.RetrofitConfig
import com.adl.appizin.utility.LocationHelper
import com.adl.appizin.utility.MyLocationListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.util.*


class LocationService : Service() {

    companion object{
        var mLocation: Location?=null
        var isServiceStarted = false
    }

    private val NOTIFICATION_CHANNEL_ID = "notifikasi channel"
    private val TAG = "LOCATION_SERVICE"

    override fun onCreate() {
        super.onCreate()


        isServiceStarted = true
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
            .setOngoing(false)
            .setSmallIcon(R.drawable.ic_launcher_background)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,NOTIFICATION_CHANNEL_ID,NotificationManager.IMPORTANCE_LOW)

            notificationChannel.description = NOTIFICATION_CHANNEL_ID
            notificationChannel.setSound(null,null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1,builder.build())

        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LocationHelper().startListeningUserLocation(this,object : MyLocationListener {
            override fun onLocationChanged(location: Location) {
                if(isServiceStarted){
                mLocation = location
                mLocation?.let{
                    Log.d(TAG,"SERVICE SEDANG BERJALAN LOKASINYA ADALAH ${it?.longitude} -  ${it?.latitude} ")
                    var tanggal=Calendar.getInstance()
                    val formatDate = "yyyy-MM-dd HH:mm:ss"
                    val sdf = SimpleDateFormat(formatDate, Locale.US)

                    RetrofitConfig().getIzin().addDataTracking("robby", it.latitude.toString(),
                        it.longitude.toString(),sdf.format(tanggal.time)).enqueue(object: Callback<ResponseTracking> {
                        override fun onResponse(call: Call<ResponseTracking>, response: Response<ResponseTracking>) {
                            Log.d("Response",response.body().toString())
                            //val data: ResponseTracking? = response.body()
                            //dataGenerate(data?.data?.adlNews as List<AdlNewsItem>)

                        }

                        override fun onFailure(call: Call<ResponseTracking>, t: Throwable) {
                            //Toast.makeText(,t.localizedMessage, Toast.LENGTH_LONG).show()
                            Log.d("Response",t.localizedMessage.toString())
                        }


                    })
                }}
            }


        })


        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted=false
    }
}