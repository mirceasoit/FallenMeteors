package workshop.mirceasoit.fallenmeteors.model

import com.squareup.moshi.Json

data class Meteor(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name")val name: String,
    @field:Json(name = "nametype")val nameType: String,
    @field:Json(name = "recclass")val recClass: String,
    @field:Json(name = "mass")val mass: String,
    @field:Json(name = "fall")val fall: String,
    @field:Json(name = "year")val year: String,
    @field:Json(name = "reclat")val recLat: Double,
    @field:Json(name = "reclong")val recLong: Double,
    @field:Json(name = "geolocation")val geoLocation: GeoLocation?
)