package com.soundapp.mobile.todotask.presentations.tasks

import com.soundapp.mobile.todotask.domain.model.Task

interface TaskAdapterListener {
    val onTaskFinishedChanged: TaskCallback
    val onClickListener: TaskCallback
    val onTaskUpdated: (Task, String) -> Unit
    val onTaskRemoved: TaskCallback
}

typealias TaskCallback = (Task) -> Unit
