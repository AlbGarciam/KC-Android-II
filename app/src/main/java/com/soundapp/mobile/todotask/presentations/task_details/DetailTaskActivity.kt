package com.soundapp.mobile.todotask.presentations.task_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.soundapp.mobile.todotask.R
import com.soundapp.mobile.todotask.presentations.add_task.AddTaskFragment
import kotlinx.android.synthetic.main.activity_main.*

private const val DETAIL_TASK_EXTRA_TASK_ID = "DETAIL_TASK_EXTRA_TASK_ID"

class DetailTaskActivity: AppCompatActivity() {
    companion object {
        fun create(context: Context, taskId: Long): Intent {
            val intent = Intent(context,  DetailTaskActivity::class.java)
            intent.putExtra(DETAIL_TASK_EXTRA_TASK_ID, taskId)
            return intent
        }
    }

    private val taskId: Long?
        get() {
            if (!intent.hasExtra(DETAIL_TASK_EXTRA_TASK_ID)) { return null }
            return intent.getLongExtra(DETAIL_TASK_EXTRA_TASK_ID, 0)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)
        setupToolbar()
        setupFragment(savedInstanceState)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar as Toolbar)
        title = getString(R.string.detail_task_title)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    private fun setupFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) { return }
        taskId?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, DetailTaskFragment.create(it))
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}