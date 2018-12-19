package com.example.android.clase15notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.Toast

class Servicio : Service() {
    var reproductor:MediaPlayer? = null
    var CHANNEL : String = "mi_canal_02"


    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        Toast.makeText(this, " servicio creado", Toast.LENGTH_SHORT).show()
        reproductor = MediaPlayer.create(this, R.raw.baron)
        reproductor?.isLooping = false
        super.onCreate()
    }

    //esto hace play del mp3 pero no crea notificacion
    //se comenta porque ahora haremos uno que si har'a play ademas de notificacion
/*    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, " servicio iniciado", Toast.LENGTH_SHORT).show()
        reproductor?.start()
        return START_NOT_STICKY
    }*/


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, " servicio iniciado", Toast.LENGTH_SHORT).show()

     if (reproductor?.isPlaying == false)
     {
        var nm = getSystemService(NotificationManager::class.java) as NotificationManager
        var channel = NotificationChannel(CHANNEL, "canal servicio", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = R.raw.baron.toString()
        nm.createNotificationChannel(channel)
     }

        val intento = Intent(this, MainActivity::class.java)
        val pendiente = PendingIntent.getActivity(this, 0, intento, 0)
        val notificacion = NotificationCompat.Builder(this, CHANNEL)
        .setContentTitle("servicio")
        .setContentText("canal de servicio")
        .setSmallIcon(R.drawable.abc_ic_arrow_drop_right_black_24dp)
        .setContentIntent(pendiente)
        .build()
        startForeground(1, notificacion)

        reproductor?.start()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(this, " servicio destruido", Toast.LENGTH_SHORT).show()
        reproductor?.stop()
        super.onDestroy()
    }
}