package com.appfinal.aplicacionmusicaxd

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appfinal.aplicacionmusicaxd.databinding.ActivityPantallaDeReproduccionBinding

class PantallaDeReproduccion : AppCompatActivity() {

    companion object
    {
        lateinit var listaDeReproduccionCanciones : ArrayList<Musica>
        var pocisionDeLaCancion : Int = 0
        var mediaPlayer : MediaPlayer? = null
    }

    private lateinit var binding: ActivityPantallaDeReproduccionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Rosa_Personsalizado_Actividades)

        // Inicializando el objeto binding
        binding = ActivityPantallaDeReproduccionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pocisionDeLaCancion = intent.getIntExtra("indice", 0)
        when(intent.getStringExtra("clase"))
        {
            "AdaptadorDeMusica" ->
            {
                listaDeReproduccionCanciones = ArrayList()
                listaDeReproduccionCanciones.addAll(MainActivity.listaDeMusicaMA)
                if(mediaPlayer == null) mediaPlayer = MediaPlayer()
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(listaDeReproduccionCanciones[pocisionDeLaCancion].recorrido)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
            }
        }
         //setContentView(R.layout.activity_pantalla_de_reproduccion)
    }
}