package com.nathalie.wordpad.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nathalie.wordpad.MainActivity
import com.nathalie.wordpad.MyApplication
import com.nathalie.wordpad.R
import com.nathalie.wordpad.adapters.WordAdapter
import com.nathalie.wordpad.databinding.FragmentWordsBinding
import com.nathalie.wordpad.viewModels.MainViewModel
import com.nathalie.wordpad.viewModels.WordsViewModel

class WordsFragment : Fragment() {
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentWordsBinding
    private val viewModel: WordsViewModel by viewModels {
        WordsViewModel.Provider((requireActivity() as MainActivity).wordRepo)
    }
    private val mainViewModel: MainViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordsBinding.inflate(layoutInflater)
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

        mainViewModel.refreshWords.observe(viewLifecycleOwner) {
            if (it) {
                refresh("")
                mainViewModel.shouldRefreshWords(false)
            }
        }

        binding.search.btnSearch.setOnClickListener {
            val search = binding.search.etSearch.text.toString()
            refresh(search)
        }
    }

    fun refresh(str: String) {
        viewModel.getWords(str)
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
        private var wordsFragmentInstance: WordsFragment? = null
        fun getInstance(): WordsFragment {
            if (wordsFragmentInstance == null) {
                wordsFragmentInstance = WordsFragment()
            }

            return wordsFragmentInstance!!
        }
    }
}