package com.nathalie.wordpad.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.nathalie.wordpad.R
import com.nathalie.wordpad.databinding.FragmentDropDownBinding
import com.nathalie.wordpad.utils.Dropdown

class DropDownFragment : Fragment() {
    private lateinit var binding: FragmentDropDownBinding
    val values = listOf("abc", "def", "luffy", "carrot", "dragon")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDropDownBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.select_dialog_item,
            values
        )

        binding.autoComplete.setAdapter(adapter)

        Dropdown.create(
            requireContext(),
            binding.dropdown2.acDropdown,
            values
        ) {
            Log.d("debugging", it)
        }
        Dropdown.create(
            requireContext(),
            binding.dropdown.acDropdown,
            values
        ) {
            Log.d("debugging", it)
        }
    }

}