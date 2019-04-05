package ch.supsi.dti.isin.meteoapp.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class LocationsHolder {

    private static LocationsHolder sLocationsHolder;
    private List<Location> mLocations;

    public static LocationsHolder get(Context context) {
        if (sLocationsHolder == null)
            sLocationsHolder = new LocationsHolder(context);

        return sLocationsHolder;
    }

    private LocationsHolder(Context context) {
        ArrayList<String> list= new ArrayList<>();
        String[] locales= Locale.getISOCountries();
        mLocations = new ArrayList<>();
        mLocations.add(createLocation("La mia posizione"));

    }
    public Location createLocation(String name){
        Location c=new Location();
        c.setName(name);
        return  c;
    }
    public List<Location> getLocations() {
        return mLocations;
    }

    public Location getLocation(UUID id) {
        for (Location location : mLocations) {
            if (location.getId().equals(id))
                return location;
        }

        return null;
    }
}
