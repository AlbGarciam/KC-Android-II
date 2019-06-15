package com.soundapp.mobile.todotask.presentations.task_details

import androidx.recyclerview.widget.DiffUtil
import com.soundapp.mobile.todotask.domain.model.SubTask
import com.soundapp.mobile.todotask.domain.model.Task

class SubTaskDiffUtil : DiffUtil.ItemCallback<SubTask>() {
    override fun areItemsTheSame(oldItem: SubTask, newItem: SubTask): Boolean = oldItem.id == newItem.id
    // Data classes automatically compares all elements
    override fun areContentsTheSame(oldItem: SubTask, newItem: SubTask): Boolean = oldItem == newItem
}