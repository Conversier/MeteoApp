package ch.supsi.dti.isin.meteoapp;


import ch.supsi.dti.isin.meteoapp.model.Location;

public interface OnTaskCompleted {
    void onTaskCompleted(Location locationRequired);
}
