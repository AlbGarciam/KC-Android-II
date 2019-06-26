package com.soundapp.mobile.todotask.data.repository

import com.soundapp.mobile.todotask.data.repository.local.subtask.SubtaskDao
import com.soundapp.mobile.todotask.data.repository.mapper.SubTaskEntityMapper
import com.soundapp.mobile.todotask.data.repository.mapper.SubTaskMapper
import com.soundapp.mobile.todotask.domain.SubTaskRepository
import com.soundapp.mobile.todotask.domain.model.SubTask

class SubTaskRepositoryImpl(
    private val subTaskDao: SubtaskDao,
    private val subTaskMapper: SubTaskMapper,
    private val subTaskEntityMapper: SubTaskEntityMapper
) : SubTaskRepository {
    override suspend fun getAllSubTaskFor(parentId: Long): List<SubTask> = subTaskDao
        .getSubtasksFor(parentId)
        .map { subTaskMapper.map(it) }

    override suspend fun addTask(task: SubTask) = subTaskDao.insert(subTaskEntityMapper.map(task))

    override suspend fun removeTask(task: SubTask) = subTaskDao.delete(subTaskEntityMapper.map(task))

    override suspend fun updateTask(task: SubTask) = subTaskDao.update(subTaskEntityMapper.map(task))
}