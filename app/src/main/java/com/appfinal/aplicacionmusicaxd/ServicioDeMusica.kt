package com.appfinal.aplicacionmusicaxd

import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.MediaSession2
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.app.NotificationCompat

class ServicioDeMusica: Service() {
    private var miCarpeta = MyCarpeta()
    var mediaPlayer:MediaPlayer?=null
    private lateinit var meddiaSession: MediaSessionCompat

    override fun onBind(intent: Intent?): IBinder {
        meddiaSession = MediaSessionCompat(baseContext, "Mi Musica")
        return miCarpeta
    }

    inner class MyCarpeta():Binder(){
        fun currentService(): ServicioDeMusica {
            return this@ServicioDeMusica
        }
    }
    fun mostrarNotificaciones(){
        val notification = androidx.core.app.NotificationCompat.Builder(baseContext, AplicacionClass.ID_CANAL)
            .setContentText(PantallaDeReproduccion.listaDeReproduccionCanciones[PantallaDeReproduccion.pocisionDeLaCancion].artista)
            .setSmallIcon(R.drawable.ic_listas)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.reproductor_musica_splash_screen))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(meddiaSession.sessionToken))
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setVisibility(androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.ic_anterior, "Anterior", null)
            .addAction(R.drawable.ic_reproducir, "Reproducir", null)
            .addAction(R.drawable.ic_siguiente, "Siguiente", null)
            .addAction(R.drawable.ic_salir, "Salir", null)
            .build()


        startForeground(13, notification)
    }
}