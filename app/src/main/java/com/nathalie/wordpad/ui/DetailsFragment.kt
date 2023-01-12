package com.nathalie.wordpad.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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


        //find word that matches the id passed from navArgs
        viewModel.getWordById(navArgs.id)

        //display word;s title, meaning, details and synonym
        viewModel.word.observe(viewLifecycleOwner) {
            if (it.status) binding.btnDone.isVisible = false
            binding.run {
                tvTitle.text = it.title
                tvMeaning.text = it.meaning
                tvDetails.text = it.details
                tvSynonym.text = it.synonym
            }
        }

        //when btn done is clicked, change word's status from 'false' to 'true'
        binding.btnDone.setOnClickListener {
            viewModel.changeStatus(navArgs.id)
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult("from_details", bundle)
            NavHostFragment.findNavController(this).popBackStack()
        }

        //when this btn is clicked, go to update word fragment
        binding.btnUpdate.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsToUpdateWord(navArgs.id)
            NavHostFragment.findNavController(this).navigate(action)
        }


        //when this btn is clicked, a dialog will pop up and ask for confirmation to delete word
        //if confirmed, word will be deleted. dialog will close is cancel btn is clicked
        binding.btnDelete.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            MaterialAlertDialogBuilder(requireContext(), R.style.WordPad_AlertDialog)
                .setTitle(binding.tvTitle.text).setMessage(binding.tvMeaning.text)
                .setCancelable(true)
                .setPositiveButton("Delete") { _, it ->
                    viewModel.deleteWord(navArgs.id)
                    setFragmentResult("from_details", bundle)
                    NavHostFragment.findNavController(this).popBackStack()
                }.setNegativeButton("Cancel") { _, it ->
                }
                .show()
        }
    }
}
