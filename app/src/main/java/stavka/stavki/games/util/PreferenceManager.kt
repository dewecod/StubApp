package stavka.stavki.games.util

import android.content.SharedPreferences
import stavka.stavki.games.util.Constant.Companion.PREF_URL

class PreferenceManager constructor(private val sharedPreferences: SharedPreferences) {

    var prefUrl: String
        get() = sharedPreferences.getString(PREF_URL, "").toString()
        set(value) {
            sharedPreferences.edit().putString(PREF_URL, value).apply()
        }

    fun remove(key: String?) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clear() {
        sharedPreferences.edit().remove(PREF_URL).apply()
    }
}
