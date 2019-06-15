package com.soundapp.mobile.todotask.data.repository.local.task

import androidx.room.*

// We have to declare the DAO

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks") // tasks is the name of the table
    suspend fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity

    @Insert
    suspend fun insert(taskEntity: TaskEntity)

    @Update
    suspend fun update(taskEntity: TaskEntity)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)

}