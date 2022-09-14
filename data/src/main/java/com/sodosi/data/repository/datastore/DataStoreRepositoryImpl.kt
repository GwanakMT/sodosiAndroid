package com.sodosi.data.repository.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sodosi.domain.repository.datastore.DataStoreRepository
import com.sodosi.domain.usecase.user.GetLastVisitedTimeUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  DataStoreRepositoryImpl.kt
 *
 *  Created by Minji Jeong on 2022/05/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DataStoreRepository {

    companion object {
        private const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
        private const val KEY_CURRENT_TIME = "KEY_CURRENT_TIME"
        private const val KEY_LAST_VISITED_TIME = "KEY_LAST_VISITED_TIME"
        private const val KEY_PHONE_NUMBER = "KEY_PHONE_NUMBER"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "SODOSI_DATASTORE")
    private val datastoreManager = DataStoreManager(context.dataStore)

    override suspend fun setToken(accessToken: String) {
        setDataStoreString(KEY_ACCESS_TOKEN, accessToken)
    }

    override suspend fun getToken(): String {
        return getDataStoreStringOnce(KEY_ACCESS_TOKEN) ?: ""
    }

    override suspend fun setLastVisitedTime(currentTimeMillis: Long) {
        val lastVisitedTime = getDataStoreLongOnce(KEY_CURRENT_TIME) ?: 0
        setDataStoreLong(KEY_CURRENT_TIME, currentTimeMillis)
        setDataStoreLong(KEY_LAST_VISITED_TIME, lastVisitedTime)
    }

    override suspend fun getLastVisitedTime(): Long {
        return getDataStoreLongOnce(KEY_LAST_VISITED_TIME) ?: 0L
    }

    override suspend fun setPhoneNumber(phoneNumber: String) {
        setDataStoreString(KEY_PHONE_NUMBER, phoneNumber)
    }

    override suspend fun getPhoneNumber(): String {
        return getDataStoreStringOnce(KEY_PHONE_NUMBER) ?: ""
    }

    private fun getDataStoreInt(key: String): Flow<Int?> {
        return datastoreManager.getDataStoreInt(key)
    }

    private fun getDataStoreBoolean(key: String): Flow<Boolean?> {
        return datastoreManager.getDataStoreBoolean(key)
    }

    private fun getDataStoreString(key: String): Flow<String?> {
        return datastoreManager.getDataStoreString(key)
    }

    private fun getDataStoreLong(key: String): Flow<Long?> {
        return datastoreManager.getDataStoreLong(key)
    }

    private suspend fun getDataStoreIntOnce(key: String): Int? {
        return datastoreManager.getDataStoreIntOnce(key)
    }

    private suspend fun getDataStoreBooleanOnce(key: String): Boolean? {
        return datastoreManager.getDataStoreBooleanOnce(key)
    }

    private suspend fun getDataStoreStringOnce(key: String): String? {
        return datastoreManager.getDataStoreStringOnce(key)
    }

    private suspend fun getDataStoreLongOnce(key: String): Long? {
        return datastoreManager.getDataStoreLongOnce(key)
    }

    private suspend fun setDataStoreInt(key: String, value: Int) {
        return datastoreManager.setDataStoreInt(key, value)
    }

    private suspend fun setDataStoreBoolean(key: String, value: Boolean) {
        return datastoreManager.setDataStoreBoolean(key, value)
    }

    private suspend fun setDataStoreString(key: String, value: String) {
        return datastoreManager.setDataStoreString(key, value)
    }

    private suspend fun setDataStoreLong(key: String, value: Long) {
        return datastoreManager.setDataStoreLong(key, value)
    }
}
