package com.example.jacob_000.q;

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

import java.util.Iterator;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final LatLng Work = new LatLng(32.166453, 34.809263);
    private final LatLng Liv = new LatLng(32.162442, 34.809341);
    private final LatLng Mcdonalds = new LatLng(32.161263, 34.810630);
    private static Marker LivMarker;
    private static Marker McdonaldsMarker;
    private Marker WorkMarker;
    private Button report;
    private Button x5;
    private Button x10;
    private Button x15;
    private Button x20;
    private View buttonsPanel;
    public static double CurrentLatitude;
    public static double CurrentLongitude;
    public static String LastReportCode;
    public static Marker[] Markers;
    public static Marker reportMarker;


    @Override
    protected void onStop() {
        super.onStop();

        try
        {
            BackgroundWorker.reader.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try
        {
            BackgroundWorker.reader.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static Marker GetNearPOI()
    {
        Marker marker;

        double mcdonaldsLatDistance = Markers[0].getPosition().latitude - CurrentLatitude;
        double mcdonaldsLngDistance = Markers[0].getPosition().longitude - CurrentLongitude;

        double livLatDistance = Markers[1].getPosition().latitude - CurrentLatitude;
        double livLngDistance = Markers[1].getPosition().longitude - CurrentLongitude;

        if (mcdonaldsLatDistance > livLatDistance && mcdonaldsLngDistance > livLngDistance)
        {
            marker = LivMarker;
        }
        else
        {
            marker = McdonaldsMarker;
        }

        return marker;
    }


    public static void UpdateReports(List<Report> reports)
    {
        for (Iterator<Report> i = reports.iterator(); i.hasNext();)
        {
                Report report = i.next();
                reportMarker = GetNearPOI();
                reportMarker.setTitle("+" + report.Queue + "웃");
                int estTime = report.Queue * 2;
                reportMarker.setSnippet("Est. " + estTime + "min");
        }
        McdonaldsMarker.showInfoWindow();
        LivMarker.showInfoWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        x5 = findViewById(R.id.x5);
        x10 = findViewById(R.id.x10);
        x15 = findViewById(R.id.x15);
        x20 = findViewById(R.id.x20);


        x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonsPanel.setVisibility(View.INVISIBLE);
                report.setVisibility(View.VISIBLE);

                Thread thread1 = new SendReport(5);
                thread1.start();

            }
        });

        x10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonsPanel.setVisibility(View.INVISIBLE);
                report.setVisibility(View.VISIBLE);

                Thread thread1 = new SendReport(10);
                thread1.start();
            }
        });

        x15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonsPanel.setVisibility(View.INVISIBLE);
                report.setVisibility(View.VISIBLE);

                Thread thread1 = new SendReport(15);
                thread1.start();
            }
        });

        x20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonsPanel.setVisibility(View.INVISIBLE);
                report.setVisibility(View.VISIBLE);

                Thread thread1 = new SendReport(20);
                thread1.start();
            }
        });

        report = findViewById(R.id.reportBtn);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                report.setVisibility(View.INVISIBLE);
                buttonsPanel = findViewById(R.id.buttonsLayout);
                buttonsPanel.setVisibility(View.VISIBLE);

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            CurrentLatitude = gpsTracker.latitude;
            CurrentLongitude = gpsTracker.longitude;
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }

        Thread thread2 = new BackgroundWorker(this);
        thread2.start();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        SetMapCustomMarkers();
        Markers = new Marker[]{McdonaldsMarker, LivMarker};
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
                         .icon(BitmapDescriptorFactory.fromResource(R.drawable.mcdonalds)));
    }

}
