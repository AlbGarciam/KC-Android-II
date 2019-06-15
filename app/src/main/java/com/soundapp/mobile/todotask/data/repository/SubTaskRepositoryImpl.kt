package com.soundapp.mobile.todotask.data.repository

import com.soundapp.mobile.todotask.data.repository.local.subtask.SubtaskDao
import com.soundapp.mobile.todotask.data.repository.local.task.TaskDao
import com.soundapp.mobile.todotask.data.repository.mapper.SubTaskEntityMapper
import com.soundapp.mobile.todotask.data.repository.mapper.SubTaskMapper
import com.soundapp.mobile.todotask.data.repository.mapper.TaskEntityMapper
import com.soundapp.mobile.todotask.data.repository.mapper.TaskMapper
import com.soundapp.mobile.todotask.domain.SubTaskRepository
import com.soundapp.mobile.todotask.domain.TaskRepository
import com.soundapp.mobile.todotask.domain.model.SubTask
import com.soundapp.mobile.todotask.domain.model.Task

class SubTaskRepositoryImpl(
    private val taskDao: SubtaskDao,
    private val taskMapper: SubTaskMapper,
    private val taskEntityMapper: SubTaskEntityMapper
) : SubTaskRepository {
    override suspend fun getAllSubTaskFor(parentId: Long): List<SubTask> = taskDao
        .getSubtasksFor(parentId)
        .map { taskMapper.map(it) }

    override suspend fun addTask(task: SubTask) = taskDao.insert(taskEntityMapper.map(task))

    override suspend fun removeTask(task: SubTask) = taskDao.delete(taskEntityMapper.map(task))

    override suspend fun updateTask(task: SubTask) = taskDao.update(taskEntityMapper.map(task))
}