package com.rprd.space_explorer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rprd.space_explorer.data.DailyPhotoLoadState
import com.rprd.space_explorer.data.DailyPhotoUpdateState
import com.rprd.space_explorer.data.FavoritePhotosLoadState
import com.rprd.space_explorer.domain.dailyphotousecases.GetDailyPhotoUseCase
import com.rprd.space_explorer.domain.dailyphotousecases.GetFavoritePhotosStatusUseCase
import com.rprd.space_explorer.domain.dailyphotousecases.UpdatePhotoFavoriteStatusUseCase
import com.rprd.space_explorer.utils.TimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyPhotoViewModel @Inject constructor(
        private val getDailyPhotoUseCase: GetDailyPhotoUseCase,
        private val updatePhotoFavoriteStatusUseCase: UpdatePhotoFavoriteStatusUseCase,
        private val getFavoritePhotosStatusUseCase: GetFavoritePhotosStatusUseCase,
        private val timeUtil: TimeUtil,
) : ViewModel() {

    private var date: String = ""

    private var _dailyPhotoStateLiveDate = MutableLiveData<DailyPhotoLoadState>()
    val dailyPhotoStateLiveData: LiveData<DailyPhotoLoadState> get() = _dailyPhotoStateLiveDate

    private var _favoritePhotosStateLiveDate = MutableLiveData<FavoritePhotosLoadState>()
    val favoritePhotosStateLiveDate: LiveData<FavoritePhotosLoadState> get() = _favoritePhotosStateLiveDate

    private var _photoUpdateStateLiveDate = MutableLiveData<DailyPhotoUpdateState>()
    val photoUpdateStateLiveDate: LiveData<DailyPhotoUpdateState> get() = _photoUpdateStateLiveDate

    private var _dailyPhotoLinkLiveDate = MutableLiveData<String>()
    val dailyPhotoLinkLiveDate: LiveData<String> get() = _dailyPhotoLinkLiveDate

    fun getDailyPhoto() {
        getPhotoByDate(getDate())
    }

    fun getNextDayPhoto() {
        val nextDay = provideDate(nextDay = true)
        if (!timeUtil.isTomorrow(nextDay)) {
            setDate(nextDay)
            getPhotoByDate(nextDay)
        } else {
            _dailyPhotoStateLiveDate.postValue(DailyPhotoLoadState.PhotoLoadError)
        }
    }

    fun getPreviousDayPhoto() {
        val previousDay = provideDate(nextDay = false)
        setDate(previousDay)
        getPhotoByDate(previousDay)
    }

    fun getPhotoByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _dailyPhotoStateLiveDate.postValue(DailyPhotoLoadState.PhotoLoading)
            val state = getDailyPhotoUseCase.getDailyPhoto(date)
            _dailyPhotoStateLiveDate.postValue(state)
        }
    }

    private fun getDate() = date.ifBlank { timeUtil.getTodayDate() }

    fun setDate(date: String) {
        this.date = date
    }

    private fun provideDate(nextDay: Boolean): String {
        val currentDate = getDate()
        return when {
            nextDay -> timeUtil.getNextDateFromDate(currentDate)
            else -> timeUtil.getPreviousDateFromDate(currentDate)
        }
    }

    fun getPhotoLink() {
        val state = dailyPhotoStateLiveData.value
        when (state) {
            is DailyPhotoLoadState.Success -> _dailyPhotoLinkLiveDate.postValue(state.dailyPhoto.dailyPhotoUrl)
            else -> _dailyPhotoLinkLiveDate.postValue("")
        }
    }

    fun updateFavoriteStatus(isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _photoUpdateStateLiveDate.postValue(DailyPhotoUpdateState.Updating)
            val updatingState = updatePhotoFavoriteStatusUseCase.updatePhotoFavoriteStatus(getDate(), isFavorite)
            _photoUpdateStateLiveDate.postValue(updatingState)
        }
    }

    fun updateFavoriteStatusByDate(isFavorite: Boolean, imageDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _photoUpdateStateLiveDate.postValue(DailyPhotoUpdateState.Updating)
            val updatingState = updatePhotoFavoriteStatusUseCase.updatePhotoFavoriteStatus(imageDate, isFavorite)
            _photoUpdateStateLiveDate.postValue(updatingState)
        }
    }

    fun getFavoritePhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoritePhotosStateLiveDate.postValue(FavoritePhotosLoadState.Loading)
            val updatingState = getFavoritePhotosStatusUseCase.getFavoritePhotosStatus()
            _favoritePhotosStateLiveDate.postValue(updatingState)
        }
    }
}