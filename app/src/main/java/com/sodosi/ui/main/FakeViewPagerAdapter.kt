package com.sodosi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.databinding.ItemSodosiViewpagerBinding
import com.sodosi.model.SodosiModel

/**
 *  FakeViewPagerAdapter.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class FakeViewPagerAdapter : ListAdapter<SodosiModel, FakeViewPagerAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemSodosiViewpagerBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    inner class ViewHolder(binding: ItemSodosiViewpagerBinding, ) : RecyclerView.ViewHolder(binding.root) {
        init {

        }

        fun bind(item: SodosiModel) {

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
