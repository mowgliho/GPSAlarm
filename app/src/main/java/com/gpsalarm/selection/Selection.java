package com.gpsalarm.selection;

import com.gpsalarm.AlarmType;

/**
 * Created by Brent on 3/31/2017.
 */

public class Selection {
    private final double latitude;
    private final double longitude;
    private final long delay;
    private final long interval;
    private final AlarmType alarmType;
    private final long snoozeInterval;

    public Selection(double latitude, double longitude, long delay, long interval, AlarmType alarmType, long snoozeInterval) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.delay = delay;
        this.interval = interval;
        this.alarmType = alarmType;
        this.snoozeInterval = snoozeInterval;
    }
}
