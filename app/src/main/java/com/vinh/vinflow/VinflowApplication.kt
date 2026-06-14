package com.vinh.vinflow

import android.app.Application
import com.vinh.vinflow.domain.usecase.category.SeedDefaultCategoriesUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltAndroidApp
class VinflowApplication : Application() {
    @Inject
    lateinit var seedDefaultCategories: SeedDefaultCategoriesUseCase

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            seedDefaultCategories()
        }
    }
}
