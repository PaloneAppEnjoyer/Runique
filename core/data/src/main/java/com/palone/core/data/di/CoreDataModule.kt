package com.palone.core.data.di

import com.palone.core.data.auth.EncryptedSessionStorage
import com.palone.core.data.networking.HttpClientFactory
import com.palone.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    single {
        HttpClientFactory(get()).build()
    }

}