package com.thinkproxi.scanbeaconexamle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thinkproxi.scanbeacon.BeaconCallbackManager;
import com.thinkproxi.scanbeacon.GetBeacons;

public class MainActivity extends AppCompatActivity implements BeaconCallbackManager.SensorIDsCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetBeacons getBeacons = new GetBeacons(this, null, null);
        getBeacons.scan();
    }

    @Override
    public void sensorIds(String sensor_ids) {

    }
}
