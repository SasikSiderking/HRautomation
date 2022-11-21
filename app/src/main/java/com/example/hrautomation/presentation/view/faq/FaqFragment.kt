package com.example.hrautomation.presentation.view.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentFaqBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.view.faq.activity_question.QuestionActivity
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class FaqFragment : Fragment() {

    private var _binding: FragmentFaqBinding? = null
    private val binding: FragmentFaqBinding
        get() = _binding!!

    private lateinit var adapter: FaqAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: FaqViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFaqBinding.inflate(inflater, container, false)

        initUi()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initUi() {
        adapter = FaqAdapter(OnFaqCategoryClickListener { id: Long, name: String ->
            startActivity(QuestionActivity.createIntent(requireContext(), id, name))
        })
        binding.faqRecyclerview.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, categoryObserver)
        viewModel.exception.observe(viewLifecycleOwner, exceptionObserver)
    }

    private val categoryObserver = Observer<List<BaseListItem>> { newItems ->
        adapter.update(newItems)
    }
    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show()

            viewModel.setToastShownState()
        }
    }
}