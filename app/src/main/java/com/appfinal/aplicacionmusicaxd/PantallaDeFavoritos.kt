package com.appfinal.aplicacionmusicaxd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appfinal.aplicacionmusicaxd.databinding.ActivityPantallaDeFavoritosBinding

class PantallaDeFavoritos : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaDeFavoritosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Rosa_Personsalizado_Actividades)

        // Inicializando el objeto binding
        binding = ActivityPantallaDeFavoritosBinding.inflate(layoutInflater)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_pantalla_de_favoritos)
    }
}