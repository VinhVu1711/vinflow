package com.vinh.vinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vinh.vinflow.core.designsystem.theme.VinflowTheme
import com.vinh.vinflow.feature.app.VinflowApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VinflowTheme {
                VinflowApp()
            }
        }
    }
}

