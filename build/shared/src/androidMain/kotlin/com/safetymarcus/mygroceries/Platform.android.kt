package com.safetymarcus.mygroceries

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getBaseUrl() = "http://10.0.2.2:8081"