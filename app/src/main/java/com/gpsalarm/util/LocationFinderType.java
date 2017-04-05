package com.gpsalarm.util;

import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Brent on 4/5/2017.
 */

public enum LocationFinderType {
    BOTH(new String[]{LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER},"Both GPS and Network"),
    GPS(new String[]{LocationManager.GPS_PROVIDER},"GPS"),
    NETWORK(new String[]{LocationManager.NETWORK_PROVIDER},"Network");

    LocationFinderType(String[] providers,String name) {
        this.providers = providers;
        this.name = name;
    }

    private final String[] providers;
    private final String name;

    public String[] getProviders() {
        return providers;
    }

    @Override
    public String toString() {
        return name;
    }
}
