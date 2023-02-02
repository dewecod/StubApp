package com.clearylabs.stubapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source(
    @Json(name = "dataType") val dataType: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "uri") val uri: String? = null,
)