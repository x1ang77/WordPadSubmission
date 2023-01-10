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
import com.chiaching.wordpad_project.databinding.FragmentDetailsBinding
import com.chiaching.wordpad_project.viewModels.DetailsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navArgs: DetailsFragmentArgs by navArgs()

        viewModel.getWordById(navArgs.id)
        viewModel.word.observe(viewLifecycleOwner){
            binding.run {
                tvTitle.text = it.title
                tvMeaning.text = it.meaning
                tvSynonyms.text = it.synonyms
                tvDetails.text = it.details
                tvStatus.text = it.status.toString()
            }
            binding.btnUpdate.setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsFragmentToUpdateWordFragment(navArgs.id)
                NavHostFragment.findNavController(this).navigate(action)
            }
            val status = binding.tvStatus.text.toString()
            binding.btnDone.visibility = if(status == "false") View.VISIBLE else View.GONE
        }



        binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.DataBinding_AlertDialog)
                .setTitle("Are you sure?")
                .setMessage("You want to delete this word? Action can not be undone.")
                .setPositiveButton("Delete") { _, _ ->
                    viewModel.deleteWord(navArgs.id)
                    val bundle = Bundle()
                    bundle.putBoolean("refresh",true)
                    setFragmentResult("from_details",bundle)
                    NavHostFragment.findNavController(this).popBackStack()
                }
                .setNegativeButton("Cancel"){ _,_->

                }.create()
                .show()


        }

        binding.btnDone.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.Done_AlertDialog)
                .setTitle("Are you sure?")
                .setMessage("Do you want to move this word to completed list")
                .setPositiveButton("Confirm") { _, _ ->
                    viewModel.completedWord(navArgs.id)
                    val bundle = Bundle()
                    bundle.putBoolean("refresh", true)
                    setFragmentResult("from_done", bundle)
                    NavHostFragment.findNavController(this).popBackStack()
                }
                .setNegativeButton("Cancel") { _, _ ->

                }.create()
                .show()
        }
    }

}