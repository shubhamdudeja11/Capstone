package com.umarpreet.capstone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int MAP_REQ=1;
    private ImageButton btnSelectArea;
    private ImageButton btnSelectSpecialArea;
    private ImageButton btnFindPath;
    private ImageButton btnSelectCopter;
    private ImageButton btnFlyManually;
    private ImageButton btnFollowPath;
    private ImageButton btnEnterCrop;
    private ImageButton btnAnalyzeImage;
    private ImageButton btnGetResult;
    private TextView textViewWifiInfo;
    private TextView textViewAreaInfo;
    private ArrayList<LatLng> markers;
    private LatLng takeOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSelectArea=(ImageButton)findViewById(R.id.imageButtonMapSelect);
        btnSelectSpecialArea=(ImageButton)findViewById(R.id.imageButtonTreeSelect);
        btnFindPath=(ImageButton)findViewById(R.id.imageButtonPathFind);
        btnSelectCopter=(ImageButton)findViewById(R.id.imageButtonSelectCopter);
        btnFlyManually=(ImageButton)findViewById(R.id.imageButtonManuallyFly);
        btnFollowPath=(ImageButton)findViewById(R.id.imageButtonFollowPath);
        btnEnterCrop=(ImageButton)findViewById(R.id.imageButtonCropSelect);
        btnAnalyzeImage=(ImageButton)findViewById(R.id.imageButtonAnalyzeImg);
        btnGetResult=(ImageButton)findViewById(R.id.imageButtonGetResult);
        textViewWifiInfo=(TextView)findViewById(R.id.textViewWifiInfo);
        textViewAreaInfo=(TextView)findViewById(R.id.textViewAreaInfo);

        textViewAreaInfo.setText("No area selected");
        setWifiInfo();

        btnSelectCopter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });


        btnSelectArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent=new Intent(getBaseContext(),MapsActivity.class);
                startActivityForResult(mapIntent,MAP_REQ);
            }
        });

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),PathActivity.class);
                intent.putExtra("markers",markers);
                intent.putExtra("takeoff",takeOff);
                startActivity(intent);
            }
        });

        btnFlyManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),RemoteActivity.class);
                startActivity(intent);
            }
        });
    }





    public void setWifiInfo()
    {
        String ssid=null;
        ConnectivityManager connManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            ssid = connectionInfo.getSSID();
            setWifiInfoText("Connected to:- "+ssid);
        }
        else {
            setWifiInfoText("Not Connected");
        }
    }

    public void setWifiInfoText(String info)
    {
        textViewWifiInfo.setText(info);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setWifiInfo();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MAP_REQ) {
            if(resultCode == Activity.RESULT_OK){
                markers=data.getParcelableArrayListExtra("markers");
                takeOff=data.getParcelableExtra("takeoff");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if(markers!=null) {
            textViewAreaInfo.setText("Area is selected");
        }
    }//onAc
}
