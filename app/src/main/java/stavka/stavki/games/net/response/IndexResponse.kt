package stavka.stavki.games.net.response


import stavka.stavki.games.model.Article
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IndexResponse(
    @Json(name = "articles") val articleList: ArticleList
) {
    @JsonClass(generateAdapter = true)
    data class ArticleList(
        @Json(name = "count") val count: Int,
        @Json(name = "page") val page: Int,
        @Json(name = "pages") val pages: Int,
        @Json(name = "results") val results: List<Article>,
        @Json(name = "totalResults") val totalResults: Int
    )
}