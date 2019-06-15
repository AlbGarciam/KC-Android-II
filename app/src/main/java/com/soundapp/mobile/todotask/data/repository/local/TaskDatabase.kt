package com.soundapp.mobile.todotask.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soundapp.mobile.todotask.data.repository.local.subtask.SubtaskDao
import com.soundapp.mobile.todotask.data.repository.local.subtask.SubtaskEntity
import com.soundapp.mobile.todotask.data.repository.local.task.TaskDao
import com.soundapp.mobile.todotask.data.repository.local.task.TaskEntity

@TypeConverters(TypeConverter::class)
@Database(entities = [TaskEntity::class, SubtaskEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    abstract fun getSubtaskDao(): SubtaskDao

}