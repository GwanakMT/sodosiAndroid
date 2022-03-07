package com.sodosi.domain.entity

/**
 *  Moment.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class Moment(
    val id: Long,               // PK
    val content: String,        // 내용
    val userId: Long,           // 모멘트 작성한 유저 ID
    val userName: String,       // 모멘트 작성한 유저 이름
    val latitude: Double,       // 위도
    val longitude: Double,      // 경도
    val jibunAddress: String,   // 지번주소
    val roadAddress: String,    // 도로명주소
    val status: String,         // 상태값(Active/Reported)
)
