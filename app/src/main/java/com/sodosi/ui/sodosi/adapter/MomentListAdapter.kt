package com.sodosi.ui.sodosi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.databinding.ItemSodosiPlaceBinding
import com.sodosi.ui.common.extensions.dp
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.common.extensions.setImageWithUrl
import com.sodosi.ui.common.extensions.setVisible
import com.sodosi.ui.sodosi.model.MomentModel

/**
 *  MomentListAdapter.kt
 *
 *  Created by Minji Jeong on 2022/05/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MomentListAdapter : ListAdapter<MomentModel, MomentListAdapter.MomentViewHolder>(diffUtil) {
    var onItemClick: ((selectedItem: MomentModel) -> Unit)? = null
    var onPhotoClick: ((imageUrlList: List<String>, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return MomentViewHolder(
            ItemSodosiPlaceBinding.inflate(inflater, parent, false),
            onItemClick,
            onPhotoClick,
        )
    }

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class MomentViewHolder(
        private val binding: ItemSodosiPlaceBinding,
        onItemClick: ((selectedItem: MomentModel) -> Unit)?,
        onPhotoClick: ((imageUrlList: List<String>, position: Int) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick

            binding.ivPhoto1.setOnClickListener {
                onPhotoClick?.invoke(binding.item?.photo ?: return@setOnClickListener, 0)
            }

            binding.ivPhoto2.setOnClickListener {
                onPhotoClick?.invoke(binding.item?.photo ?: return@setOnClickListener, 1)
            }

            binding.ivPhoto3.setOnClickListener {
                onPhotoClick?.invoke(binding.item?.photo ?: return@setOnClickListener, 2)
            }

            binding.tvPhotoLayer.setOnClickListener {
                onPhotoClick?.invoke(binding.item?.photo ?: return@setOnClickListener, 2)
            }
        }

        fun bind(item: MomentModel) {
            binding.item = item
            val photoBindingList = listOf(binding.ivPhoto1, binding.ivPhoto2, binding.ivPhoto3)
            val padding = 4.dp
            when (item.photo.size) {
                0 -> {
                    binding.photoLayout.setGone()
                }

                1 -> {
                    binding.secondLayout.setGone()
                    binding.thirdLayout.setGone()
                }

                2 -> {
                    binding.thirdLayout.setGone()
                    binding.secondLayout.setPadding(padding, 0, 0, 0)
                }

                3 -> {
                    binding.secondLayout.setPadding(padding, 0, 0, 0)
                    binding.thirdLayout.setPadding(0, padding, 0, 0)
                }
                else -> {
                    binding.secondLayout.setPadding(padding, 0, 0, 0)
                    binding.thirdLayout.setPadding(0, padding, 0, 0)

                    binding.tvPhotoLayer.setVisible()
                    binding.tvPhotoLayer.text = "+${item.photo.size - 3}"
                }
            }

            item.photo.forEachIndexed { index, url ->
                if (index < 3) {
                    photoBindingList[index].setImageWithUrl(url)
                }
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MomentModel>() {
            override fun areItemsTheSame(oldItem: MomentModel, newItem: MomentModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MomentModel, newItem: MomentModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
