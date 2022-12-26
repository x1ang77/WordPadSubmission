package com.nathalie.wordpad.ui

import android.os.Bundle
import android.telecom.Call.Details
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.nathalie.wordpad.MyApplication
import com.nathalie.wordpad.R
import com.nathalie.wordpad.databinding.FragmentDetailsBinding
import com.nathalie.wordpad.databinding.FragmentWordsBinding
import com.nathalie.wordpad.viewModels.DetailsViewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Provider((requireContext().applicationContext as MyApplication).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navArgs: DetailsFragmentArgs by navArgs()

        viewModel.getWordById(navArgs.id)

        viewModel.word.observe(viewLifecycleOwner) {
            binding.run {
                tvTitle.text = it.title
                tvMeaning.text = it.meaning
                tvDetails.text = it.details
                tvSynonym.text = it.synonym
            }
        }

        binding.btnDone.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

}