package com.soundapp.mobile.todotask.domain.model

import org.threeten.bp.Instant

// We use a new structure for task in order to break dependency with TaskEntity
data class Task (
    val id: Long,
    val content: String,
    val createdAt: Instant,
    val isHighPriority: Boolean,
    val isFinished: Boolean
)