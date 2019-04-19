package ch.supsi.dti.isin.meteoapp.db;

import android.database.Cursor;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.Weather;

public class CursorWrapper extends android.database.CursorWrapper {
    public CursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Location getLocation(){
        String id = getString(getColumnIndex(DbSchema.DbTable.Cols.UUID));
        String name = getString(getColumnIndex(DbSchema.DbTable.Cols.NAME));
        String wname = getString(getColumnIndex(DbSchema.DbTable.Cols.WNAME));
        double temp=getDouble(getColumnIndex(DbSchema.DbTable.Cols.TEMP));
        double mintemp=getDouble(getColumnIndex(DbSchema.DbTable.Cols.MINTEMP));
        double maxtemp=getDouble(getColumnIndex(DbSchema.DbTable.Cols.MAXTEMP));
        String description = getString(getColumnIndex(DbSchema.DbTable.Cols.DESCRIPTION));
        Location location=new Location(UUID.fromString(id), name);
        location.setWeather(new Weather(wname,description,temp,maxtemp,mintemp));
        return location;
    }
}
