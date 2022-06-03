package com.sodosi.data.repository

import com.sodosi.domain.entity.Sodosi
import com.sodosi.domain.repository.SodosiListRepository
import javax.inject.Inject

/**
 *  SodosiListRepositoryImpl.kt
 *
 *  Created by Minji Jeong on 2022/03/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiListRepositoryImpl @Inject constructor() : SodosiListRepository {
    override suspend fun getMainSodosiList(): List<Sodosi> {
        return listOf(
            Sodosi(
                id = 111,
                name = "똥손인 나도\n여기서 찍으면 인생샷",
                userId = 111,
                userProfile = "",
                userName = "시장 관악산악회",
                momentCount = 50,
                userCount = 34,
                status = "",
                icon = "camera",
                thumbnailImage = "",
                dateTime = ""
            ),
            Sodosi(
                id = 222,
                name = "나만 알고 싶은\n공부하기 좋은 카페",
                userId = 222,
                userProfile = "",
                userName = "스위트걸",
                momentCount = 48,
                userCount = 32,
                status = "",
                icon = "cafe",
                thumbnailImage = "",
                dateTime = ""
            ),
            Sodosi(
                id = 333,
                name = "직접 가보면\n을씨년스러운 장소",
                userId = 333,
                userProfile = "",
                userName = "SpooKiz",
                momentCount = 35,
                userCount = 20,
                status = "",
                icon = "danger",
                thumbnailImage = "",
                dateTime = ""
            ),
            Sodosi(
                id = 444,
                name = "댕댕이를 위한\n베스트 산책 코스",
                userId = 444,
                userProfile = "",
                userName = "강형욱",
                momentCount = 12,
                userCount = 30,
                status = "",
                icon = "dog",
                thumbnailImage = "",
                dateTime = ""
            )
        )
    }

    override suspend fun getCommentedSodosiList(): List<Sodosi> {
        return listOf(
            Sodosi(
                id = 1,
                name = "힙에 취하고 싶을 때",
                userId = 1,
                userProfile = "중구시립도서관",
                userName = "",
                momentCount = 30,
                userCount = 10,
                status = "",
                icon = "\uD83C\uDFB1",
                thumbnailImage = "",
                dateTime = ""
            ),
            Sodosi(
                id = 2,
                name = "동국대 새내기들 필수 코스",
                userId = 2,
                userProfile = "",
                userName = "뭉치",
                momentCount = 24,
                userCount = 12,
                status = "",
                icon = "\uD83D\uDC25",
                thumbnailImage = "",
                dateTime = ""
            ),
            Sodosi(
                id = 3,
                name = "비건들 모여라",
                userId = 3,
                userProfile = "",
                userName = "민지짱",
                momentCount = 15,
                userCount = 3,
                status = "",
                icon = "\uD83C\uDF50",
                thumbnailImage = "",
                dateTime = ""
            ),
        )
    }

    override suspend fun getBookmarkSodosiList(): List<Sodosi> {
        return listOf(
            Sodosi(
                id = 1,
                name = "힙에 취하고 싶을 때",
                userId = 1,
                userProfile = "중구시립도서관",
                userName = "",
                momentCount = 30,
                userCount = 10,
                status = "",
                icon = "\uD83C\uDFB1",
                thumbnailImage = "",
                dateTime = ""
            ),
            Sodosi(
                id = 2,
                name = "동국대 새내기들 필수 코스",
                userId = 2,
                userProfile = "",
                userName = "뭉치",
                momentCount = 24,
                userCount = 12,
                status = "",
                icon = "\uD83D\uDC25",
                thumbnailImage = "",
                dateTime = ""
            ),
            Sodosi(
                id = 3,
                name = "비건들 모여라",
                userId = 3,
                userProfile = "",
                userName = "민지짱",
                momentCount = 15,
                userCount = 3,
                status = "",
                icon = "\uD83C\uDF50",
                thumbnailImage = "",
                dateTime = ""
            ),
        )
    }

    override suspend fun getHotSodosiList(): List<Sodosi> {
        return listOf(
            Sodosi(
                id = 1,
                name = "힙에 취하고 싶을 때",
                userId = 1,
                userProfile = "중구시립도서관",
                userName = "",
                momentCount = 30,
                userCount = 10,
                status = "",
                icon = "\uD83C\uDFB1",
                thumbnailImage = "",
                dateTime = "",
                bookmarked = true,
            ),
            Sodosi(
                id = 2,
                name = "동국대 새내기들 필수 코스",
                userId = 2,
                userProfile = "",
                userName = "뭉치",
                momentCount = 24,
                userCount = 12,
                status = "",
                icon = "\uD83D\uDC25",
                thumbnailImage = "",
                dateTime = "",
                bookmarked = true,
            ),
            Sodosi(
                id = 3,
                name = "비건들 모여라",
                userId = 3,
                userProfile = "",
                userName = "민지짱",
                momentCount = 15,
                userCount = 3,
                status = "",
                icon = "\uD83C\uDF50",
                thumbnailImage = "",
                dateTime = ""
            ),
        )
    }

    override suspend fun getNewSodosiList(): List<Sodosi> {
        return listOf(
            Sodosi(
                id = 4,
                name = "보드게임 마니아들의 성지",
                userId = 1,
                userProfile = "이펙티브 소도시",
                userName = "",
                momentCount = 3,
                userCount = 1,
                status = "",
                icon = "\uD83C\uDFB2",
                thumbnailImage = "",
                dateTime = ""
            ),
            Sodosi(
                id = 5,
                name = "빵순이들의 빵지순례",
                userId = 5,
                userProfile = "",
                userName = "빵순이",
                momentCount = 13,
                userCount = 5,
                status = "",
                icon = "\uD83E\uDD50",
                thumbnailImage = "",
                dateTime = ""
            ),
            Sodosi(
                id = 3,
                name = "비건들 모여라",
                userId = 3,
                userProfile = "",
                userName = "민지짱",
                momentCount = 15,
                userCount = 3,
                status = "",
                icon = "\uD83C\uDF50",
                thumbnailImage = "",
                dateTime = ""
            ),
        )
    }
}