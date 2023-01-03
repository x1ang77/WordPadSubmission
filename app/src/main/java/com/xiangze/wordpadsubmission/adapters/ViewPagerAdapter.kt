package com.xiangze.wordpad.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlin.jvm.internal.Intrinsics

class ViewPagerAdapter(
    fragments2: List<Fragment>,
    fragmentManager: FragmentManager?,
    lifecycle: Lifecycle?
) :
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {
    private val fragments: List<Fragment>

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    init {
        Intrinsics.checkNotNullParameter(fragments2, "fragments")
        Intrinsics.checkNotNullParameter(fragmentManager, "fragmentManager")
        Intrinsics.checkNotNullParameter(lifecycle, "lifecycle")
        fragments = fragments2
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}