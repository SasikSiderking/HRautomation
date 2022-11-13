package com.example.hrautomation.presentation.view.colleagues.employee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEmployeeBinding
import com.example.hrautomation.presentation.model.EmployeeItem
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class EmployeeActivity : AppCompatActivity() {
    private var _binding: ActivityEmployeeBinding? = null
    private val binding: ActivityEmployeeBinding
        get() = _binding!!

    private val selectedEmployeeId: Long by lazy { intent.getLongExtra(ID_EXTRA, 0L) }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EmployeeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)

        _binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()

            viewModel.loadData(selectedEmployeeId)
    }

    override fun onDestroy() {
        _binding?.unbind()
        _binding = null
        super.onDestroy()
    }

    private val selectedEmployeeObserver = Observer<EmployeeItem> { colleague ->
        binding.employeeFullName.text = colleague.name
        binding.employeeFullEmail.setText(colleague.email)
        binding.employeeFullPost.setText(colleague.post)
        binding.employeeFullProject.setText(colleague.project)
        binding.employeeFullAbout.setText(colleague.info)
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_LONG).show()

            viewModel.setToastShownState()
        }
    }

    private fun initUi() {
        viewModel.selectedEmployee.observe(this, selectedEmployeeObserver)
        viewModel.exception.observe(this, exceptionObserver)
    }

    companion object {
        private const val ID_EXTRA = "selectedEmployeeId"

        fun createIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, EmployeeActivity::class.java)
            intent.putExtra(ID_EXTRA, id)
            return intent
        }
    }
}