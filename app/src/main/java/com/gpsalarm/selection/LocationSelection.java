package com.gpsalarm.selection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gpsalarm.Constants;
import com.gpsalarm.gpsalarm.R;

public class LocationSelection extends Selector {
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        errorText = (TextView) findViewById(R.id.locationError);
    }

    @Override
    public void submit(View view) {
        String lat = ((EditText) findViewById(R.id.LatitudeEditText)).getText().toString();
        String lon = ((EditText) findViewById(R.id.LongitudeEditText)).getText().toString();
        if(lat.equals("") || lon.equals("")) {
            errorText.setText(R.string.locationError);
        } else {
            selectionBuilder.setLatitude(Double.parseDouble(lat));
            selectionBuilder.setLongitude(Double.parseDouble(lon));
            super.submit();
        }
    }
}
