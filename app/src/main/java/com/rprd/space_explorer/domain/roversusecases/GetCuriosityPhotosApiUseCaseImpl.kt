package com.rprd.space_explorer.domain.roversusecases

import com.rprd.space_explorer.data.repositories.CuriosityRepository

class GetCuriosityPhotosApiUseCaseImpl(
        private val curiosityRepository: CuriosityRepository,
) : GetCuriosityPhotosApiUseCase {
    override suspend fun getCuriosityPhoto(sol: Int, page: Int) =
            curiosityRepository.getCuriosityPhotosApi(sol, page)
}