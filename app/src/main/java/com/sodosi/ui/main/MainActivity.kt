package com.sodosi.ui.main

import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sodosi.R
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.databinding.ActivityMainBinding
import com.sodosi.ui.home.HomeFragment
import com.sodosi.ui.mypage.MypageFragment

/**
 *  MainActivity.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private val homeFragment by lazy { HomeFragment() }
    private val mypageFragment by lazy { MypageFragment() }
    private var activeFragment: Fragment = homeFragment

    private var backPressWaitTime = 0L

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainViewModel by viewModels()

    override fun initViews() = with(binding) {
        changeFragment(activeFragment)

        binding.bottomTab.setOnItemClickListener {
            when (it.id) {
                R.id.tabHome -> changeFragment(homeFragment)
                R.id.tabCreateMap -> Toast.makeText(this@MainActivity, "CREATE MAP", Toast.LENGTH_SHORT).show()
                R.id.tabMypage -> changeFragment(mypageFragment)
            }
        }
    }

    override fun observeData() {

    }

    override fun onBackPressed() {
        when {
            System.currentTimeMillis() - backPressWaitTime >= 1500 -> {
                backPressWaitTime = System.currentTimeMillis()
                Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    fun changeFragment(currentFragment: Fragment) {
        val fm = supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(activeFragment)

        if (!currentFragment.isAdded) {
            fm.add(
                R.id.mainFragmentContainer,
                currentFragment,
                currentFragment.javaClass.simpleName
            )
                .show(currentFragment).commit()
        } else if (activeFragment != currentFragment) {
            fm.show(currentFragment).commit()
        } else {
            // 같은 탭 두번 누름(scroll to top)
        }

        activeFragment = currentFragment
    }
}
