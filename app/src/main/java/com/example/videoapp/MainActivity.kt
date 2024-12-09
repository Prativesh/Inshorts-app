package com.example.videoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.videoapp.database.AppDatabase
import com.example.videoapp.navigation.MyApp
import com.example.videoapp.ui.theme.VideoAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppDatabase.initialize(applicationContext)

        setContent {
            navController = rememberNavController()
            VideoAppTheme {
                MyApp(navController = navController)
            }
        }
    }
}