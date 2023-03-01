package com.example.handtalks.ui.fragments

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
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
import com.example.handtalks.R
import com.example.handtalks.databinding.QuizFragmentBinding
import com.example.handtalks.other.Constants.ARSL_PATH
import com.example.handtalks.other.Constants.ASL_PATH
import com.example.handtalks.other.Constants.arabicAlphabets
import com.example.handtalks.other.Constants.englishAlphabets
import com.example.handtalks.other.modelPath
import com.example.handtalks.other.toBitmap
import com.example.handtalks.ui.viewmodels.MainViewModel
import com.example.handtalks.ui.viewmodels.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class QuizFragment : Fragment(R.layout.quiz_fragment) {
    private lateinit var binding: QuizFragmentBinding
    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private val quizViewModel: QuizViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var handler: Handler

    @Inject
    lateinit var classes: Array<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = QuizFragmentBinding.bind(view)
        Toast.makeText(requireContext(), modelPath, Toast.LENGTH_LONG).show()

        handler = Handler(Looper.getMainLooper())
        requestPermission()
        setTextToRandomElement()
        subscribeToLiveData()

    }

    private fun setTextToRandomElement() {
        binding.checkImage.visibility = View.INVISIBLE
        binding.randChar.visibility = View.VISIBLE
        var randomIndex: Int
        var randomElement: String
        if (modelPath == ARSL_PATH) {
            randomIndex = Random.nextInt(arabicAlphabets.size);
            randomElement = arabicAlphabets[randomIndex]
            binding.randChar.text = randomElement
        } else if (modelPath == ASL_PATH) {
            randomIndex = Random.nextInt(englishAlphabets.size);
            randomElement = englishAlphabets[randomIndex]
            binding.randChar.text = randomElement

        }

    }


    private fun subscribeToLiveData() {

        quizViewModel.charModelListenerQuiz.observe(viewLifecycleOwner) { userChar ->
            compareTwoCharacters(userChar)
        }
    }

    private fun compareTwoCharacters(userChar: String) {
        val ranText = binding.randChar.text.toString()
        if (userChar == ranText) {
            binding.randChar.visibility = View.INVISIBLE
            val animationToLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.to_left)
            binding.randChar.startAnimation(animationToLeft)

            binding.randChar.visibility = View.INVISIBLE
            //binding.checkImage.visibility = View.VISIBLE
            setTextToRandomElement()


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
                            quizViewModel.setLiveData(mainViewModel.getLabel())
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