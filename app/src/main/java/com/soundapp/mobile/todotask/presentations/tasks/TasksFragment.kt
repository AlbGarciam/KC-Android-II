package com.soundapp.mobile.todotask.presentations.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soundapp.mobile.todotask.R
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.utils.extensions.EqualSpacingItemDecoration
import com.soundapp.mobile.utils.extensions.observe
import com.soundapp.mobile.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.viewmodel.ext.android.viewModel

class TasksFragment: Fragment() {

    private val tasksViewModel: TasksViewModel by viewModel()
    private val adapter : TaskAdapter by lazy {
        TaskAdapter{
            tasksViewModel.toggleFinished(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        bindState()
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
            observe(isLoadingState) { onLoadingState(it) }
            observe(tasksState) { onTasksLoaded(it) }
        }
    }

    private fun onLoadingState( isLoading: Boolean ) {
        tasksLoader.setVisible(isLoading)
    }

    private fun onTasksLoaded(tasks: List<Task>) {
        adapter.submitList(tasks)
    }
}