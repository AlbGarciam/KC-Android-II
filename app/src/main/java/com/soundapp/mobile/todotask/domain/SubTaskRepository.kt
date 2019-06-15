package com.soundapp.mobile.todotask.domain

import com.soundapp.mobile.todotask.domain.model.SubTask

interface SubTaskRepository {

    suspend fun getAllSubTaskFor(parentId: Long): List<SubTask>

    suspend fun addTask(task: SubTask)

    suspend fun removeTask(task: SubTask)

}