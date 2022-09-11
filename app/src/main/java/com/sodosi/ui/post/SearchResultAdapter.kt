package com.sodosi.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.databinding.ItemSearchPlaceBinding
import com.sodosi.model.POIDataModel

/**
 *  SearchResultAdapter.kt
 *
 *  Created by Minji Jeong on 2022/09/11
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SearchResultAdapter : ListAdapter<POIDataModel, SearchResultAdapter.ViewHolder>(diffUtil) {
    var onItemClick: ((selectedItem: POIDataModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(
            ItemSearchPlaceBinding.inflate(inflater, parent, false),
            onItemClick,
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class ViewHolder(
        private val binding: ItemSearchPlaceBinding,
        onItemClick: ((selectedItem: POIDataModel) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.rootView.setOnClickListener {
                onItemClick?.invoke(binding.item ?: return@setOnClickListener)
            }
        }

        fun bind(item: POIDataModel) {
            binding.item = item
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<POIDataModel>() {
            override fun areItemsTheSame(oldItem: POIDataModel, newItem: POIDataModel): Boolean {
                return oldItem.address == newItem.address
            }

            override fun areContentsTheSame(oldItem: POIDataModel, newItem: POIDataModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
