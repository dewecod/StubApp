package stavka.stavki.games.net.api

import stavka.stavki.games.model.Config
import stavka.stavki.games.net.response.IndexResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IndexApi {
    @POST("api/v1/article/getArticles")
    suspend fun getIndex(@Body config: Config): Response<IndexResponse>
}