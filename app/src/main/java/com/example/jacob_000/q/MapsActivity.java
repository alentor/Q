package com.example.jacob_000.q;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final LatLng Work = new LatLng(32.166453, 34.809263);
    private final LatLng Liv = new LatLng(32.162442, 34.809341);
    private final LatLng Mcdonalds = new LatLng(32.161263, 34.810630);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        SetMapCustomMarkers();
        mMap.setMapType(R.raw.map_style_json);

        mMap.setMinZoomPreference(15);
        mMap.setMaxZoomPreference(20);

        // Add a marker in Sydney and move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Work));
    }

    private void SetMapCustomMarkers()
    {
        mMap.addMarker(new MarkerOptions()
                .position(Work)
                .title("CmTrading")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cmtrading)));

        mMap.addMarker(new MarkerOptions()
                .position(Liv)
                .title("Liv")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.liv)));

        mMap.addMarker(new MarkerOptions()
                .position(Mcdonalds)
                .title("Mcdonalds")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mcdonalds)));
    }

}
