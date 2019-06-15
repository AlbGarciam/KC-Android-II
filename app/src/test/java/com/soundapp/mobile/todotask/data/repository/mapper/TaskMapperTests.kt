package com.soundapp.mobile.todotask.data.repository.mapper

import com.soundapp.mobile.todotask.data.repository.local.task.TaskEntity
import org.junit.Assert
import org.junit.Test
import org.threeten.bp.Instant

class TaskMapperTests {
    val mapper = TaskMapper()
    @Test
    fun `When a task entity is mapped then a task should be returned`() {
        val now = Instant.now()
        val taskEntity = TaskEntity(1, "test", now, false, true)
        val task = mapper.map(taskEntity)
        Assert.assertEquals(taskEntity.id, task.id)
        Assert.assertEquals(taskEntity.content, task.content)
        Assert.assertEquals(taskEntity.createdAt, task.createdAt)
        Assert.assertEquals(taskEntity.isHighPriority, task.isHighPriority)
        Assert.assertEquals(taskEntity.isFinished, task.isFinished)
    }
}