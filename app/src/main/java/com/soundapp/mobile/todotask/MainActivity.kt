package com.soundapp.mobile.todotask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.soundapp.mobile.todotask.domain.model.Task
import com.soundapp.mobile.todotask.presentations.add_task.AddTaskActivity
import com.soundapp.mobile.todotask.presentations.task_details.DetailTaskActivity
import com.soundapp.mobile.todotask.presentations.tasks.TasksFragment
import com.soundapp.mobile.todotask.presentations.tasks.TasksFragmentListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TasksFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupFragment(savedInstanceState)
        bindActions()
    }

    private fun bindActions() {
        fab.setOnClickListener {
            val intent = Intent(this,  AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, TasksFragment())
                .commit()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar as Toolbar)
    }

    override fun onTaskClicked(task: Task) {
        val intent = DetailTaskActivity.create(this, task.id)
        startActivity(intent)
    }
}
