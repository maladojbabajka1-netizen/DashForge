package com.dashforge.app

import android.os.Build

object DeviceInfo {

    fun getSupportedAbis(): List<String> {
        return Build.SUPPORTED_ABIS.toList()
    }

    fun getPrimaryAbi(): String {
        return Build.SUPPORTED_ABIS.firstOrNull()
            ?: "unknown"
    }

    fun isArm64(): Boolean {
        return "arm64-v8a" in Build.SUPPORTED_ABIS
    }

    fun getArchitectureLabel(): String {
        return when {
            isArm64() -> "ARM64"
            "armeabi-v7a" in Build.SUPPORTED_ABIS -> "ARM32"
            "x86_64" in Build.SUPPORTED_ABIS -> "x86_64"
            "x86" in Build.SUPPORTED_ABIS -> "x86"
            else -> "Unknown"
        }
    }
}
