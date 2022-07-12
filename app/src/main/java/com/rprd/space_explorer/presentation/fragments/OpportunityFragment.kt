package com.rprd.space_explorer.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rprd.space_explorer.R
import com.rprd.space_explorer.data.RoverPhoto
import com.rprd.space_explorer.data.RoverPhotoLoadState
import com.rprd.space_explorer.databinding.FragmentOpportunityBinding
import com.rprd.space_explorer.presentation.adapters.OpportunityAdapter
import com.rprd.space_explorer.presentation.viewmodels.OpportunityViewModel
import com.rprd.space_explorer.utils.ANIMATION_DURATION
import com.rprd.space_explorer.utils.ImageLoader
import com.rprd.space_explorer.utils.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class OpportunityFragment : Fragment() {
    private var binding by Delegates.notNull<FragmentOpportunityBinding>()
    private val viewModel: OpportunityViewModel by viewModels()
    private var opportunityAdapter: OpportunityAdapter? = null

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var toastUtil: ToastUtil


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentOpportunityBinding.inflate(inflater, container, false)
        initAdapter()
        initListeners()
        loadPhotos()
        initObservers()
        runRotateAnimator(binding.roverIcon)
        return binding.root
    }

    private fun initAdapter() {
        opportunityAdapter = OpportunityAdapter(context, imageLoader)
        binding.opportunityRecycler.layoutManager = LinearLayoutManager(context)
        binding.opportunityRecycler.adapter = opportunityAdapter
    }

    private fun loadPhotos() {
        viewModel.getOpportunityPhotos()
    }

    private fun initObservers() {
        viewModel.opportunityPhotosLiveData.observe(viewLifecycleOwner) { photoLoadState ->
            when (photoLoadState) {
                is RoverPhotoLoadState.Success -> {
                    showProgressBar(false)
                    opportunityAdapter?.setData(photoLoadState.roverPhotos)
                    setupUI(photoLoadState.roverPhotos)
                }
                RoverPhotoLoadState.PhotosLoading -> showProgressBar(true)
                RoverPhotoLoadState.LoadError -> {
                    showProgressBar(false)
                    toastUtil.showToast(getString(R.string.image_load_error_message))
                }
            }
        }
    }

    private fun initListeners() {
        binding.roverIcon.setOnClickListener {
            runRotateAnimator(binding.roverIcon)
        }

        binding.nextArrow.setOnClickListener {
            viewModel.getNextDayPhoto()
        }

        binding.previousArrow.setOnClickListener {
            viewModel.getPreviousDayPhoto()
        }
    }


    private fun setupUI(roverPhotos: List<RoverPhoto>) {
        binding.roverName.text = roverPhotos[0].roverName
    }

    private fun showProgressBar(show: Boolean) {
        binding.opportunityPhotoProgressBar.isVisible = show
    }

    private fun runRotateAnimator(view: View) {
        view.animate()
                .setDuration(1L)
                .rotationY(180f)
                .withEndAction { runToRightAnimator(view) }
                .start()
    }

    private fun runRotateAnimatorEnd(view: View) {
        view.animate()
                .setDuration(1L)
                .rotationY(-360f)
                .withEndAction { runToLeftAnimator(view) }
                .start()
    }

    private fun runToRightAnimator(view: View) {
        view.animate()
                .setDuration(ANIMATION_DURATION)
                .translationX(250f)
                .withEndAction { runRotateAnimatorEnd(view) }
                .start()
    }

    private fun runToLeftAnimator(view: View) {
        view.animate()
                .setDuration(ANIMATION_DURATION)
                .translationX(-5f)
                .start()
    }

}