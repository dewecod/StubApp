package com.clearylabs.stubapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Source(
    @Json(name = "dataType") val dataType: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "uri") val uri: String? = null,
):Parcelable