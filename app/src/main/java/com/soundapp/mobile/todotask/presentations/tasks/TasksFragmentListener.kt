package com.soundapp.mobile.todotask.presentations.tasks

import com.soundapp.mobile.todotask.domain.model.Task

interface TasksFragmentListener {
    fun onTaskClicked(task: Task)
}