package com.booleanull.core.functional

sealed class Task<out F, out S> {

    data class Success<out S>(val success: S) : Task<Nothing, S>()
    data class Failure<out F>(val failure: F) : Task<F, Nothing>()

    @Suppress("UNCHECKED_CAST")
    inline fun <T> fold(succeeded: (S) -> T, failed: (F) -> T = { this as T }): T =
        when (this) {
            is Failure -> failed(failure)
            is Success -> succeeded(success)
        }
}

inline fun <F, S1, S2> Task<F, S1>.flatMap(succeeded: (S1) -> Task<F, S2>): Task<F, S2> =
    fold(succeeded, { this as Task.Failure })

inline fun <F, S1, S2> Task<F, S1>.map(f: (S1) -> S2): Task<F, S2> =
    flatMap { Task.Success(f(it)) }