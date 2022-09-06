package com.sodosi.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(private val dataStore: DataStore<Preferences>) {

    suspend fun setDataStoreInt(key: String, value: Int) {
        setDataStore(intPreferencesKey(key), value)
    }

    suspend fun setDataStoreBoolean(key: String, value: Boolean) {
        setDataStore(booleanPreferencesKey(key), value)
    }

    suspend fun setDataStoreString(key: String, value: String) {
        setDataStore(stringPreferencesKey(key), value)
    }

    suspend fun setDataStoreLong(key: String, value: Long) {
        setDataStore(longPreferencesKey(key), value)
    }

    private suspend fun <T> setDataStore(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getDataStoreInt(key: String): Flow<Int?> {
        return getDataStore(intPreferencesKey(key))
    }

    fun getDataStoreBoolean(key: String): Flow<Boolean?> {
        return getDataStore(booleanPreferencesKey(key))

    }

    fun getDataStoreString(key: String): Flow<String?> {
        return getDataStore(stringPreferencesKey(key))
    }

    fun getDataStoreLong(key: String): Flow<Long?> {
        return getDataStore(longPreferencesKey(key))
    }

    suspend fun getDataStoreIntOnce(key: String): Int? {
        return getDataStoreInt(key).first()
    }

    suspend fun getDataStoreBooleanOnce(key: String): Boolean? {
        return getDataStoreBoolean(key).first()

    }

    suspend fun getDataStoreStringOnce(key: String): String? {
        return getDataStoreString(key).first()
    }

    suspend fun getDataStoreLongOnce(key: String): Long? {
        return getDataStoreLong(key).first()
    }

    private fun <T> getDataStore(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key]
            }
    }

    private suspend fun <T> removeDataStore(key: Preferences.Key<T>) {
        dataStore.edit {
            if (it.contains(key)) {
                it.remove(key)
            }
        }
    }

    suspend fun removeDataStoreString(key: String) {
        removeDataStore(stringPreferencesKey(key))
    }
}
