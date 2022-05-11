package com.sodosi.ui.sodosi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.databinding.ItemSodosiPlaceBinding
import com.sodosi.ui.sodosi.model.PlaceModel

/**
 *  PlaceListAdapter.kt
 *
 *  Created by Minji Jeong on 2022/05/12
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class PlaceListAdapter : ListAdapter<PlaceModel, PlaceListAdapter.PlaceViewHolder>(diffUtil) {
    var onItemClick: ((selectedItem: PlaceModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return PlaceViewHolder(
            ItemSodosiPlaceBinding.inflate(inflater, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class PlaceViewHolder(
        private val binding: ItemSodosiPlaceBinding,
        onItemClick: ((selectedItem: PlaceModel) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: PlaceModel) {
            binding.item = item

        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PlaceModel>() {
            override fun areItemsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
