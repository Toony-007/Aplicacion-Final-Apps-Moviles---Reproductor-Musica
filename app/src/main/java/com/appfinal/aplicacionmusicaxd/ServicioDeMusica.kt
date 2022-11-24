package com.appfinal.aplicacionmusicaxd

 import android.app.PendingIntent
 import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
 import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat

class ServicioDeMusica: Service() {
    private var miCarpeta = MyCarpeta()
    var mediaPlayer:MediaPlayer?=null
    private lateinit var meddiaSession: MediaSessionCompat
    private lateinit var runnable: Runnable

    override fun onBind(intent: Intent?): IBinder {
        meddiaSession = MediaSessionCompat(baseContext, "Mi Musica")
        return miCarpeta
    }

    inner class MyCarpeta():Binder(){
        fun currentService(): ServicioDeMusica {
            return this@ServicioDeMusica
        }
    }
    fun mostrarNotificaciones(id_boton_play_y_pause: Int){

        val prevIntent = Intent(baseContext, NotificacionRecibida::class.java).setAction(AplicacionClass.ANTERIOR)
        val anteriorPendingIntent = PendingIntent.getBroadcast(baseContext, 0, prevIntent,PendingIntent.FLAG_CANCEL_CURRENT)

        val playIntent = Intent(baseContext, NotificacionRecibida::class.java).setAction(AplicacionClass.PLAY)
        val playPendingIntent = PendingIntent.getBroadcast(baseContext, 0, playIntent,PendingIntent.FLAG_CANCEL_CURRENT)

        val siguienteIntent = Intent(baseContext, NotificacionRecibida::class.java).setAction(AplicacionClass.SIGUIENTE)
        val siguientePendingIntent = PendingIntent.getBroadcast(baseContext, 0, siguienteIntent,PendingIntent.FLAG_CANCEL_CURRENT)

        val salirIntent = Intent(baseContext, NotificacionRecibida::class.java).setAction(AplicacionClass.SALIR)
        val salirPendingIntent = PendingIntent.getBroadcast(baseContext, 0, salirIntent,PendingIntent.FLAG_CANCEL_CURRENT)



        val notification = androidx.core.app.NotificationCompat.Builder(baseContext, AplicacionClass.ID_CANAL)
            .setContentText(PantallaDeReproduccion.listaDeReproduccionCanciones[PantallaDeReproduccion.pocisionDeLaCancion].artista)
            .setSmallIcon(R.drawable.ic_icono_musica)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.reproductor_musica_splash_screen))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(meddiaSession.sessionToken))
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setVisibility(androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.ic_anterior, "Anterior", anteriorPendingIntent)
            .addAction(id_boton_play_y_pause, "Reproducir", playPendingIntent)
            .addAction(R.drawable.ic_siguiente, "Siguiente", siguientePendingIntent)
            .addAction(R.drawable.ic_salir, "Salir", salirPendingIntent)
            .build()


        startForeground(13, notification)
    }

    fun createMeadiaPlayer()
    {
        try
        {
            if(PantallaDeReproduccion.servicioDeMusica!!.mediaPlayer == null) PantallaDeReproduccion.servicioDeMusica!!.mediaPlayer = MediaPlayer()
            PantallaDeReproduccion.servicioDeMusica!!.mediaPlayer!!.reset()
            PantallaDeReproduccion.servicioDeMusica!!.mediaPlayer!!.setDataSource(PantallaDeReproduccion.listaDeReproduccionCanciones[PantallaDeReproduccion.pocisionDeLaCancion].recorrido)
            PantallaDeReproduccion.servicioDeMusica!!.mediaPlayer!!.prepare()
            PantallaDeReproduccion.binding.idBotonPlayYPause.setIconResource(R.drawable.ic_pausa)
            PantallaDeReproduccion.servicioDeMusica!!.mostrarNotificaciones(R.drawable.ic_pausa)

        } catch (e: Exception)
        {
            return
        }
    }

}