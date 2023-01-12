package com.example.wordapp.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordapp.MyApplication
import com.example.wordapp.R
import com.example.wordapp.adapters.WordAdapter
import com.example.wordapp.databinding.FragmentAllWordsBinding
import com.example.wordapp.databinding.SortDialogBinding
import com.example.wordapp.data.models.SortCategory
import com.example.wordapp.data.models.SortOrder
import com.example.wordapp.viewModels.AllWordsViewModel
import com.example.wordapp.viewModels.HomeViewModel

// This is a fragment class, and it holds the data for the UI of All Words Fragment.
class AllWordsFragment : Fragment() {
    private lateinit var binding: FragmentAllWordsBinding
    private lateinit var adapter: WordAdapter
    private val viewModel: AllWordsViewModel by viewModels {
        AllWordsViewModel.Provider(
//            both methods work for Provider
//            (requireActivity().application as MyApplication).wordRepo,
            (requireContext().applicationContext as MyApplication).wordRepo,
            (requireActivity().application as MyApplication).storageService
        )
    }
    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    private var order = ""
    private var category = ""
    private var emptyQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllWordsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        val dialogBinding = SortDialogBinding.inflate(layoutInflater)
        val myDialog = Dialog(requireContext(), R.style.Custom_AlertDialog)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewModel.sortCategory.observe(viewLifecycleOwner) {
            dialogBinding.rbWord.isChecked = it == SortCategory.WORD.name
            dialogBinding.rbDate.isChecked = it == SortCategory.DATE.name
            category = it
        }

        viewModel.sortOrder.observe(viewLifecycleOwner) {
            dialogBinding.rbAsc.isChecked = it == SortOrder.ASC.name
            dialogBinding.rbDsc.isChecked = it == SortOrder.DSC.name
            order = it
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.onSwipeRefresh()
        }

        viewModel.swipeRefreshLayoutFinished.asLiveData().observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
        }

        var sortSearch = ""
        viewModel.words.observe(viewLifecycleOwner) { words ->
            binding.llEmpty.isGone = words.isNotEmpty()
            adapter.setWords(words)
        }

        homeViewModel.refreshAllWords.observe(viewLifecycleOwner) {
            if (it) {
                refresh("")
                homeViewModel.doRefreshAllWords(false)
            }
        }

        binding.run {
            search.svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    p0?.let {
                        refresh(it)
                    }
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    p0?.let {
                        refresh(it)
                    }
                    return false
                }
            })

            fabAdd.setOnClickListener {
                val directory = HomeFragmentDirections.actionHomeFragmentToAddWordFragment()
                NavHostFragment.findNavController(requireParentFragment()).navigate(directory)
            }

            search.ibSort.setOnClickListener {
                dialogBinding.radioGroup.setOnCheckedChangeListener { _, id ->
                    order = when (id) {
                        R.id.rbAsc -> {
                            SortOrder.ASC.name
                        }
                        else -> {
                            SortOrder.DSC.name
                        }
                    }

                }
                dialogBinding.radioGroup2.setOnCheckedChangeListener { _, id ->
                    category = when (id) {
                        R.id.rbWord -> {
                            SortCategory.WORD.name
                        }
                        else -> {
                            SortCategory.DATE.name
                        }
                    }
                }
                myDialog.setContentView(dialogBinding.root)
                myDialog.setCancelable(true)
                myDialog.show()

                dialogBinding.btnDone.setOnClickListener {
                    if (dialogBinding.radioGroup.checkedRadioButtonId == -1
                        || dialogBinding.radioGroup2.checkedRadioButtonId == -1
                    ) {
                        dialogBinding.tvWarning.isVisible = true
                    } else {
                        sortRefresh(order, category, sortSearch)
                        viewModel.onChangeSortBy(category)
                        viewModel.onChangeSortOrder(order)
                        myDialog.dismiss()
                    }
                }
            }
        }
    }

    // This is a private function that binds the adapter and layout to the fragment.
    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(this.activity)
        adapter = WordAdapter(emptyList()) {
            val directory = HomeFragmentDirections.actionHomeFragmentToWordFragment(it.id!!)
            NavHostFragment.findNavController(this).navigate(directory)
        }
        binding.rvAllWords.adapter = adapter
        binding.rvAllWords.layoutManager = layoutManager
    }

    // This is a private function to enable the recycler view to list the data set once again if the data set changes.
    private fun refresh(str: String) {
        viewModel.getWords(str)
    }

    // This is a private function to enable the recycler view to list the data set once again after sorting.
    private fun sortRefresh(order: String, category: String, str: String) {
        viewModel.sortWords(order, category, str)
    }

    companion object {
        private var allWordsFragmentInstance: AllWordsFragment? = null

        // This function allows the fragment to behave as a singleton
        fun getInstance(): AllWordsFragment {
            if (allWordsFragmentInstance == null) {
                allWordsFragmentInstance = AllWordsFragment()
            }
            return allWordsFragmentInstance!!
        }
    }
}