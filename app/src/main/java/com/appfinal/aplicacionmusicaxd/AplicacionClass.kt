package com.appfinal.aplicacionmusicaxd

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat

class AplicacionClass:Application (){
    companion object{
        const val ID_CANAL="canal_1"
        const val PLAY="Play"
        const val SIGUIENTE="Siguiente"
        const val ANTERIOR="Anterior"
        const val SALIR="Salir"
    }
    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(ID_CANAL, "Esta reproduciendo una cancion", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "Este es un canal importante para mostrar la canción!!"
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}