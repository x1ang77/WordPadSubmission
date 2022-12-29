package com.nathalie.wordpad.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nathalie.wordpad.MainActivity

import com.nathalie.wordpad.adapters.ViewPagerAdapter
import com.nathalie.wordpad.databinding.FragmentMainBinding
import com.nathalie.wordpad.viewModels.MainViewModel
import com.nathalie.wordpad.viewModels.WordsViewModel


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val wordsFragment = WordsFragment.getInstance()
    private val completedWordsFragment = CompletedWordsFragment.getInstance()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentPage = 0

        val adapter = ViewPagerAdapter(
            listOf(wordsFragment, completedWordsFragment),
            childFragmentManager,
            lifecycle
        )

        binding.vpWordPad.adapter = adapter


        binding.vpWordPad.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
            }
        })


        TabLayoutMediator(binding.tlWordPad, binding.vpWordPad) { tab, pos ->
            tab.text = when (pos) {
                0 -> "New Word"
                else -> "Completed Word"
            }
        }.attach()

        setFragmentResultListener("from_add_item") { _, result ->
            val refresh = result.getBoolean("refresh")
            viewModel.shouldRefreshWords(refresh)
        }

        setFragmentResultListener("from_edit") { _, result ->
            val refresh = result.getBoolean("refresh")
            viewModel.shouldRefreshWords(refresh)
            viewModel.shouldRefreshCompletedWords(refresh)
        }

        setFragmentResultListener("from_details") { _, result ->
            val refresh = result.getBoolean("refresh")
            viewModel.shouldRefreshWords(refresh)
            viewModel.shouldRefreshCompletedWords(refresh)
        }
    }
}