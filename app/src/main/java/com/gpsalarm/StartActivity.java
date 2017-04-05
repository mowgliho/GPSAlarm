package com.gpsalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gpsalarm.gpsalarm.R;
import com.gpsalarm.selection.AlarmSelection;
import com.gpsalarm.selection.CheckFrequencySelection;
import com.gpsalarm.selection.CheckStartSelection;
import com.gpsalarm.selection.LocationSelection;
import com.gpsalarm.selection.Selection;
import com.gpsalarm.selection.SelectionBuilder;
import com.gpsalarm.tracking.Tracker;
import com.gpsalarm.util.LocationFinderType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StartActivity extends AppCompatActivity {
    private static final int LOCATIONSELECTION = 1,DELAYSELECTION = 2, CHECKFREQUENCYSELECTION = 3, ALARMSELECTION = 4;
    private static final DateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss");

    private SelectionBuilder selectionBuilder = new SelectionBuilder();
    private TextView selectedLocationText,
        selectedDelayText,
        selectedFrequencyText,
        selectedAlarmText,
        errorText;
    private Spinner locationFinder;
    private ArrayAdapter<LocationFinderType> locationFinderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        selectionBuilder.clear();
        selectedLocationText = (TextView) findViewById(R.id.locationSelection);
        selectedDelayText = (TextView) findViewById(R.id.delaySelection);
        selectedFrequencyText = (TextView) findViewById(R.id.frequencySelection);
        selectedAlarmText = (TextView) findViewById(R.id.alarmSelection);
        errorText = (TextView) findViewById(R.id.startErrorText);
        locationFinder = (Spinner) findViewById(R.id.locationFinder);
        locationFinderAdapter = new ArrayAdapter<LocationFinderType>(
                this,R.layout.support_simple_spinner_dropdown_item,LocationFinderType.values());
        locationFinder.setAdapter(locationFinderAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedLocationText.setText("Lat: " + selectionBuilder.getLatitude() + " Lon: " + selectionBuilder.getLongitude());
        selectedDelayText.setText("" + selectionBuilder.getStartCheckTime());
        selectedFrequencyText.setText("" + selectionBuilder.getInterval());
        selectedAlarmText.setText("Alarm: " + selectionBuilder.getAlarmType() + " snooze: " + selectionBuilder.getSnoozeInterval());
        locationFinder.setSelection(locationFinderAdapter.getPosition(selectionBuilder.getFinderType()));
    }

    public void openLocationSelection(View view) {
        Intent intent = getIntent(LocationSelection.class);
        startActivityForResult(intent, LOCATIONSELECTION);
    }

    public void openDelaySelection(View view) {
        Intent intent = getIntent(CheckStartSelection.class);
        startActivityForResult(intent,DELAYSELECTION);
    }

    public void openCheckFrequencySelection(View view) {
        Intent intent = getIntent(CheckFrequencySelection.class);
        startActivityForResult(intent,CHECKFREQUENCYSELECTION);
    }

    public void openAlarmSelection(View view) {
        Intent intent = getIntent(AlarmSelection.class);
        startActivityForResult(intent,ALARMSELECTION);
    }

    private Intent getIntent(Class classObj) {
        Intent intent = new Intent(this, classObj);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.SELECTIONBUILDER, selectionBuilder);
        intent.putExtras(bundle);
        return(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == Constants.HASRESULT) {
            this.selectionBuilder = intent.getExtras().getParcelable(Constants.SELECTIONBUILDER);
        }
    }

    public void submit(View view) {
        Selection selection = selectionBuilder.generate();
        if(selection != null) {
            Intent intent = new Intent(this, Tracker.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.SELECTION, selectionBuilder.generate());
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            errorText.setText(R.string.badSelection);
        }

    }
}
