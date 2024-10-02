package com.palone.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}