package com.sodosi.ui.sodosi.bottomsheet

import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sodosi.databinding.FragmentPlaceBottomSheetBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.post.ZoomPhotoActivity
import com.sodosi.ui.sodosi.CreateMomentActivity
import com.sodosi.ui.sodosi.SodosiActivity
import com.sodosi.ui.sodosi.SodosiViewModel
import com.sodosi.ui.sodosi.adapter.PlaceListAdapter
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
            moveToSearchPlaceActivityWithLocation?.invoke()
        }
    }

    private fun initPlaceRecyclerView() {
        binding.placeRecyclerView.apply {
            adapter = placeAdapter.apply {
                onItemClick = {
                    (requireActivity() as SodosiActivity).showMomentBottomSheet(it)
                }
                onPhotoClick = { imageUrlList, position ->
                    val intent = ZoomPhotoActivity.getIntent(requireContext(), position, imageUrlList.map { it.toUri() })
                    startActivity(intent)
                }
            }
        }
    }

    companion object {
        private var moveToSearchPlaceActivityWithLocation: (() -> Unit)? = null

        fun newInstance(moveToSearchPlaceActivityWithLocation: () -> Unit): PlaceBottomSheetFragment {
            this.moveToSearchPlaceActivityWithLocation = moveToSearchPlaceActivityWithLocation
            return PlaceBottomSheetFragment()
        }
    }
}
