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


public class HTTPRequest extends AsyncTask<Location, Void, String> {

    public static final String TAG = "Test";
    private static final String API_KEY = "4808e5b3883c21c5462931bdece6fd6e";


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
        String descr = list.getJSONArray("weather").getJSONObject(0).getString("main");
        System.out.println("info: " + temp + "," + temp_max +"," + temp_min +"," + descr);
        return new Weather(descr,temp,temp_max,temp_min);

    }


    @Override
    protected String doInBackground(Location... locations) {
        try {
            String url = Uri.parse("https://api.openweathermap.org/data/2.5/find")
                    .buildUpon()
                    .appendQueryParameter("q", locations[0].getmName())
                    .appendQueryParameter("units", "metric")
                    .appendQueryParameter("appid", API_KEY)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            locations[0].setWeather(parseItems(jsonBody));
            System.out.println("LOCATION NOW: " + locations[0]);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return "OK";
    }

}