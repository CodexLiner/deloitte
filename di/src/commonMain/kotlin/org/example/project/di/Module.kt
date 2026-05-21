package org.example.project.di

import org.example.project.data.repositoryModule
import org.example.project.domain.domainModule
import org.koin.core.context.startKoin

val appModule = listOf(
    repositoryModule,
    domainModule
)

fun initKoin() {
    runCatching {
        startKoin {
            modules(appModule)
        }
    }
}
