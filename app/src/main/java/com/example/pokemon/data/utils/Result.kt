package com.example.pokemon.data.utils


sealed class Result<T>(
    val data: T? = null,
    val code: Int? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(message: String, code: Int? = null) : Result<T>(code = code, message = message)
    class Loading<T> : Result<T>()
}
