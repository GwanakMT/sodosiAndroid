package com.sodosi.domain.entity

/**
 *  Sodosi.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class Sodosi(
    val id: Long,                       // PK
    val name: String,                   // 소도시명
    val userId: Long,                   // 소도시 생성한 유저 아이디
    val userProfile: String,            // 소도시 생성한 유저 프로필 이미지
    val userName: String,               // 소도시 생성한 유저 닉네임
    val momentCount: Int,               // 소도시에 연결된 순간 수
    val userCount: Int,                 // 소시민 수
    val status: String,                 // 상태값(Active/Reported)
    val bookmarked: Boolean = false,    // 북마크 여부
    val icon: String,                   // 소도시 아이콘
    val thumbnailImage: String,         // 소도시 썸네일 이미지
    val dateTime: String,               // 최종 저장 시간
)
