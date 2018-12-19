package com.example.android.clase15notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //el intento se llama igual al de llamar actividad, pero en este caso
        //se trae la clase servicio, de tipo SERVICE. asi se carga los servicios
        var intento = Intent(this, Servicio :: class.java)

        btnStart.setOnClickListener{
            //startService(intento)
            ContextCompat.startForegroundService(this,intento)
        }

        btnStop.setOnClickListener {
            stopService(intento)
        }

        //SE CREA EL CANAL DE LA NOTIFICACION
    btnNotificacion.setOnClickListener {
        val id = "mi_canal_01"
        val nombre = getString(R.string.abc_action_bar_home_description)
        val description = getString(R.string.abc_action_bar_home_description)
        val importancia = NotificationManager.IMPORTANCE_HIGH
        val canal = NotificationChannel(id, nombre, importancia)

        canal.description = description
        canal.enableLights(true)
        canal.lightColor = Color.RED
        canal.enableVibration(true)

        //builder de la notificacion
        val builder = NotificationCompat.Builder(this, id)
        builder.setSmallIcon(R.drawable.notification_icon_background)
        builder.setContentTitle("mi notificacion")
        builder.setContentText("mensaje de notificacion")

        val intento = Intent(this,MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntent(intento)

        val resultado = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(resultado)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(canal)
        notificationManager.notify(0,builder.build())

    }



    }


    }

