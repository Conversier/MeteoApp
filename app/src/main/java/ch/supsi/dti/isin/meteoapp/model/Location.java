package ch.supsi.dti.isin.meteoapp.model;

import android.content.ContentValues;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.db.DbSchema;

public class Location {
    private UUID Id;
    private String mName;
    private Weather weather;

    public Location(UUID id, String mName) {
        this.Id = id;
        this.mName = mName;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Location() {
        Id = UUID.randomUUID();
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public static ContentValues getContentValues(Location location) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.DbTable.Cols.UUID, location.getId().toString());
        values.put(DbSchema.DbTable.Cols.NAME, location.getName());
        return values;
    }

    @Override
    public String toString() {
        return "Location{" +
                "Id=" + Id +
                ", mName='" + mName + '\'' +
                ", weather=" + weather +
                '}';
    }
}