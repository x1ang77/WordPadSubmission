package com.chiaching.wordpad_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chiaching.wordpad_project.databinding.ItemLayoutWordBinding
import com.chiaching.wordpad_project.data.model.Word

class WordAdapter (var items: List<Word>, val onClick:(item: Word) -> Unit):RecyclerView.Adapter<WordAdapter.ItemWordHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemWordHolder {
        val binding = ItemLayoutWordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemWordHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemWordHolder, position: Int) {
        val item = items[position]
        holder.binding.run {
            tvTitle.text = item.title
            tvDescription.text = item.details
            cvTaskItem.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount() = items.size

    fun setWords(items: List<Word> ){
        this.items = items
        notifyDataSetChanged()
    }

    class ItemWordHolder(val binding: ItemLayoutWordBinding): RecyclerView.ViewHolder(binding.root)
}