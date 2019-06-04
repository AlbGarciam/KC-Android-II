package com.soundapp.mobile.todotask.di

import androidx.room.Room
import com.soundapp.mobile.todotask.data.repository.TaskRepositoryImpl
import com.soundapp.mobile.todotask.data.repository.local.TaskDatabase
import com.soundapp.mobile.todotask.data.repository.mapper.TaskEntityMapper
import com.soundapp.mobile.todotask.data.repository.mapper.TaskMapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module{
    single {
        Room.databaseBuilder(
            androidContext(),
            TaskDatabase::class.java,
            "tasks.db"
        ).build()
    }

    factory { // We want that DAO is updated every time we access to it
        get<TaskDatabase>().getTaskDao()
    }


    single {
        TaskRepositoryImpl(get(), get(), get()) // this will call the factory
    }

    single {
        TaskMapper()
    }

    single {
        TaskEntityMapper()
    }
}