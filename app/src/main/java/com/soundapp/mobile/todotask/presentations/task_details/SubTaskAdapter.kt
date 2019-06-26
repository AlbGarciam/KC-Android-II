package com.soundapp.mobile.todotask.presentations.task_details

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
import com.soundapp.mobile.todotask.domain.model.SubTask
import kotlinx.android.synthetic.main.item_task.view.*

class SubTaskAdapter(private val listenerSub: SubTaskDetailsAdapterListener): ListAdapter<SubTask, SubTaskAdapter.SubTaskViewHolder>(SubTaskDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_task, parent, false)
        return SubTaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubTaskViewHolder, position: Int) {
        holder.update(getItem(position))
    }

    inner class SubTaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun update(task: SubTask) {
            with(itemView) {
                if (task.isFinished) {
                    applyStrikeThrough(cardContentText, task.content)
                } else {
                    removeStrikeThrough(cardContentText, task.content)
                }
                dateTextView.visibility = View.GONE
                finishSwitch.isChecked = task.isFinished
                statusIndicator.setImageResource(R.drawable.ic_indicator_low_priority)
                finishSwitch.setOnClickListener {
                    onFinishClicked(task)
                }

                setOnTouchListener { _, _ -> onCardTouched(task) }

                edit.setOnClickListener { onEditClicked() }

                delete.setOnClickListener { listenerSub.onSubTaskRemoved(task, adapterPosition) }
            }
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

        private val onFinishClicked: (SubTask) -> Unit = {task ->
            with(itemView) {
                listenerSub.onSubTaskFinishedChanged(task, adapterPosition)
                val isChecked = finishSwitch.isChecked
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

        private val onCardTouched: (SubTask) -> Boolean = { task ->
            with(itemView) {
                if (cardContentText.isEnabled) {
                    cardContentText.isEnabled = false
                    listenerSub.onSubTaskUpdated(task, adapterPosition, cardContentText.text.toString())
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