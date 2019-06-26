package com.soundapp.mobile.todotask.data.repository.local.subtask

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.soundapp.mobile.todotask.data.repository.local.task.TaskEntity

@Entity(tableName = "subtasks",
    indices = arrayOf(Index(value = ["id", "parentId"]))
)
data class SubtaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ForeignKey(entity = TaskEntity::class, parentColumns = ["id"], childColumns = ["parentId"], onDelete = CASCADE)
    val parentId: Long,
    val content: String,
    val isFinished: Boolean
)