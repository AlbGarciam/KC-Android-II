package com.soundapp.mobile.todotask.data.repository.mapper

import com.soundapp.mobile.todotask.data.repository.local.subtask.SubtaskEntity
import com.soundapp.mobile.todotask.data.repository.local.task.TaskEntity
import com.soundapp.mobile.todotask.domain.model.SubTask
import com.soundapp.mobile.todotask.domain.model.Task

class SubTaskMapper: Mapper<SubtaskEntity, SubTask> {
    override fun map(input: SubtaskEntity): SubTask =
        with(input) {
            SubTask(
                id = id,
                parentId = parentId,
                content = content,
                isFinished = isFinished
            )
        }
}