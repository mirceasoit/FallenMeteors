package workshop.mirceasoit.fallenmeteors.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import workshop.mirceasoit.fallenmeteors.R


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    private var name: String = ""
    private var lat: Double = DEFAULT_LAT
    private var lon: Double = DEFAULT_LON

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        intent?.let {
            name = it.getStringExtra(NAME_KEY).toString()
            lat = it.getStringExtra(LAT_KEY)?.toDouble() ?: DEFAULT_LAT
            lon = it.getStringExtra(LON_KEY)?.toDouble() ?: DEFAULT_LON
        }

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val mainIntent = Intent(this@MapActivity, MeteorsActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val position = LatLng(lat, lon)
        val cameraPosition = CameraPosition.Builder()
                .target(position)
                .zoom(DEFAULT_ZOOM).build()
        mMap?.let {
            it.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_meteor))
            )
            it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    companion object {
        const val DEFAULT_LAT = 0.0
        const val DEFAULT_LON = 0.0
        const val DEFAULT_ZOOM = 5f

        const val NAME_KEY = "name"
        const val LAT_KEY = "lat"
        const val LON_KEY = "lon"
    }
}