package com.example.handtalks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.handtalks.R
import com.example.handtalks.databinding.SettingsFragmentBinding
import com.example.handtalks.other.languageCodes
import com.example.handtalks.other.languageModels
import com.example.handtalks.other.modelPath
import com.example.handtalks.other.selectedModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.settings_fragment) {
    private lateinit var binding: SettingsFragmentBinding
    var selectedLanguageCode: String? = null

    @Inject
    lateinit var classes :Array<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            setUpSpinnerAdapter()
            spinnerSelectedItem()
            handleSelectBtn()

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

    private fun handleSelectBtn() {
        binding.btnSelect.setOnClickListener {


            modelPath = languageModels[selectedLanguageCode]!!
            selectedModel = selectedLanguageCode!!


            findNavController().navigate(R.id.action_settingsFragment_to_lessonsFragment)

        }
    }


}