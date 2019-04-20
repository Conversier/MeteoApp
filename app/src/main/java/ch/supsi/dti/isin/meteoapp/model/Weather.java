package ch.supsi.dti.isin.meteoapp.model;

import java.io.Serializable;

public class Weather implements Serializable {

    private  String cityName;

    private String name;

    public Weather(String name,String description, Double temperature, Double maxTemperature, Double minTemperature, String cityName) {
        this.name = name;
        this.description = description;
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.cityName=cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    private Double temperature;
    private Double maxTemperature;
    private Double minTemperature;

    public Weather(String name, Double temperature, Double maxTemperature, Double minTemperature) {
        this.name = name;
        this.description="";
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        cityName=null;
    }

    public Weather(String name,String description, Double temperature, Double maxTemperature, Double minTemperature) {
        this.name = name;
        this.description = description;
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public String getName() {
        return name;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return  new Weather(this.name,
                this.description,
                this.temperature,
                this.maxTemperature,
                this.minTemperature);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", temperature=" + temperature +
                ", maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                '}';
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
