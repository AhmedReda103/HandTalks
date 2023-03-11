package com.example.handtalks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.handtalks.R
import com.example.handtalks.databinding.SettingsFragment2Binding
import com.example.handtalks.other.*
import com.example.handtalks.ui.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.settings_fragment2) {
    private lateinit var binding: SettingsFragment2Binding
    var selectedLanguageCode: String? = null

    private val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var classes: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragment2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLanguage()

        binding.apply {
            selectLangRg.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_asl -> {
                        viewModel.setLanguage(Constants.ASL)
                        modelPath = languageModels[Constants.ASL].toString()

                    }
                    R.id.radio_arsl -> {
                        viewModel.setLanguage(Constants.ARSL)
                        modelPath = languageModels[Constants.ARSL].toString()

                    }
                }


            }
        }

    }

    private fun checkLanguage() {
        binding.apply {
            viewModel.getLanguage.observe(requireActivity()) { language ->
                when (language) {
                    Constants.ARSL -> {
                        radioArsl.isChecked = true
                    }
                    Constants.ASL -> {
                        radioAsl.isChecked = true
                    }
                }
            }
        }
    }


}