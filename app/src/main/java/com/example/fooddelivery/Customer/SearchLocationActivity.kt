package com.example.fooddelivery.Customer

import android.location.Location
import android.location.LocationListener
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.fooddelivery.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_search_location.*
import java.io.IOException

class SearchLocationActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener,
    GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    private var mMap: GoogleMap?=null
    internal lateinit var mLastLocation: Location
    internal var mCurrentLocationMarker: Marker?=null
    internal var mGoogleApiClient: GoogleApiClient?=null
    internal lateinit var mLocationRequest: LocationRequest
    private var latitude=0.0
    private var longitude=0.0
//    var getLat=0.0
//    var getLong=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)
    latitude=intent.getDoubleExtra("lat",0.0)
    longitude=intent.getDoubleExtra("long",0.0)

        val mapFragment=supportFragmentManager.findFragmentById(R.id.frmMaps) as SupportMapFragment
        mapFragment.getMapAsync(this)
        btnConfirm.setOnClickListener{
            var intent= Intent(this,HomeActivity::class.java)
            intent.putExtra("lat",latitude)
            intent.putExtra("long",longitude)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap=googleMap;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
            ){
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled=true
            }
        }else{
            buildGoogleApiClient()
            mMap!!.isBuildingsEnabled=true
        }
    }

    protected fun buildGoogleApiClient(){
        mGoogleApiClient=GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }
    override fun onLocationChanged(location: Location) {
//        location.latitude=getLat
//        location.longitude=getLong
        mLastLocation=location
        if(mCurrentLocationMarker!=null){
            mCurrentLocationMarker!!.remove()
        }
        val latLng=LatLng(location.latitude,location.longitude)
        val markerOptions=MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("current position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mCurrentLocationMarker=mMap!!.addMarker(markerOptions)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.moveCamera(CameraUpdateFactory.zoomTo(25f))
        if(mGoogleApiClient!=null){
            LocationServices.getFusedLocationProviderClient(this)
        }
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest= LocationRequest()
        mLocationRequest.interval=1000
        mLocationRequest.fastestInterval=1000
        mLocationRequest.priority=LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        )==PackageManager.PERMISSION_GRANTED){
            LocationServices.getFusedLocationProviderClient(this)
        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
    fun searchLocation(view: View){
        val locationSearch: EditText =findViewById(R.id.editTextSearchLocation)
        var location:String=locationSearch.text.toString().trim()
        var addressList:List<Address>?=null
        if(location==null||location==""){
            Toast.makeText(this,"Please enter location",Toast.LENGTH_LONG).show()
        }else{
            val geoCoder= Geocoder(this)
            try{
                addressList=geoCoder.getFromLocationName(location,1)
            }catch (e:IOException){
                e.printStackTrace()
            }
            val address=addressList!![0]
            val latLng=LatLng(address.latitude,address.longitude)
            mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20f))
            latitude=address.latitude
            longitude=address.longitude
        }
    }
}