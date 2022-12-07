package com.sodosi.data.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.sodosi.data.mapper.MomentMapper
import com.sodosi.data.network.api.SodosiApi
import com.sodosi.data.spec.request.ReportRequest
import com.sodosi.domain.Result
import com.sodosi.domain.entity.Moment
import com.sodosi.domain.entity.Place
import com.sodosi.domain.repository.MomentRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import org.json.JSONObject
import javax.inject.Inject

/**
 *  MomentRepositoryImpl.kt
 *
 *  Created by Minji Jeong on 2022/10/31
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MomentRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sodosiApi: SodosiApi,
    private val momentMapper: MomentMapper,
) : MomentRepository {
    override suspend fun postMoment(
        sodosiId: Long,
        latitude: Double,
        longitude: Double,
        roadAddress: String,
        jibunAddress: String,
        addressDetail: String,
        contents: String,
        imageList: List<String>,
    ): Result<Moment> {

        val jsonString = JSONObject()
            .put("latitude", latitude)
            .put("longitude", longitude)
            .put("roadAddress", roadAddress)
            .put("jibunAddress", jibunAddress)
            .put("addressDetail", addressDetail)
            .put("contents", contents)
            .toString()

        val jsonBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonString)

        val multiParts = imageList.map {
            Uri.parse(it).asMultipart()
        }

        return try {
            val result = sodosiApi.postMoment(sodosiId, jsonBody, multiParts)
            Result.Success(momentMapper.mapToEntity(result.data))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPlaceListBySodosi(sodosiId: Long): Result<List<Place>> {
        return try {
            val result = sodosiApi.getPlaceListBySodosi(sodosiId)
            Result.Success(result.data.map { momentMapper.mapToEntity(it) })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMyMomentList(): Result<List<Moment>> {
        return try {
            val result = sodosiApi.getMyMomentList()
            Result.Success(result.data.map { momentMapper.mapToEntity(it) })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun reportMoment(sodosiId: Long, momentId: Long, reason: String): Result<Unit> {
        return try {
            val request = ReportRequest(moment_id = momentId, reason = reason)
            sodosiApi.repostMoment(sodosiId, request)

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun Uri.asMultipart(): MultipartBody.Part? {
        val contentResolver = context.contentResolver
        return contentResolver.query(this, null, null, null, null)?.let {
            if (it.moveToNext()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultipart)?.toMediaType()
                    }

                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                    }
                }
                it.close()
                MultipartBody.Part.createFormData("imageList[]", displayName, requestBody)
            } else {
                it.close()
                null
            }
        }
    }
}
