package com.example.handtalks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.handtalks.databinding.ItemCardLanguageAdapterBinding
import com.example.handtalks.models.AlphapetsItem


class CardsLanguageAdapter(private val items : List<AlphapetsItem> , private val listener : CardsInteractionClickListener) : RecyclerView.Adapter<CardsLanguageAdapter.ViewHolder>(){

    class ViewHolder( val binding : ItemCardLanguageAdapterBinding ) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCardLanguageAdapterBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.binding.numberOfLettersItemCard.text = data.letters
        holder.binding.titleItemCardLang.text = data.title

        holder.binding.root.setOnClickListener {
            listener.onItemClick(position)
        }
    }
}