package stavka.stavki.games.net.repository

import stavka.stavki.games.model.Config
import stavka.stavki.games.net.api.IndexApi
import stavka.stavki.games.net.response.IndexResponse
import stavka.stavki.games.util.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class IndexRepository constructor(
    private val indexApi: IndexApi,
    private val preferenceManager: PreferenceManager
) {
    suspend fun loadDataFromApi(config: Config): Response<IndexResponse> = withContext(Dispatchers.IO) { indexApi.getIndex(config) }
}