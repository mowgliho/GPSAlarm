package com.gpsalarm.tracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gpsalarm.Constants;
import com.gpsalarm.gpsalarm.R;
import com.gpsalarm.selection.Selection;

public class Tracker extends AppCompatActivity {
    private Selection selection;
    private TextView selectionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        this.selection = (Selection) this.getIntent().getExtras().getParcelable(Constants.SELECTION);
        this.selectionText = (TextView) findViewById(R.id.trackerSelection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectionText.setText(selection.toString());
    }
}
