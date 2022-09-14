package com.sodosi.di

import com.sodosi.data.repository.SodosiRepositoryImpl
import com.sodosi.data.repository.UserRepositoryImpl
import com.sodosi.data.repository.datastore.DataStoreRepositoryImpl
import com.sodosi.domain.repository.SodosiRepository
import com.sodosi.domain.repository.UserRepository
import com.sodosi.domain.repository.datastore.DataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  RepositoryModule.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindSodosiRepository(impl: SodosiRepositoryImpl): SodosiRepository
}
