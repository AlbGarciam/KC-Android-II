package com.soundapp.mobile.todotask.data.repository.local

import androidx.room.Database

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase {

    abstract fun getTaskDao(): TaskDao

}