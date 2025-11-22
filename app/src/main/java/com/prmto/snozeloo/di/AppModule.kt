package com.prmto.snozeloo.di

import android.content.Context
import androidx.room.Room
import com.prmto.snozeloo.data.local.SnoozelooDatabase
import com.prmto.snozeloo.data.local.dao.AlarmDao
import com.prmto.snozeloo.data.repository.AlarmRepositoryImpl
import com.prmto.snozeloo.domain.repository.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): SnoozelooDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = SnoozelooDatabase::class.java,
            name = "snozeloo.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(
        snoozelooDatabase: SnoozelooDatabase
    ): AlarmRepository {
        return AlarmRepositoryImpl(snoozelooDatabase.alarmDao)
    }
}