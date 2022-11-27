package com.example.hrautomation.presentation.view.faq.activity_question

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.hrautomation.R
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()

        viewModel.loadData(selectedCategoryId)
        supportActionBar?.title = intent.getStringExtra(NAME_EXTRA)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private val questionsObserver = Observer<List<BaseListItem>> { newItems ->
        adapter.update(newItems)
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(this, R.string.toast_overall_error, Toast.LENGTH_LONG).show()
            viewModel.clearExceptionState()
        }
    }

    private val isLoadingObserver = Observer<Boolean> { isLoading ->
        binding.progressBar.isVisible = isLoading
    }

    private fun initUi() {
        adapter = QuestionAdapter()
        binding.questionRecyclerview.adapter = adapter

        viewModel.data.observe(this, questionsObserver)
        viewModel.exception.observe(this, exceptionObserver)
        viewModel.isLoading.observe(this, isLoadingObserver)
    }

    companion object {
        private const val ID_EXTRA = "selectedCategoryId"
        private const val NAME_EXTRA = "selectedCategoryName"

        fun createIntent(context: Context, id: Long, name: String): Intent {
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra(ID_EXTRA, id)
            intent.putExtra(NAME_EXTRA, name)
            return intent
        }
    }
}