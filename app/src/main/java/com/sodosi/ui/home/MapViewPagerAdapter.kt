package com.sodosi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.databinding.ItemMapBinding

/**
 *  MapViewPagerAdapter.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MapViewPagerAdapter : ListAdapter<MapPreview, MapViewPagerAdapter.ViewHolder>(DiffCallback()) {
    var onItemClick: ((selectedItem: MapPreview) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemMapBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position % currentList.size])
    }

    override fun getItemCount(): Int {
        return if (currentList.size < 2) currentList.size else Integer.MAX_VALUE
    }

    inner class ViewHolder(
        private val binding: ItemMapBinding,
        onItemClick: ((selectedItem: MapPreview) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: MapPreview, ) {
            binding.item = item
        }
    }

    private class DiffCallback: DiffUtil.ItemCallback<MapPreview>() {
        override fun areItemsTheSame(oldItem: MapPreview, newItem: MapPreview): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MapPreview, newItem: MapPreview): Boolean {
            // TODO : 추후 엔티티에 고유 id값 추가해서 id로 비교하기
            return oldItem.title == newItem.title
        }
    }
}
