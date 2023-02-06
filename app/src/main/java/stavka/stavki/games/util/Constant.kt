package stavka.stavki.games.util

import stavka.stavki.games.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

class Constant {
    companion object {
        const val BASE_URL = BuildConfig.MAIN_URL

        const val API_RESULT_TYPE: String = "articles"
        const val API_ARTICLES_COUNT: Int = 50
        const val API_KEY: String = "f4dba601-3a14-4df8-ada5-9d41a0a0c03c"

        const val APP_UPDATE_REQUEST_CODE = 1991

        const val NETWORK_TIMEOUT: Long = 30
        const val MAX_MEMORY_CACHE: Long = 20 * 1024 * 1024

        // Recyclerview
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1

        // Paging
        const val FIRST_PAGE = 1
        const val PER_PAGE = 15

        // Shared preferences
        const val APP_PREFERENCES: String = "stavka.stavki.games.APP_PREFERENCES"
        const val PREF_URL = "stavka.stavki.games.url"

        val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val apiDayFormat = SimpleDateFormat("dd", Locale.ENGLISH)
        val apiMonthFormat = SimpleDateFormat("MM", Locale.ENGLISH)
        val apiYearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val apiTimeFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val apiDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    }
}
