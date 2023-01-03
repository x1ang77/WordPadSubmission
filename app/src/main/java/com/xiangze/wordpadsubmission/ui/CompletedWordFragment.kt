package com.xiangze.wordpadsubmission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xiangze.wordpadsubmission.R
import kotlin.jvm.internal.Intrinsics

class CompletedWordFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Intrinsics.checkNotNullParameter(inflater, "inflater")
        return inflater.inflate(R.layout.fragment_completed_word, container, false)
    }

    companion object {
        var completedWordsFragmentInstance: CompletedWordFragment? = null

        fun getInstance(): CompletedWordFragment {
            if (completedWordsFragmentInstance == null) {
                completedWordsFragmentInstance = CompletedWordFragment()
            }

            return completedWordsFragmentInstance!!
        }
    }
}