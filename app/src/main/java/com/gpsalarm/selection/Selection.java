package com.gpsalarm.selection;

import android.os.Parcel;
import android.os.Parcelable;

import com.gpsalarm.AlarmType;

/**
 * Created by Brent on 3/31/2017.
 */

public class Selection implements Parcelable {
    private final double latitude;
    private final double longitude;
    private final long startCheckTime;
    private final long interval;
    private final AlarmType alarmType;
    private final long snoozeInterval;

    public Selection(double latitude, double longitude, long startCheckTime, long interval, AlarmType alarmType, long snoozeInterval) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.startCheckTime = startCheckTime;
        this.interval = interval;
        this.alarmType = alarmType;
        this.snoozeInterval = snoozeInterval;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getStartCheckTime() {
        return startCheckTime;
    }

    public long getInterval() {
        return interval;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public long getSnoozeInterval() {
        return snoozeInterval;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeLong(startCheckTime);
        dest.writeLong(interval);
        dest.writeSerializable(alarmType);
        dest.writeLong(snoozeInterval);
    }

    private Selection(Parcel in) {
        this(in.readDouble(), in.readDouble(), in.readLong(), in.readLong(), (AlarmType) in.readSerializable(), in.readLong());
    }

    public static final Parcelable.Creator<Selection> CREATOR
            = new Parcelable.Creator<Selection>() {
        @Override
        public Selection createFromParcel(Parcel in) {
            return new Selection(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Selection[] newArray(int size) {
            return new Selection[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append("Lat: ").append(latitude);
        builder.append("Lon: ").append(longitude);
        builder.append("Del: ").append(startCheckTime);
        builder.append("Int: ").append(interval);
        builder.append("Ala: ").append(alarmType);
        builder.append("Sno: ").append(snoozeInterval);
        return (builder.toString());
    }
}