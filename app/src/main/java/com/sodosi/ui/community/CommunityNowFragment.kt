package com.sodosi.ui.community

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.databinding.FragmentCommunityNowBinding
import com.sodosi.ui.thread.ThreadActivity

/**
 *  CommunityNowFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class CommunityNowFragment : BaseFragment<CommunityViewModel, FragmentCommunityNowBinding>() {
    override fun getViewBinding() = FragmentCommunityNowBinding.inflate(layoutInflater)

    override val viewModel: CommunityViewModel by viewModels()

    override fun initViews(): Unit = with(binding) {
        initNowRecyclerView()

        viewModel.getCommunityNowComment()
    }

    override fun observeData() {
        viewModel.nowComment.asLiveData().observe(viewLifecycleOwner) {
            (binding.nowRecyclerView.adapter as CommunityCommentAdapter).submitList(it)
        }
    }

    private fun initNowRecyclerView() {
        binding.nowRecyclerView.apply {
            adapter = CommunityCommentAdapter().apply {
                onItemClick = {
                    val intent = Intent(context, ThreadActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
