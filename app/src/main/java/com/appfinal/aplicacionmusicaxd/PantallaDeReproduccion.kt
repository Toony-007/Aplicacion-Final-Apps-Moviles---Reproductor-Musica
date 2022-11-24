package com.appfinal.aplicacionmusicaxd

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.appfinal.aplicacionmusicaxd.databinding.ActivityPantallaDeReproduccionBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PantallaDeReproduccion : AppCompatActivity(), ServiceConnection, MediaPlayer.OnCompletionListener {
    private lateinit var runnable: Runnable

    companion object
    {
        lateinit var listaDeReproduccionCanciones : ArrayList<Musica>
        var pocisionDeLaCancion : Int = 0
        var estaReproduciendo:Boolean = false
        var servicioDeMusica: ServicioDeMusica? =null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityPantallaDeReproduccionBinding
    }

    //private lateinit var binding: ActivityPantallaDeReproduccionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Rosa_Personsalizado_Actividades)

        // Inicializando el objeto binding
        binding = ActivityPantallaDeReproduccionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Para arranque de servicio.
        val intent = Intent(this, ServicioDeMusica::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        startService(intent)

        initializeLayout()
        binding.btnRegresarReproduccion.setOnClickListener { finish() }
        binding.idBotonPlayYPause.setOnClickListener{
            if (estaReproduciendo) pauseMusic()
            else playMusic()
        }
        binding.idBotonAnterior.setOnClickListener{
            prevNextSong(increment = false)
        }
        binding.idBotonAvanzar.setOnClickListener{
            prevNextSong(increment = true)
        }
         //setContentView(R.layout.activity_pantalla_de_reproduccion)

        binding.idBarraReproduccion.setOnSeekBarChangeListener(object:OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) servicioDeMusica!!.mediaPlayer!!.seekTo(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
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
            if(servicioDeMusica!!.mediaPlayer == null) servicioDeMusica!!.mediaPlayer = MediaPlayer()
            servicioDeMusica!!.mediaPlayer!!.reset()
            servicioDeMusica!!.mediaPlayer!!.setDataSource(listaDeReproduccionCanciones[pocisionDeLaCancion].recorrido)
            servicioDeMusica!!.mediaPlayer!!.prepare()
            servicioDeMusica!!.mediaPlayer!!.start()
            estaReproduciendo = true
            binding.idBotonPlayYPause.setIconResource(R.drawable.ic_pausa)
            servicioDeMusica!!.mostrarNotificaciones(R.drawable.ic_pausa)
            binding.idTiempoCancion.text = duracionDelFormato(servicioDeMusica!!.mediaPlayer!!.currentPosition.toLong())
            binding.idTiempoTotal.text = duracionDelFormato(servicioDeMusica!!.mediaPlayer!!.duration.toLong())
            binding.idBarraReproduccion.progress = 0
            binding.idBarraReproduccion.max = servicioDeMusica!!.mediaPlayer!!.duration
            servicioDeMusica!!.mediaPlayer!!.setOnCompletionListener(this)

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
            }
            "MainActivity"->{
                listaDeReproduccionCanciones = ArrayList()
                listaDeReproduccionCanciones.addAll(MainActivity.listaDeMusicaMA)
                listaDeReproduccionCanciones.shuffle()
                setLayout()
            }
        }
    }

    private fun playMusic() {
        binding.idBotonPlayYPause.setIconResource(R.drawable.ic_pausa)
        servicioDeMusica!!.mostrarNotificaciones(R.drawable.ic_pausa)
        estaReproduciendo = true
        servicioDeMusica!!.mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        binding.idBotonPlayYPause.setIconResource(R.drawable.ic_reproducir)
        servicioDeMusica!!.mostrarNotificaciones(R.drawable.ic_reproducir)
        estaReproduciendo = false
        servicioDeMusica!!.mediaPlayer!!.pause()
    }

    private fun prevNextSong(increment: Boolean)
    {
        if(increment)
        {
            setSongPocisition(increment = true)
            setLayout()
            createMeadiaPlayer()
        }
        else
        {
            setSongPocisition(increment = false)
            setLayout()
            createMeadiaPlayer()
        }
    }



    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as ServicioDeMusica.MyCarpeta
        servicioDeMusica = binder.currentService()
        createMeadiaPlayer()
        servicioDeMusica!!.barraEnProgreso()

        //servicioDeMusica!!.mostrarNotificaciones()
        //servicioDeMusica!!.mostrarNotificaciones(R.drawable.ic_pausa)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        servicioDeMusica = null
    }

    override fun onCompletion(mp: MediaPlayer?) {
        setSongPocisition(increment = true)
        createMeadiaPlayer()
        try{setLayout()}catch (e:Exception){return}
    }
}