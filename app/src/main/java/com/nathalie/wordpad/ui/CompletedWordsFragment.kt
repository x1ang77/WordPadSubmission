package com.nathalie.wordpad.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.nathalie.wordpad.MainActivity
import com.nathalie.wordpad.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nathalie.wordpad.MyApplication
import com.nathalie.wordpad.adapters.WordAdapter
import com.nathalie.wordpad.data.Model.SortBy
import com.nathalie.wordpad.data.Model.SortOrder
import com.nathalie.wordpad.databinding.FragmentCompletedWordsBinding
import com.nathalie.wordpad.databinding.FragmentWordsBinding
import com.nathalie.wordpad.databinding.SortDialogBinding
import com.nathalie.wordpad.viewModels.CompletedWordsViewModel
import com.nathalie.wordpad.viewModels.MainViewModel
import com.nathalie.wordpad.viewModels.WordsViewModel

class CompletedWordsFragment : Fragment() {
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentWordsBinding
    private val viewModel: CompletedWordsViewModel by viewModels {
        CompletedWordsViewModel.Provider(
            (requireActivity().applicationContext as MyApplication).wordRepo,
            (requireActivity().applicationContext as MyApplication).storageService,
        )
    }

    private var search: String = ""
    private val mainViewModel: MainViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    var order: String = ""
    var type: String = ""

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

        val dialogBinding = SortDialogBinding.inflate(layoutInflater)
        val myDialog = Dialog(requireContext(), R.style.WordPad_AlertDialog)

        viewModel.sortBy.observe(viewLifecycleOwner) {
            dialogBinding.btnTitle.isChecked = it === SortBy.TITLE.name
            dialogBinding.btnDate.isChecked = it === SortBy.DATE.name

        }
        viewModel.sortOrder.observe(viewLifecycleOwner) {
            dialogBinding.btnAsc.isChecked = it === SortOrder.ASC.name
            dialogBinding.btnDsc.isChecked = it === SortOrder.DSC.name
        }

        binding.srlRefresh.setOnRefreshListener {
            viewModel.onRefresh()
            binding.search.etSearch.setText("")
        }

        viewModel.swipeRefreshLayoutFinished.asLiveData()
            .observe(viewLifecycleOwner) {
                binding.srlRefresh.isRefreshing = false
            }

        binding.efabAddNewItem.setOnClickListener {
            val action = MainFragmentDirections.actionMainToAddWord()
            NavHostFragment.findNavController(this).navigate(action)
        }
        viewModel.words.observe(viewLifecycleOwner) {
            adapter.setWords(it)
            binding.llEmpty.isVisible = adapter.itemCount <= 0
        }

        mainViewModel.refreshCompletedWords.observe(viewLifecycleOwner) {
            if (it) {
                refresh("")
                mainViewModel.shouldRefreshWords(false)
            }
        }

        binding.search.btnSearch.setOnClickListener {
            val search = binding.search.etSearch.text.toString()
            refresh(search)
        }

        binding.search.btnSort.setOnClickListener {
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogBinding.radioGroup.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.btnAsc -> {
                        order = SortOrder.ASC.name
                    }
                    else -> {
                        order = SortOrder.DSC.name
                    }
                }
            }

            dialogBinding.radioGroup2.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.btnTitle -> {
                        type = SortBy.TITLE.name
                    }
                    else -> {
                        type = SortBy.DATE.name
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
                    viewModel.onChangeSortBy(type)
                    viewModel.onChangeSortOrder(order)
                    viewModel.sortWords(order, type, search)
                    myDialog.dismiss()
                }

            }
        }
    }


    fun refresh(str: String) {
        lifecycleScope.launchWhenResumed {
            viewModel.getWords(str)
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