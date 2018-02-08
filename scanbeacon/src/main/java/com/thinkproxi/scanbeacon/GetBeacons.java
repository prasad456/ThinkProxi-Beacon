package com.thinkproxi.scanbeacon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by prasad on 8/2/18.
 */

public class GetBeacons implements BeaconConsumer {

    private Activity mActivity;
    private Context mContext;
    private BeaconManager beaconManager;
    private BeaconCallbackManager beaconCallbackManager;
    private PrefManager prefManager;
    private String exhibit_names = "";
    private ArrayList<Beacon> validIBeaconList = new ArrayList<>();
    private ArrayList<String> validSensorIdsList = new ArrayList<>();
    private ArrayList<String> detectedSensorIdsList = new ArrayList<>();

    public GetBeacons(Activity mActivity, GetUniqueBeaconse uniqueBeaconsData, BeaconCallbackManager callbackManager) {
        this.mActivity = mActivity;
        this.mContext = mActivity.getApplicationContext();
        prefManager = new PrefManager(mContext);
        prefManager.sensorIdsList(uniqueBeaconsData);
        beaconCallbackManager = callbackManager;
        try {
            beaconManager = BeaconManager.getInstanceForApplication(mContext);
            beaconManager.getBeaconParsers().add(new BeaconParser().
                    setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
            beaconManager.bind(this);
        } catch (Exception e) {
            Log.d("Exc", e.toString());
        }
    }

    public void scan() {
        try {
            long timeBetweenScans = 100;
            long timeScanPeriod = 100;
            if (Build.VERSION.SDK_INT > 23) { //CHECK IF NOUGAT OR MORE
                timeBetweenScans = 300;
                timeScanPeriod = 600;
            }
            beaconManager.setForegroundBetweenScanPeriod(timeBetweenScans);
            beaconManager.setForegroundScanPeriod(timeScanPeriod);
            beaconManager.setBackgroundBetweenScanPeriod(timeBetweenScans);
            beaconManager.setBackgroundScanPeriod(timeScanPeriod);
        } catch (Exception e) {
            Log.e("ScanBeacon", "Scan: ", e);
        }
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                if (beacons.size() > 0) {
                    try {
                        if (beacons.size() > 0) {
                            String beacon_company_id = "";
                            ArrayList<Beacon> mList = new ArrayList<>(beacons);
                            // Toast.makeText(context,mList.size(),Toast.LENGTH_SHORT).show();
                            validIBeaconList.addAll(mList);
                            if (mList.size() > 0) {
                                ArrayList<String> sensorIdsList = new ArrayList<>();
                                Boolean validBeacons = false, isValidExhibit = false;
                                for (int j = 0; j < mList.size(); j++) {
                                    Beacon iBeacon1 = mList.get(j);
                                    String uuid = iBeacon1.getId1().toString();
                                    int major = iBeacon1.getId2().toInt();
                                    int minor = iBeacon1.getId3().toInt();

                                    String sid = prefManager.getSensorIDList(uuid.toLowerCase() + major + minor).trim();
                                    beacon_company_id = prefManager.getCompanyIDList(uuid.toLowerCase() + major + minor).trim();
                                    if (!sid.equals("")) {
                                        //   String sid = prefManager.getSensorIdIBeacon(iBeacon1).trim();
                                        if (!sensorIdsList.contains(sid)) {
                                            validBeacons = true;
                                            sensorIdsList.add(sid);
                                            if ((validSensorIdsList.isEmpty() || !validSensorIdsList.contains(sid)) && (detectedSensorIdsList.isEmpty() || !detectedSensorIdsList.contains(sid))) {

                                                if (!prefManager.getExhibitName(sid).equals("")) {
                                                    if (exhibit_names.equals("")) {
                                                        detectedSensorIdsList.add(sid);
                                                        isValidExhibit = true;
                                                        exhibit_names = prefManager.getExhibitName(sid);
                                                    } else {
                                                        detectedSensorIdsList.add(sid);
                                                        isValidExhibit = true;
                                                        exhibit_names = exhibit_names + ", " + prefManager.getExhibitName(sid);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (validBeacons) {
                                    beaconCallbackManager.sensorIdsReturn(sensorIdsList.toString());
                                    if (isValidExhibit) {

                                        //singleTonMethods.exhibitAlert("You are approaching " + "\n" + exhibit_names);
                                        exhibit_names = "";
                                    }
                                    // gettingSenorIds(sensorIdsList,beacon_company_id);
                                }
                                sensorIdsList.clear();
                                mList.clear();
                            }
                        }

                    } catch (Exception e) {
                        Log.d("Exc", e.toString());
                    }
                }
            }
        });
        try {
            beaconManager.addMonitorNotifier(new MonitorNotifier() {
                @Override
                public void didEnterRegion(Region region) {
                }

                @Override
                public void didExitRegion(Region region) {
                }

                @Override
                public void didDetermineStateForRegion(int state, Region region) {
                }
            });
        } catch (Exception ignored) {
        }

        try {

            beaconManager.startMonitoringBeaconsInRegion(new Region(
                    "myRangingUniqueId", null, null, null));
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));

        } catch (RemoteException ignored) {
        }
    }

    @Override
    public Context getApplicationContext() {
        return mContext;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        mActivity.unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return mActivity.bindService(intent, serviceConnection, i);
    }
}
