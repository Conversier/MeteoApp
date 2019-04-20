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
    private double lat;
    private double lon;
    private Weather weather;
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Location location=new Location();
        location.setId(this.getId());
        location.setName(this.getName());
        location.setLat(this.getLat());
        location.setLon(this.getLon());
        location.setWeather((Weather)this.getWeather().clone());
        return location;
    }

    public Location(UUID id, String mName) {
        this.Id = id;
        this.mName = mName;
        lat=0.0;
        lon=0.0;
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
        lat=0.0;
        lon=0.0;
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