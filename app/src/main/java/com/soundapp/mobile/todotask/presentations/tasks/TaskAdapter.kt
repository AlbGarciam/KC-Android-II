package com.soundapp.mobile.todotask.presentations.tasks

import android.animation.ValueAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soundapp.mobile.todotask.R
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.utils.extensions.indicator
import com.soundapp.mobile.utils.extensions.indicatorForFinishStatus
import com.soundapp.mobile.utils.extensions.setVisible
import com.soundapp.mobile.utils.extensions.timeAgo
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(
    private val onFinishedStatusChanged: (task: Task) -> Unit,
    private val onClickListener: (task: Task) -> Unit,
    private val onTaskUpdatedRequested: (task: Task, text: String) -> Unit
): ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.update(getItem(position))
    }

    inner class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun update(task: Task) {
            with(itemView) {
                if (task.isFinished) {
                    applyStrikeThrough(cardContentText, task.content)
                } else {
                    removeStrikeThrough(cardContentText, task.content)
                }
                dateTextView.text = task.createdAt.timeAgo()
                finishSwitch.isChecked = task.isFinished
                statusIndicator.setImageResource(task.indicator())
                finishSwitch.setOnClickListener {
                    onFinishClicked(task)
                }

                setOnTouchListener { _, _ ->
                    onCardTouched(task)
                }

                edit.setOnClickListener {
                    onEditClicked()
                }
            }
            itemView.setOnClickListener { onClickListener(task) }
        }

        private fun applyStrikeThrough(view: TextView, content: String, animated: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()

            if (animated) {
                ValueAnimator.ofInt(content.length).apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
            } else {
                span.setSpan(spanStrike, 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                view.text = span
            }

        }

        private fun removeStrikeThrough(view: TextView, content: String, animated: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()
            if (animated) {
                ValueAnimator.ofInt(content.length).apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, content.length - (it.animatedValue as Int), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
            } else {
                view.text = content
            }
        }

        private val onFinishClicked: (Task) -> Unit = {task ->
            with(itemView) {
                onFinishedStatusChanged(task)
                val isChecked = finishSwitch.isChecked
                statusIndicator.setImageResource(task.indicatorForFinishStatus(isChecked))
                if (isChecked) {
                    applyStrikeThrough(cardContentText, task.content, true)
                } else {
                    removeStrikeThrough(cardContentText, task.content, true)
                }
            }
        }

        private val onEditClicked: () -> Unit = {
            itemView.cardContentText.isEnabled = true
            itemView.cardContentText.requestFocus()
        }

        private val onCardTouched: (Task) -> Boolean = { task ->
            with(itemView) {
                if (cardContentText.isEnabled) {
                    cardContentText.isEnabled = false
                    onTaskUpdatedRequested(task, cardContentText.text.toString())
                    val isChecked = itemView.finishSwitch.isChecked
                    if (isChecked) {
                        applyStrikeThrough(cardContentText, cardContentText.text.toString(), true)
                    } else {
                        removeStrikeThrough(cardContentText, cardContentText.text.toString(), true)
                    }
                }
                requestFocus()
            }
            false
        }
    }

}