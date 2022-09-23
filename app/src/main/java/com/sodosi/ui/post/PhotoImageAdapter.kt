package com.sodosi.ui.post

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sodosi.databinding.ItemPostMomentPhotoBinding

/**
 *  PostImageAdapter.kt
 *
 *  Created by Minji Jeong on 2022/09/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class PhotoImageAdapter : ListAdapter<Uri, PhotoImageAdapter.PhotoViewHolder>(diffUtil) {
    var onPhotoClick: ((selectedUri: Uri, position: Int) -> Unit)? = null
    var onDeleteButtonClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return PhotoViewHolder(
            ItemPostMomentPhotoBinding.inflate(inflater, parent, false),
            onPhotoClick,
            onDeleteButtonClick,
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class PhotoViewHolder(
        private val binding: ItemPostMomentPhotoBinding,
        private val onPhotoClick: ((selectedItem: Uri, position: Int) -> Unit)?,
        private val onDeleteButtonClick: ((position: Int) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var uri: Uri? = null

        init {
            binding.photo.setOnClickListener {
                uri?.let { onPhotoClick?.invoke(it, adapterPosition) }
            }

            binding.deleteButton.setOnClickListener {
                onDeleteButtonClick?.invoke(adapterPosition)
            }
        }

        fun bind(uri: Uri) {
            this.uri = uri

            Glide.with(binding.root.context)
                .load(uri)
                .into(binding.photo)

            binding.photo.setOnClickListener {
                onPhotoClick?.invoke(uri, adapterPosition)
            }

            binding.deleteButton.setOnClickListener {
                onDeleteButtonClick?.invoke(adapterPosition)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem.path == newItem.path
            }
        }
    }
}
