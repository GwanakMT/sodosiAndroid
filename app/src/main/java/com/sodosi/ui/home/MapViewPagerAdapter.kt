package com.sodosi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.databinding.ItemMapBinding

/**
 *  HomeFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MapViewPagerAdapter : RecyclerView.Adapter<MapViewPagerAdapter.ViewHolder>() {
    private var items: List<MapPreview> = emptyList()
    var onItemClick: ((selectedItem: MapPreview) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemMapBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position % items.size])
    }

    override fun getItemCount(): Int {
        return if (items.size < 2) items.size else Integer.MAX_VALUE
    }

    fun setItem(item: List<MapPreview>) {
        items = item
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemMapBinding,
        onItemClick: ((selectedItem: MapPreview) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(
            item: MapPreview,
        ) {
            binding.item = item
        }
    }
}