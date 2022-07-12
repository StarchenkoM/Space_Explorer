package com.rprd.space_explorer.data

sealed class FavoritePhotosLoadState {
    data class Success(val favoritePhotos: List<DailyPhoto>) : FavoritePhotosLoadState()
    object Loading : FavoritePhotosLoadState()
    object LoadError : FavoritePhotosLoadState()
}