package com.palone.auth.domain

import com.palone.core.domain.util.DataError
import com.palone.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}