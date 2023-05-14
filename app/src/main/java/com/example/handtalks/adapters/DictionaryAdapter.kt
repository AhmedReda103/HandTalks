package com.example.handtalks.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.handtalks.R
import com.example.handtalks.databinding.DictionaryItemBinding
import com.example.handtalks.models.DictionaryItem

class DictionaryAdapter(private val dictionaryItem: List<DictionaryItem>) :
    RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {
    private var onItemClickListener: ((DictionaryItem) -> Unit)? = null
    fun setOnItemClickListener(listener: (DictionaryItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DictionaryViewHolder {
        return DictionaryViewHolder(
            DictionaryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        // holder.bind(dictionaryItem[position])

        val dictionaryItem = dictionaryItem[position]
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(dictionaryItem) }


            }

        }
        holder.binding.letterName.text = dictionaryItem.letterName
    }

    override fun getItemCount(): Int = dictionaryItem.size


    class DictionaryViewHolder(val binding: DictionaryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

//        private val letter = view.findViewById<TextView>(R.id.letter_name)
//        private val letterImage = view.findViewById<ImageView>(R.id.leter_img)


//        fun bind(dictionaryItem: DictionaryItem){
//            letter.text = dictionaryItem.letterName
//            Glide.with(this).load(dictionaryItem.letterImage).into(letterImage)
//
//        }

}