package org.example.cross_unstabilizer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
