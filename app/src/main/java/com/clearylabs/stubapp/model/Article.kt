package com.clearylabs.stubapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "source") val source: Source,
    @Json(name = "authors") val authors: List<Author>,
    @Json(name = "body") val body: String? = null,
    @Json(name = "dataType") val dataType: String? = null,
    @Json(name = "date") val date: String? = null,
    @Json(name = "dateTime") val dateTime: String? = null,
    @Json(name = "dateTimePub") val dateTimePub: String? = null,
    @Json(name = "eventUri") val eventUri: String? = null,
    @Json(name = "image") val image: String? = null,
    @Json(name = "isDuplicate") val isDuplicate: Boolean,
    @Json(name = "lang") val lang: String,
    @Json(name = "relevance") val relevance: Int? = 0,
    @Json(name = "sentiment") val sentiment: Double? = 0.0,
    @Json(name = "sim") val sim: Double? = 0.0,
    @Json(name = "time") val time: String? = null,
    @Json(name = "title") val title: String,
    @Json(name = "uri") val uri: String? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "wgt") val wgt: Int? = 0,
)