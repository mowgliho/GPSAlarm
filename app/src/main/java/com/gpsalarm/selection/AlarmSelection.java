package com.gpsalarm.selection;

import android.os.Bundle;
import android.view.View;

import com.gpsalarm.util.AlarmType;
import com.gpsalarm.gpsalarm.R;

public class AlarmSelection extends Selector {
    private static final long DEFAULTSNOOZETIME = 300000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_selection);
    }

    @Override
    public void submit(View view) {
        selectionBuilder.setAlarmType(AlarmType.DEFAULT);
        selectionBuilder.setSnoozeInterval(DEFAULTSNOOZETIME);
        super.submit();
    }
}
