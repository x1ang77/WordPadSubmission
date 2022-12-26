package com.nathalie.wordpad.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.nathalie.wordpad.MainActivity
import com.nathalie.wordpad.Model.Word
import com.nathalie.wordpad.MyApplication
import com.nathalie.wordpad.R
import com.nathalie.wordpad.databinding.FragmentAddWordBinding
import com.nathalie.wordpad.viewModels.AddWordViewModel

class AddWordFragment : Fragment() {
    private lateinit var binding: FragmentAddWordBinding
    private val viewModel: AddWordViewModel by viewModels {
        AddWordViewModel.Provider((requireActivity() as MainActivity).wordRepo)
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
            val synonym = binding.etSynonym.text.toString()
            val details = binding.etDetails.text.toString()

            if (validate(title, meaning, synonym, details)) {
                viewModel.addWord(Word(null, title, meaning, synonym, details))
                val bundle = Bundle()
                bundle.putBoolean("refresh", true)
                setFragmentResult("from_add_item", bundle)
                NavHostFragment.findNavController(this).popBackStack()
            } else {
                val snackbar =
                    Snackbar.make(
                        binding.root,
                        "Make sure you fill in everything!",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.show()
            }
        }
    }

    private fun validate(vararg list: String): Boolean {
        for (field in list) {
            if (field.isEmpty()) {
                return false
            }
        }
        return true
    }
}