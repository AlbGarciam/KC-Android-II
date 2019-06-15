package com.soundapp.mobile.todotask.presentations.task_details

import androidx.lifecycle.MutableLiveData
import com.soundapp.mobile.todotask.domain.SubTaskRepository
import com.soundapp.mobile.todotask.domain.TaskRepository
import com.soundapp.mobile.todotask.domain.model.SubTask
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.todotask.presentations.BaseViewModel
import com.soundapp.mobile.utils.extensions.isValidAsContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTaskViewModel(
    private val taskRepository: TaskRepository,
    private val subtaskRepository: SubTaskRepository
): BaseViewModel()  {
    val taskState = MutableLiveData<Task>()
    val isLoadingState = MutableLiveData<Boolean>()
    val isDeletedState = MutableLiveData<Boolean>()
    val subtasksState = MutableLiveData<List<SubTask>>()

    fun updateTask(task: Task) {
        if (task.content.isValidAsContent()) else { return }
        launch {
            withContext(Dispatchers.IO) { taskRepository.updateTask(task) }
            taskState.value = task
        }
    }

    fun retrieveTask(id: Long) {
        launch {
            isLoadingState.value = true
            taskState.value  = withContext(Dispatchers.IO) { taskRepository.getTaskById(id = id) }
            subtasksState.value = withContext(Dispatchers.IO) { subtaskRepository.getAllSubTaskFor(id)}
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

    fun generateSubtask() {
        taskState.value?.let {task ->
            val subTask = SubTask(0, task.id, "Subtask", false)
            launch {
                withContext(Dispatchers.IO) { subtaskRepository.addTask(subTask) }
                val subtasks = withContext(Dispatchers.IO) { subtaskRepository.getAllSubTaskFor(task.id)}
                subtasksState.value = subtasks
            }
        }
    }

    private fun updateTask(newTask: SubTask) {
        taskState.value?.let { task ->
            launch {
                withContext(Dispatchers.IO) { subtaskRepository.updateTask(newTask) }
                val subtasks = withContext(Dispatchers.IO) { subtaskRepository.getAllSubTaskFor(task.id) }
                subtasksState.value = subtasks // Keep synchronized view with viewmodel
            }
        }
    }

    fun toggleFinished(task: SubTask) {
        val newTask = task.copy(isFinished = !task.isFinished)
        updateTask(newTask)
    }

    fun updateSubTaskContent(task: SubTask, newContent: String) {
        val newTask = task.copy(content = newContent)
        updateTask(newTask)
    }
}