package com.palone.core.domain.util

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
