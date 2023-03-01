package com.example.handtalks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.handtalks.R
import com.example.handtalks.databinding.IntroAppItemBinding
import com.example.handtalks.ui.fragments.AppIntro.Companion.MAX_STEP

class AppIntroViewPager2Adapter : RecyclerView.Adapter<PagerVH2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH2 {
        return PagerVH2(
            IntroAppItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = MAX_STEP

    override fun onBindViewHolder(holder: PagerVH2, position: Int) =

        holder.itemView.run {

        with(holder) {
            if (position == 0) {
                bindingDesign.introTitle.text = context.getString(R.string.intro_title_1)
                bindingDesign.introDescription.text =
                    context.getString(R.string.intro_description_1)
                bindingDesign.introImage.setImageResource(R.drawable.first_image)
            }
            if (position == 1) {
                bindingDesign.introTitle.text = context.getString(R.string.intro_title_2)
                bindingDesign.introDescription.text =
                    context.getString(R.string.intro_description_2)
                bindingDesign.introImage.setImageResource(R.drawable.second_image)
            }
            if (position == 2) {
                bindingDesign.introTitle.text = context.getString(R.string.intro_title_3)
                bindingDesign.introDescription.text =
                    context.getString(R.string.intro_description_3)
                bindingDesign.introImage.setImageResource(R.drawable.third_image)
            }
            if (position == 3) {
                bindingDesign.introTitle.text = context.getString(R.string.intro_title_4)
                bindingDesign.introDescription.text =
                    context.getString(R.string.intro_description_4)
                bindingDesign.introImage.setImageResource(R.drawable.forth_image)
            }
        }
    }
}

class PagerVH2(val bindingDesign: IntroAppItemBinding) : RecyclerView.ViewHolder(bindingDesign.root)