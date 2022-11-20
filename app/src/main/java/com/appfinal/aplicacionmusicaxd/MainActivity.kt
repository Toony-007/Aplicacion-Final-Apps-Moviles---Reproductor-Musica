package com.appfinal.aplicacionmusicaxd

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.appfinal.aplicacionmusicaxd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Creacndo primer objeto obligatorio
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        solicitandoPermisoDeTiempoDeEjecución()
        setTheme(R.style.Theme_AplicacionMusicaXD)

        // Inicializando el objeto binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    // Para pedir permiso
    private fun solicitandoPermisoDeTiempoDeEjecución()
    {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                , 13)
        }
    }

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
}