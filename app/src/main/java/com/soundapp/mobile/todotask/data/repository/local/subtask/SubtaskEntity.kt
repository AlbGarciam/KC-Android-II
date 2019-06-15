package com.soundapp.mobile.todotask.data.repository.local.subtask

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.soundapp.mobile.todotask.data.repository.local.task.TaskEntity

@Entity(tableName = "subtasks", foreignKeys = [
    ForeignKey(
        entity = TaskEntity::class,
        parentColumns = ["id"],
        childColumns = ["parentId"],
        onDelete = CASCADE
    )])
data class SubtaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val parentId: Long,
    val content: String,
    val isFinished: Boolean
)