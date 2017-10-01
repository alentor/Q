package com.example.jacob_000.q;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final LatLng Work = new LatLng(32.166453, 34.809263);
    private final LatLng Liv = new LatLng(32.162442, 34.809341);
    private final LatLng Mcdonalds = new LatLng(32.161263, 34.810630);
    private Marker LivMarker;
    private Marker McdonaldsMarker;
    private Marker WorkMarker;
    private Button report;
    private Button x10;
    private Button x20;
    private Button x30;
    private Button x40;
    private View buttonsPanel;
    private static int totalPplNumLiv;
    private static int totalPplNumMcdonalds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        x10 = findViewById(R.id.x10);
        x20 = findViewById(R.id.x20);
        x30 = findViewById(R.id.x30);
        x40 = findViewById(R.id.x40);



        x10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LivMarker.setTitle((totalPplNumLiv += 10) + "웃");
                LivMarker.showInfoWindow();
                buttonsPanel.setVisibility(View.INVISIBLE);
                report.setVisibility(View.VISIBLE);
            }
        });

        x20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LivMarker.setTitle((totalPplNumLiv += 20) + "웃");
                LivMarker.showInfoWindow();
                buttonsPanel.setVisibility(View.INVISIBLE);
                report.setVisibility(View.VISIBLE);
            }
        });

        x30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LivMarker.setTitle((totalPplNumLiv += 30) + "웃");
                LivMarker.showInfoWindow();
                buttonsPanel.setVisibility(View.INVISIBLE);
                report.setVisibility(View.VISIBLE);
            }
        });

        x40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LivMarker.setTitle((totalPplNumLiv += 40) + "웃");
                LivMarker.showInfoWindow();
                buttonsPanel.setVisibility(View.INVISIBLE);
                report.setVisibility(View.VISIBLE);
            }
        });

        report = findViewById(R.id.reportBtn);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                report.setVisibility(View.INVISIBLE);

                buttonsPanel = findViewById(R.id.buttonsLayout);
                buttonsPanel.setVisibility(View.VISIBLE);

            }});

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void SendReport()
    {
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("/media/webservice/httppost.php");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write("");
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


        } catch (Exception ex) {

        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }
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
        WorkMarker = mMap.addMarker(new MarkerOptions()
                    .position(Work)
                    .title("CmTrading")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cmtrading)));

        LivMarker = mMap.addMarker(new MarkerOptions()
                    .position(Liv)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.liv)));

        McdonaldsMarker = mMap.addMarker(new MarkerOptions()
                         .position(Mcdonalds)
                         .title("Mcdonalds")
                         .icon(BitmapDescriptorFactory.fromResource(R.drawable.mcdonalds)));
    }

}
