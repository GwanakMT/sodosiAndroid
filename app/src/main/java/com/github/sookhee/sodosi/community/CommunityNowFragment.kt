package com.github.sookhee.sodosi.community

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentCommunityNowBinding

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
            (binding.nowRecyclerView.adapter as CommunityCommentAdapter).setItems(it)
        }
    }

    private fun initNowRecyclerView() {
        binding.nowRecyclerView.apply {
            adapter = CommunityCommentAdapter().apply {
                onItemClick = {

                }
            }
        }
    }
}
