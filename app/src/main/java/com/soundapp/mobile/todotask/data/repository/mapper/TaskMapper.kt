package com.soundapp.mobile.todotask.data.repository.mapper

import com.soundapp.mobile.todotask.data.repository.local.task.TaskEntity
import com.soundapp.mobile.todotask.domain.model.Task

class TaskMapper: Mapper<TaskEntity, Task> {
    override fun map(input: TaskEntity): Task =
        with(input) {
            Task(id,
                content,
                createdAt,
                isHighPriority,
                isFinished
            )
        }
}