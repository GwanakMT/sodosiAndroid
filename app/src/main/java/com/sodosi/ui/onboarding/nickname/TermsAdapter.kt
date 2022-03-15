package com.sodosi.ui.onboarding.nickname

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sodosi.databinding.ItemOnboardingTermBinding
import com.sodosi.domain.entity.Terms
import androidx.recyclerview.widget.DiffUtil
import com.sodosi.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 *  TermsAdapter.kt
 *
 *  Created by Minji Jeong on 2022/03/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class TermsAdapter :
    RecyclerView.Adapter<TermsAdapter.ViewHolder>() {

    private var items: MutableList<Terms> = mutableListOf()
    var onItemClick: ((selectedItem: Terms) -> Unit)? = null

    private val _isAllowAll = MutableStateFlow<Boolean>(false)
    val isAllowAll: StateFlow<Boolean> = _isAllowAll

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemOnboardingTermBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(employees: List<Terms>) {
        val diffCallback = TermsDiffCallback(this.items, employees)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItems(): List<Terms> = items

    inner class ViewHolder(
        private val binding: ItemOnboardingTermBinding,
        onItemClick: ((selectedItem: Terms) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(terms: Terms, position: Int) {
            binding.item = terms
            binding.tvTerms.switchCheckDrawable(terms.isAgree)
            binding.tvTerms.setOnClickListener {
                items[position].isAgree = !items[position].isAgree
                binding.tvTerms.switchCheckDrawable(items[position].isAgree)
            }
        }
    }

    private fun TextView.switchCheckDrawable(isAgree: Boolean) {
        if (isAgree) {
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_interface_checked_24, 0, 0, 0)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_interface_unchecked_24, 0, 0, 0)
        }

        _isAllowAll.value = items.all { it.isAgree }
    }

    inner class TermsDiffCallback(
        private val oldTermsList: List<Terms>,
        private val newTermsList: List<Terms>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldTermsList.size

        override fun getNewListSize(): Int = newTermsList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldTermsList[oldItemPosition]
            val newItem = newTermsList[newItemPosition]

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldTermsList[oldItemPosition]
            val newItem = newTermsList[newItemPosition]

            return oldItem == newItem
        }
    }
}
