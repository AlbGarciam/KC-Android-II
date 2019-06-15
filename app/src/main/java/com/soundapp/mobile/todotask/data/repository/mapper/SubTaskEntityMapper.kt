package com.soundapp.mobile.todotask.data.repository.mapper

import com.soundapp.mobile.todotask.data.repository.local.subtask.SubtaskEntity
import com.soundapp.mobile.todotask.data.repository.local.task.TaskEntity
import com.soundapp.mobile.todotask.domain.model.SubTask
import com.soundapp.mobile.todotask.domain.model.Task

class SubTaskEntityMapper: Mapper<SubTask, SubtaskEntity> {
    override fun map(input: SubTask): SubtaskEntity =
        with(input) {
            SubtaskEntity(
                id = id,
                parentId = parentId,
                content = content,
                isFinished = isFinished
            )
        }
}