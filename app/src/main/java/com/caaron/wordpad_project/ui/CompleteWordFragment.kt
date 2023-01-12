package com.caaron.wordpad_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.caaron.wordpad_project.MyApplication
import com.caaron.wordpad_project.adapter.NewWordAdapter
import com.caaron.wordpad_project.databinding.FragmentCompleteWordBinding
import com.caaron.wordpad_project.databinding.FragmentNewWordBinding
import com.caaron.wordpad_project.viewModels.CompletedViewModel
import com.caaron.wordpad_project.viewModels.NewViewModel
import com.caaron.wordpad_project.viewModels.MainViewModel

class CompleteWordFragment private constructor() : Fragment() {
    private lateinit var binding: FragmentNewWordBinding
    private lateinit var adapter: NewWordAdapter
    private val viewModel: CompletedViewModel by viewModels {
        CompletedViewModel.Provider((requireContext().applicationContext as MyApplication).wordRepo)
    }
    private val mainViewModel: MainViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewWordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        viewModel.words.observe(viewLifecycleOwner) {
            binding.empty.visibility =
                if (it.isEmpty()) View.VISIBLE else View.GONE
            adapter.setWords(it)
        }

        mainViewModel.refreshCompletedWords.observe(viewLifecycleOwner) {
            if (it) {
                refresh("")
                mainViewModel.shouldRefreshCompletedWords(false)
            }
        }

//        binding.search.btnSearch.setOnClickListener{
//            val search = binding.search.etSearch.text.toString()
//            refresh(search)
//        }

        //This onQuery function is to find the word from the string and search the word from the application
        binding.search.etSearch.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override  fun onQueryTextSubmit(p0:String?): Boolean{
                p0?.let{
                    refresh(it)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let{
                    refresh(it)
                }
                return false
            }
        })

        binding.efabAddNew.visibility = View.GONE
    }

    //This refresh function is to keep updating the word from application
    fun refresh(str: String) {
        viewModel.getCompletedWords(str)
    }


    //This function is for the recycle view by creating a new instance of completedWord,
    //passing an empty list and a lambda function
    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = NewWordAdapter(emptyList()) {
            val action = it.id?.let { it1 ->
                MainFragmentDirections.actionMainFragmentToDetailsFragment(
                    it1, it.title, it.meaning, it.synonym, it.details
                )
            }
            if (action != null) {
                NavHostFragment.findNavController(this).navigate(action)
            }
        }
        binding.rvWords.layoutManager = layoutManager
        binding.rvWords.adapter = adapter
    }

    companion object {
        private var completeWordFragmentInstance: CompleteWordFragment? = null

        //This function allow the fragment to behave as a singleton
        fun getInstance(): CompleteWordFragment {
            if (completeWordFragmentInstance == null) {
                completeWordFragmentInstance = CompleteWordFragment()
            }
            return completeWordFragmentInstance!!
        }
    }
}