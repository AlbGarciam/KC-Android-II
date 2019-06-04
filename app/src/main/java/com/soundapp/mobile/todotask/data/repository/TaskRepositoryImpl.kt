package com.soundapp.mobile.todotask.data.repository

import com.soundapp.mobile.todotask.data.repository.local.TaskDao
import com.soundapp.mobile.todotask.data.repository.mapper.TaskEntityMapper
import com.soundapp.mobile.todotask.data.repository.mapper.TaskMapper
import com.soundapp.mobile.todotask.domain.TaskRepository
import com.soundapp.mobile.todotask.domain.model.Task

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val taskMapper: TaskMapper,
    private val taskEntityMapper: TaskEntityMapper
) : TaskRepository {
    override suspend fun getAll(): List<Task>  = taskDao.getAll().map { taskMapper.map(it) }

    override suspend fun getTaskById(id: Long): Task = taskMapper.map(taskDao.getTaskById(id))

    override suspend fun addTask(task: Task) = taskDao.insert(taskEntityMapper.map(task))

    override suspend fun updateTask(task: Task) = taskDao.update(taskEntityMapper.map(task))

    override suspend fun removeTask(task: Task) = taskDao.delete(taskEntityMapper.map(task))
}