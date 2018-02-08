package com.thinkproxi.scanbeacon;

/**
 * Created by ADMIN on 1/11/2018.
 */

public class BeaconCallbackManager {

    SensorIDsCallBack SensorIDsCallBack;

    public void registerSensorIdsCallBack(SensorIDsCallBack sensorIDsCallBack)
    {
        this.SensorIDsCallBack = sensorIDsCallBack;
    }
    public void sensorIdsReturn(String sensor_ids)
    {
        SensorIDsCallBack.sensorIds(sensor_ids);
    }

    public interface SensorIDsCallBack
    {
        void sensorIds(String sensor_ids);
    }
}
