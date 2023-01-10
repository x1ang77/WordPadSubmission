package com.chiaching.wordpad_project.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chiaching.wordpad_project.MainActivity
import com.chiaching.wordpad_project.MyApplication
import com.chiaching.wordpad_project.R
import com.chiaching.wordpad_project.adapter.WordAdapter
import com.chiaching.wordpad_project.databinding.FragmentCompletedWordBinding
import com.chiaching.wordpad_project.viewModels.CompletedWordViewModel


class CompletedWordFragment : Fragment() {
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentCompletedWordBinding
    private val viewModel: CompletedWordViewModel by viewModels {
        CompletedWordViewModel.Provider((requireContext().applicationContext as MyApplication).wordRepo)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedWordBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        viewModel.words.observe(viewLifecycleOwner) { words ->
            binding.ivEmpty.visibility = if(words.isEmpty()) View.VISIBLE else View.GONE
            binding.tvEmpty.visibility = if(words.isEmpty()) View.VISIBLE else View.GONE
            adapter.setWords(words)
        }

        binding.svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
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

        binding.btnFilter.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.filter_dialog,null)
            val filterDialog = Dialog(requireContext(), R.style.Filter_AlertDialog)
            filterDialog.setContentView(dialogView)
            filterDialog.setCancelable(true)
            filterDialog.show()
            filterDialog.findViewById<Button>(R.id.btn_filter_done).setOnClickListener{
                val radioG1 = filterDialog.findViewById<RadioGroup>(R.id.radioGroup_order)
                val radioG2 = filterDialog.findViewById<RadioGroup>(R.id.radioGroup_by)
                val radioG1Checked = radioG1.checkedRadioButtonId
                val radioG2Checked = radioG2.checkedRadioButtonId
                val radioG1Button = filterDialog.findViewById<RadioButton>(radioG1Checked)
                val radioG2Button = filterDialog.findViewById<RadioButton>(radioG2Checked)
                val radioG1ButtonText = radioG1Button.text
                val radioG2ButtonText = radioG2Button.text
                sortRefresh(radioG1ButtonText.toString(), radioG2ButtonText.toString())
                filterDialog.hide()
            }
        }


    }

    fun sortRefresh(order:String,by:String) {
        viewModel.sortWords(order,by)
    }


    fun refresh(str: String) {
        viewModel.getWords(str)
    }

    fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = WordAdapter(emptyList()) {
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(it.id!!.toLong())
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.rvWord.adapter = adapter
        binding.rvWord.layoutManager = layoutManager
    }

    companion object {
        private var completedWordsFragmentInstance: CompletedWordFragment? = null
        fun getInstance(): CompletedWordFragment {
            if (completedWordsFragmentInstance == null) {
                completedWordsFragmentInstance = CompletedWordFragment()
            }

            return completedWordsFragmentInstance!!
        }
    }

}