package com.palone.auth.data

import com.palone.auth.domain.AuthRepository
import com.palone.core.data.networking.post
import com.palone.core.domain.util.DataError
import com.palone.core.domain.util.EmptyResult
import io.ktor.client.HttpClient


class AuthRepositoryImpl(private val httpClient: HttpClient) : AuthRepository {
    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(email, password)
        )
    }
}