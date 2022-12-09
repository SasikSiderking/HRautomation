package com.example.hrautomation.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.tryLaunch(
    contextPiece: CoroutineContext = EmptyCoroutineContext,
    doOnLaunch: suspend CoroutineScope.() -> Unit,
    doOnError: (Throwable) -> Unit = {},
    doOnComplete: () -> Unit = {},
): Job {
    val wrapperJob = SupervisorJob(coroutineContext.job)
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, error -> doOnError(error) }

    return launch(contextPiece + wrapperJob + coroutineExceptionHandler) {
        doOnLaunch()
    }.apply {
        invokeOnCompletion {
            wrapperJob.complete()
            doOnComplete()
        }
    }
}

inline fun <T> runSuspendCatching(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.failure(e)
    }
}