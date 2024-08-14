package com.palone.core.domain.util

sealed interface DataError:Error {
    enum class Network:DataError {
        UNAUTHORISED,
        REQUEST_TIMEOUT,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local:DataError {
        DISK_FULL
    }
}