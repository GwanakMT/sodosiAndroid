package com.sodosi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.R
import com.sodosi.databinding.ItemSodosiViewpagerBinding
import com.sodosi.domain.entity.Sodosi

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
        holder.bind(currentList[position % currentList.size], position)
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

        fun bind(item: Sodosi, position: Int) {
            binding.item = item

//            binding.tvIndicator.text = "${position % currentList.size + 1}/${currentList.size}"
//            binding.tvEmoji.visibility = View.GONE

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
