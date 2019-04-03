package ch.supsi.dti.isin.meteoapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Location {
    private UUID Id;
    private String mName;

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

    public static String autoCompleteName(String cityname) throws JSONException {
        RestClient rc=new RestClient();
        JSONObject obj=null;
        String key=
                "AIzaSyALUp7fZvGAZIsApVH25N0DpwCP7tw39Jg "
                ;
        //"AIzaSyALUp7fZvGAZIsApVH25N0DpwCP7tw39Jg";
        String url="https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+cityname.replace(' ','+')+"&key="+key;
        //System.out.println("Autocomplete: "+url);
        obj=new JSONObject(rc.create(url).getDaGoogle(""));
        JSONArray predictions=obj.getJSONArray("predictions");
        JSONObject predictions_0=predictions.getJSONObject(0);
        String finded_name=predictions_0.get("description").toString();
        return  finded_name;
    }
}