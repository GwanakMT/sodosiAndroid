package com.sodosi.model

/**
 *  SodosiModel.kt
 *
 *  Created by Minji Jeong on 2022/09/15
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class SodosiModel(
    val id: Long,                   // 소도시 아이디
    val name: String,               // 소도시 이름
    val momentCount: Int,           // 해당 소도시에 기록된 순간 수
    val userCount: Int,             // 해당 소도시에 참여한 사람 수
    val icon: String,               // 아이콘(이모지)
    val momentImage: String?,       // 대표 이미지
    val isMarked: Boolean,            // 북마크 여부
)
