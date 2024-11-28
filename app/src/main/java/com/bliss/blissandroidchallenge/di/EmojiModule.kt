package com.bliss.blissandroidchallenge.di

import android.content.Context
import androidx.room.Room
import com.bliss.blissandroidchallenge.data.emojis.EmojiRepositoryImpl
import com.bliss.blissandroidchallenge.data.emojis.local.dao.EmojiDao
import com.bliss.blissandroidchallenge.data.emojis.local.database.EmojiDb
import com.bliss.blissandroidchallenge.domain.emojis.model.EMOJI_TABLE
import com.bliss.blissandroidchallenge.domain.emojis.repository.EmojiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmojiModule {
    @Provides
    @Singleton
    fun provideEmojiDatabase(@ApplicationContext appContext: Context): EmojiDb {
        return Room.databaseBuilder(
            appContext,
            EmojiDb::class.java,
            EMOJI_TABLE
        ).build()
    }

    @Provides
    @Singleton
    fun provideEmojiDao(emojiDb: EmojiDb): EmojiDao {
        return emojiDb.emojiDao()
    }

    @Provides
    @Singleton
    fun provideEmojiRepository(emojiDao: EmojiDao): EmojiRepository {
        return EmojiRepositoryImpl(emojiDao)
    }
}