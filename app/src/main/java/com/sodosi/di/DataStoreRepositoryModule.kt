package com.sodosi.di

import com.sodosi.data.repository.datastore.DataStoreRepositoryImpl
import com.sodosi.domain.repository.datastore.DataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  DataStoreRepositoryModule.kt
 *
 *  Created by Minji Jeong on 2022/05/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@InstallIn(SingletonComponent::class)
@Module
abstract class DataStoreRepositoryModule {
    @Singleton
    @Binds
    abstract fun provideDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository
}
