package com.rprd.space_explorer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rprd.space_explorer.data.CuriosityDateState
import com.rprd.space_explorer.data.RoverPhotoLoadState
import com.rprd.space_explorer.domain.roversusecases.GetCuriosityPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CuriosityViewModel @Inject constructor(
        private val getCuriosityPhotosUseCase: GetCuriosityPhotosUseCase,
) : ViewModel() {

    private var _curiosityPhotosLiveData = MutableLiveData<RoverPhotoLoadState>()
    val curiosityPhotoLoadLiveData: LiveData<RoverPhotoLoadState> = _curiosityPhotosLiveData

    private var _currentDateLiveData = MutableLiveData<CuriosityDateState>()

    fun getCuriosityPhotos() {
        _curiosityPhotosLiveData.postValue(RoverPhotoLoadState.PhotosLoading)
        viewModelScope.launch(Dispatchers.IO) {
            _curiosityPhotosLiveData.postValue(getCuriosityPhotosUseCase.getCuriosityPhoto(getDate()))
        }
    }

    private fun getDate() = _currentDateLiveData.value?.date ?: DEFAULT_DATE
    private fun setDate(date: Int) {
        _currentDateLiveData.postValue(CuriosityDateState(date))
    }

    fun getNextDayPhoto() {
        var currentDate = getDate()
        setDate(++currentDate)
        getCuriosityPhotos()
    }

    fun getPreviousDayPhoto() {
        val currentDate = getDate()
        handlePreviousDayReceiving(currentDate)
    }

    private fun handlePreviousDayReceiving(currentDate: Int) {
        var date = currentDate
        if (isPositiveDate(date)) {
            setDate(--date)
            getCuriosityPhotos()
        } else {
            _curiosityPhotosLiveData.postValue(RoverPhotoLoadState.LoadError)
        }
    }

    private fun isPositiveDate(date: Int): Boolean {
        var temp = date
        return --temp > 0
    }

    companion object {
        private const val DEFAULT_DATE = 1000
    }

}
