package com.soundapp.mobile.todotask.presentations.add_task

import androidx.lifecycle.MutableLiveData
import com.soundapp.mobile.todotask.domain.TaskRepository
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.todotask.presentations.BaseViewModel
import com.soundapp.mobile.utils.Event
import com.soundapp.mobile.utils.extensions.call
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant

class AddTaskViewModel(
    private val taskRepository: TaskRepository
): BaseViewModel() {
    val closeAction = MutableLiveData<Event<Unit>>()


    fun save(content: String, isHighlighted: Boolean) {
        if (!validateContent(content)) { return }
        launch {
            withContext(Dispatchers.IO) { taskRepository.addTask(Task(0,
                content, Instant.now(), isHighPriority = isHighlighted, isFinished = false))
            }
            closeAction.call() // Extension
        }

    }

    private fun validateContent(content: String) : Boolean = content.isNotEmpty()
}