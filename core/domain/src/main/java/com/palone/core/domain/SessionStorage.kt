package com.palone.core.domain

import com.palone.core.domain.util.AuthInfo

interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}