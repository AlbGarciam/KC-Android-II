package com.soundapp.mobile.todotask.data.repository.local.subtask

import androidx.room.*

@Dao
interface SubtaskDao {

    @Query("SELECT * FROM subtasks WHERE parentId = :parentId") // tasks is the name of the table
    suspend fun getSubtasksFor(parentId: Long): List<SubtaskEntity>

    @Insert
    suspend fun insert(taskEntity: SubtaskEntity)

    @Delete
    suspend fun delete(taskEntity: SubtaskEntity)

}