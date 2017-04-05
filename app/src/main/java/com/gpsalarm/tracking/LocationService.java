package com.gpsalarm.tracking;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.gpsalarm.Constants;
import com.gpsalarm.gpsalarm.R;
import com.gpsalarm.selection.Selection;
import com.gpsalarm.util.LocationFinderType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.gpsalarm.Constants.ONGOING_NOTIFICATION_ID;

/**
 * Created by Brent on 4/5/2017.
 */

public class LocationService extends Service {
    private static final String TAG = "LOCATIONSERVICE";
    private LocationManager locationManager = null;
    private static final float LOCATION_DISTANCE = 0;//TODO change to an input
    private final Binder binder = new Binder();
    private Selection selection;
    private final Set<LocationListener> locationListeners = new HashSet<LocationListener>();

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"Bound to Location Service");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        this.selection = null;
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        this.selection = (Selection) intent.getExtras().getParcelable(Constants.SELECTION);
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (locationManager != null) {
            for (android.location.LocationListener listener : locationListeners) {
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

    public class Binder extends android.os.Binder {
        void addLocationServiceListener(LocationListener listener) {
            LocationFinderType finderType = selection.getFinderType();
            long locationInterval = selection.getInterval();
            for(String provider : finderType.getProviders()) {
                try {
                    locationManager.requestLocationUpdates(
                            provider, locationInterval, LOCATION_DISTANCE,
                            listener);
                } catch (java.lang.SecurityException ex) {
                    Log.i(TAG, "fail to request location update, ignore", ex);
                } catch (IllegalArgumentException ex) {
                    Log.d(TAG, "network provider does not exist, " + ex.getMessage());
                }
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                Intent notificationIntent = new Intent(LocationService.this, Tracker.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(LocationService.this, 0, notificationIntent, 0);
                Notification notification = new Notification.Builder(LocationService.this)
                        .setContentTitle(getText(R.string.notification_title))
                        .setContentText(getText(R.string.notification_message))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .build();
                startForeground(ONGOING_NOTIFICATION_ID, notification);
            }
            locationListeners.add(listener);
        }
    }
}