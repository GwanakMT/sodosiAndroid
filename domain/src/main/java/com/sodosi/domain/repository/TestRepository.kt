package com.sodosi.domain.repository

/**
 *  TestRepository.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface TestRepository {
    suspend fun requestAlbumInfo(id: Int): String
}
