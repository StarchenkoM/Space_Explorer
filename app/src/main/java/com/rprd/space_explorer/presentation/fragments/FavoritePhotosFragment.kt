package com.rprd.space_explorer.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rprd.space_explorer.R
import com.rprd.space_explorer.data.DailyPhotoUpdateState
import com.rprd.space_explorer.data.FavoritePhotosLoadState
import com.rprd.space_explorer.databinding.FragmentFavoritePhotosBinding
import com.rprd.space_explorer.listeners.OnButtonClickedListener
import com.rprd.space_explorer.presentation.adapters.FavoritePhotosAdapter
import com.rprd.space_explorer.presentation.viewmodels.DailyPhotoViewModel
import com.rprd.space_explorer.utils.ImageLoader
import com.rprd.space_explorer.utils.ToastUtil
import com.rprd.space_explorer.utils.WebViewLoader
import com.rprd.space_explorer.utils.navigateSafe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class FavoritePhotosFragment : Fragment(), OnButtonClickedListener {

    private var adapter: FavoritePhotosAdapter? = null
    private var binding by Delegates.notNull<FragmentFavoritePhotosBinding>()
    private val viewModel: DailyPhotoViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var webViewLoader: WebViewLoader

    @Inject
    lateinit var toastUtil: ToastUtil

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritePhotosBinding.inflate(inflater, container, false)
        initAdapter()
        loadPhotos()
        initObservers()
        return binding.root
    }

    private fun initAdapter() {
        adapter = FavoritePhotosAdapter(context, this, imageLoader, webViewLoader)
        binding.favoriteRecycler.layoutManager = LinearLayoutManager(context)
        binding.favoriteRecycler.adapter = adapter
    }

    private fun loadPhotos() {
        viewModel.getFavoritePhotos()
    }

    private fun initObservers() {
        viewModel.favoritePhotosStateLiveDate.observe(viewLifecycleOwner) { loadState ->
            when (loadState) {
                is FavoritePhotosLoadState.Success -> {
                    showProgressBar(false)
                    adapter?.setData(loadState.favoritePhotos)
                }
                FavoritePhotosLoadState.Loading -> showProgressBar(true)
                FavoritePhotosLoadState.LoadError -> {
                    showProgressBar(false)
                    toastUtil.showToast(resources.getString(R.string.image_loading_error_message))
                }
            }
        }
    }

    private fun showProgressBar(show: Boolean) {
        binding.favoriteProgressBar.isVisible = show
    }

    override fun onDescriptionClicked(description: String) {
        val action = FavoritePhotosFragmentDirections.openDescriptionDialog(description)
        findNavController().navigateSafe(action)
    }

    override fun onLikeButtonClicked(isFavorite: Boolean, imageDate: String) {
        viewModel.updateFavoriteStatusByDate(isFavorite, imageDate)
    }


}