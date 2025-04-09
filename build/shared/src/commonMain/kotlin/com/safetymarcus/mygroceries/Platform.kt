package com.safetymarcus.mygroceries

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform