package com.soundapp.mobile.todotask.data.repository.mapper

import com.soundapp.mobile.todotask.data.repository.local.task.TaskEntity
import com.soundapp.mobile.todotask.domain.model.Task

class TaskEntityMapper: Mapper<Task, TaskEntity> {
    override fun map(input: Task): TaskEntity =
        with(input) {
            TaskEntity(
                id,
                content,
                createdAt,
                isHighPriority,
                isFinished
            )
        }
}