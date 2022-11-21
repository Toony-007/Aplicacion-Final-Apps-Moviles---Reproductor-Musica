package com.appfinal.aplicacionmusicaxd

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appfinal.aplicacionmusicaxd.databinding.ActivityPantallaDeReproduccionBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PantallaDeReproduccion : AppCompatActivity() {

    companion object
    {
        lateinit var listaDeReproduccionCanciones : ArrayList<Musica>
        var pocisionDeLaCancion : Int = 0
        var mediaPlayer : MediaPlayer? = null
        var estaReproduciendo = false
    }

    private lateinit var binding: ActivityPantallaDeReproduccionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Rosa_Personsalizado_Actividades)

        // Inicializando el objeto binding
        binding = ActivityPantallaDeReproduccionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeLayout()
        binding.idBotonPlayYPause.setOnClickListener{
            if (estaReproduciendo) pauseMusic()
            else playMusic()
        }

         //setContentView(R.layout.activity_pantalla_de_reproduccion)
    }

    private fun setLayout()
    {
        Glide.with(this)
            .load(listaDeReproduccionCanciones[pocisionDeLaCancion].artUri)
            .apply(RequestOptions().placeholder(R.drawable.reproductor_musica_splash_screen).centerCrop())
            .into(binding.imgCancionActual)
        binding.idNombreCancionActial.text = listaDeReproduccionCanciones[pocisionDeLaCancion].titulo
    }

    private fun createMeadiaPlayer()
    {
        try
        {
            if(mediaPlayer == null) mediaPlayer = MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(listaDeReproduccionCanciones[pocisionDeLaCancion].recorrido)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            estaReproduciendo = true
            binding.idBotonPlayYPause.setIconResource(R.drawable.ic_pausa)
        } catch (e: Exception)
        {
            return
        }
    }

    private fun initializeLayout()
    {
        pocisionDeLaCancion = intent.getIntExtra("indice", 0)
        when(intent.getStringExtra("clase"))
        {
            "AdaptadorDeMusica" ->
            {
                listaDeReproduccionCanciones = ArrayList()
                listaDeReproduccionCanciones.addAll(MainActivity.listaDeMusicaMA)
                setLayout()
                createMeadiaPlayer()
            }
        }
    }

    private fun playMusic()
    {
        binding.idBotonPlayYPause.setIconResource(R.drawable.ic_pausa)
        estaReproduciendo = true
        mediaPlayer!!.start()
    }

    private fun pauseMusic()
    {
        binding.idBotonPlayYPause.setIconResource(R.drawable.ic_reproducir)
        estaReproduciendo = false
        mediaPlayer!!.pause()
    }
}