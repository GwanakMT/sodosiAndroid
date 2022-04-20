package com.sodosi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.databinding.ItemSodosiTypeRectangleBinding
import com.sodosi.databinding.ItemSodosiTypeSquareBinding
import com.sodosi.domain.entity.Sodosi
import com.sodosi.util.LogUtil
import java.lang.Exception

/**
 *  SodosiAdapter.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiAdapter : ListAdapter<Sodosi, RecyclerView.ViewHolder>(DiffCallback()) {
    enum class ViewType { RECTANGLE, SQUARE }

    var itemViewType: ViewType = ViewType.RECTANGLE
    var onItemClick: ((selectedItem: Sodosi) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (itemViewType) {
            ViewType.RECTANGLE -> RectangleViewHolder(
                ItemSodosiTypeRectangleBinding.inflate(
                    inflater,
                    parent,
                    false
                ), onItemClick
            )
            ViewType.SQUARE -> SquareViewHolder(
                ItemSodosiTypeSquareBinding.inflate(
                    inflater,
                    parent,
                    false
                ), onItemClick
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(itemViewType) {
            ViewType.RECTANGLE -> (holder as RectangleViewHolder).bind(currentList[position])
            ViewType.SQUARE -> (holder as SquareViewHolder).bind(currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class RectangleViewHolder(
        private val binding: ItemSodosiTypeRectangleBinding,
        onItemClick: ((selectedItem: Sodosi) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: Sodosi) {
            binding.item = item
            try {
                binding.tvEmoji.text = item.icon
            } catch (e: Exception) {
                LogUtil.e("${e.message}", "${SodosiAdapter::class.simpleName}")
            }
        }
    }

    inner class SquareViewHolder(
        private val binding: ItemSodosiTypeSquareBinding,
        onItemClick: ((selectedItem: Sodosi) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: Sodosi) {
            binding.item = item
            try {
                binding.tvEmoji.text = item.icon
            } catch (e: Exception) {
                LogUtil.e("${e.message}", "${SodosiAdapter::class.simpleName}")
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Sodosi>() {
        override fun areItemsTheSame(oldItem: Sodosi, newItem: Sodosi): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Sodosi, newItem: Sodosi): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
