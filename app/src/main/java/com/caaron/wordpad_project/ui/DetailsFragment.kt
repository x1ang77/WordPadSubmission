package com.caaron.wordpad_project.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.caaron.wordpad_project.MyApplication
import com.caaron.wordpad_project.R
import com.caaron.wordpad_project.databinding.FragmentDetailsBinding
import com.caaron.wordpad_project.viewModels.DetailsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Provider(
            (requireContext().applicationContext as MyApplication).wordRepo
        )
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
                tvTitle.text =
                    it.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                tvMeaning.text =
                    it.meaning.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                tvSynonym.text =
                    it.synonym.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                tvDetails.text =
                    it.details.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            }
        }

        binding.btnUpdate.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsToUpdate(
                navArgs.id,
                navArgs.title,
                navArgs.meaning,
                navArgs.synonym,
                navArgs.details
            )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btnDone.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.DataBinding_AlertDialog)
                .setTitle("Are you sure?")
                .setMessage("Do you want to move this word to completed list?")
                .setNegativeButton("Cancel") { _, _ ->

                }
                .setPositiveButton("Confirm") { _, _ ->
                    viewModel.changeStatus(navArgs.id)
                    val bundle = Bundle()
                    bundle.putBoolean("refresh", true)
                    setFragmentResult("from_details", bundle)
                    NavHostFragment.findNavController(this).popBackStack()
                }.create()
                .show()
        }

        binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.DataBinding_AlertDialog)
                .setTitle("Are you sure?")
                .setMessage("You want to delete this word? Action can not be undone.")
                .setNegativeButton("Cancel") { _, _ ->

                }
                .setPositiveButton("Delete") { _, _ ->
                    viewModel.deleteWord(navArgs.id)
                    val bundle = Bundle()
                    bundle.putBoolean("refresh", true)
                    setFragmentResult("from_delete", bundle)
                    NavHostFragment.findNavController(this).popBackStack()
                }.create()
                .show()
        }

    }

}