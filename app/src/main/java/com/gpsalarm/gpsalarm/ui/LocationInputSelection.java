package com.gpsalarm.gpsalarm.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.gpsalarm.gpsalarm.R;
import com.gpsalarm.gpsalarm.engine.location.LocationInputType;

public class LocationInputSelection extends AppCompatActivity {
    private Spinner selector;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_input_selection);
        this.selector = (Spinner) findViewById(R.id.select_location_input_spinner);
        ArrayAdapter<LocationInputType> adapter  = new ArrayAdapter<LocationInputType>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                LocationInputType.values()
        );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        this.selector.setAdapter(adapter);
        this.button = (Button) findViewById(R.id.select_location_input_button);
    }

    public void sendSelection(View view) {
        System.out.println(selector.getSelectedItem());
    }
}
