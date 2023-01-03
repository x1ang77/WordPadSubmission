package com.xiangze.wordpadsubmission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiangze.wordpadsubmission.MyApplication
import com.xiangze.wordpadsubmission.viewModels.HomeViewModel
import com.xiangze.wordpadsubmission.adapters.WordAdapter
import com.xiangze.wordpadsubmission.databinding.FragmentHomeBinding
import kotlin.jvm.internal.Intrinsics

class HomeFragment : Fragment() {
    /* access modifiers changed from: private */
    lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels{
        HomeViewModel.Provider((requireActivity().application as MyApplication).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Intrinsics.checkNotNullParameter(view, "view")
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        binding.efabAddItem.setOnClickListener {
            val action = MainFragmentDirections.actionMainToAddWord()
            NavHostFragment.findNavController(this).navigate(action)
        }

        viewModel.words.observe(viewLifecycleOwner) {
            adapter.setWords(it)
        }
    }

    fun refresh() {
        viewModel.getAllWords()
    }

    fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = WordAdapter(emptyList()) {

        }
        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = layoutManager
    }

    companion object {
        private var homeFragment: HomeFragment? = null

        fun getInstance(): HomeFragment {
            if (homeFragment == null) {
                homeFragment = HomeFragment()
            }

            return homeFragment!!
        }
    }
}