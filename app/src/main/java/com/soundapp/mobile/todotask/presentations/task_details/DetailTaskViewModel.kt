package com.soundapp.mobile.todotask.presentations.task_details

import androidx.lifecycle.MutableLiveData
import com.soundapp.mobile.todotask.domain.TaskRepository
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.todotask.presentations.BaseViewModel
import com.soundapp.mobile.utils.extensions.isValidAsContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTaskViewModel(
    private val taskRepository: TaskRepository
): BaseViewModel()  {
    val taskState = MutableLiveData<Task>()
    val isLoadingState = MutableLiveData<Boolean>()
    val isDeletedState = MutableLiveData<Boolean>()

    fun updateTask(task: Task) {
        if (task.content.isValidAsContent()) else { return }
        launch {
            isLoadingState.value = true
            withContext(Dispatchers.IO) { taskRepository.updateTask(task) }
            taskState.value = task
            isLoadingState.value = false
        }
    }

    fun retrieveTask(id: Long) {
        launch {
            isLoadingState.value = true
            taskState.value  = withContext(Dispatchers.IO) { taskRepository.getTaskById(id = id) }
            isLoadingState.value = false
        }
    }

    fun deleteTask(task: Task) {
        launch {
            isLoadingState.value = true
            withContext(Dispatchers.IO) { taskRepository.removeTask(task) }
            isDeletedState.value = true
        }
    }
}