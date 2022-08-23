package com.sodosi.domain.repository

/**
 *  TokenRepository.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface TokenRepository {
    fun getToken(): String
    fun setToken(accessToken: String)
}
