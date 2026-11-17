
package com.dashforge.app

object ModuleCompatibility {

    const val TARGET_GAME_VERSION = "2.2091"

    fun check(
        module: ModModule,
        game: GameDetails
    ): CompatibilityResult {

        if (!game.installed) {
            return CompatibilityResult(
                false,
                "Geometry Dash is not installed"
            )
        }

        val gameVersion = game.versionName

        if (gameVersion == null) {
            return CompatibilityResult(
                false,
                "Could not detect game version"
            )
        }

        if (gameVersion !in module.supportedGameVersions) {
            return CompatibilityResult(
                false,
                "Unsupported game version: $gameVersion"
            )
        }

        val deviceAbi = DeviceInfo.getPrimaryAbi()

        if (deviceAbi !in module.supportedAbis) {
            return CompatibilityResult(
                false,
                "Unsupported device architecture: $deviceAbi"
            )
        }

        return CompatibilityResult(
            true,
            "Compatible"
        )
    }
}

data class CompatibilityResult(
    val compatible: Boolean,
    val message: String
)
