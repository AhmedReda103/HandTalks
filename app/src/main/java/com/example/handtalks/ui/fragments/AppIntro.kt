package com.example.handtalks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.handtalks.R
import com.example.handtalks.adapters.AppIntroViewPager2Adapter
import com.example.handtalks.animations.ZoomOutPageTransformer
import com.example.handtalks.databinding.IntroAppBinding
import com.google.android.material.tabs.TabLayoutMediator

class AppIntro : Fragment(R.layout.intro_app) {
    private lateinit var binding: IntroAppBinding
    val navOptions =
        NavOptions.Builder()
            .setPopUpTo(R.id.appIntro, true)
            .build()

    companion object {
        const val MAX_STEP = 4
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = IntroAppBinding.bind(view)
        binding.viewPager2.adapter = AppIntroViewPager2Adapter()

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position -> }.attach()

        binding.viewPager2.setPageTransformer(ZoomOutPageTransformer())
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == MAX_STEP - 1) {
                    binding.btnNext.text = getString(R.string.intro_get_started)
                    binding.btnNext.contentDescription =
                        getString(R.string.intro_get_started)
                    binding.btnNext.setOnClickListener {
                        findNavController().navigate(R.id.chooseLangFragment, null, navOptions)
                    }
                } else {
                    binding.btnNext.text = getString(R.string.intro_next)
                    binding.btnNext.contentDescription = getString(R.string.intro_next)
                }
            }
        })
        binding.btnSkip.setOnClickListener {
            findNavController().navigate(R.id.chooseLangFragment, null, navOptions)
        }
        binding.btnNext.setOnClickListener {
            if (binding.btnNext.text.toString() == getString(R.string.intro_get_started)) {
                findNavController().navigate(R.id.chooseLangFragment)
            } else {
                val current = (binding.viewPager2.currentItem) + 1
                binding.viewPager2.currentItem = current
                if (current >= MAX_STEP - 1) {
                    binding.btnNext.text = getString(R.string.intro_get_started)
                    binding.btnNext.contentDescription =
                        getString(R.string.intro_get_started)
                    binding.btnNext.setOnClickListener {
                        findNavController().navigate(R.id.chooseLangFragment, null, navOptions)
                    }
                } else {
                    binding.btnNext.text = getString(R.string.intro_next)
                    binding.btnNext.contentDescription = getString(R.string.intro_next)
                }
            }
        }
    }
}
