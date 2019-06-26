package com.soundapp.mobile.todotask.presentations.task_details

import com.soundapp.mobile.todotask.domain.model.SubTask

interface SubTaskDetailsAdapterListener {
    val onSubTaskFinishedChanged: SubTaskCallback
    val onSubTaskUpdated: SubTaskChangeCallback
    val onSubTaskRemoved: SubTaskCallback
}

typealias SubTaskCallback = (SubTask) -> Unit
typealias SubTaskChangeCallback = (SubTask, String) -> Unit
