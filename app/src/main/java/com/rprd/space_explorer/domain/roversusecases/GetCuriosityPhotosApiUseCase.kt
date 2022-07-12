package com.rprd.space_explorer.domain.roversusecases

import com.rprd.space_explorer.data.RoverPhotosApiResponse
import retrofit2.Response

interface GetCuriosityPhotosApiUseCase {
    suspend fun getCuriosityPhoto(sol: Int, page: Int): Response<RoverPhotosApiResponse>
}