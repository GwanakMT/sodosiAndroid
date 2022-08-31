package com.sodosi.data.repository

import android.content.Context
import com.sodosi.domain.repository.TokenRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 *  TokenRepositoryImpl.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class TokenRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenRepository {
    private val prefs = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)

    override fun getToken(): String {
        return prefs.getString(KEY_ACCESS_TOKEN, "") ?: ""
    }

    override fun setToken(accessToken: String) {
        prefs.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply()
    }

    companion object {
        private const val PREFERENCE_FILE_NAME = "sodosi"

        private const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
    }
}
