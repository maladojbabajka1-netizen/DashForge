package com.dashforge.app

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

data class GameDetails(
    val installed: Boolean,
    val packageName: String?,
    val versionName: String?,
    val versionCode: Long?
)

object GameInfo {

    private val possiblePackages = listOf(
        "com.robtopx.geometryjump"
    )

    fun detect(context: Context): GameDetails {
        val packageManager = context.packageManager

        for (packageName in possiblePackages) {
            try {
                val info: PackageInfo =
                    if (android.os.Build.VERSION.SDK_INT >= 33) {
                        packageManager.getPackageInfo(
                            packageName,
                            PackageManager.PackageInfoFlags.of(0)
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        packageManager.getPackageInfo(
                            packageName,
                            0
                        )
                    }

                val versionCode =
                    if (android.os.Build.VERSION.SDK_INT >= 28) {
                        info.longVersionCode
                    } else {
                        @Suppress("DEPRECATION")
                        info.versionCode.toLong()
                    }

                return GameDetails(
                    installed = true,
                    packageName = packageName,
                    versionName = info.versionName,
                    versionCode = versionCode
                )

            } catch (_: PackageManager.NameNotFoundException) {
                // Try the next package name.
            }
        }

        return GameDetails(
            installed = false,
            packageName = null,
            versionName = null,
            versionCode = null
        )
    }
}
