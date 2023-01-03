package com.xiangze.wordpadsubmission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xiangze.wordpadsubmission.R
import kotlin.jvm.internal.Intrinsics

class UpdateWordFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Intrinsics.checkNotNullParameter(inflater, "inflater")
        return inflater.inflate(R.layout.fragment_update_word, container, false)
    }
}