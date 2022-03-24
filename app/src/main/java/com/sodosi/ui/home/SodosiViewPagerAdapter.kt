package com.sodosi.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.R
import com.sodosi.databinding.ItemSodosiViewpagerBinding
import com.sodosi.domain.entity.Sodosi
import com.sodosi.util.LogUtil
import java.lang.Exception

/**
 *  SodosiViewPagerAdapter.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiViewPagerAdapter :
    ListAdapter<Sodosi, SodosiViewPagerAdapter.ViewHolder>(DiffCallback()) {
    var onItemClick: ((selectedItem: Sodosi) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemSodosiViewpagerBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position % currentList.size])
    }

    override fun getItemCount(): Int {
        return if (currentList.size < 2) currentList.size else Integer.MAX_VALUE
    }

    inner class ViewHolder(
        private val binding: ItemSodosiViewpagerBinding,
        onItemClick: ((selectedItem: Sodosi) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: Sodosi) {
            binding.item = item

            binding.tvEmoji.visibility = View.GONE
            when (item.icon) {
                "cafe" -> binding.ivSodosi.setImageResource(R.drawable.sodosi_viewpager_cafe)
                "camera" -> binding.ivSodosi.setImageResource(R.drawable.sodosi_viewpager_camera)
                "danger" -> binding.ivSodosi.setImageResource(R.drawable.sodosi_viewpager_danger)
                "dog" -> binding.ivSodosi.setImageResource(R.drawable.sodosi_viewpager_dog)
                else -> {
                    binding.ivSodosi.setImageResource(R.drawable.sodosi_viewpager_flag)
                    try {
                        binding.tvEmoji.text = item.icon
                        binding.tvEmoji.visibility = View.VISIBLE
                    } catch (e: Exception) {
                        LogUtil.e("${e.message}", "${SodosiAdapter::class.simpleName}")
                    }
                }
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
