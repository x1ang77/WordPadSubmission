package com.caaron.wordpad_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.caaron.wordpad_project.MyApplication
import com.caaron.wordpad_project.R
import com.caaron.wordpad_project.databinding.FragmentUpdateBinding
import com.caaron.wordpad_project.data.model.Word
import com.caaron.wordpad_project.viewModels.EditDeleteViewModel

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    val viewModel: EditDeleteViewModel by viewModels {
        EditDeleteViewModel.Provider(
            (requireContext().applicationContext as MyApplication).wordRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navArgs: UpdateFragmentArgs by navArgs()
        viewModel.getWordById(navArgs.id)
        viewModel.word.observe(viewLifecycleOwner) {
            binding.run {
                etTitle.setText(it.title)
                etMeaning.setText(it.meaning)
                etSynonyms.setText(it.synonym)
                etDetails.setText(it.details)
            }
        }

        binding.btnUpdate.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val meaning = binding.etMeaning.text.toString()
            val synonyms = binding.etSynonyms.text.toString()
            val details = binding.etDetails.text.toString()

            val word = Word(navArgs.id, title, meaning, synonyms, details,false)
            viewModel.updateWord(navArgs.id, word)
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult("from_edit", bundle)
            NavHostFragment.findNavController(this).popBackStack()
//            val action = UpdateFragmentDirections.actionUpdateToMain()
//            NavHostFragment.findNavController(this).navigate(action)
        }
    }

}