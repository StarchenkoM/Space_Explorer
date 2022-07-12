package com.rprd.space_explorer.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.rprd.space_explorer.R
import com.rprd.space_explorer.data.DailyPhoto
import com.rprd.space_explorer.data.DailyPhotoLoadState
import com.rprd.space_explorer.data.DailyPhotoUpdateState
import com.rprd.space_explorer.databinding.FragmentDailyPhotoBinding
import com.rprd.space_explorer.presentation.activities.PhotoPreviewActivity
import com.rprd.space_explorer.presentation.viewmodels.DailyPhotoViewModel
import com.rprd.space_explorer.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates


@AndroidEntryPoint
class DailyPhotoFragment : Fragment() {
    private var isFavorite = false
    private var binding by Delegates.notNull<FragmentDailyPhotoBinding>()
    private val viewModel: DailyPhotoViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var webViewLoader: WebViewLoader

    @Inject
    lateinit var toastUtil: ToastUtil

    @Inject
    lateinit var timeUtil: TimeUtil

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyPhotoBinding.inflate(inflater, container, false)
        getDailyPhoto()
        initListeners()
        initObservers()
        return binding.root
    }

    private fun getDailyPhoto() {
        viewModel.getDailyPhoto()
    }

    private fun initListeners() {
//        handleImageZooming()
        handleNextDayButton()
        handlePreviousDayButton()
        handleSearchButton()
        handleShareButton()
        handleLikeButton()
    }

    private fun handleSearchButton() {
        binding.searchBtn.setOnClickListener {
            openDatePicker()
        }
    }

    private fun handleShareButton() {
        binding.shareBtn.setOnClickListener {
            viewModel.getPhotoLink()
        }
    }

    private fun handleLikeButton() {
        binding.likeBtn.setOnClickListener {
            viewModel.updateFavoriteStatus(!isFavorite)
        }
    }

    private fun shareImageLink(link: String) {
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, link)
            this.type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share image url"))
    }

    private fun openDatePicker() {
        val currentDate = timeUtil.getCurrentDateInLong()
        val datePicker = setDatePicker(currentDate)
        datePicker.show(parentFragmentManager, "tag")
        handlePositiveButtonClick(datePicker)
    }

    private fun setConstraints(currentDate: Long) = CalendarConstraints.Builder()
            .setOpenAt(currentDate)
            .setValidator(DateValidatorPointBackward.now())

    private fun setDatePicker(currentDate: Long): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date_title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(setConstraints(currentDate).build())
                .setTheme(R.style.MaterialCalendarTheme)
                .build()
    }

    private fun handlePositiveButtonClick(datePicker: MaterialDatePicker<Long>) {
        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            val convertedDate = timeUtil.convertToStringDate(selectedDate)
            viewModel.setDate(convertedDate)
            viewModel.getPhotoByDate(convertedDate)
        }
    }

    private fun handleImageZooming() {
        binding.dailyPhotoIv.setOnClickListener { view ->
            val intent = Intent(context, PhotoPreviewActivity::class.java)
            val trans = resources.getString(R.string.shared_image)
            intent.putExtra("imageUrl", trans)
            val activityOptions = activity?.parent?.let { activity ->
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        view, trans
                )
            }
            startActivity(intent, activityOptions?.toBundle())
        }
    }

    private fun handleNextDayButton() {
        binding.nextDay.setOnClickListener {
            viewModel.getNextDayPhoto()
        }
    }

    private fun handlePreviousDayButton() {
        binding.previousDay.setOnClickListener {
            viewModel.getPreviousDayPhoto()
        }
    }

    private fun initObservers() {
        observeDailyPhotoState()
        observePhotoSharingLiveData()
        observePhotoUpdateStateLiveDate()
    }

    private fun observePhotoSharingLiveData() {
        viewModel.dailyPhotoLinkLiveDate.observe(viewLifecycleOwner) { imageUrl ->
            if (imageUrl.isNotBlank()) {
                shareImageLink(imageUrl)
            }
        }
    }

    private fun observeDailyPhotoState() {
        viewModel.dailyPhotoStateLiveData.observe(viewLifecycleOwner) { photoLoadState ->
            when (photoLoadState) {
                is DailyPhotoLoadState.Success -> {
                    setupUI(photoLoadState.dailyPhoto)
                    showProgressBar(false)
                }
                DailyPhotoLoadState.PhotoLoading -> showProgressBar(true)
                DailyPhotoLoadState.PhotoLoadError -> {
                    toastUtil.showToast(getString(R.string.image_loading_error_message))
                    showProgressBar(false)
                }
            }
        }
    }

    private fun observePhotoUpdateStateLiveDate() {
        viewModel.photoUpdateStateLiveDate.observe(viewLifecycleOwner) { state ->
            when (state) {
                DailyPhotoUpdateState.Updating -> showProgressBar(true)
                DailyPhotoUpdateState.UpdateError -> {
                    showProgressBar(false)
                    toastUtil.showToast(R.string.photo_wasnt_updated)
                }
                is DailyPhotoUpdateState.Success -> {
                    showProgressBar(false)
                    isFavorite = state.isFavorite
                    setLikeIcon(state.isFavorite)
                }
            }
        }
    }

    private fun showProgressBar(showProgressBar: Boolean) {
        binding.photoLoadingProgress.isVisible = showProgressBar
    }

    private fun setupUI(dailyPhoto: DailyPhoto) {
        isFavorite = dailyPhoto.isFavorite
        loadMedia(dailyPhoto)
        binding.dailyPhotoTitleTv.text = dailyPhoto.dailyPhotoTitle
        binding.dailyPhotoDateTv.text = dailyPhoto.pictureDate
        binding.dailyPhotoExplanationTv.text = dailyPhoto.explanation

        setLikeIcon(dailyPhoto.isFavorite)
    }

    private fun setLikeIcon(isFavorite: Boolean) {
        val icon = if (isFavorite) R.drawable.ic_heart_checked else R.drawable.ic_heart
        binding.likeBtn.setImageResource(icon)
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