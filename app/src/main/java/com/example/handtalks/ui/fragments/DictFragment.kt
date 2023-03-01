package com.example.handtalks.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.handtalks.R
import com.example.handtalks.adapters.DictionaryAdapter
import com.example.handtalks.databinding.DictFragmentBinding
import com.example.handtalks.other.Constants.ARSL_DIC_LIST
import com.example.handtalks.other.Constants.ASL_DIC_LIST
import com.example.handtalks.other.Constants.ASL_PATH
import com.example.handtalks.other.modelPath

class DictFragment : Fragment(R.layout.dict_fragment) {
    private lateinit var binding: DictFragmentBinding
    private lateinit var dictionaryAdapter: DictionaryAdapter
    private lateinit var dictionaryRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DictFragmentBinding.bind(view)
        setupRV()
        dictionaryAdapter.setOnItemClickListener {
            showDialog(it.letterImage)
        }

    }

    private fun setupRV() {
        dictionaryRecyclerView = binding.dictFragmentRV
        if (modelPath == ASL_PATH) {
            dictionaryAdapter = DictionaryAdapter(ASL_DIC_LIST)
        } else {
            dictionaryAdapter = DictionaryAdapter(ARSL_DIC_LIST)
        }
        dictionaryRecyclerView.layoutManager =
            StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL)
        dictionaryRecyclerView.adapter = dictionaryAdapter

    }

    private fun showDialog(letterImage: Int) {

        val dialog = Dialog(this.requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog)
        dialog.findViewById<ImageView>(R.id.dialog_img).setImageResource(letterImage)
        dialog.show()

    }
}