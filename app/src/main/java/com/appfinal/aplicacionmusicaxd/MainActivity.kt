package com.appfinal.aplicacionmusicaxd

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.appfinal.aplicacionmusicaxd.databinding.ActivityMainBinding
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    // Creando primer objeto obligatorio
    private lateinit var binding: ActivityMainBinding

    // Creando segundo objeto para la navegacion lateral
    private lateinit var toggle: ActionBarDrawerToggle

    // Creando tercer objeto para la clase adaptador de musica
    private lateinit var AdaptadorDeMusica: AdaptadorDeMusica

    companion object
    {
        var listaDeMusicaMA : ArrayList<Musica> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()


        // ASignando accion al boton aleatorio.
        binding.botonAleatorio.setOnClickListener{
            //Toast.makeText(this@MainActivity, "Boton de aleatorio Presionado", Toast.LENGTH_SHORT).show()
            /*val intent = Intent(this@MainActivity, PantallaDeReproduccion::class.java)
            startActivity(intent)*/
            startActivity(Intent(this@MainActivity, PantallaDeReproduccion::class.java))
        }

        // Asignando accion al boton favoritos.
        binding.botonFavoritos.setOnClickListener{
            /*val intent = Intent(this@MainActivity, PantallaDeFavoritos::class.java)
            startActivity(intent)*/
            startActivity(Intent(this@MainActivity, PantallaDeFavoritos::class.java))
        }

        // Asignando accion al boton listos.
        binding.botonListas.setOnClickListener{
            /*val intent = Intent(this@MainActivity, PantallaDeListas::class.java)
            startActivity(intent)*/
            startActivity(Intent(this@MainActivity, PantallaDeListas::class.java))
        }

        // Asignando accion a los botones de la barra de navegacion.
        binding.vwNavegacion.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.nav_foro -> Toast.makeText(baseContext, "Foro", Toast.LENGTH_SHORT).show()
                R.id.nav_ajustes -> Toast.makeText(baseContext, "Ajustes", Toast.LENGTH_SHORT).show()
                R.id.nav_acerca_de -> Toast.makeText(baseContext, "Acerca de", Toast.LENGTH_SHORT).show()
                R.id.nav_salir -> exitProcess(1)
            }
            true
        }
    }

    // Para pedir permiso
    private fun requestRuntimePermission()
    {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                , 13)
        }
    }

    // Resultado de la solicitud de permisos
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 13)
        {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permiso Concedido", Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    , 13)
        }
    }

    // Elemento de opciones seleccionado
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    // inicializacion de los diseños
    //@RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("SetTextI18n")
    private fun initializeLayout()
    {
        requestRuntimePermission()
        setTheme(R.style.Rosa_Personsalizado_Navegacion)

        // Inicializando el objeto binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Para la ventana de naveagcion lateral
        toggle = ActionBarDrawerToggle(this, binding.root, R.string.txt_abierto, R.string.txt_cerrado)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        listaDeMusicaMA = getAllAudio()
        /*
        val listaDeCanciones = ArrayList<String>()
        listaDeCanciones.add("Primera cancion")
        listaDeCanciones.add("Segunda cancion")
        listaDeCanciones.add("Tercera cancion")
        listaDeCanciones.add("Cuarta cancion")
        listaDeCanciones.add("Quinta cancion")
         */
        binding.pnlCanciones.setHasFixedSize(true)
        binding.pnlCanciones.setItemViewCacheSize(13)
        binding.pnlCanciones.layoutManager = LinearLayoutManager(this@MainActivity)
        AdaptadorDeMusica = AdaptadorDeMusica(this@MainActivity, listaDeMusicaMA)
        binding.pnlCanciones.adapter = AdaptadorDeMusica
        binding.idCacionesTotales.text = "Canciónes totales: "+AdaptadorDeMusica.itemCount
    }


    @SuppressLint("Recycle", "Range", "SuspiciousIndentation")
    //@RequiresApi(Build.VERSION_CODES.R)
    private fun getAllAudio(): ArrayList<Musica>
    {
        val listaTemporal = ArrayList<Musica>()
        val seleccion = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val proyeccion = arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID)
        val mostrador = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proyeccion,seleccion,null,
            MediaStore.Audio.Media.DATE_ADDED + " DESC", null)
        if(mostrador != null){
            if(mostrador.moveToFirst())
                do {
                    val tituloM = mostrador.getString(mostrador.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idM = mostrador.getString(mostrador.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumM = mostrador.getString(mostrador.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistaM = mostrador.getString(mostrador.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val recorridoM = mostrador.getString(mostrador.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val duracionM = mostrador.getLong(mostrador.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIdM = mostrador.getLong(mostrador.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                    val uri = Uri.parse("content://media/external/audio/albumart")
                    val artUriM = Uri.withAppendedPath(uri, albumIdM).toString()
                    val musica = Musica(id = idM, titulo = tituloM, album = albumM, artista = artistaM, recorrido = recorridoM, duracion = duracionM,
                        artUri = artUriM)
                    val archivo = File(musica.recorrido)
                    if(archivo.exists())
                        listaTemporal.add(musica)
                }while (mostrador.moveToNext())
            mostrador.close()
        }
        return listaTemporal

    }


}