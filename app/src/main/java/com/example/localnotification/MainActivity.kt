package com.example.localnotification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.localnotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID ="1"
    lateinit var mainBinding:ActivityMainBinding
    var counter=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        mainBinding.sendNotification.setOnClickListener {
            counter++
            mainBinding.sendNotification.text=counter.toString()
            if (counter%5==0){
                startNotification()
            }
        }
    }

    private fun startNotification() {
        val builder = NotificationCompat.Builder(this@MainActivity,CHANNEL_ID)
        //// If android version greater than oreo....
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            val channel =NotificationChannel(CHANNEL_ID,"1",NotificationManager.IMPORTANCE_DEFAULT)
            val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("This title")
                .setContentText("Your Message")
        }
        //If android version less than oreo....
        else
        {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("This title")
                .setContentText("Your Message").priority = NotificationManager.IMPORTANCE_DEFAULT
        }
        val notificationManagerCompat = NotificationManagerCompat.from(this@MainActivity)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManagerCompat.notify(1,builder.build())

        }
    }

