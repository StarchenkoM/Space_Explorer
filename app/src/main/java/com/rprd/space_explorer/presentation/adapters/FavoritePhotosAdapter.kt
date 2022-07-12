package com.rprd.space_explorer.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rprd.space_explorer.data.DailyPhoto
import com.rprd.space_explorer.databinding.FavoritePhotoItemBinding
import com.rprd.space_explorer.listeners.OnButtonClickedListener
import com.rprd.space_explorer.utils.ImageLoader
import com.rprd.space_explorer.utils.WebViewLoader

class FavoritePhotosAdapter(
        private val context: Context?,
        private val listener: OnButtonClickedListener,
        private val imageLoader: ImageLoader,
        private val webViewLoader: WebViewLoader,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var photos: List<DailyPhoto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePhotosViewHolder {
        val binding =
                FavoritePhotoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavoritePhotosViewHolder(binding, listener, imageLoader, webViewLoader)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = photos[position]
        (holder as FavoritePhotosViewHolder).bind(photo)
    }

    override fun getItemCount(): Int = photos.size

    fun setData(photos: List<DailyPhoto>) {
        this.photos = photos.asReversed()
        notifyDataSetChanged()
    }

}
