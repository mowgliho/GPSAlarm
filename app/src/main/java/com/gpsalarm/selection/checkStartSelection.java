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

public class checkStartSelection extends AppCompatActivity {
    private static final DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
    private TextView errorText;

    private SelectionBuilder selectionBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_start_selection);
        this.selectionBuilder = (SelectionBuilder) this.getIntent().getExtras().getParcelable(Constants.SELECTIONBUILDER);
        this.errorText = (TextView) findViewById(R.id.checkStartError);
        ((DatePicker) findViewById(R.id.checkStartDatePicker)).setMinDate(System.currentTimeMillis()-1000);
    }

    public void submit(View view) {
        long time = getDateFromPickers(
                (DatePicker) findViewById(R.id.checkStartDatePicker),
                (TimePicker) findViewById(R.id.checkStartTimePicker)
        );
        if(time < System.currentTimeMillis()) {
            errorText.setText("Please select a time in the future, not one " + (System.currentTimeMillis() - time)/1000 + " seconds ago");
        } else {
            selectionBuilder.setStartCheckTime(time);
            Intent resultIntent = new Intent();
            resultIntent.putExtra(Constants.SELECTIONBUILDER, selectionBuilder);
            setResult(Constants.HASRESULT, resultIntent);
            finish();
        }
    }

    private long getDateFromPickers(DatePicker datePicker, TimePicker timePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE, Build.VERSION.SDK_INT >= 23? timePicker.getMinute():timePicker.getCurrentMinute());
        calendar.set(Calendar.HOUR_OF_DAY, Build.VERSION.SDK_INT >= 23? timePicker.getHour(): timePicker.getCurrentHour());
        return calendar.getTimeInMillis();
    }
}
