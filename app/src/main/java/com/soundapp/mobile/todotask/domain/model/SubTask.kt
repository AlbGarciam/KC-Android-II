package com.soundapp.mobile.todotask.domain.model

// We use a new structure for task in order to break dependency with TaskEntity
data class SubTask (
    val id: Long,
    val parentId: Long,
    val content: String,
    val isFinished: Boolean
)