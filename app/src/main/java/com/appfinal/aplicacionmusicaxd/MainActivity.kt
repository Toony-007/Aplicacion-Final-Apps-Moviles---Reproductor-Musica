package com.appfinal.aplicacionmusicaxd

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.appfinal.aplicacionmusicaxd.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    // Creando primer objeto obligatorio
    private lateinit var binding: ActivityMainBinding

    // Creando segundo objeto para la navegacion lateral
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        solicitandoPermisoDeTiempoDeEjecucion()
        setTheme(R.style.Rosa_Personsalizado_Navegacion)

        // Inicializando el objeto binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Para la ventana de naveagcion lateral
        toggle = ActionBarDrawerToggle(this, binding.root, R.string.txt_abierto, R.string.txt_cerrado)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


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
    private fun solicitandoPermisoDeTiempoDeEjecucion()
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

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (toggle.onOptionsItemSelected(item))
        return true
    return super.onOptionsItemSelected(item)
    }
}