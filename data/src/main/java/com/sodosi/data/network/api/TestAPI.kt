package com.sodosi.data.network.api

import com.sodosi.data.spec.Album
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  TestApi.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface TestAPI {
    @GET("/albums/{id}")
    suspend fun getAlbum(@Path("id") id: Int): Album
}
