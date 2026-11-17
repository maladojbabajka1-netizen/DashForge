
package com.dashforge.app

object ModuleManager {

    private val installedModules = mutableSetOf<String>()
    private val disabledModules = mutableSetOf<String>()

    fun install(id: String): Boolean {
        val module = ModuleRegistry.findById(id)
            ?: return false

        if (!module.isCompatible("2.2091", "arm64-v8a")) {
            return false
        }

        installedModules.add(id)
        disabledModules.remove(id)
        return true
    }

    fun uninstall(id: String): Boolean {
        disabledModules.remove(id)
        return installedModules.remove(id)
    }

    fun enable(id: String): Boolean {
        if (id !in installedModules) return false
        disabledModules.remove(id)
        return true
    }

    fun disable(id: String): Boolean {
        if (id !in installedModules) return false
        disabledModules.add(id)
        return true
    }

    fun isInstalled(id: String): Boolean {
        return id in installedModules
    }

    fun isEnabled(id: String): Boolean {
        return id in installedModules &&
            id !in disabledModules
    }

    fun getInstalled(): List<String> {
        return installedModules.toList()
    }
}
