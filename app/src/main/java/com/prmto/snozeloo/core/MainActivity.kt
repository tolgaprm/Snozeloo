package com.prmto.snozeloo.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.prmto.snozeloo.presentation.navigation.NavigationRoot
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnozelooTheme {
                val navController = rememberNavController()
                NavigationRoot(navController)
            }
        }
    }
}