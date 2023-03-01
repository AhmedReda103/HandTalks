package com.example.handtalks.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.handtalks.R
import com.example.handtalks.adapters.CardsInteractionClickListener
import com.example.handtalks.adapters.CardsLanguageAdapter
import com.example.handtalks.databinding.LessonsFragmentBinding
import com.example.handtalks.other.Constants
import com.example.handtalks.other.Constants.ARSL_PATH
import com.example.handtalks.other.modelPath
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LessonsFragment : Fragment(R.layout.lessons_fragment), CardsInteractionClickListener {
    private lateinit var binding: LessonsFragmentBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LessonsFragmentBinding.bind(view)

        Toast.makeText(requireContext(), modelPath, Toast.LENGTH_LONG).show()

        setupRecyclerView(modelPath)
    }

    private fun setupRecyclerView(selectedItem: String) {
        val adapter: CardsLanguageAdapter
        if (selectedItem == ARSL_PATH) {
            adapter = CardsLanguageAdapter(Constants.getARSLItems(), this)
            binding.lessonFragmentRv.adapter = adapter
        } else {
            adapter = CardsLanguageAdapter(Constants.getASLItems(), this)
            binding.lessonFragmentRv.adapter = adapter
        }
    }

    override fun onItemClick(position: Int) {
        val action = LessonsFragmentDirections.actionLessonsFragmentToPracticeFragment(position)
        findNavController().navigate(action)
        Toast.makeText(requireContext(), "${position}  item ", Toast.LENGTH_LONG).show()
    }

}