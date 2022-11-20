package com.example.hrautomation.presentation.view.faq.activity_question

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityQuestionBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class QuestionActivity : AppCompatActivity() {
    private var _binding: ActivityQuestionBinding? = null
    private val binding: ActivityQuestionBinding
        get() = _binding!!

    private lateinit var adapter: QuestionAdapter

    private val selectedCategoryId: Long by lazy { intent.getLongExtra(ID_EXTRA, 0L) }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: QuestionViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)

        _binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()

        viewModel.loadData(selectedCategoryId)
    }

    override fun onDestroy() {
        _binding?.unbind()
        _binding = null
        viewModel.clearToastState()
        super.onDestroy()
    }

    private val questionsObserver = Observer<List<BaseListItem>> { newItems ->
        adapter.update(newItems)
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_LONG).show()
        }
    }

    private fun initUi() {
        adapter = QuestionAdapter()
        binding.questionRecyclerview.adapter = adapter

        viewModel.data.observe(this, questionsObserver)
        viewModel.exception.observe(this, exceptionObserver)
    }

    companion object {
        private const val ID_EXTRA = "selectedCategoryId"

        fun createIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra(ID_EXTRA, id)
            return intent
        }
    }
}