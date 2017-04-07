package com.gpsalarm.selection;

import android.os.Parcel;
import android.os.Parcelable;

import com.gpsalarm.util.AlarmType;
import com.gpsalarm.util.LocationFinderType;

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
    private LocationFinderType finderType;
    private double distanceAway;

    public SelectionBuilder() {
        this.latitude = -1;
        this.longitude = -1;
        this.startCheckTime = System.currentTimeMillis() + 30000;
        this.interval = 0;
        this.alarmType = AlarmType.DEFAULT;
        this.snoozeInterval = 300000;
        this.finderType = LocationFinderType.BOTH;
        this.distanceAway = 100;
    }

    public void clear() {
        this.latitude = -1;
        this.longitude = -1;
        this.startCheckTime = System.currentTimeMillis() + 30000;
        this.interval = 0;
        this.alarmType = AlarmType.DEFAULT;
        this.snoozeInterval = 300000;
        this.finderType = LocationFinderType.BOTH;
        this.distanceAway = 100;
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

    public void setFinderType(LocationFinderType finderType) {
        this.finderType = finderType;
    }

    public void setDistanceAway(float distanceAway) {
        this.distanceAway = distanceAway;
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
        else return new Selection(latitude,longitude, startCheckTime,interval,alarmType,snoozeInterval, finderType, distanceAway);
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

    public LocationFinderType getFinderType() {
        return finderType;
    }

    public double getDistanceAway() {
        return distanceAway;
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
        dest.writeSerializable(finderType);
        dest.writeDouble(distanceAway);
    }

    private SelectionBuilder(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.startCheckTime = in.readLong();
        this.interval = in.readLong();
        this.alarmType = (AlarmType) in.readSerializable();
        this.snoozeInterval = in.readLong();
        this.finderType = (LocationFinderType) in.readSerializable();
        this.distanceAway = in.readDouble();
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
        builder.append("Fin: ").append(finderType);
        builder.append("Dst: ").append(distanceAway);
        return(builder.toString());
    }
}
