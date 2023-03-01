package com.example.handtalks.ui.fragments

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.handtalks.R
import com.example.handtalks.adapters.PracticeAdapter
import com.example.handtalks.databinding.PacticeFragmentBinding
import com.example.handtalks.other.toBitmap
import com.example.handtalks.ui.viewmodels.MainViewModel
import com.example.handtalks.ui.viewmodels.PracticeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class PracticeFragment : Fragment(R.layout.pactice_fragment) {

    private val TAG = "PracticeActivity"
    private lateinit var binding: PacticeFragmentBinding
    private val viewModel: PracticeViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var handler: Handler
    private lateinit var imageAdapter: PracticeAdapter
    private val args by navArgs<PracticeFragmentArgs>()

    private var images = ArrayList<Int>()
    private var chars = ArrayList<String>()
    private var pos = 0;

    private val runnable = Runnable {
        binding.viewPagerImageSlider.currentItem = binding.viewPagerImageSlider.currentItem + 1

    }

    @Inject
    lateinit var classes: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PacticeFragmentBinding.bind(view)
        binding.viewPagerImageSlider.isUserInputEnabled = false
        requestPermission()
        initViewPager()
        setupTransformer()
        subscribeToLiveData()

    }

    private fun subscribeToLiveData() {
        viewModel.charModelListenerPractice.observe(viewLifecycleOwner) { userChar ->
            compareTwoCharacters(userChar)
        }
    }

    private fun compareTwoCharacters(userChar: String) {
        if (pos < chars.size) {
            if (userChar == chars[pos]) {
                handler.post(runnable)
                pos++
            }
        } else {
            binding.viewPagerImageSlider.visibility = View.INVISIBLE
            binding.checkImage.visibility = View.VISIBLE
        }

        Log.d(TAG, "subscribeToLiveData: ${userChar}")
    }


    private fun setupTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        binding.viewPagerImageSlider.setPageTransformer(transformer)
    }

    private fun initViewPager() {
        handler = Handler(Looper.getMainLooper())
        if (args.position == 0) {
            images = viewModel.getImagesForFirstItem()
            chars = viewModel.getCharForFirstItem()
            imageAdapter = PracticeAdapter(images)
        } else if (args.position == 1) {
            images = viewModel.getImagesForSecondItem()
            chars = viewModel.getCharForSecondItem()
            imageAdapter = PracticeAdapter(images)
        } else {
            images = viewModel.getImagesForThirdItem()
            chars = viewModel.getCharForThirdItem()
            imageAdapter = PracticeAdapter(images)
        }

        binding.viewPagerImageSlider.apply {
            adapter = imageAdapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

    }


    private fun startCamera() {
        val processCameraProvider = ProcessCameraProvider.getInstance(requireContext())
        processCameraProvider.addListener({
            try {
                val cameraProvider = processCameraProvider.get()
                val preview = Preview.Builder().build()
                    .also { it.setSurfaceProvider(binding.viewFinder.surfaceProvider) }
                val imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor) { imageProxy ->
                            mainViewModel.detectionAndClassification(
                                preProcessing(imageProxy),
                                classes
                            )
                            viewModel.setLiveData(mainViewModel.getLabel())
                            Timber.d(mainViewModel.getLabel() ?: "None")
                            //  Log.d(TAG, "startCameraBefore: ${char}")
                            imageProxy.close()
                            // Log.d(TAG, "startCameraAfter: ${char}")
                        }
                    }
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))

        //Log.d(TAG, "startCameralastfun: ${char} ")
    }

    private fun preProcessing(imageProxy: ImageProxy): Bitmap {
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        val matrix = Matrix()
        matrix.postRotate(rotationDegrees.toFloat())
        val imageProxyToBitmap = imageProxy.toBitmap()
        return Bitmap.createBitmap(
            imageProxyToBitmap, 0, 0,
            imageProxyToBitmap.width,
            imageProxyToBitmap.height,
            matrix, true
        )
    }

    private fun requestPermission() {
        requestCameraPermissionIfMissing { granted ->
            if (granted)
                startCamera()
            else
                Toast.makeText(requireContext(), "please allow the permission", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun requestCameraPermissionIfMissing(onResult: ((Boolean) -> Unit)) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
            onResult(true)
        else
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                onResult(it)
            }.launch(android.Manifest.permission.CAMERA)
    }


}