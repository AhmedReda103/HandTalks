package com.example.handtalks.adapters

import android.graphics.PorterDuff
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.handtalks.R
import com.example.handtalks.databinding.ItemPracticeViewBinding
import com.example.handtalks.models.PracticeItem
import java.util.concurrent.ConcurrentHashMap

class PracticeAdapter(private var items : ArrayList<Int>
                      ) : RecyclerView.Adapter<PracticeAdapter.PracticeViewHolder>() {

        private val TAG = "PracticeAdapter"

    class PracticeViewHolder(val binding:ItemPracticeViewBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        return PracticeViewHolder(ItemPracticeViewBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {

        val image = items[position]


        holder.binding.imageView.apply {
            setImageResource(image)
        }

        holder.binding.imageView.isFocused
        //Log.d(TAG, "onBindViewHolder: $char")

    }







}