package com.nathalie.wordpad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nathalie.wordpad.MainActivity
import com.nathalie.wordpad.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nathalie.wordpad.adapters.WordAdapter
import com.nathalie.wordpad.databinding.FragmentCompletedWordsBinding
import com.nathalie.wordpad.viewModels.CompletedWordsViewModel
import com.nathalie.wordpad.viewModels.WordsViewModel

class CompletedWordsFragment : Fragment() {
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentCompletedWordsBinding
    private val viewModel: CompletedWordsViewModel by viewModels {
        CompletedWordsViewModel.Provider((requireActivity() as MainActivity).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletedWordsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        binding.efabAddNewItem.setOnClickListener {
            val action = MainFragmentDirections.actionMainToAddWord()
            NavHostFragment.findNavController(this).navigate(action)
        }
        viewModel.words.observe(viewLifecycleOwner) {
            adapter.setWords(it)
        }
    }

    fun refresh(str: String, status: Boolean) {
        lifecycleScope.launchWhenResumed {
            viewModel.getWords(str, status)
        }
    }

    fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = WordAdapter(emptyList()) {
            val action = MainFragmentDirections.actionMainToDetails(it.id!!)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = layoutManager
    }

    companion object {
        private var completedWordsFragmentInstance: CompletedWordsFragment? = null
        fun getInstance(): CompletedWordsFragment {
            if (completedWordsFragmentInstance == null) {
                completedWordsFragmentInstance = CompletedWordsFragment()
            }

            return completedWordsFragmentInstance!!
        }
    }

}