package com.nathalie.wordpad.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nathalie.wordpad.MainActivity
import com.nathalie.wordpad.Model.Word
import com.nathalie.wordpad.MyApplication
import com.nathalie.wordpad.R
import com.nathalie.wordpad.databinding.FragmentUpdateWordBinding
import com.nathalie.wordpad.viewModels.UpdateWordViewModel

class UpdateWordFragment : Fragment() {
    private lateinit var binding: FragmentUpdateWordBinding
    val viewModel: UpdateWordViewModel by viewModels {
        UpdateWordViewModel.Provider((requireActivity() as MainActivity).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateWordBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navArgs: UpdateWordFragmentArgs by navArgs()
        var status = false

        viewModel.getWordById(navArgs.id)
        viewModel.word.observe(viewLifecycleOwner) {
            status = it.status
            binding.run {
                etTitle.setText(it.title)
                etMeaning.setText(it.meaning)
                etSynonym.setText(it.synonym)
                etDetails.setText(it.details)
            }
        }

        binding.btnAdd.setOnClickListener {
            val id = navArgs.id
            val title = binding.etTitle.text.toString()
            val meaning = binding.etMeaning.text.toString()
            val synonym = binding.etSynonym.text.toString()
            val details = binding.etDetails.text.toString()

            if (validate(title, meaning, synonym, details)) {
                val word = Word(id, title, meaning, synonym, details, status)
                viewModel.updateWord(id, word)
                val bundle = Bundle()
                bundle.putBoolean("refresh", true)
                setFragmentResult("from_edit", bundle)

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