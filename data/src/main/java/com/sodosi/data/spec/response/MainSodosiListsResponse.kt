package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  MainSodosiListsResponse.kt
 *
 *  Created by Minji Jeong on 2022/09/19
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class MainSodosiListsResponse(
    val hasSodosi: Boolean = false,                                 // 소도시 생성 여부
    val bannerList: List<SodosiResponse> = emptyList(),             // 메인 배너 소도시
    val mySodosiList: List<SodosiResponse> = emptyList(),           // 내가 참여중인 소도시
    val interestSodosiList: List<SodosiResponse> = emptyList(),     // 내 관심 소도시
    val hotSodosiList: List<SodosiResponse> = emptyList(),          // 지금 HOT한 소도시
    val newSodosiList: List<SodosiResponse> = emptyList(),          // 새롭게 추천하는 소도시
)
