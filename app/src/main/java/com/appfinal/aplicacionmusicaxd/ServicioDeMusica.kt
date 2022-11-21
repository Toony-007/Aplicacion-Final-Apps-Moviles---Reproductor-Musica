package com.appfinal.aplicacionmusicaxd

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.media.app.NotificationCompat

class ServicioDeMusica: Service() {
    private var miCarpeta = MyCarpeta()
    var mediaPlayer:MediaPlayer?=null

    override fun onBind(intent: Intent?): IBinder {
        return miCarpeta
    }

    inner class MyCarpeta():Binder(){
        fun currentService(): ServicioDeMusica {
            return this@ServicioDeMusica
        }
    }
    fun mostrarNotificaciones(){

    }
}