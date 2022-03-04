package com.sodosi.data.repository

import com.sodosi.data.network.ServiceBuilder
import com.sodosi.data.network.api.TestAPI
import com.sodosi.domain.repository.TestRepository
import javax.inject.Inject

/**
 *  TestRepositoryImpl.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class TestRepositoryImpl @Inject constructor() : TestRepository {
    override suspend fun requestAlbumInfo(id: Int): String {
        val result = ServiceBuilder.buildService<TestAPI>().getAlbum(id)

        return result.toString()
    }
}
