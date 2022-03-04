package com.sodosi.data.spec

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val userId: Int = 0,
    val id: Int = 0,
    val title: String = "",
    val comment: String = "default comment"
)
