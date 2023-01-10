package com.chiaching.wordpad_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.chiaching.wordpad_project.MainActivity
import com.chiaching.wordpad_project.MyApplication
import com.chiaching.wordpad_project.databinding.FragmentAddWordBinding
import com.chiaching.wordpad_project.data.model.Word
import com.chiaching.wordpad_project.viewModels.AddWordViewModel
import com.google.android.material.snackbar.Snackbar

class AddWordFragment : Fragment() {
    private lateinit var binding: FragmentAddWordBinding
    private val viewModel: AddWordViewModel by viewModels {
        AddWordViewModel.Provider((requireContext().applicationContext as MyApplication).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWordBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val meaning = binding.etMeaning.text.toString()
            val synonym = binding.etSynonyms.text.toString()
            val details = binding.etDetails.text.toString()

            if((title.isNotEmpty() && meaning.isNotEmpty() && synonym.isNotEmpty() && details.isNotEmpty())) {
                viewModel.addWord(Word(null, title, meaning, synonym, details, false))
                val bundle = Bundle()
                bundle.putBoolean("refresh", true)
                setFragmentResult("from_add_item", bundle)
                NavHostFragment.findNavController(this).popBackStack()
            }else{
                Snackbar.make(
                    binding.root,
                    "Please fill in everything to add new word!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}
