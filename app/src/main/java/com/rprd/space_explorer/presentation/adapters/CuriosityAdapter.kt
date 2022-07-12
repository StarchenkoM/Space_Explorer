package com.rprd.space_explorer.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rprd.space_explorer.data.RoverPhoto
import com.rprd.space_explorer.databinding.RoverPhotoItemBinding
import com.rprd.space_explorer.utils.ImageLoader

class CuriosityAdapter(
        private val context: Context?,
        private val imageLoader: ImageLoader,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var roverPhotos: List<RoverPhoto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoverPhotosViewHolder {
        val binding =
                RoverPhotoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return RoverPhotosViewHolder(binding, imageLoader)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dbPhoto = roverPhotos[position]
        (holder as RoverPhotosViewHolder).bind(dbPhoto)
    }

    override fun getItemCount(): Int = roverPhotos.size

    fun setData(roverPhotos: List<RoverPhoto>) {
        this.roverPhotos = roverPhotos
        notifyDataSetChanged()
    }

}
