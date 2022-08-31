package com.sodosi.data.mapper

import com.sodosi.data.spec.response.UserPrivacyPolicyContentsResponse
import javax.inject.Inject

/**
 *  UserPrivacyPolicyMapper.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class UserPrivacyPolicyMapper @Inject constructor() {
    fun mapToEntity(privacyPolicyList: List<UserPrivacyPolicyContentsResponse.PrivacyPolicy>): List<String> {
        return privacyPolicyList.map {
            it.toString()
        }
    }
}
