package com.appfinal.aplicacionmusicaxd

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.system.exitProcess

class NotificacionRecibida:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            AplicacionClass.ANTERIOR -> anterSiguiCancion(increment = false, context = context!!)
            AplicacionClass.PLAY -> if(PantallaDeReproduccion.estaReproduciendo)pausarMusica()else reproducirMusica()
            AplicacionClass.SIGUIENTE -> anterSiguiCancion(increment = false, context = context!!)
            AplicacionClass.SALIR -> {
                PantallaDeReproduccion.servicioDeMusica!!.stopForeground(true)
                PantallaDeReproduccion.servicioDeMusica = null
                exitProcess(1)

            }
        }
    }
    private fun reproducirMusica(){
        PantallaDeReproduccion.estaReproduciendo = true
        PantallaDeReproduccion.servicioDeMusica!!.mediaPlayer!!.start()
        PantallaDeReproduccion.servicioDeMusica!!.mostrarNotificaciones(R.drawable.ic_pausa)
        PantallaDeReproduccion.binding.idBotonPlayYPause.setIconResource(R.drawable.ic_pausa)
    }

    private fun pausarMusica(){
        PantallaDeReproduccion.estaReproduciendo = false
        PantallaDeReproduccion.servicioDeMusica!!.mediaPlayer!!.pause()
        PantallaDeReproduccion.servicioDeMusica!!.mostrarNotificaciones(R.drawable.ic_reproducir)
        PantallaDeReproduccion.binding.idBotonPlayYPause.setIconResource(R.drawable.ic_reproducir)
    }

    private fun anterSiguiCancion(increment: Boolean, context: Context){
        setSongPocisition(increment = increment)
        PantallaDeReproduccion.servicioDeMusica!!.createMeadiaPlayer()
        Glide.with(context)
            .load(PantallaDeReproduccion.listaDeReproduccionCanciones[PantallaDeReproduccion.pocisionDeLaCancion].artUri)
            .apply(RequestOptions().placeholder(R.drawable.reproductor_musica_splash_screen).centerCrop())
            .into(PantallaDeReproduccion.binding.imgCancionActual)
        PantallaDeReproduccion.binding.idNombreCancionActial.text = PantallaDeReproduccion.listaDeReproduccionCanciones[PantallaDeReproduccion.pocisionDeLaCancion].titulo
        reproducirMusica()
    }
}