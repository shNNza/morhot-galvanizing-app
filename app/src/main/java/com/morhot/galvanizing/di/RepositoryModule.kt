package com.morhot.galvanizing.di

import com.morhot.galvanizing.domain.repository.JobCardRepository
import com.morhot.galvanizing.domain.repository.impl.JobCardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindJobCardRepository(
        jobCardRepositoryImpl: JobCardRepositoryImpl
    ): JobCardRepository
}