package com.nathalie.wordpad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nathalie.wordpad.data.Model.Word
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
        //display word's title, meaning and date
        holder.binding.run {
            tvTitle.text = item.title
            tvMeaning.text = item.meaning
            tvDate.text = item.date.toString()

            cvWordItem.setOnClickListener {
                onClick(item)
            }
        }
    }

    //get the size of items
    override fun getItemCount(): Int {
        return items.size
    }

    //set words with items
    fun setWords(items: List<Word>) {
        this.items = items
        notifyDataSetChanged()
    }


    class ItemWordHolder(val binding: ItemLayoutWordBinding) : RecyclerView.ViewHolder(binding.root)
}