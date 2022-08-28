package com.haziqkamel.di

import com.haziqkamel.repository.HeroRepository
import com.haziqkamel.repository.HeroRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    //Inject HeroRepositoryImpl into AllHeroes
    // Singleton: Single instance across the project

    single<HeroRepository> {
        HeroRepositoryImpl()
    }
}