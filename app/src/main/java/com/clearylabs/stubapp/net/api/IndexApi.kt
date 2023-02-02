package com.clearylabs.stubapp.net.api

import com.clearylabs.stubapp.model.Config
import com.clearylabs.stubapp.net.response.IndexResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IndexApi {
    @POST("api/v1/article/getArticles")
    suspend fun getIndex(@Body config: Config): Response<IndexResponse>
}