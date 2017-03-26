package com.gpsalarm.gpsalarm.engine.location;

/**
 * Created by Brent on 3/26/2017.
 */

public enum LocationInputType {
    COORDINATES("coordinates"), DATABASE("well known locations"), INTERNET("internet search"), HISTORY("my history");

    private final String displayString;

    private LocationInputType(String displayString) {
        this.displayString = displayString;
    }

    @Override
    public String toString() {
        return displayString;
    }
}
