package com.sodosi.domain.repository.datastore

import kotlinx.coroutines.flow.Flow

/**
 *  DataStoreRepository.kt
 *
 *  Created by Minji Jeong on 2022/05/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface DataStoreRepository {
    suspend fun setToken(accessToken: String)
    suspend fun getToken(): String

    suspend fun setLastVisitedTime(currentTimeMillis: Long)
    suspend fun getLastVisitedTime(): Long

    suspend fun setPhoneNumber(phoneNumber: String)
    suspend fun getPhoneNumber(): String

    suspend fun setSuggestBannerHidden()
    suspend fun getSuggestBannerHidden(): Boolean

//    fun getDataStoreInt(key: String): Flow<Int?>
//    fun getDataStoreBoolean(key: String): Flow<Boolean?>
//    fun getDataStoreString(key: String): Flow<String?>
//    fun getDataStoreLong(key: String): Flow<Long?>
//
//    suspend fun getDataStoreIntOnce(key: String): Int?
//    suspend fun getDataStoreBooleanOnce(key: String): Boolean?
//    suspend fun getDataStoreStringOnce(key: String): String?
//    suspend fun getDataStoreLongOnce(key: String): Long?
//
//    suspend fun setDataStoreInt(key: String, value: Int)
//    suspend fun setDataStoreBoolean(key: String, value: Boolean)
//    suspend fun setDataStoreString(key: String, value: String)
//    suspend fun setDataStoreLong(key: String, value: Long)
}