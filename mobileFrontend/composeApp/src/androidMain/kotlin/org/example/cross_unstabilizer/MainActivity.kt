package org.example.cross_unstabilizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import getDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dbBuilder=getDatabaseBuilder(applicationContext)
            App(dbBuilder)
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}