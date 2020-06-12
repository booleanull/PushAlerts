package com.booleanull.core.functional

sealed class Either<out F, out S> {

    data class Success<out S>(val success: S) : Either<Nothing, S>()
    data class Failure<out F>(val failure: F) : Either<F, Nothing>()

    inline fun <T> fold(succeeded: (S) -> T, failed: (F) -> T = { this as T }): T =
        when (this) {
            is Failure -> failed(failure)
            is Success -> succeeded(success)
        }
}

inline fun <F, S1, S2> Either<F, S1>.flatMap(succeeded: (S1) -> Either<F, S2>): Either<F, S2> =
    fold(succeeded, { this as Either.Failure })

inline fun <F, S1, S2> Either<F, S1>.map(f: (S1) -> S2): Either<F, S2> =
    flatMap { Either.Success(f(it)) }