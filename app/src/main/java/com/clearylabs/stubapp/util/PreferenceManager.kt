package com.clearylabs.stubapp.util

import android.content.SharedPreferences
import com.clearylabs.stubapp.util.Constant.Companion.PREF_USER_ID

class PreferenceManager constructor(private val sharedPreferences: SharedPreferences) {

    var prefUserId: Int
        get() = sharedPreferences.getInt(PREF_USER_ID, 0)
        set(value) {
            sharedPreferences.edit().putInt(PREF_USER_ID, value).apply()
        }

    fun remove(key: String?) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clear() {
        sharedPreferences.edit().remove(PREF_USER_ID).apply()
    }
}
