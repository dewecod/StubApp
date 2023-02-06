package stavka.stavki.games.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Author(
    @Json(name = "isAgency") val isAgency: Boolean? = false,
    @Json(name = "name") val name: String? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "uri") val uri: String? = null
):Parcelable