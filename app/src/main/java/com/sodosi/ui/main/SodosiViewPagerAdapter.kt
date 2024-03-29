package com.sodosi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.R
import com.sodosi.databinding.ItemSodosiViewpagerBinding
import com.sodosi.model.SodosiModel

/**
 *  SodosiViewPagerAdapter.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiViewPagerAdapter :
    ListAdapter<SodosiModel, SodosiViewPagerAdapter.ViewHolder>(DiffCallback()) {
    var onItemClick: ((selectedItem: SodosiModel) -> Unit)? = null
    var onBookMarkClick: ((selectedItem: SodosiModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemSodosiViewpagerBinding.inflate(inflater, parent, false), onItemClick, onBookMarkClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position % currentList.size], position)
    }

    override fun getItemCount(): Int {
        return if (currentList.size < 2) currentList.size else Integer.MAX_VALUE
    }

    inner class ViewHolder(
        private val binding: ItemSodosiViewpagerBinding,
        onItemClick: ((selectedItem: SodosiModel) -> Unit)?,
        onBookMarkClick: ((selectedItem: SodosiModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
            binding.ivBookmark.setOnClickListener {
                binding.item?.let {
                    toggleBookMark(it)
                }
            }
        }

        fun bind(item: SodosiModel, position: Int) {
            binding.item = item

            setBookMarkIcon(item)

            when (item.icon) {
                "cafe" -> {
                    binding.ivSodosi.setImageResource(R.drawable.sodosi_viewpager_cafe)
                    binding.cardViewBackground.setCardBackgroundColor(ContextCompat.getColor(binding.cardViewBackground.context, R.color.indigo))
                }
                "camera" -> {
                    binding.ivSodosi.setImageResource(R.drawable.sodosi_viewpager_camera)
                    binding.cardViewBackground.setCardBackgroundColor(ContextCompat.getColor(binding.cardViewBackground.context, R.color.green800))
                }
                "danger" -> {
                    binding.ivSodosi.setImageResource(R.drawable.sodosi_viewpager_danger)
                    binding.cardViewBackground.setCardBackgroundColor(ContextCompat.getColor(binding.cardViewBackground.context, R.color.orange))
                }
                "dog" -> {
                    binding.ivSodosi.setImageResource(R.drawable.sodosi_viewpager_dog)
                    binding.cardViewBackground.setCardBackgroundColor(ContextCompat.getColor(binding.cardViewBackground.context, R.color.yellow))
                }
                else -> {

                }
            }
        }

        private fun toggleBookMark(item: SodosiModel) {
            // copy로 값 변경하지 않고 우선 PatchMarkSodosiUseCase로
            binding.item?.let { onBookMarkClick?.invoke(it) }

            // isMarked 값 및 아이콘 변경
            val updatedItem = item.copy(isMarked = !item.isMarked)
            binding.item = updatedItem

            setBookMarkIcon(updatedItem)
        }

        private fun setBookMarkIcon(item: SodosiModel) {
            if (item.isMarked) {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_selected)
            } else {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_unselected)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<SodosiModel>() {
        override fun areItemsTheSame(oldItem: SodosiModel, newItem: SodosiModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SodosiModel, newItem: SodosiModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
