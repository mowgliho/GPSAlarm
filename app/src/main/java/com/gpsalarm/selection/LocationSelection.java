package com.gpsalarm.selection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gpsalarm.Constants;
import com.gpsalarm.gpsalarm.R;

public class LocationSelection extends AppCompatActivity {
    private SelectionBuilder selectionBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        this.selectionBuilder = (SelectionBuilder) this.getIntent().getExtras().getParcelable(Constants.SELECTIONBUILDER);
    }

    public void submit(View view) {
        selectionBuilder.setLatitude(Double.parseDouble(((EditText) findViewById(R.id.LatitudeEditText)).getText().toString()));
        selectionBuilder.setLongitude(Double.parseDouble(((EditText) findViewById(R.id.LongitudeEditText)).getText().toString()));
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.SELECTIONBUILDER, selectionBuilder);
        setResult(Constants.HASRESULT, resultIntent);
        finish();
    }

    public void back(View view) {
        setResult(Constants.DEFAULTRESULTCODE, new Intent());
        finish();
    }
}
