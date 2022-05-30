package com.sodosi.ui.sodosi

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sodosi.databinding.FragmentPlaceBottomSheetBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.sodosi.adapter.PlaceListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *  MomentBottomSheetFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class PlaceBottomSheetFragment : BaseFragment<SodosiViewModel, FragmentPlaceBottomSheetBinding>() {
    private val placeAdapter by lazy { PlaceListAdapter() }

    override val viewModel: SodosiViewModel by activityViewModels()

    override fun getViewBinding() = FragmentPlaceBottomSheetBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        setOnClickListener()
        initPlaceRecyclerView()
    }

    override fun observeData() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.placeList.collect { placeList ->
                    if (placeList.isNotEmpty()) {
                        binding.tvPlaceCount.text = placeList.size.toString()
                        placeAdapter.submitList(placeList)
                    } else {
                        // TODO: 등록된 장소 없을 때
                    }
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.etPlace.setOnClickListener {
            val intent = Intent(context, CreateMomentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initPlaceRecyclerView() {
        binding.placeRecyclerView.apply {
            adapter = placeAdapter.apply {
                onItemClick = {
                    (requireActivity() as SodosiActivity).showMomentBottomSheet(it)
                }
                onPhotoClick = { imageUrlList, position ->
                    Toast.makeText(context, "position: $position", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
