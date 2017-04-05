package com.gpsalarm.tracking;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.gpsalarm.Constants;
import com.gpsalarm.selection.Selection;
import com.gpsalarm.util.LocationFinderType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Brent on 4/5/2017.
 */

public class LocationService extends Service {
    private static final String TAG = "LOCATIONSERVICE";
    private LocationManager locationManager = null;
    private static final float LOCATION_DISTANCE = 0;//TODO change to an input
    private Map<String,LocationListener> finders;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        Selection selection = (Selection) intent.getExtras().getParcelable(Constants.SELECTION);
        LocationFinderType finderType = selection.getFinderType();
        this.finders = new HashMap<String, LocationListener>();
        for(String provider : finderType.getProviders()) {
            finders.put(provider,new LocationListener(provider));
        }
        long locationInterval = selection.getInterval();
        for(Map.Entry<String, LocationListener> entry : finders.entrySet()) {
            try {
                locationManager.requestLocationUpdates(
                        entry.getKey(), locationInterval, LOCATION_DISTANCE,
                        entry.getValue());
            } catch (java.lang.SecurityException ex) {
                Log.i(TAG, "fail to request location update, ignore", ex);
            } catch (IllegalArgumentException ex) {
                Log.d(TAG, "network provider does not exist, " + ex.getMessage());
            }
        }
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (locationManager != null) {
            for (android.location.LocationListener listener : finders.values()) {
                try {
                    locationManager.removeUpdates(listener);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private class LocationListener implements android.location.LocationListener {
        Location lastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            lastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            lastLocation.set(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }
}