package org.example.cross_unstabilizer

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import getMyDatabaseBuilder

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "UnstabilizerKMP",
    ) {
        val dbBuilder=getMyDatabaseBuilder()
        App(dbBuilder)
    }
}