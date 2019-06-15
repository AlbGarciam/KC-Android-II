package com.soundapp.mobile.todotask.presentations.task_details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soundapp.mobile.todotask.R
import com.soundapp.mobile.todotask.domain.model.SubTask
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_detail_task.*
import kotlinx.android.synthetic.main.fragment_detail_task.dateTextView
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.Instant

private const val DETAIL_TASK_EXTRA_TASK_ID = "DETAIL_TASK_EXTRA_TASK_ID"

class DetailTaskFragment: Fragment() {
    companion object {
        fun create(taskId: Long) : DetailTaskFragment {
            val fragment = DetailTaskFragment()
            val args = Bundle()
            args.putLong(DETAIL_TASK_EXTRA_TASK_ID, taskId)
            fragment.arguments = args
            return fragment
        }
    }

    private val detailTaskViewModel: DetailTaskViewModel by viewModel()
    private val adapter : SubTaskAdapter by lazy {
        SubTaskAdapter(
            onFinishedStatusChanged = { detailTaskViewModel.toggleFinished(it) },
            onTaskUpdatedRequested = { subtask, content ->
                detailTaskViewModel.updateSubTaskContent(subtask, content)
            })
    }

    private var taskId: Long? = null
        set(value) {
            value?.run { detailTaskViewModel.retrieveTask(this) }
            field = value
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_detail_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        bindEvents()
        bindActions()
        taskId = arguments?.getLong(DETAIL_TASK_EXTRA_TASK_ID)
    }

    private fun setUpRecycler() {
        with(subtasksList) {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            adapter = this@DetailTaskFragment.adapter
            addItemDecoration(EqualSpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.card_spacing)))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete -> {
                detailTaskViewModel.taskState.value?.let {task ->
                    detailTaskViewModel.deleteTask(task)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun bindActions() {
        saveButton.setOnClickListener(onSaveClicked)
        addButton.setOnClickListener(onAddClicked)
    }

    private fun bindEvents() {
        with(detailTaskViewModel) {
            observe(isLoadingState, onLoadingChanged)
            observe(taskState, onTaskUpdated)
            observe(subtasksState, onSubTasksLoaded)
            observe(isDeletedState, onTaskDeleted)
        }
    }

    private val onSaveClicked: (View) -> Unit = {
        val content = taskContent.text.toString()
        detailTaskViewModel.taskState.value?.let{ currentTask ->
            if (content.isValidAsContent()) {
                val newTask = Task(
                    id = currentTask.id,
                    content = content,
                    createdAt = Instant.now(),
                    isHighPriority = highlightBox.isChecked,
                    isFinished = isFinishedBox.isChecked
                )
                detailTaskViewModel.updateTask(newTask)
            }
        }
    }

    private val onAddClicked: (View) -> Unit = {
        detailTaskViewModel.generateSubtask()
    }

    private val onLoadingChanged: (Boolean) -> Unit = {
        taskLoader.setVisible(it)
        detailInfoContainer.setVisible(!it)
        saveButton.setVisible(!it)
        subtasksList.setVisible(!it)
        addButton.setVisible(!it)
    }

    private val onTaskUpdated: (Task) -> Unit = {
        with(it) {
            taskContent.setText(content)
            dateTextView.text = createdAt.timeAgo()
            highlightBox.isChecked = isHighPriority
            isFinishedBox.isChecked = isFinished
        }
    }

    private val onTaskDeleted: (Boolean) -> Unit = {isDeleted ->
        if (isDeleted) {
            requireActivity().finish()
        }
    }

    private val onSubTasksLoaded: (List<SubTask>) -> Unit = {
        adapter.submitList(it)
    }
}