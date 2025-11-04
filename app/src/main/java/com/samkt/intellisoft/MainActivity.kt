package com.samkt.intellisoft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.samkt.intellisoft.core.ui.theme.IntellisoftTheme
import com.samkt.intellisoft.features.navigation.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntellisoftTheme {
                App()
            }
        }
    }
}
