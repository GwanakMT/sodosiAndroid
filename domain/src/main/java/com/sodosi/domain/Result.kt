package com.sodosi.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 *  Result.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()
    class Error(val exception: Exception) : Result<Nothing>()
}

fun <T> CoroutineScope.launchWithNetworkResult(
    result: Result<T>,
    suspendOnSuccess: suspend CoroutineScope.(T) -> Unit,
    suspendOnError: suspend CoroutineScope.(Exception) -> Unit,
) {
    when(result) {
        is Result.Success -> launch {
            suspendOnSuccess(result.data)
        }
        is Result.Error -> launch {
            suspendOnError(result.exception)
        }
    }
}
