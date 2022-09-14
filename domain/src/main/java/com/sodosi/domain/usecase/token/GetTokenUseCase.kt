package com.sodosi.domain.usecase.token

import com.sodosi.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

/**
 *  GetTokenUseCase.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetTokenUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
){
    suspend operator fun invoke(): String {
        return dataStoreRepository.getToken()
    }
}
