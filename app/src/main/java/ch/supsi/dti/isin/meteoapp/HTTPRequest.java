package ch.supsi.dti.isin.meteoapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.Weather;


public class HTTPRequest extends AsyncTask<Location, Void, String> {

    public static final String TAG = "Test";
    private static final String API_KEY = "4808e5b3883c21c5462931bdece6fd6e";

    public static void doRequest(Location mLocation) {
        HTTPRequest t = new HTTPRequest();
        try {

            t.execute(mLocation).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);

            int bytesRead;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();

            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }


    private Weather parseItems(JSONObject jsonBody) throws  JSONException {

        JSONObject list = jsonBody.getJSONArray("list").getJSONObject(0);
        JSONObject main = list.getJSONObject("main");
        Double temp = main.getDouble("temp");
        Double temp_min = main.getDouble("temp_min");
        Double temp_max = main.getDouble("temp_max");
        String cityName="";

        String name = list.getJSONArray("weather").getJSONObject(0).getString("main");
        String description = list.getJSONArray("weather").getJSONObject(0).getString("description");
        //System.out.println("info: " + temp + "," + temp_max +"," + temp_min +"," + descr);
        if(list.has("name"))
         return new Weather(name,description,temp,temp_max,temp_min,list.get("name").toString());
        else
            return new Weather(name,description,temp,temp_max,temp_min);
    }


    @Override
    protected String doInBackground(Location... locations) {
        try {
            String locationName=locations[0].getName();
            String url="";
            if(locationName.equals("GPS")){
                url = Uri.parse("https://api.openweathermap.org/data/2.5/find")
                        .buildUpon()
                        .appendQueryParameter("lat", locations[0].getLat()+"")
                        .appendQueryParameter("lon", locations[0].getLon()+"")
                        .appendQueryParameter("units", "metric")
                        .appendQueryParameter("appid", API_KEY)
                        .build().toString();
            }else{
                 url = Uri.parse("https://api.openweathermap.org/data/2.5/find")
                        .buildUpon()
                        .appendQueryParameter("q", locations[0].getName())
                        .appendQueryParameter("units", "metric")
                        .appendQueryParameter("appid", API_KEY)
                        .build().toString();
            }
            System.out.println("URL: "+url);
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);


            int count=jsonBody.getInt("count");
            if(count>0){
                locations[0].setWeather(parseItems(jsonBody));
                //System.out.println("LOCATION NOW: " + locations[0]);
            }else{
                locations[0].setWeather(null);
                //System.out.println("City doesn't exists");
            }

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return "OK";
    }

}