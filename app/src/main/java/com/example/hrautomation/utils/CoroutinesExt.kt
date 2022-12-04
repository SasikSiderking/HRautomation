package com.example.hrautomation.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.tryLaunch(
    coroutineContext: CoroutineContext,
    doOnLaunch: suspend () -> Unit,
    doOnError: (Throwable) -> Unit = {},
    doOnComplete: () -> Unit = {},
): Job {
    val wrapperJob = SupervisorJob(coroutineContext.job)
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, error -> doOnError(error) }

    return launch(coroutineContext + wrapperJob + coroutineExceptionHandler) {
        doOnLaunch()
    }.apply {
        invokeOnCompletion {
            wrapperJob.complete()
            doOnComplete()
        }
    }
}

fun <T> runSuspendCatching(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.failure(e)
    }
}