package ch.supsi.dti.isin.meteoapp.db;

import android.database.Cursor;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.model.Location;

public class CursorWrapper extends android.database.CursorWrapper {
    public CursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Location getLocation(){
        String id = getString(getColumnIndex(DbSchema.DbTable.Cols.UUID));
        String name = getString(getColumnIndex(DbSchema.DbTable.Cols.NAME));

        return new Location(UUID.fromString(id), name);
    }
}
