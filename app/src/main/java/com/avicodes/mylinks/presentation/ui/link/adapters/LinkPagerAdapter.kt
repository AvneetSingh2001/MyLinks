package com.avicodes.mylinks.presentation.ui.link.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.avicodes.mylinks.presentation.ui.link.pager.RecentLinkSummaryFragment
import com.avicodes.mylinks.presentation.ui.link.pager.TopLinkSummaryFragment

class LinkPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                TopLinkSummaryFragment()
            }
            else -> {
                RecentLinkSummaryFragment()
            }
        }
    }

}