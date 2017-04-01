package com.gpsalarm.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gpsalarm.Constants;
import com.gpsalarm.gpsalarm.R;

/**
 * Created by Brent on 4/1/2017.
 */

public abstract class Selector extends AppCompatActivity {
    protected SelectionBuilder selectionBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.selectionBuilder = (SelectionBuilder) this.getIntent().getExtras().getParcelable(Constants.SELECTIONBUILDER);
    }

    public abstract void submit(View view);

    protected void submit() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.SELECTIONBUILDER, selectionBuilder);
        setResult(Constants.HASRESULT, resultIntent);
        finish();
    }
}
