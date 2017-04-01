package com.gpsalarm.selection;

import android.os.Parcel;
import android.os.Parcelable;

import com.gpsalarm.AlarmType;

/**
 * Created by Brent on 3/31/2017.
 */

public class SelectionBuilder implements Parcelable {
    private double latitude;
    private double longitude;
    private long startCheckTime;
    private long interval;
    private AlarmType alarmType;
    private long snoozeInterval;

    public SelectionBuilder() {
        this.latitude = -1;
        this.longitude = -1;
        this.startCheckTime = -1;
        this.interval = -1;
        this.alarmType = null;
        this.snoozeInterval = -1;
    }

    public void clear() {
        latitude = -1;
        longitude = -1;
        startCheckTime = -1;
        interval = -1;
        alarmType = null;
        snoozeInterval = -1;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStartCheckTime(long startCheckTime) {
        this.startCheckTime = startCheckTime;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public void setSnoozeInterval(long snoozeInterval) {
        this.snoozeInterval = snoozeInterval;
    }

    public boolean validate() {
        return(
            latitude >= 0
            && longitude >= 0
            && startCheckTime >= 0
            && interval >= 0
            && alarmType != null
            && snoozeInterval != -1
        );
    }

    public Selection generate() {
        if(!validate()) return null;
        else return new Selection(latitude,longitude, startCheckTime,interval,alarmType,snoozeInterval);
    }

    public double getLatitude() {
        return(latitude);
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

    private SelectionBuilder(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.startCheckTime = in.readLong();
        this.interval = in.readLong();
        this.alarmType = (AlarmType) in.readSerializable();
        this.snoozeInterval = in.readLong();
    }

    public static final Parcelable.Creator<SelectionBuilder> CREATOR
            = new Parcelable.Creator<SelectionBuilder>() {


        @Override
        public SelectionBuilder createFromParcel(Parcel in) {
            return new SelectionBuilder(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public SelectionBuilder[] newArray(int size) {
            return new SelectionBuilder[size];
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
        return(builder.toString());
    }
}
