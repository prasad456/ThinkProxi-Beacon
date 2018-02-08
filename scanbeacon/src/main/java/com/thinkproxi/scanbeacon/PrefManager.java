package com.thinkproxi.scanbeacon;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ADMIN on 1/10/2018.
 */

public class PrefManager {
    @SerializedName("PREF_NAME")
    private static final String PREF_NAME = "valuesShrd";
    @SerializedName("pref")
    SharedPreferences pref;
    @SerializedName("editor")
    SharedPreferences.Editor editor;
    @SerializedName("mContext")
    Context mContext;
    Gson gson;
    // shared pref mode
    private int MODE_WORLD_READABLE;

    public PrefManager(@NonNull Context context) {
        this.mContext = context;
        MODE_WORLD_READABLE = 0;
        gson = new Gson();
        pref = context.getSharedPreferences(PREF_NAME, mContext.MODE_PRIVATE);
        editor = pref.edit();
    }
    @SerializedName("setString")
    public boolean setString(@NonNull String key, @NonNull String value) {
        if (!key.isEmpty() && !value.isEmpty()) {
            editor.putString(key, value);
            editor.commit();
            return true;
        } else
            return false;
    }
    @SerializedName("getString")
    public String getString(@NonNull String key) {
        String reg_type = pref.getString(key, "");
        if (reg_type.isEmpty()) {
            reg_type = getData(key);
        }
        return reg_type.trim();
    }
    @SerializedName("getData")
    private String getData(String key) {
        String value = "";
        try {
            String temp = pref.getString("value", "");
            if (!temp.equals("")) {
                JSONObject valuesObj = new JSONObject(temp);
                value = valuesObj.getString(key);
                valuesObj.put(key, null);
                editor.putString("value", valuesObj.toString());
                editor.apply();
            }

        } catch (Exception e) {
        }
        return value;
    }

    public void sensorIdsList(GetUniqueBeaconse getUniqueBeaconse) {
        try {
            //create test hashmap
            HashMap<String, String> sensoridsMap = new HashMap<>();
            HashMap<String, String> exhibitNameMap = new HashMap<>();
            HashMap<String, String> companyidMap = new HashMap<>();
            int size = getUniqueBeaconse.getData().size();
            for (int i = 0; i < size; i++) {
                String key = getUniqueBeaconse.getData().get(i).getUUIDMajorMinor().toLowerCase().trim();
                String sensorid = getUniqueBeaconse.getData().get(i).getSensor_id().toLowerCase().trim();
                sensoridsMap.put(key, sensorid);
                String companyid = getUniqueBeaconse.getData().get(i).getCompany_id().toLowerCase().trim();
                companyidMap.put(key, companyid);
                String exhibit = getUniqueBeaconse.getData().get(i).getExhibit_name();
                try {
                    if (exhibit == null) {
                        exhibit = "";
                    }
                } catch (Exception e) {
                }

                exhibitNameMap.put(sensorid, exhibit);
            }

            String hashMapString = gson.toJson(sensoridsMap);
            String exhibitNameString = gson.toJson(exhibitNameMap);
            String companyIdString = gson.toJson(companyidMap);
            pref.edit().putString("uniquebeaconslist", hashMapString).apply();
            PrefManager prefManager = new PrefManager(mContext);
            prefManager.setString("uuidlist",hashMapString);
            pref.edit().putString("exhibitnameslist", exhibitNameString).apply();
            pref.edit().putString("companyIdList", companyIdString).apply();

        } catch (Exception e) {
        }


       /* //get from shared prefs
        String storedHashMapString = pref.getString("hashString", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> testHashMap2 = gson.fromJson(storedHashMapString, type);

        //use values
        String toastString = testHashMap2.get("key1") + " | " + testHashMap2.get("key2");
       // Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();*/
    }

    public String getSensorIDList(String scanneduuid) {
        String sensor_id = "";
        try {
            String storedHashMapString = pref.getString("uniquebeaconslist", "oopsDintWork");
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            HashMap<String, String> uniquebeaconslist = gson.fromJson(storedHashMapString, type);
            sensor_id = uniquebeaconslist.get(scanneduuid);
            if (sensor_id == null) {
                sensor_id = "";
            }
        } catch (Exception e) {
        }


        return sensor_id;
    }
    public String getCompanyIDList(String scanneduuid) {
        String sensor_id = "";
        try {
            String storedHashMapString = pref.getString("companyIdList", "oopsDintWork");
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            HashMap<String, String> uniquebeaconslist = gson.fromJson(storedHashMapString, type);
            sensor_id = uniquebeaconslist.get(scanneduuid);
            if (sensor_id == null) {
                sensor_id = "";
            }
        } catch (Exception e) {
        }


        return sensor_id;
    }
    public String getExhibitName(String sensorid) {
        String sensor_id = "";
        try {
            String storedHashMapString = pref.getString("exhibitnameslist", "oopsDintWork");
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            HashMap<String, String> exhibitslist = gson.fromJson(storedHashMapString, type);
            sensor_id = exhibitslist.get(sensorid);
            if (sensor_id == null) {
                sensor_id = "";
            }
        } catch (Exception e) {
        }


        return sensor_id;
    }
}
