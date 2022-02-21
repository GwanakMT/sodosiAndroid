package com.sodosi.ui.community

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.sodosi.R
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.databinding.FragmentCommunityBinding
import com.google.android.material.tabs.TabLayout

/**
 *  CommunityFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CommunityFragment : BaseFragment<CommunityViewModel, FragmentCommunityBinding>() {
    private val nowFragment by lazy { CommunityNowFragment() }
    private val hotFragment by lazy { CommunityHotFragment() }
    private var activeFragment: Fragment = nowFragment

    override fun getViewBinding() = FragmentCommunityBinding.inflate(layoutInflater)

    override val viewModel: CommunityViewModel by viewModels()

    override fun initViews(): Unit = with(binding) {
        binding.communityTabLayout.apply {
            addTab(binding.communityTabLayout.newTab().apply {
                setText(R.string.community_title_now)
            })

            addTab(binding.communityTabLayout.newTab().apply {
                setText(R.string.community_title_hot)
            })

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> changeFragment(nowFragment)
                        else -> changeFragment(hotFragment)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }

        changeFragment(activeFragment)
    }

    override fun observeData() {

    }

    private fun changeFragment(currentFragment: Fragment) {
        val fm = childFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(activeFragment)

        if (!currentFragment.isAdded) {
            fm.add(
                R.id.communityFragmentContainer,
                currentFragment,
                currentFragment.javaClass.simpleName
            )
                .show(currentFragment).commit()
        } else if (activeFragment != currentFragment) {
            fm.show(currentFragment).commit()
        }

        activeFragment = currentFragment
    }
}
