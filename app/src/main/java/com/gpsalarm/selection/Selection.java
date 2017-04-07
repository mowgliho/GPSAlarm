package com.gpsalarm.selection;

import android.os.Parcel;
import android.os.Parcelable;

import com.gpsalarm.util.AlarmType;
import com.gpsalarm.util.LocationFinderType;

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
    private final LocationFinderType finderType;
    private final double distanceAway;

    public Selection(double latitude, double longitude, long startCheckTime, long interval,
                     AlarmType alarmType, long snoozeInterval, LocationFinderType finderType,
                     double distanceAway) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.startCheckTime = startCheckTime;
        this.interval = interval;
        this.alarmType = alarmType;
        this.snoozeInterval = snoozeInterval;
        this.finderType = finderType;
        this.distanceAway = distanceAway;
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

    private Selection(Parcel in) {
        this(
                in.readDouble(),
                in.readDouble(),
                in.readLong(),
                in.readLong(),
                (AlarmType) in.readSerializable(),
                in.readLong(),
                (LocationFinderType) in.readSerializable(),
                in.readDouble());
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
        builder.append("Fin: ").append(finderType);
        builder.append("Dst: ").append(distanceAway);
        return (builder.toString());
    }
}