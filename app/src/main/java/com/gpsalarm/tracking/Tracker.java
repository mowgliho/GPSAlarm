package com.gpsalarm.tracking;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.gpsalarm.Constants;
import com.gpsalarm.gpsalarm.R;
import com.gpsalarm.selection.Selection;

public class Tracker extends AppCompatActivity implements LocationListener {
    private static final String TAG = "TRACKER";
    private Selection selection;
    private TextView selectionText;
    private TextView trackerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        this.selection = (Selection) this.getIntent().getExtras().getParcelable(Constants.SELECTION);
        this.selectionText = (TextView) findViewById(R.id.trackerSelection);
        this.trackerLocation = (TextView) findViewById(R.id.trackerLocation);
        Intent intent = new Intent(this, LocationService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.SELECTION,this.selection);
        intent.putExtras(bundle);
        startService(intent);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectionText.setText(selection.toString());
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationService.Binder binder = (LocationService.Binder) service;
            binder.addLocationServiceListener(Tracker.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    Location lastLocation = new Location("");

    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "onLocationChanged: " + location);
        trackerLocation.setText(location.toString());
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
