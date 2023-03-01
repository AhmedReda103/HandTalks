package com.example.handtalks.di

import android.content.Context
import com.example.handtalks.cnn.ModelsInterpreterImpl
import com.example.handtalks.other.modelPath
import com.example.handtalks.repositories.CnnRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object ModelModule {

    @ViewModelScoped
    @Provides
    @Named("modelPath")
    fun provideNameProvider() = modelPath

    @ViewModelScoped
    @Provides
    @Named("classifierC")
    fun loadModelFileC(
        @ApplicationContext context: Context,
        @Named("modelPath") modelPath: String
    ): ByteBuffer {
        val fileDescriptor = context.assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    @ViewModelScoped
    @Provides
    @Named("detectionD")
    fun loadModelFileD(@ApplicationContext context: Context): ByteBuffer {
        val fileDescriptor = context.assets.openFd("HandDetection.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    @ViewModelScoped
    @Provides
    @Named("classifier")
    fun provideTFLiteInterpreterClassifier(@Named("classifierC") model: ByteBuffer): Interpreter {
        val options = Interpreter.Options().setNumThreads(4)
        return Interpreter(model, options)
    }

    @ViewModelScoped
    @Provides
    @Named("detection")
    fun provideTFLiteInterpreterDetection(@Named("detectionD") model: ByteBuffer): Interpreter {
        val options = Interpreter.Options().setNumThreads(4)
        return Interpreter(model, options)
    }

    @ViewModelScoped
    @Provides
    fun provideModelsInterpreter(
        @Named("classifier") interpreterClassifier: Interpreter,
        @Named("detection") interpreterDetection: Interpreter,
    ): ModelsInterpreterImpl {
        return ModelsInterpreterImpl(interpreterClassifier, interpreterDetection)
    }

    @ViewModelScoped
    @Provides
    fun provideCnnRepoImpl(modelsInterpreterImpl: ModelsInterpreterImpl): CnnRepoImpl {
        return CnnRepoImpl(modelsInterpreterImpl)
    }
}