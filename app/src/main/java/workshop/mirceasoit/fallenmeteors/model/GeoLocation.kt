package workshop.mirceasoit.fallenmeteors.model

import com.squareup.moshi.Json

data class GeoLocation(@field:Json(name = "type")val type: String,
                       @field:Json(name = "coordinates")val coordinates: List<Double>)