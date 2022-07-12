package com.rprd.space_explorer.presentation.adapters

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.rprd.space_explorer.R
import com.rprd.space_explorer.data.DailyPhoto
import com.rprd.space_explorer.databinding.FavoritePhotoItemBinding
import com.rprd.space_explorer.listeners.OnButtonClickedListener
import com.rprd.space_explorer.presentation.viewmodels.DailyPhotoViewModel
import com.rprd.space_explorer.utils.IMAGE
import com.rprd.space_explorer.utils.ImageLoader
import com.rprd.space_explorer.utils.VIDEO
import com.rprd.space_explorer.utils.WebViewLoader

class FavoritePhotosViewHolder(
        private val binding: FavoritePhotoItemBinding,
        private val listener: OnButtonClickedListener,
        private val imageLoader: ImageLoader,
        private val webViewLoader: WebViewLoader,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: DailyPhoto) {
        loadMedia(photo)
        binding.dailyPhotoTitleTv.text = photo.dailyPhotoTitle
        binding.dailyPhotoDateTv.text = photo.pictureDate
        setLikeIcon(photo.isFavorite)
        handleLikeButtonClick(photo.isFavorite, photo.pictureDate)
        openImageInBrowser(photo.dailyPhotoUrl)
        showDescription(photo.explanation)
        shareImageLink(photo.dailyPhotoUrl)
    }

    private fun showDescription(description: String) {
        binding.descriptionBtn.setOnClickListener {
            listener.onDescriptionClicked(description)
        }
    }

    private fun handleLikeButtonClick(isFavorite: Boolean, date: String) {
        binding.likeBtn.setOnClickListener {
//            listener.onLikeButtonClicked(!isFavorite, date)
            Toast.makeText(binding.root.context, "Sorry this function isn't implemented yet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLikeIcon(isFavorite: Boolean) {
        val icon = if (isFavorite) R.drawable.ic_heart_checked else R.drawable.ic_heart
        binding.likeBtn.setImageResource(icon)
    }

    private fun openImageInBrowser(imageUrl: String) {
        binding.dailyPhotoIv.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl))
            binding.root.context.startActivity(browserIntent)
        }
    }

    private fun shareImageLink(imageUrl: String) {
        binding.shareBtn.setOnClickListener {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, imageUrl)
                this.type = "text/plain"
            }
            binding.root.context.startActivity(Intent.createChooser(shareIntent, binding.root.context.getString(R.string.share_image_url)))
        }
    }

    private fun loadMedia(dailyPhoto: DailyPhoto) {
        val mediaType = dailyPhoto.mediaType
        when (mediaType) {
            IMAGE -> {
                val imageVisible = binding.dailyPhotoIv.isVisible
                if (!imageVisible) {
                    binding.dailyPhotoIv.isVisible = true
                    binding.contentWebView.visibility = View.GONE
                }
                imageLoader.loadImage(
                        dailyPhoto.dailyPhotoUrl,
                        binding.dailyPhotoIv,
                        R.drawable.no_photography_gray
                )
            }
            VIDEO -> {
                binding.dailyPhotoIv.visibility = View.GONE
                binding.contentWebView.isVisible = true
                webViewLoader.loadWebView(dailyPhoto.dailyPhotoUrl, binding.contentWebView)
            }
        }
    }

}