package com.soundapp.mobile.todotask.domain.model

import org.threeten.bp.Instant

// We use a new structure for task in order to break dependency with TaskEntity
data class Task (
    val id: Long,
    val content: String,
    val createdAt: Instant,
    val isHighPriority: Boolean,
    val isFinished: Boolean
) {
    enum class Status {
        FINISHED, HIGH_PRIORITY, LOW_PRIORITY
    }

    var status: Status = Status.LOW_PRIORITY
        get() {
            return when(isFinished) {
                true -> Status.FINISHED
                false -> if (isHighPriority) Status.HIGH_PRIORITY else Status.LOW_PRIORITY
            }
        }
}