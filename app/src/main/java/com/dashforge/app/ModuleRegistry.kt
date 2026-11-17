
package com.dashforge.app

object ModuleRegistry {

    private val modules = mutableListOf<ModModule>()

    fun register(module: ModModule): Boolean {
        if (modules.any { it.id == module.id }) {
            return false
        }

        modules.add(module)
        return true
    }

    fun getAll(): List<ModModule> {
        return modules.toList()
    }

    fun findById(id: String): ModModule? {
        return modules.find { it.id == id }
    }

    fun search(query: String): List<ModModule> {
        return modules.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true) ||
            it.author.contains(query, ignoreCase = true)
        }
    }

    fun remove(id: String): Boolean {
        return modules.removeAll { it.id == id }
    }

    fun clear() {
        modules.clear()
    }
}
