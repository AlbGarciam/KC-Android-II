package com.soundapp.mobile.todotask.presentations.tasks

import androidx.recyclerview.widget.DiffUtil
import com.soundapp.mobile.todotask.domain.model.Task

class TaskDiffUtil : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id
    // Data classes automatically compares all elements
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem
}