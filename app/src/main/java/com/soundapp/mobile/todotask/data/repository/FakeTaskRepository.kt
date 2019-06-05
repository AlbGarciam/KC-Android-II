package com.soundapp.mobile.todotask.data.repository

import com.soundapp.mobile.todotask.domain.TaskRepository
import com.soundapp.mobile.todotask.domain.model.Task
import kotlinx.coroutines.runBlocking
import org.threeten.bp.Instant

class FakeTaskRepository : TaskRepository {
    private val tasks : MutableList<Task> by lazy {
        mutableListOf(
            Task(1, "🥳", Instant.now(), false, false),
            Task(2, "Texto un poco mas largo a ver como se comporta🥳", Instant.now(), false, true),
            Task(3, "Texto un poco mas largo a ver como se comporta📦🥳", Instant.now(), false, true)
        )
    }
    override suspend fun getAll(): List<Task> = runBlocking { tasks }

    override suspend fun getTaskById(id: Long): Task = runBlocking { tasks.first { it.id == id }}

    override suspend fun addTask(task: Task) { tasks.add(task) }

    override suspend fun updateTask(task: Task) {
        val storedTask = tasks.first { it.id == task.id }
        val index = tasks.indexOf(storedTask)
        tasks[index] = task
    }

    override suspend fun removeTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}