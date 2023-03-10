package stavka.stavki.games.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Config(
    @Json(name = "apiKey") val apiKey: String,
    @Json(name = "articlesCount") val articlesCount: Int,
    @Json(name = "resultType") val resultType: String,
    @Json(name = "keyword") val keyword: List<String>,
    @Json(name = "ignoreKeyword") val ignoreKeyword: List<String>,
)