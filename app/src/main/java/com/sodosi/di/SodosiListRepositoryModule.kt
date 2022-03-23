package com.sodosi.di

import com.sodosi.data.repository.SodosiListRepositoryImpl
import com.sodosi.domain.repository.SodosiListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 *  SodosiListRepositoryModule.kt
 *
 *  Created by Minji Jeong on 2022/03/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@InstallIn(ViewModelComponent::class)
@Module
abstract class SodosiListRepositoryModule {
    @Binds
    abstract fun provideSodosiListRepository(impl: SodosiListRepositoryImpl): SodosiListRepository
}
