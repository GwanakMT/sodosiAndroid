package com.sodosi.domain.entity

/**
 *  Comment.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class Comment(
    val id: Long,           // PK
    val momentId: Long,     // 코멘트가 달린 순간 아이디
    val userId: Long,       // 코멘트 단 유저 아이디
    val userName: String,   // 코멘트 단 유저 이름
    val content: String,    // 내용
    val status: String,     // 상태값(Active/Reported)
)
