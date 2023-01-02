package com.caaron.wordpad_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.caaron.wordpad_project.adapter.NewWordAdapter
import com.caaron.wordpad_project.adapter.ViewPagerAdapter
import com.caaron.wordpad_project.databinding.FragmentMainBinding
import com.caaron.wordpad_project.viewModels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val newWordFragment = NewWordFragment.getInstance()
    private val completedFragment = CompleteWordFragment.getInstance()
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

        val adapter = ViewPagerAdapter(
            listOf(newWordFragment, completedFragment),
            childFragmentManager,
            lifecycle
        )

        binding.vpWord.adapter = adapter

        TabLayoutMediator(binding.tlWord, binding.vpWord) { tab, pos ->
            tab.text = when (pos) {
                0 -> "New"
                else -> "Completed"
            }
        }.attach()

        setFragmentResultListener("from_add_item") { _, result ->
            val refresh = result.getBoolean("refresh")
            viewModel.shouldRefreshWords(refresh)
        }

        setFragmentResultListener("from_details") { _, result ->
            val refresh = result.getBoolean("refresh")
            viewModel.shouldRefreshWords(refresh)
            viewModel.shouldRefreshCompletedWords(refresh)
        }

        setFragmentResultListener("from_done") { _, result ->
            val refresh = result.getBoolean("refresh")
            viewModel.shouldRefreshWords(refresh)
            viewModel.shouldRefreshCompletedWords(refresh)
        }

        setFragmentResultListener("from_delete") { _, result ->
            val refresh = result.getBoolean("refresh")
            viewModel.shouldRefreshWords(refresh)
            viewModel.shouldRefreshCompletedWords(refresh)
        }

        setFragmentResultListener("from_edit") { _, result ->
            val refresh = result.getBoolean("refresh")
            viewModel.shouldRefreshWords(refresh)
            viewModel.shouldRefreshCompletedWords(refresh)
        }
    }

}