package com.sodosi.domain.repository.datastore

import kotlinx.coroutines.flow.Flow

/**
 *  DataStoreRepository.kt
 *
 *  Created by Minji Jeong on 2022/05/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface DataStoreRepository {
    fun getDataStoreInt(key: String): Flow<Int?>
    fun getDataStoreBoolean(key: String): Flow<Boolean?>
    fun getDataStoreString(key: String): Flow<String?>

    suspend fun getDataStoreIntOnce(key: String): Int?
    suspend fun getDataStoreBooleanOnce(key: String): Boolean?
    suspend fun getDataStoreStringOnce(key: String): String?

    suspend fun setDataStoreInt(key: String, value: Int)
    suspend fun setDataStoreBoolean(key: String, value: Boolean)
    suspend fun setDataStoreString(key: String, value: String)
}