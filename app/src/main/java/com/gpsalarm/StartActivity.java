package com.gpsalarm;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gpsalarm.gpsalarm.R;
import com.gpsalarm.selection.LocationSelection;
import com.gpsalarm.selection.SelectionBuilder;

public class StartActivity extends AppCompatActivity {
    private static final int LOCATIONSELECTION = 0;

    private SelectionBuilder selectionBuilder = new SelectionBuilder();
    private TextView selectedLocationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        selectionBuilder.clear();
        selectedLocationText = (TextView) findViewById(R.id.locationSelection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedLocationText.setText(selectionBuilder.getLatitude());
    }

    public void openLocationSelection(View view) {
        Intent intent = getIntent(LocationSelection.class);
        startActivityForResult(intent, LOCATIONSELECTION);
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
        if(requestCode == LOCATIONSELECTION && resultCode == Constants.HASRESULT) {
            this.selectionBuilder = intent.getExtras().getParcelable(Constants.SELECTIONBUILDER);
        }
    }
}
