package com.rprd.space_explorer.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rprd.space_explorer.R
import com.rprd.space_explorer.databinding.ActivityPhotoPreviewBinding
import com.rprd.space_explorer.utils.ImageLoader
import javax.inject.Inject
import kotlin.properties.Delegates

class PhotoPreviewActivity : AppCompatActivity() {
    private var binding by Delegates.notNull<ActivityPhotoPreviewBinding>()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoPreviewBinding.inflate(layoutInflater)
        val imageUrl = intent.getStringExtra("imageUrl")
        imageLoader.loadImage(imageUrl, binding.previewIv, R.drawable.no_photography_gray)
        setContentView(binding.root)
    }
}