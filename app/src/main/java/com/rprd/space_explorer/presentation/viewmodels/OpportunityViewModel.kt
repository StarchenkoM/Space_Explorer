package com.rprd.space_explorer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rprd.space_explorer.data.OpportunityDateState
import com.rprd.space_explorer.data.RoverPhotoLoadState
import com.rprd.space_explorer.domain.roversusecases.GetOpportunityPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpportunityViewModel @Inject constructor(
        private val getOpportunityPhotosUseCase: GetOpportunityPhotosUseCase,
) : ViewModel() {

    private var _opportunityPhotosLiveData = MutableLiveData<RoverPhotoLoadState>()
    val opportunityPhotosLiveData: LiveData<RoverPhotoLoadState> = _opportunityPhotosLiveData

    private var _currentDateLiveData = MutableLiveData<OpportunityDateState>()

    fun getOpportunityPhotos() {
        _opportunityPhotosLiveData.postValue(RoverPhotoLoadState.PhotosLoading)
        viewModelScope.launch(Dispatchers.IO) {
            _opportunityPhotosLiveData.postValue(
                    getOpportunityPhotosUseCase.getOpportunityPhoto(getDate())
            )
        }
    }

    private fun getDate() = _currentDateLiveData.value?.date ?: DEFAULT_DATE
    private fun setDate(date: Int) {
        _currentDateLiveData.postValue(OpportunityDateState(date))
    }

    fun getNextDayPhoto() {
        var currentDate = getDate()
        setDate(++currentDate)
        getOpportunityPhotos()
    }

    fun getPreviousDayPhoto() {
        val currentDate = getDate()
        handlePreviousDayReceiving(currentDate)
    }

    private fun handlePreviousDayReceiving(currentDate: Int) {
        var date = currentDate
        if (isPositiveDate(date)) {
            setDate(--date)
            getOpportunityPhotos()
        } else {
            _opportunityPhotosLiveData.postValue(RoverPhotoLoadState.LoadError)
        }
    }

    private fun isPositiveDate(date: Int): Boolean {
        var temp = date
        return --temp > 0
    }

    companion object {
        private const val DEFAULT_DATE = 1002
    }

}
