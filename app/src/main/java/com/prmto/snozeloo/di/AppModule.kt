package com.prmto.snozeloo.di

import android.app.AlarmManager
import android.content.Context
import androidx.room.Room
import com.prmto.snozeloo.data.alarm_manager.AndroidAlarmScheduler
import com.prmto.snozeloo.data.local.SnoozelooDatabase
import com.prmto.snozeloo.data.local.dao.AlarmDao
import com.prmto.snozeloo.data.repository.AlarmRepositoryImpl
import com.prmto.snozeloo.domain.alarm_manager.AlarmScheduler
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
        snoozelooDatabase: SnoozelooDatabase,
        alarmScheduler: AlarmScheduler
    ): AlarmRepository {
        return AlarmRepositoryImpl(snoozelooDatabase.alarmDao, alarmScheduler)
    }

    @Provides
    @Singleton
    fun provideAlarmManager(
        @ApplicationContext context: Context
    ): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Provides
    @Singleton
    fun provideAlarmScheduler(
        @ApplicationContext context: Context,
        alarmManager: AlarmManager
    ): AlarmScheduler {
        return AndroidAlarmScheduler(context, alarmManager)
    }
}