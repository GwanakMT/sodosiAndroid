package com.sodosi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sodosi.R
import com.sodosi.databinding.ItemSodosiTypeHorizontalBinding
import com.sodosi.databinding.ItemSodosiTypeVerticalBinding
import com.sodosi.model.SodosiModel
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.common.extensions.setVisible
import com.sodosi.util.LogUtil

/**
 *  SodosiListAdapter.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiListAdapter : ListAdapter<SodosiModel, RecyclerView.ViewHolder>(diffUtil) {
    enum class ViewType { VERTICAL, HORIZONTAL }

    var itemViewType: ViewType = ViewType.VERTICAL
    var onItemClick: ((selectedItem: SodosiModel) -> Unit)? = null
    var onBookmarkClick: ((selectedItem: SodosiModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (itemViewType) {
            ViewType.VERTICAL -> RectangleViewHolder(
                ItemSodosiTypeVerticalBinding.inflate(
                    inflater,
                    parent,
                    false
                ), onItemClick, onBookmarkClick
            )
            ViewType.HORIZONTAL -> SquareViewHolder(
                ItemSodosiTypeHorizontalBinding.inflate(
                    inflater,
                    parent,
                    false
                ), onItemClick
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(itemViewType) {
            ViewType.VERTICAL -> (holder as RectangleViewHolder).bind(currentList[position])
            ViewType.HORIZONTAL -> (holder as SquareViewHolder).bind(currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class RectangleViewHolder(
        private val binding: ItemSodosiTypeVerticalBinding,
        onItemClick: ((selectedItem: SodosiModel) -> Unit)?,
        onBookmarkClick: ((selectedItem: SodosiModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
            if (onBookmarkClick != null) {
                binding.onBookmarkClick = onBookmarkClick
            } else {
                binding.ivBookmark.setGone()
            }
        }

        fun bind(item: SodosiModel) {
            binding.item = item
            try {
                if (item.momentImage.isNullOrEmpty()) {
                    binding.tvEmoji2.setGone()
                    binding.emojiBackground2.setGone()
                    binding.tvEmoji.setVisible()
                    binding.tvEmoji.text = item.icon

                } else {
                    binding.tvEmoji.setGone()
                    binding.emojiBackground2.setVisible()
                    binding.tvEmoji2.setVisible()
                    binding.tvEmoji2.text = item.icon
                    Glide.with(binding.root.context)
                        .load(item.momentImage)
                        .error(R.drawable.background_oval_gray)
                        .into(binding.sodosiImageView)
                }
            } catch (e: Exception) {
                LogUtil.e("${e.message}", "${SodosiListAdapter::class.simpleName}")
            }
        }
    }

    class SquareViewHolder(
        private val binding: ItemSodosiTypeHorizontalBinding,
        onItemClick: ((selectedItem: SodosiModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: SodosiModel) {
            binding.item = item
            try {
                binding.tvEmoji.text = item.icon
            } catch (e: Exception) {
                LogUtil.e("${e.message}", "${SodosiListAdapter::class.simpleName}")
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SodosiModel>() {
            override fun areItemsTheSame(oldItem: SodosiModel, newItem: SodosiModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SodosiModel, newItem: SodosiModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
