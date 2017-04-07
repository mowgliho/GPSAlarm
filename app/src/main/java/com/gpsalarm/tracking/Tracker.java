package com.gpsalarm.tracking;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.gpsalarm.Constants;
import com.gpsalarm.gpsalarm.R;
import com.gpsalarm.selection.Selection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tracker extends AppCompatActivity implements LocationListener {
    public static Intent getIntent(Context context, Selection selection, boolean fromAlarm) {
        Intent intent = new Intent(context, Tracker.class);
        intent.putExtra("fromAlarm",fromAlarm);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.SELECTION, selection);
        intent.putExtras(bundle);
        return intent;
    }

    private static final String TAG = "TRACKER";
    private static final DateFormat format = new SimpleDateFormat("HH:mm:ss");
    private Selection selection;
    private Location target;
    private TextView selectionText;
    private TextView trackerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        this.selection = (Selection) this.getIntent().getExtras().getParcelable(Constants.SELECTION);
        this.selectionText = (TextView) findViewById(R.id.trackerSelection);
        this.trackerLocation = (TextView) findViewById(R.id.trackerLocation);
        this.target = new Location("");
        this.target.setLatitude(selection.getLatitude());
        this.target.setLongitude(selection.getLongitude());
        if(selection.getStartCheckTime() <= System.currentTimeMillis() ||
                this.getIntent().getBooleanExtra("fromAlarm",false) == true) {
            bind();
        } else{
            Intent intent = Tracker.getIntent(this,selection,true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP,selection.getStartCheckTime(),pendingIntent);
        }

    }

    public void bind() {
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
        private LocationService.Binder binder;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationService.Binder binder = (LocationService.Binder) service;
            binder.addLocationServiceListener(Tracker.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder.removeLocationServiceListener(Tracker.this);
        }
    };

    Location lastLocation = new Location("");

    @Override
    public void onLocationChanged(Location location) {
        changeLocation(location);
    }

    public boolean changeLocation(Location location) {
        Log.e(TAG, "onLocationChanged: " + location);
        trackerLocation.setText(location.distanceTo(target) + " " + format.format(new Date()));
        lastLocation.set(location);
        if(location.distanceTo(target) < selection.getDistanceAway()) {
            unbindService(serviceConnection);
            MediaPlayer player = MediaPlayer.create(this,
                    Settings.System.DEFAULT_RINGTONE_URI);
            player.start();
            return true;
        } else {
            return false;
        }
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
