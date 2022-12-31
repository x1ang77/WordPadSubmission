package com.chiaching.wordpad_project.adapter

import android.content.res.Resources.NotFoundException
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chiaching.wordpad_project.repository.WordRepository
import com.chiaching.wordpad_project.ui.CompletedWordFragment
import com.chiaching.wordpad_project.ui.MainFragment
import com.chiaching.wordpad_project.ui.NewWordFragment
import java.security.AlgorithmConstraints
import java.util.logging.Filter

class PagerAdapter (
    val fragments: List<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}