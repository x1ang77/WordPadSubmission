package com.xiangze.wordpadsubmission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xiangze.wordpadsubmission.viewModels.DetailsViewModel
import com.xiangze.wordpadsubmission.databinding.FragmentDetailsBinding
import kotlin.jvm.internal.Intrinsics

class DetailsFragment : Fragment() {
    /* access modifiers changed from: private */
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Intrinsics.checkNotNullParameter(view, "view")
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWordById(
           0
        )
        viewModel.word.observe(viewLifecycleOwner) {

        }
    }
}