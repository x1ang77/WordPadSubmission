package com.nathalie.wordpad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nathalie.wordpad.Model.Word
import com.nathalie.wordpad.databinding.ItemLayoutWordBinding

class WordAdapter(
    private var items: List<Word>,
    val onClick: (item: Word) -> Unit
) : RecyclerView.Adapter<WordAdapter.ItemWordHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemWordHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutWordBinding.inflate(layoutInflater, parent, false)
        return ItemWordHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemWordHolder, position: Int) {
        val item = items[position]
        holder.binding.run {
            tvTitle.text = item.title
            tvMeaning.text = item.meaning

            cvWordItem.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setWords(items: List<Word>) {
        this.items = items
        notifyDataSetChanged()
    }


    class ItemWordHolder(val binding: ItemLayoutWordBinding) : RecyclerView.ViewHolder(binding.root)
}