package ch.supsi.dti.isin.meteoapp.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.db.CursorWrapper;
import ch.supsi.dti.isin.meteoapp.db.DbSchema;

public class LocationsHolder {

    private static LocationsHolder sLocationsHolder;
    private List<Location> mLocations;

    public static LocationsHolder get(Context context, SQLiteDatabase db) {
        if (sLocationsHolder == null)
            sLocationsHolder = new LocationsHolder(context,db);
        return sLocationsHolder;
    }

    public static LocationsHolder get(Context context) {
        if (sLocationsHolder == null)
            sLocationsHolder = new LocationsHolder(context);

        return sLocationsHolder;
    }

    private LocationsHolder(Context context){
        mLocations = new ArrayList<>();
    }
    private LocationsHolder(Context context, SQLiteDatabase database) {
        mLocations = new ArrayList<>();

        //LETTURA DA DB PER CARICARE LA LISTA DI LOCATIONS
        CursorWrapper cursorWrapper = queryData(null, null, database);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                Location entry = cursorWrapper.getLocation();
                //res += "\n" + entry.getName();
                mLocations.add(entry);
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }


        /*for (int i = 0; i < 10; i++) {
            Location location = new Location();
            location.setName("Location # " + i);
            mLocations.add(location);
        }*/
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

    private CursorWrapper queryData(String whereClause, String[] whereArgs,SQLiteDatabase db){
        Cursor cursor = db.query(
                DbSchema.DbTable.NAME,
                null,   //columns - null selects all columns
                whereClause,
                whereArgs,
                null,   //groupBy
                null,   //having
                null    //orderBy
        );

        return new CursorWrapper(cursor);
    }
}
