package com.github.sookhee.sodosi.community

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sookhee.sodosi.databinding.ItemCommunityCommentBinding
import com.sodosi.domain.entity.Comment

/**
 *  CommunityCommentAdapter.kt
 *
 *  Created by Minji Jeong on 2022/02/19
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class CommunityCommentAdapter :
    RecyclerView.Adapter<CommunityCommentAdapter.ViewHolder>() {

    private var items: List<Comment> = emptyList()
    var onItemClick: ((selectedItem: Comment) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<Comment>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCommunityCommentBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        private val binding: ItemCommunityCommentBinding,
        onItemClick: ((selectedItem: Comment) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(
            item: Comment,
        ) {
            binding.comment = item
        }
    }
}
