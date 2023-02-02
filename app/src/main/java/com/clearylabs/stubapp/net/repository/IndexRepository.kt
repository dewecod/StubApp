package com.clearylabs.stubapp.net.repository

import com.clearylabs.stubapp.model.Config
import com.clearylabs.stubapp.net.api.IndexApi
import com.clearylabs.stubapp.net.response.IndexResponse
import com.clearylabs.stubapp.util.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class IndexRepository constructor(
    private val indexApi: IndexApi,
    private val preferenceManager: PreferenceManager
) {
    suspend fun loadDataFromApi(config: Config): Response<IndexResponse> = withContext(Dispatchers.IO) { indexApi.getIndex(config) }
}