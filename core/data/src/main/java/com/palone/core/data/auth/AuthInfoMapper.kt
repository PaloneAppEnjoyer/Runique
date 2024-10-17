package com.palone.core.data.auth

import com.palone.core.domain.util.AuthInfo

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(accessToken, refreshToken, userId)
}

fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(accessToken, refreshToken, userId)
}