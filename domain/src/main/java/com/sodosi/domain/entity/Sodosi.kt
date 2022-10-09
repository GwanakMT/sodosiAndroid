package com.sodosi.domain.entity

/**
 *  Sodosi.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class Sodosi(
    val id: Long,                   // 소도시 아이디
    val name: String,               // 소도시 이름
    val momentCount: Int,           // 해당 소도시에 기록된 순간 수
    val userCount: Int,             // 해당 소도시에 참여한 사람 수
    val icon: String,               // 아이콘(이모지)
    val momentImage: String?,       // 대표 이미지
    val isMarked: Boolean,          // 북마크 여부
    val isMine: Boolean,            // 본인의 소도시인지 여부
)

enum class SodosiCategory {
    MAIN_BANNER,        // 메인 배너
    COMMENTED,          // 내가 참여한 소도시
    MARKED,             // 내 관심 소도시
    HOT,                // 지금 HOT한 소도시
    NEW,                // 새롭게 추천하는 소도시
}
