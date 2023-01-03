package com.xiangze.wordpadsubmission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xiangze.wordpad.adapters.ViewPagerAdapter
import com.xiangze.wordpadsubmission.MyApplication
import com.xiangze.wordpadsubmission.viewModels.HomeViewModel
import com.xiangze.wordpadsubmission.databinding.FragmentMainBinding
import kotlin.jvm.internal.Intrinsics

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val completedWordFragment = CompletedWordFragment.getInstance()
    private val homeFragment = HomeFragment.getInstance()

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Provider((requireActivity().application as MyApplication).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setFragmentResultListener("from_add_item") { _, result ->
            val refresh = result.getBoolean("refresh")
        }

        val supportFragmentManager = requireActivity().supportFragmentManager

        val adapter = ViewPagerAdapter(listOf(homeFragment, completedWordFragment), supportFragmentManager, lifecycle)

        binding.vpWordPad.adapter = adapter

        TabLayoutMediator(binding.tlWordPad, binding.vpWordPad) { tab, pos ->
            tab.text = when(pos) {
                0 -> "New Words"
                else -> "Completed Words"
            }
        }.attach()

    }

}