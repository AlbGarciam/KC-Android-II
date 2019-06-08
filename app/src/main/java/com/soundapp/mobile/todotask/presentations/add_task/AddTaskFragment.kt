package com.soundapp.mobile.todotask.presentations.add_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soundapp.mobile.todotask.R
import com.soundapp.mobile.utils.extensions.consume
import com.soundapp.mobile.utils.extensions.observe
import kotlinx.android.synthetic.main.fragment_add_task.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddTaskFragment: Fragment() {

    private val addTaskViewModel: AddTaskViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvents()
        bindActions()
    }

    private fun bindActions() {
        saveButton.setOnClickListener {
            val taskContent = taskContent.text
            val checkboxValue = checkBox.isChecked
            addTaskViewModel.save(taskContent.toString(), checkboxValue)
        }
    }

    private fun bindEvents() {
        with(addTaskViewModel) {
            observe(closeAction) {
                it.consume {
                    onClose()
                }
            }
        }
    }

    private fun onClose() {
        requireActivity().finish()
    }
}