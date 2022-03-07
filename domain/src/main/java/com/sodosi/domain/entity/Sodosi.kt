package com.sodosi.domain.entity

/**
 *  Sodosi.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class Sodosi(
    val id: Long,               // PK
    val name: String,           // 소도시명
    val userId: Long,           // 소도시 생성한 유저 아이디
    val userName: String,       // 소도시 생성한 유저 닉네임
    val momentCount: String,    // 소도시에 연결된 순간 수
    val status: String,         // 상태값(Active/Reported)
    val icon: String,           // 소도시 아이콘
    val dateTime: String,       // 최종 저장 시간
)
