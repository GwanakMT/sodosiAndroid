package com.sodosi.domain.usecase

import com.sodosi.domain.repository.TestRepository
import javax.inject.Inject

/**
 *  GetAlbumUseCase.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetAlbumUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(id: Int): String {
        return testRepository.requestAlbumInfo(id)
    }
}
