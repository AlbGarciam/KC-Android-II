package com.soundapp.mobile.todotask.data.repository.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.soundapp.mobile.todotask.domain.TaskRepository
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.todotask.presentations.tasks.TasksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.*
import org.threeten.bp.Instant

class TasksViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope()
    private val taskRepository: TaskRepository = mock()

    lateinit var tasksViewModel: TasksViewModel

    @Before // Executed before any test
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        tasksViewModel = TasksViewModel(taskRepository)
    }

    @After // Executed after any test
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `When the user loads the tasks then a loading should be shown`() = testDispatcher.runBlockingTest {
        // Given
        val tasks = listOf(
            Task(1, "🥳", Instant.now(), false, false),
            Task(2, "Texto un poco mas largo a ver como se comporta🥳", Instant.now(), false, true),
            Task(3, "Texto un poco mas largo a ver como se comporta📦🥳", Instant.now(), false, true)
        )
        // We need to call it with coroutines. We can block the thread because it is a test
        whenever(taskRepository.getAll()).thenReturn(tasks)
        // When
        tasksViewModel.loadTasks()

        // Then
        verify(taskRepository).getAll()
        Assert.assertEquals(tasks, tasksViewModel.tasksState.value)
    }

    @Test
    fun `When the user toggles the finished checkbox then the value should be updated`() = testDispatcher.runBlockingTest {
        // Given
        val tasks = listOf(
            Task(1, "🥳", Instant.now(), false, false),
            Task(2, "Texto un poco mas largo a ver como se comporta🥳", Instant.now(), false, true),
            Task(3, "Texto un poco mas largo a ver como se comporta📦🥳", Instant.now(), false, true)
        )
        // We need to call it with coroutines. We can block the thread because it is a test
        whenever(taskRepository.getAll()).thenReturn(tasks)
        // When
        tasksViewModel.loadTasks()
        tasksViewModel.toggleFinished(tasks.first())

        // Then
        verify(taskRepository).updateTask(argThat {
            isFinished // isFinished == true
        })
    }

}