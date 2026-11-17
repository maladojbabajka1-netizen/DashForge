
package com.dashforge.app

import android.content.Context

object ModuleStorage {

    private const val PREFS_NAME = "dashforge_modules"
    private const val INSTALLED_KEY = "installed_modules"
    private const val DISABLED_KEY = "disabled_modules"

    private fun preferences(context: Context) =
        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

    fun saveInstalled(
        context: Context,
        ids: Set<String>
    ) {
        preferences(context).edit()
            .putStringSet(INSTALLED_KEY, ids)
            .apply()
    }

    fun loadInstalled(context: Context): Set<String> {
        return preferences(context)
            .getStringSet(INSTALLED_KEY, emptySet())
            ?.toSet() ?: emptySet()
    }

    fun saveDisabled(
        context: Context,
        ids: Set<String>
    ) {
        preferences(context).edit()
            .putStringSet(DISABLED_KEY, ids)
            .apply()
    }

    fun loadDisabled(context: Context): Set<String> {
        return preferences(context)
            .getStringSet(DISABLED_KEY, emptySet())
            ?.toSet() ?: emptySet()
    }

    fun clear(context: Context) {
        preferences(context).edit().clear().apply()
    }
}
