package com.umarpreet.capstone;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class PathActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng takeOff;
    private ArrayList<LatLng> markers;
    private ImageButton btnTuneCamera;
    private ImageButton btnOverlap;
    private ImageButton btnStart;
    private ImageButton btnStop;
    final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        Intent intent=getIntent();
        takeOff=intent.getParcelableExtra("takeoff");
        markers=intent.getParcelableArrayListExtra("markers");

        btnTuneCamera=(ImageButton)findViewById(R.id.imageButtonTuneCamera);
        btnOverlap=(ImageButton)findViewById(R.id.imageButtonSetOverlap);
        btnStart=(ImageButton)findViewById(R.id.imageButtonStartPathMake);
        btnStop=(ImageButton)findViewById(R.id.imageButtonStopPathMake);

        btnTuneCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.enter_camera);
                dialog.setTitle("Camera Tuning");
                dialog.show();

            }
        });

        btnOverlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.enter_overlap);
                dialog.setTitle("Overlap Settings");
                dialog.show();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridDetails gridDetails=new GridDetails(markers,takeOff,10,10);
                gridDetails.PointConversion();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        PolygonOptions polygonOptions=new PolygonOptions();
        polygonOptions.addAll(markers);
        polygonOptions.strokeColor(R.color.colorPrimaryDark);
        polygonOptions.strokeWidth(8);
        Polygon polygon=mMap.addPolygon(polygonOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(takeOff,15));
    }
}
