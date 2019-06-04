package com.soundapp.mobile.todotask.domain

import com.soundapp.mobile.todotask.domain.model.Task

interface TaskRepository {

    suspend fun getAll(): List<Task>

    suspend fun getTaskById(id: Long): Task

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun removeTask(task: Task)

}