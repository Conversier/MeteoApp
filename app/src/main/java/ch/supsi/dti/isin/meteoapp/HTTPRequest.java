package ch.supsi.dti.isin.meteoapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.Weather;


public class HTTPRequest extends AsyncTask<Void, Void, Location> {

    public static final String TAG = "Test";
    private static final String API_KEY = "4808e5b3883c21c5462931bdece6fd6e";

    private OnTaskCompleted listener;
    private String locationName;

    public HTTPRequest(OnTaskCompleted listener,String locationName) {
        this.listener = listener;
        this.locationName = locationName;
    }


    public byte[] getUrlBytes(String urlSpec) throws IOException {
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

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public Location getInfo() {
        return doInBackground();

    }

    private void parseItems(Location locationRequested, JSONObject jsonBody) throws IOException, JSONException {
        JSONArray weather = jsonBody.getJSONArray("weather");
        String main = weather.get(1).toString();
        Double temperature = Double.parseDouble(jsonBody.getJSONObject("temp").toString());
        Double maxTemperature = Double.parseDouble(jsonBody.getJSONObject("temp_min").toString());
        Double minTemperature = Double.parseDouble(jsonBody.getJSONObject("temp_max").toString());
        locationRequested.setWeather(new Weather(main,temperature,maxTemperature,minTemperature));
    }



    @Override
    protected Location doInBackground(Void... voids) {
        Location locationRequested = new Location();
        try {
            String url = Uri.parse("https://api.openweathermap.org/data/2.5/find")
                    .buildUpon()
                    .appendQueryParameter("q", locationName)
                    .appendQueryParameter("units", "metric")
                    .appendQueryParameter("appid", API_KEY)
                    .build().toString();
            System.out.println(url);
            String jsonString = getUrlString(url);
            System.out.println(jsonString);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(locationRequested, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return locationRequested;
    }
}