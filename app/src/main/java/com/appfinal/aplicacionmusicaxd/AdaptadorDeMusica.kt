package com.appfinal.aplicacionmusicaxd

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appfinal.aplicacionmusicaxd.databinding.ActivityVistaDeLasCancionesBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdaptadorDeMusica(private val contexto: Context, private val listaDeCanciones: ArrayList<Musica>) : RecyclerView.Adapter<AdaptadorDeMusica.MiTitular>(){
    class MiTitular(biding: ActivityVistaDeLasCancionesBinding) : RecyclerView.ViewHolder(biding.root) {

        val titulo = biding.idNombreCancion
        val album = biding.idAlbumCancion
        val imagen = biding.imgCancion
        val duracion = biding.idDuracionCancion
    }

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): AdaptadorDeMusica.MiTitular
    {
        return MiTitular(ActivityVistaDeLasCancionesBinding.inflate(LayoutInflater.from(contexto), parent, false))
    }

    override fun onBindViewHolder (holder: AdaptadorDeMusica.MiTitular, position: Int)
    {
        holder.titulo.text = listaDeCanciones[position].titulo
        holder.album.text = listaDeCanciones[position].album
        holder.duracion.text = listaDeCanciones[position].duracion.toString()

        // Obtener y mostrar la imagen de la cacion
        Glide.with(contexto)
            .load(listaDeCanciones[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.reproductor_musica_splash_screen).centerCrop())
            .into(holder.imagen)


    }

    override fun getItemCount(): Int
    {
        return listaDeCanciones.size
    }

}