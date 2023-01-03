package com.xiangze.wordpadsubmission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.google.android.material.snackbar.Snackbar
import com.xiangze.wordpadsubmission.MyApplication
import com.xiangze.wordpadsubmission.databinding.FragmentAddWordBinding
import com.xiangze.wordpadsubmission.models.Word
import com.xiangze.wordpadsubmission.viewModels.AddWordViewModel


class AddWordFragment : Fragment() {
    private lateinit var binding: FragmentAddWordBinding
    private val viewModel: AddWordViewModel by viewModels {
        AddWordViewModel.Provider(repo = (requireActivity().application as MyApplication).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            val title: String = binding.etTitle.text.toString()
            val meaning: String = binding.etMeaning.text.toString()
            val synonym: String = binding.etSynonym.text.toString()
            val details: String = binding.etDetails.text.toString()

            val word = Word(null, title, meaning, synonym, details)
            viewModel.addWord(word)

            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult( "from_add_item", bundle)
            findNavController(this).popBackStack()
        }

        val snackbar = Snackbar.make(
            binding.root,
            "Not all inputs filled", Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }
}