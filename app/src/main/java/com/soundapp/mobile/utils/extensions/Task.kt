package com.soundapp.mobile.utils.extensions

import com.soundapp.mobile.todotask.R
import com.soundapp.mobile.todotask.domain.model.Task

fun Task.indicator() : Int = status.drawable()

fun Task.Status.drawable() : Int = when(this) {
    Task.Status.FINISHED -> R.drawable.ic_indicator_finished
    Task.Status.HIGH_PRIORITY -> R.drawable.ic_indicator_high_priority
    Task.Status.LOW_PRIORITY -> R.drawable.ic_indicator_low_priority
}

fun Task.indicatorForFinishStatus(status: Boolean) : Int = when(status){
    true -> Task.Status.FINISHED.drawable()
    false -> if (isHighPriority) Task.Status.HIGH_PRIORITY.drawable() else Task.Status.LOW_PRIORITY.drawable()
}