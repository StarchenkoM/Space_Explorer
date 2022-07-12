package com.rprd.space_explorer.domain.dailyphotousecases

import com.rprd.space_explorer.data.FavoritePhotosLoadState

interface GetFavoritePhotosStatusUseCase {
    suspend fun getFavoritePhotosStatus(): FavoritePhotosLoadState
}