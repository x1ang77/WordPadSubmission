package com.chiaching.wordpad_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.chiaching.wordpad_project.MyApplication
import com.chiaching.wordpad_project.R
import com.chiaching.wordpad_project.databinding.FragmentUpdateWordBinding
import com.chiaching.wordpad_project.model.Word
import com.chiaching.wordpad_project.viewModels.UpdateWordViewModel

class UpdateWordFragment : Fragment() {
    private lateinit var binding: FragmentUpdateWordBinding

    val viewModel: UpdateWordViewModel by viewModels{
        UpdateWordViewModel.Provider(
            (requireContext().applicationContext as MyApplication).wordRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateWordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navArgs: UpdateWordFragmentArgs by navArgs()

        viewModel.getWordById(navArgs.id)

        viewModel.word.observe(viewLifecycleOwner){
            binding.run {
                etTitle.setText(it.title)
                etMeaning.setText(it.meaning)
                etSynonyms.setText(it.synonyms)
                etDetails.setText(it.details)
                tvStatus.setText(it.status.toString())
            }

        }

        binding.btnEdit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val meaning = binding.etMeaning.text.toString()
            val synonyms = binding.etSynonyms.text.toString()
            val details = binding.etDetails.text.toString()
            val status = binding.tvStatus.text.toString().toBoolean()
            val word = Word(navArgs.id, title, meaning, synonyms, details, status)
            viewModel.updateWord(navArgs.id, word)
            val bundle = Bundle()
            bundle.putBoolean("refresh",true)
            setFragmentResult("from_details",bundle)
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

}