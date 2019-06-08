package com.soundapp.mobile.todotask.di

import androidx.room.Room
import com.soundapp.mobile.todotask.data.repository.FakeTaskRepository
import com.soundapp.mobile.todotask.data.repository.TaskRepositoryImpl
import com.soundapp.mobile.todotask.data.repository.local.TaskDatabase
import com.soundapp.mobile.todotask.data.repository.mapper.TaskEntityMapper
import com.soundapp.mobile.todotask.data.repository.mapper.TaskMapper
import com.soundapp.mobile.todotask.domain.TaskRepository
import com.soundapp.mobile.todotask.presentations.add_task.AddTaskViewModel
import com.soundapp.mobile.todotask.presentations.task_details.DetailTaskViewModel
import com.soundapp.mobile.todotask.presentations.tasks.TasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
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


    single<TaskRepository>() {
        TaskRepositoryImpl(get(), get(), get()) // this will call the factory
    }

    single<TaskRepository>(qualifier = named("fake")) {
        FakeTaskRepository() // this will call the factory
    }

    single {
        TaskMapper()
    }

    single {
        TaskEntityMapper()
    }

    viewModel {
        TasksViewModel(get())
    }

    viewModel {
        AddTaskViewModel(get())
    }

    viewModel {
        DetailTaskViewModel(get())
    }
}