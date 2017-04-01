package com.gpsalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gpsalarm.gpsalarm.R;
import com.gpsalarm.selection.CheckFrequencySelection;
import com.gpsalarm.selection.CheckStartSelection;
import com.gpsalarm.selection.LocationSelection;
import com.gpsalarm.selection.SelectionBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StartActivity extends AppCompatActivity {
    private static final int LOCATIONSELECTION = 1,DELAYSELECTION = 2, CHECKFREQUENCYSELECTION = 3;
    private static final DateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss");

    private SelectionBuilder selectionBuilder = new SelectionBuilder();
    private TextView selectedLocationText,
        selectedDelayText,
        selectedFrequencyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        selectionBuilder.clear();
        selectedLocationText = (TextView) findViewById(R.id.locationSelection);
        selectedDelayText = (TextView) findViewById(R.id.delaySelection);
        selectedFrequencyText = (TextView) findViewById(R.id.frequencySelection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedLocationText.setText("Lat: " + selectionBuilder.getLatitude() + " Lon: " + selectionBuilder.getLongitude());
        selectedDelayText.setText("" + selectionBuilder.getStartCheckTime());
        selectedFrequencyText.setText("" + selectionBuilder.getInterval());
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
}
