package org.example.project.data

import org.example.project.data.repository.ProductRepositoryImpl
import org.example.project.domain.repository.RewardsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<RewardsRepository> { ProductRepositoryImpl() }
}
