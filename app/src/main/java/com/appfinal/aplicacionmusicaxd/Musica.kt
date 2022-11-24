package com.appfinal.aplicacionmusicaxd

import android.media.MediaMetadataRetriever
import java.security.cert.CertPath
import java.util.concurrent.TimeUnit

data class Musica(val id: String, val titulo: String, val album: String, val artista: String, val duracion: Long = 0, val recorrido: String,
                  val artUri:String)

fun duracionDelFormato(duration: Long):String
{
    val minutos = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val segundos = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) -
            minutos*TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
    return String.format("%02d:%02d", minutos, segundos)
}
fun traerImagenArt(path: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(path)
    return retriever.embeddedPicture
}
fun setSongPocisition(increment: Boolean)
{
    if(increment)
    {
        if(PantallaDeReproduccion.listaDeReproduccionCanciones.size - 1 == PantallaDeReproduccion.pocisionDeLaCancion)
            PantallaDeReproduccion.pocisionDeLaCancion = 0
        else ++PantallaDeReproduccion.pocisionDeLaCancion
    }
    else
    {
        if(0 == PantallaDeReproduccion.pocisionDeLaCancion)
            PantallaDeReproduccion.pocisionDeLaCancion = PantallaDeReproduccion.listaDeReproduccionCanciones.size - 1
        else --PantallaDeReproduccion.pocisionDeLaCancion
    }
}