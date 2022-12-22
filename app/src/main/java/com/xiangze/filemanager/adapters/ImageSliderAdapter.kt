package com.xiangze.filemanager.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiangze.filemanager.databinding.ImageSliderBinding
import java.io.File

class ImageSliderAdapter(
    private var items: List<File>
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageSliderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = items[position]
        holder.binding.ivImage.setImageURI(Uri.fromFile(item))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ImageViewHolder(val binding: ImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root)
}