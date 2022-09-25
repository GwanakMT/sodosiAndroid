package com.sodosi.ui.post

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sodosi.databinding.ItemZoomPhotoBinding

/**
 *  ZoomPhotoAdapter.kt
 *
 *  Created by Minji Jeong on 2022/09/26
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class ZoomPhotoAdapter() : RecyclerView.Adapter<ZoomPhotoAdapter.PhotoViewHolder>() {
    private var photoList = emptyList<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return PhotoViewHolder(
            ItemZoomPhotoBinding.inflate(inflater, parent, false)
        )
    }

    override fun getItemCount(): Int = photoList.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    fun submit(list: List<Uri>) {
        photoList = list
        notifyDataSetChanged()
    }

    class PhotoViewHolder(
        private val binding: ItemZoomPhotoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(uri: Uri) {
            Glide.with(binding.root.context)
                .load(uri)
                .into(binding.photo)
        }
    }
}
