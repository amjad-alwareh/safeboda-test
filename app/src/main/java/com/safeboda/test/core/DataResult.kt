package com.safeboda.test.core

data class DataResult<out T>(val status: Status, val data: T?, val message: String?) {

    var throwable: Throwable? = null

    constructor(status: Status, data: T?, throwable: Throwable?) : this(status, data, throwable?.message) {
        this.throwable = throwable
    }

    companion object {
        fun <T> success(data: T?): DataResult<T> {
            return DataResult(Status.SUCCESS, data, message = null)
        }

        fun <T> error(msg: String, data: T? = null): DataResult<T> {
            return DataResult(Status.ERROR, data, msg)
        }

        fun <T> error(throwable: Throwable?, data: T? = null): DataResult<T> {
            return DataResult(Status.ERROR, data, throwable = throwable)
        }

        fun <T> loading(): DataResult<T> {
            return DataResult(Status.LOADING, null, message = null)
        }

    }
}

enum class Status {
    LOADING,
    ERROR,
    SUCCESS
}