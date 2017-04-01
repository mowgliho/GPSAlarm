package com.gpsalarm.selection;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gpsalarm.Constants;
import com.gpsalarm.gpsalarm.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckFrequencySelection extends Selector {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_frequency_selection);
    }

    @Override
    public void submit(View view) {
        selectionBuilder.setInterval(Long.parseLong(((EditText) findViewById(R.id.checkFrequencyText)).getText().toString()));
        super.submit();
    }
}
