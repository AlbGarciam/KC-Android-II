package com.soundapp.mobile.todotask.presentations.tasks

import androidx.lifecycle.MutableLiveData
import com.soundapp.mobile.todotask.domain.TaskRepository
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.todotask.presentations.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksViewModel(
    private val repository: TaskRepository
): BaseViewModel() {

    val tasksState = MutableLiveData<List<Task>>()
    val isLoadingState = MutableLiveData<Boolean>()
    val isEmptyState = MutableLiveData<Boolean>()

    fun loadTasks() {
        // Launch will take the scope that we declared on #BaseViewModel if we not pass anything
        launch {
            // Main thread
            showLoading(true)
            val result = withContext(Dispatchers.IO) {
                // IO thread
                repository.getAll()
            }
            // Main thread
            tasksState.value = result
            showLoading(false)
            isEmptyState.value = result.isEmpty()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        isLoadingState.value = isLoading
    }

    fun toggleFinished(task: Task) {
        val newTask = task.copy(isFinished = !task.isFinished)
        launch(Dispatchers.IO) {
            repository.updateTask(newTask)
            loadTasks() // Keep synchronized view with viewmodel
        }
    }

    fun updateTaskContent(task: Task, newContent: String) {
        val newTask = task.copy(content = newContent)
        launch(Dispatchers.IO) {
            repository.updateTask(newTask)
            loadTasks() // Keep synchronized view with viewmodel
        }
    }

    fun deleteTask(task: Task) {
        launch {
            withContext(Dispatchers.IO) { repository.removeTask(task) }
            loadTasks() // Keep synchronized view with viewmodel
        }
    }

}