package com.example.handtalks.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.handtalks.R
import com.example.handtalks.databinding.ChooseLangFragmentBinding
import com.example.handtalks.other.languageCodes
import com.example.handtalks.other.languageModels
import com.example.handtalks.other.modelPath
import com.example.handtalks.other.selectedModel
import com.example.handtalks.ui.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseLangFragment : Fragment(R.layout.choose_lang_fragment) {


    private lateinit var binding: ChooseLangFragmentBinding
    var selectedLanguageCode: String? = null
    private val settingViewModel  by viewModels<SettingsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ChooseLangFragmentBinding.bind(view)

        setUpSpinnerAdapter()
        spinnerSelectedItem()
        handleSelectBtn()
    }

    private fun handleSelectBtn() {
        binding.btnSelect.setOnClickListener {
            modelPath = languageModels[selectedLanguageCode]!!
            selectedModel = selectedLanguageCode!!
            settingViewModel.setLanguage(selectedModel)
            settingViewModel.setFirstTimeLaunch(true)
            navigateToLessonsFragment()
        }
    }

    private fun navigateToLessonsFragment() {
        val navOptions =
            NavOptions.Builder()
                .setPopUpTo(R.id.chooseLangFragment, true)
                .build()

        findNavController().navigate(
            R.id.action_chooseLangFragment_to_lessonsFragment,
            null,
            navOptions
        )
    }

    private fun spinnerSelectedItem() {
        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedLanguageCode = languageCodes[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedLanguageCode = languageCodes[parent?.adapter?.getItem(0) as Int]
                }
            }
    }

    private fun setUpSpinnerAdapter() {
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            R.id.language_name,
            languageCodes
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val icon: ImageView = view.findViewById(R.id.language_icon)
                if (position == 0) {
                    icon.setImageResource(R.drawable.egypt)
                } else if (position == 1) {
                    icon.setImageResource(R.drawable.usa)
                }
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val icon: ImageView = view.findViewById(R.id.language_icon)
                if (position == 0) {
                    icon.setImageResource(R.drawable.egypt)
                } else if (position == 1) {
                    icon.setImageResource(R.drawable.usa)
                }
                return view
            }
        }
        binding.languageSpinner.adapter = adapter
    }

}