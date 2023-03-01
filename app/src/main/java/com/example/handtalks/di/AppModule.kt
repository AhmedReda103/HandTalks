package com.example.handtalks.di

import android.content.Context
import android.content.SharedPreferences
import com.example.handtalks.other.*
import com.example.handtalks.other.Constants.ARSL
import com.example.handtalks.other.Constants.ASL
import com.example.handtalks.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Provides
    fun provideClasses(): Array<String> =
        if (modelPath == Constants.ASL_PATH) aslChars else ArslChar


}