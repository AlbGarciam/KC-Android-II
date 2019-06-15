package com.soundapp.mobile.todotask.data.repository.local.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val content: String,
    val createdAt: Instant,
    val isHighPriority: Boolean,
    val isFinished: Boolean
)