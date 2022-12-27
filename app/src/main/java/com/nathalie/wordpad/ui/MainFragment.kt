package com.nathalie.wordpad.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.nathalie.wordpad.MainActivity
import com.nathalie.wordpad.R
import com.nathalie.wordpad.adapters.ViewPagerAdapter
import com.nathalie.wordpad.databinding.FragmentMainBinding
import com.nathalie.wordpad.viewModels.WordsViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val wordsFragment = WordsFragment.getInstance()
    private val completedWordsFragment = CompletedWordsFragment.getInstance()
    private val viewModel: WordsViewModel by viewModels {
        WordsViewModel.Provider((requireActivity() as MainActivity).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(
            listOf(wordsFragment, completedWordsFragment),
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.vpWordPad.adapter = adapter

        TabLayoutMediator(binding.tlWordPad, binding.vpWordPad) { tab, pos ->
            tab.text = when (pos) {
                0 -> "Files"
                else -> "Gallery"
            }
        }.attach()

        setFragmentResultListener("from_add_item") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                wordsFragment.refresh()
            }
        }
    }
}