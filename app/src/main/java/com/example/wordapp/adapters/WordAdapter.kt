package com.example.wordapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordapp.databinding.ItemLayoutWordBinding
import com.example.wordapp.data.models.Word
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

// This class is an adapter for handling each unit of Word item.
// This adapter acts as a bridge between an AdapterView and data for that view, that is the data from Word model for that view.
// The adapter provides access to the Word data items and is responsible for creating a view for each item in the data set.
class WordAdapter(private var items: List<Word>, val onClick: (word: Word) -> Unit) :
    RecyclerView.Adapter<WordAdapter.WordHolder>() {

    // This inner class acts as the layout for each unit of Word data.
    inner class WordHolder(val binding: ItemLayoutWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // This function binds the layout to the adapter.
        fun bind() {
            val item = items[adapterPosition]
            binding.run {
                tvWord.text = item.word
                tvMeaning.text = item.meaning
                cvWord.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val binding =
            ItemLayoutWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordHolder(binding)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // This function enables the RecyclerView to refresh the data set every time a datum is created, updated, or deleted.
    fun setWords(items: List<Word>) {
        this.items = items
        notifyDataSetChanged()
    }
}