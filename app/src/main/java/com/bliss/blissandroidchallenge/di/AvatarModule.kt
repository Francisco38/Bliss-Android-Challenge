package com.bliss.blissandroidchallenge.di

import android.content.Context
import androidx.room.Room
import com.bliss.blissandroidchallenge.data.usersAvatar.AvatarRepositoryImpl
import com.bliss.blissandroidchallenge.data.usersAvatar.local.dao.AvatarDao
import com.bliss.blissandroidchallenge.data.usersAvatar.local.database.AvatarDb
import com.bliss.blissandroidchallenge.domain.usersAvatar.model.AVATAR_TABLE
import com.bliss.blissandroidchallenge.domain.usersAvatar.repository.AvatarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AvatarModule {
    @Provides
    @Singleton
    fun provideAvatarDatabase(@ApplicationContext appContext: Context): AvatarDb {
        return Room.databaseBuilder(
            appContext,
            AvatarDb::class.java,
            AVATAR_TABLE
        ).build()
    }

    @Provides
    @Singleton
    fun provideAvatarDao(avatarDb: AvatarDb): AvatarDao {
        return avatarDb.avatarDao()
    }

    @Provides
    @Singleton
    fun provideAvatarRepository(avatarDao: AvatarDao): AvatarRepository {
        return AvatarRepositoryImpl(avatarDao)
    }
}