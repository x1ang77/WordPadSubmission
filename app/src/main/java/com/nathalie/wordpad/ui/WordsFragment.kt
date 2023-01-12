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
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nathalie.wordpad.MainActivity
import com.nathalie.wordpad.data.Model.SortBy
import com.nathalie.wordpad.data.Model.SortOrder
import com.nathalie.wordpad.MyApplication
import com.nathalie.wordpad.R
import com.nathalie.wordpad.adapters.WordAdapter
import com.nathalie.wordpad.databinding.FragmentWordsBinding
import com.nathalie.wordpad.databinding.SortDialogBinding
import com.nathalie.wordpad.viewModels.MainViewModel
import com.nathalie.wordpad.viewModels.WordsViewModel

class WordsFragment : Fragment() {
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentWordsBinding
    private lateinit var dialogBinding: SortDialogBinding
    private var search: String = ""
    private val viewModel: WordsViewModel by viewModels {
        WordsViewModel.Provider(
            (requireActivity().applicationContext as MyApplication).wordRepo,
            (requireActivity().applicationContext as MyApplication).storageService,
        )

    }
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
        val dialogBinding = SortDialogBinding.inflate(layoutInflater)
        val myDialog = Dialog(requireContext(), R.style.WordPad_AlertDialog)

        setupAdapter()

        //determine which radio button is selected, "Title" or "Date"
        viewModel.sortBy.observe(viewLifecycleOwner) {
            dialogBinding.btnTitle.isChecked = it === SortBy.TITLE.name
            dialogBinding.btnDate.isChecked = it === SortBy.DATE.name
        }

        //determine which radio button is selected, "asc" or "dsc"
        viewModel.sortOrder.observe(viewLifecycleOwner) {
            dialogBinding.btnAsc.isChecked = it === SortOrder.ASC.name
            dialogBinding.btnDsc.isChecked = it === SortOrder.DSC.name
        }

        //when refresh set texh in search bar to be empty
        binding.srlRefresh.setOnRefreshListener {
            viewModel.onRefresh()
            binding.search.etSearch.setText("")
        }

        //stop refreshing after refresh once
        viewModel.swipeRefreshLayoutFinished.asLiveData()
            .observe(viewLifecycleOwner) {
                binding.srlRefresh.isRefreshing = false
            }

        //navigate to add item fragment
        binding.efabAddNewItem.setOnClickListener {
            val action = MainFragmentDirections.actionMainToAddWord()
            NavHostFragment.findNavController(this).navigate(action)
        }

        //show "No words yet" icon and text when there's no words
        viewModel.words.observe(viewLifecycleOwner) {
            adapter.setWords(it)
            binding.llEmpty.isVisible = adapter.itemCount <= 0
        }


        mainViewModel.refreshWords.observe(viewLifecycleOwner) {
            if (it) {
                refresh("")
                mainViewModel.shouldRefreshWords(false)
            }
        }

        //when search btn is clicked, filter words according to the words in search bar
        binding.search.btnSearch.setOnClickListener {
            search = binding.search.etSearch.text.toString()
            refresh(search)
        }

       //sort dialog pops out when sort btn is clicked
        binding.search.btnSort.setOnClickListener {
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogBinding.radioGroup.setOnCheckedChangeListener { _, id ->
                order = when (id) {
                    R.id.btnAsc -> {
                        SortOrder.ASC.name
                    }
                    else -> {
                        SortOrder.DSC.name
                    }
                }

            }

            dialogBinding.radioGroup2.setOnCheckedChangeListener { _, id ->
                type = when (id) {
                    R.id.btnTitle -> {
                        SortBy.TITLE.name
                    }
                    else -> {
                        SortBy.DATE.name
                    }
                }
            }
            myDialog.setContentView(dialogBinding.root)
            myDialog.setCancelable(true)
            myDialog.show()

            //when done btn is clicked inside sort dialog, set sortOrder and sortBy according to the radio btns that are selected
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

    //fetch words
    fun refresh(str: String) {
        viewModel.getWords(str)
    }

    //adapter for words
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