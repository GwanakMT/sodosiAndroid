package com.sodosi.domain.usecase.user

import com.sodosi.domain.Result
import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  GetUserPrivacyPolicyContents.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetUserPrivacyPolicyContents @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<String>> {
        return userRepository.getUserPrivacyPolicyContents()
    }
}
