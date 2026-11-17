package com.dashforge.app

object ModuleCatalog {

    fun getDefaultModules(): List<ModModule> {
        return listOf(

            ModModule(
                id = "dashforge.performance",
                name = "Performance Tools",
                description = "Performance information and diagnostics.",
                version = "1.0.0",
                author = "DashForge",
                supportedGameVersions = listOf("2.2091"),
                supportedAbis = listOf("arm64-v8a")
            ),

            ModModule(
                id = "dashforge.progress",
                name = "Progress Tracker",
                description = "A foundation for tracking game progress.",
                version = "1.0.0",
                author = "DashForge",
                supportedGameVersions = listOf("2.2091"),
                supportedAbis = listOf("arm64-v8a")
            ),

            ModModule(
                id = "dashforge.visuals",
                name = "Visual Tools",
                description = "A foundation for visual preferences.",
                version = "1.0.0",
                author = "DashForge",
                supportedGameVersions = listOf("2.2091"),
                supportedAbis = listOf("arm64-v8a")
            )
        )
    }

    fun registerDefaults() {
        getDefaultModules().forEach { module ->
            ModuleRegistry.register(module)
        }
    }
}
