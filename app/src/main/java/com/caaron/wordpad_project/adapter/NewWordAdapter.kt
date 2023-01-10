package com.caaron.wordpad_project.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caaron.wordpad_project.databinding.ItemLayoutFileBinding
import com.caaron.wordpad_project.data.model.Word
import java.io.File
import java.util.*

class NewWordAdapter(
    private var words: List<Word>,
    val onClick: (item: Word) -> Unit
) : RecyclerView.Adapter<NewWordAdapter.NewWordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewWordViewHolder {
        val binding = ItemLayoutFileBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NewWordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewWordViewHolder, position: Int) {
        val item = words[position]
        holder.binding.run {
            title.text =
                item.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            definition.text =
                item.meaning.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            cvWord.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount() = words.size

//    @JvmName("setWords1")
    fun setWords(items: List<Word>) {
        this.words = items
        notifyDataSetChanged()
    }

    class NewWordViewHolder(val binding: ItemLayoutFileBinding) :
        RecyclerView.ViewHolder(binding.root)
}