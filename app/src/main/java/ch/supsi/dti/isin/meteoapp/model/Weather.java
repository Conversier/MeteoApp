package ch.supsi.dti.isin.meteoapp.model;

public class Weather {

    private String descrition;

    public void setDescrition(String descrition) {
        this.descrition = descrition;
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

    public Weather(String descrition, Double temperature, Double maxTemperature, Double minTemperature) {
        this.descrition = descrition;
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public String getDescrition() {
        return descrition;
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
    public String toString() {
        return "Weather{" +
                "descrition='" + descrition + '\'' +
                ", temperature=" + temperature +
                ", maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                '}';
    }
}
