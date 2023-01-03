package com.xiangze.wordpadsubmission.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiangze.wordpadsubmission.databinding.ItemLayoutWordBinding

import com.xiangze.wordpadsubmission.models.Word
import kotlin.jvm.internal.Intrinsics


class WordAdapter(var items: List<Word>, val onClick: (word: Word) -> Unit) :
    RecyclerView.Adapter<WordAdapter.ItemWordHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemWordHolder {
        Intrinsics.checkNotNullParameter(parent, "parent")
        val binding: ItemLayoutWordBinding =
            ItemLayoutWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Intrinsics.checkNotNullExpressionValue(binding, "inflate(layoutInflater, parent, false)")
        return ItemWordHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemWordHolder, position: Int) {
        val item: Word = items[position]
        holder.binding.run {
          tvTitle.setText(item.title)
            tvMeaning.setText(item.meaning)
            cvWordItem.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setWords(words: List<Word>) {
        Intrinsics.checkNotNullParameter(words, "words")
        items = words
        notifyDataSetChanged()
    }

    class ItemWordHolder(val binding: ItemLayoutWordBinding) : RecyclerView.ViewHolder(binding.root)


}