package ch.supsi.dti.isin.meteoapp.model;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.db.DbSchema;

public class Location implements Serializable {
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
        values.put(DbSchema.DbTable.Cols.WNAME,location.getWeather().getName());
        values.put(DbSchema.DbTable.Cols.TEMP,location.getWeather().getTemperature());
        values.put(DbSchema.DbTable.Cols.MINTEMP,location.getWeather().getMinTemperature());
        values.put(DbSchema.DbTable.Cols.MAXTEMP,location.getWeather().getMaxTemperature());
        values.put(DbSchema.DbTable.Cols.DESCRIPTION,location.getWeather().getDescription());
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