package com.caaron.wordpad_project.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.caaron.wordpad_project.MyApplication
import com.caaron.wordpad_project.R
import com.caaron.wordpad_project.adapter.NewWordAdapter
import com.caaron.wordpad_project.databinding.FragmentNewWordBinding
import com.caaron.wordpad_project.databinding.ItemLayoutAlertBinding
import com.caaron.wordpad_project.viewModels.NewViewModel
import com.caaron.wordpad_project.viewModels.MainViewModel

class NewWordFragment private constructor() : Fragment() {

    private lateinit var binding: FragmentNewWordBinding
    private lateinit var adapter: NewWordAdapter
    private val viewModel: NewViewModel by viewModels {
        NewViewModel.Provider((requireContext().applicationContext as MyApplication).wordRepo)
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

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        //This onClick function allow you to navigate from main fragment to add fragment
        binding.efabAddNew.setOnClickListener {
            val action = MainFragmentDirections.actionMainToAdd()
            NavHostFragment.findNavController(this).navigate(action)
        }

        viewModel.words.observe(viewLifecycleOwner) {
            binding.empty.visibility =
                if (it.isEmpty()) View.VISIBLE else View.GONE
            adapter.setWords(it)
            Log.d("search", it.toString())
        }

        mainViewModel.refreshWords.observe(viewLifecycleOwner) {
            if (it) {
                refresh("")
                mainViewModel.shouldRefreshWords(false)
            }
        }

//        binding.search.btnSearch.setOnClickListener{
//            val search = binding.search.etSearch.text.toString()
//            refresh(search)
//        }
        binding.search.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        //This onClick function allow you to sort the word by selecting the ratio
        binding.search.btnSort.setOnClickListener{
            val dialogBinding = layoutInflater.inflate(R.layout.item_layout_alert,null,false)

            val myDialog = Dialog(requireContext(),R.style.DataBinding_AlertDialog)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.show()
            myDialog.findViewById<Button>(R.id.sort).setOnClickListener {
                val rg=myDialog.findViewById<RadioGroup>(R.id.radioGroup1)
                val rg2=myDialog.findViewById<RadioGroup>(R.id.radioGroup2)
                val radioId=rg.checkedRadioButtonId
                val radioId2 = rg2.checkedRadioButtonId
                val radioButton1 = myDialog.findViewById<RadioButton>(radioId)
                val radioButton2 = myDialog.findViewById<RadioButton>(radioId2)
                val radioButtonText1 = radioButton1.text
                val radioButtonText2 = radioButton2.text
                sortrefresh(radioButtonText1.toString(),radioButtonText2.toString())
                myDialog.hide()
            }
        }
    }

    //This refresh function is to keep updating the word from application
    fun refresh(str: String) {
        viewModel.getWords(str)
    }

    //This sortRefresh function is to keep updating the word from application when you sorted
    fun sortrefresh(order:String,by:String) {
        viewModel.sortWord(order,by)
    }

    //This function is for the recycle view by creating a new instance of completedWord,
    //passing an empty list and a lambda function
    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = NewWordAdapter(emptyList()) {
            val action =
                it.id?.let { it1 ->
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(
                        it1,
                        it.title,
                        it.meaning,
                        it.synonym,
                        it.details
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
        private var newWordFragmentInstance: NewWordFragment? = null

        //This function allow the fragment to behave as a singleton
        fun getInstance(): NewWordFragment {
            if (newWordFragmentInstance == null) {
                newWordFragmentInstance = NewWordFragment()
            }

            return newWordFragmentInstance!!
        }

    }

}