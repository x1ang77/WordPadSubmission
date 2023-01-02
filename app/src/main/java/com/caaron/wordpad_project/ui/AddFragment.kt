package com.caaron.wordpad_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.caaron.wordpad_project.MyApplication
import com.caaron.wordpad_project.R
import com.caaron.wordpad_project.databinding.FragmentAddBinding
import com.caaron.wordpad_project.model.Word
import com.caaron.wordpad_project.viewModels.AddViewModel
import com.google.android.material.snackbar.Snackbar

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private val viewModel: AddViewModel by viewModels {
        AddViewModel.Provider((requireContext().applicationContext as MyApplication).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val meaning = binding.etMeaning.text.toString()
            val synonyms = binding.etSynonyms.text.toString()
            val details = binding.etDetails.text.toString()

            if (title.isEmpty() || meaning.isEmpty() || synonyms.isEmpty() || details.isEmpty()) {
                val snackBar =
                    Snackbar.make(binding.root, "Please enter all the values", Snackbar.LENGTH_LONG)
                snackBar.show()
            } else {
                viewModel.addWord(Word(null, title, meaning, synonyms,details,false))
                val bundle = Bundle()
                bundle.putBoolean("refresh", true)
                setFragmentResult("from_add_item", bundle)
                NavHostFragment.findNavController(this).popBackStack()
            }
        }
    }

}