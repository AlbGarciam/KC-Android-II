package com.soundapp.mobile.todotask.presentations.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soundapp.mobile.todotask.R
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.utils.extensions.EqualSpacingItemDecoration
import com.soundapp.mobile.utils.extensions.observe
import com.soundapp.mobile.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.viewmodel.ext.android.viewModel

class TasksFragment: Fragment(), TaskAdapterListener {

    private val tasksViewModel: TasksViewModel by viewModel()
    private val adapter : TaskAdapter by lazy {
        TaskAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        bindState()
    }

    override fun onResume() {
        super.onResume()
        tasksViewModel.loadTasks()
    }

    private fun setUpRecycler() {
        with(tasksRecyclerView) {
            layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL, false)
            adapter = this@TasksFragment.adapter
            addItemDecoration(EqualSpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.card_spacing)))
        }
    }

    private fun bindState() {
        with(tasksViewModel) {
            observe(isLoadingState, onLoadingState)
            observe(tasksState, onTasksLoaded)
            observe(isEmptyState, onEmptyStateChanged)
        }
    }

    private val onTasksLoaded: (List<Task>) -> Unit = { adapter.submitList(it) }
    private val onEmptyStateChanged: (Boolean) -> Unit = { isEmpty ->
        if (tasksViewModel.isLoadingState.value == false) {
            emptyText.setVisible(isEmpty)
            tasksRecyclerView.setVisible(!isEmpty)
        }
    }
    private val onLoadingState: (Boolean) -> Unit = { isLoading ->
        tasksLoader.setVisible(isLoading)
        tasksRecyclerView.setVisible(!isLoading)
        emptyText.setVisible(!isLoading)
        if (!isLoading) {
            tasksViewModel.isEmptyState.value?.let { isEmpty ->
                emptyText.setVisible(isEmpty)
                tasksRecyclerView.setVisible(!isEmpty)
            }
        }
    }

    override val onTaskFinishedChanged: TaskCallback = { tasksViewModel.toggleFinished(it) }
    override val onClickListener: TaskCallback = { (context as? TasksFragmentListener)?.onTaskClicked(it) }
    override val onTaskUpdated: (Task, String) -> Unit = { task, newContent -> tasksViewModel.updateTaskContent(task, newContent)}
    override val onTaskRemoved: TaskCallback = { tasksViewModel.deleteTask(it) }
}