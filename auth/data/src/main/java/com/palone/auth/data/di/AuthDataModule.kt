package com.palone.auth.data.di

import com.palone.auth.data.EmailPatternValidator
import com.palone.auth.domain.PatternValidator
import com.palone.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}