package com.sodosi.di

import com.sodosi.data.repository.TestRepositoryImpl
import com.sodosi.domain.repository.TestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 *  TestRepositoryModule.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@InstallIn(ViewModelComponent::class)
@Module
abstract class TestRepositoryModule {
    @Binds
    abstract fun provideTestRepository(impl: TestRepositoryImpl): TestRepository
}
