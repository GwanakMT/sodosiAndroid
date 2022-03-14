package com.sodosi.ui.onboarding.nickname

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.R
import com.sodosi.databinding.ItemOnboardingTermBinding
import com.sodosi.domain.entity.Terms
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *  TermsAdapter.kt
 *
 *  Created by Minji Jeong on 2022/03/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class TermsAdapter : ListAdapter<Terms, TermsAdapter.ViewHolder>(DiffCallback()) {
    var isAllowAll = MutableStateFlow(false)
    var onItemClick: ((selectedItem: Terms) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemOnboardingTermBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class ViewHolder(
        private val binding: ItemOnboardingTermBinding,
        onItemClick: ((selectedItem: Terms) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: Terms, position: Int) {
            binding.item = item
            binding.tvTerms.switchCheckDrawable(item.isAgree)
            binding.tvTerms.setOnClickListener {
                currentList[position].isAgree = !currentList[position].isAgree
                binding.tvTerms.switchCheckDrawable(currentList[position].isAgree)
            }
        }
    }

    private fun TextView.switchCheckDrawable(isAgree: Boolean) {
        if (isAgree) {
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_selected, 0, 0, 0)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_unselected, 0, 0, 0)
        }

        isAllowAll.value = currentList.all{it.isAgree}
    }

    private class DiffCallback : DiffUtil.ItemCallback<Terms>() {
        override fun areItemsTheSame(oldItem: Terms, newItem: Terms): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Terms, newItem: Terms): Boolean {
            // TODO : 추후 엔티티에 고유 id값 추가해서 id로 비교하기
            return oldItem.id == newItem.id
        }
    }
}
