package com.bliss.blissandroidchallenge.di

import com.bliss.blissandroidchallenge.data.usersRepo.RepoRepositoryImpl
import com.bliss.blissandroidchallenge.domain.usersRepo.repository.RepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideRepoRepository(): RepoRepository {
        return RepoRepositoryImpl()
    }
}