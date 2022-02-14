package com.github.sookhee.sodosi.main

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.sookhee.sodosi.R
import com.github.sookhee.sodosi.common.base.BaseActivity
import com.github.sookhee.sodosi.community.CommunityFragment
import com.github.sookhee.sodosi.databinding.ActivityMainBinding
import com.github.sookhee.sodosi.home.HomeFragment
import com.github.sookhee.sodosi.mypage.MypageFragment

/**
 *  HomeFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private val homeFragment by lazy { HomeFragment() }
    private val communityFragment by lazy { CommunityFragment() }
    private val mypageFragment by lazy { MypageFragment() }
    private var activeFragment: Fragment = homeFragment

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainViewModel by viewModels()

    override fun initViews() = with(binding) {
        changeFragment(activeFragment)

        binding.bottomTab.setOnItemClickListener {
            when (it.id) {
                R.id.tabHome -> changeFragment(homeFragment)
                R.id.tabCommunity -> changeFragment(communityFragment)
                R.id.tabMypage -> changeFragment(mypageFragment)
            }
        }
    }

    override fun observeData() {

    }

    private fun changeFragment(currentFragment: Fragment) {
        val fm = supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(activeFragment)

        if (!currentFragment.isAdded) {
            fm.add(R.id.mainFragmentContainer, currentFragment, currentFragment.javaClass.simpleName)
                .show(currentFragment).commit()
        } else if (activeFragment != currentFragment) {
            fm.show(currentFragment).commit()
        } else {
            // 같은 탭 두번 누름(scroll to top)
        }

        activeFragment = currentFragment
    }
}
