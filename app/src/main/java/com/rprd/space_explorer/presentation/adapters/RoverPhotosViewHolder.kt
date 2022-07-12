package com.rprd.space_explorer.presentation.adapters

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.rprd.space_explorer.R
import com.rprd.space_explorer.data.RoverPhoto
import com.rprd.space_explorer.databinding.RoverPhotoItemBinding
import com.rprd.space_explorer.utils.ImageLoader

class RoverPhotosViewHolder(
        private val binding: RoverPhotoItemBinding,
        private val imageLoader: ImageLoader,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(roverPhoto: RoverPhoto) {
        imageLoader.loadImage(roverPhoto.imageUrl, resizeX = 800, resizeY = 800, binding.roverPhotoIv, R.drawable.no_photography_gray)

        binding.earthTimeTv.text = "Earth date: ${roverPhoto.earthDate}"
        binding.marsTimeTv.text = "Mars sol: ${roverPhoto.sol}"
        binding.cameraNameTv.text = "Camera: ${roverPhoto.roverCameraName}"

        binding.photoUrlTv.text = binding.root.resources.getString(R.string.full_size_image)

        binding.photoUrlTv.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(roverPhoto.imageUrl))
            binding.root.context.startActivity(browserIntent)
        }
    }
}