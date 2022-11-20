package com.appfinal.aplicacionmusicaxd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appfinal.aplicacionmusicaxd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Creacndo primer objeto obligatorio
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AplicacionMusicaXD)

        // Inicializando el objeto binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonAleatorio.setOnClickListener{
            //Toast.makeText(this@MainActivity, "Boton de aleatorio Presionado", Toast.LENGTH_SHORT).show()
            /*val intent = Intent(this@MainActivity, PantallaDeReproduccion::class.java)
            startActivity(intent)*/
            startActivity(Intent(this@MainActivity, PantallaDeReproduccion::class.java))
        }

        binding.botonFavoritos.setOnClickListener{
            /*val intent = Intent(this@MainActivity, PantallaDeFavoritos::class.java)
            startActivity(intent)*/
            startActivity(Intent(this@MainActivity, PantallaDeFavoritos::class.java))
        }

        binding.botonListas.setOnClickListener{
            /*val intent = Intent(this@MainActivity, PantallaDeListas::class.java)
            startActivity(intent)*/
            startActivity(Intent(this@MainActivity, PantallaDeListas::class.java))
        }
    }
}