package com.sodosi.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.databinding.ItemCommunityCommentBinding
import com.sodosi.domain.entity.Comment

/**
 *  CommunityCommentAdapter.kt
 *
 *  Created by Minji Jeong on 2022/02/19
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CommunityCommentAdapter :
    ListAdapter<Comment, CommunityCommentAdapter.ViewHolder>(DiffCallback()) {

    var onItemClick: ((selectedItem: Comment) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCommunityCommentBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class ViewHolder(
        private val binding: ItemCommunityCommentBinding,
        onItemClick: ((selectedItem: Comment) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: Comment) {
            binding.comment = item
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
