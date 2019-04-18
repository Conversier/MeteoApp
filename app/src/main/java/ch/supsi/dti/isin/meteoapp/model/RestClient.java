package ch.supsi.dti.isin.meteoapp.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RestClient  {

    private Logger logger = Logger.getAnonymousLogger();

    private String baseUrl;

    protected RestClient() {
        super();
    }

    @Override
    public String toString() {
        return "RestClient{" +
                "baseUrl='" + baseUrl + '\'' +
                '}';
    }

    public static RestClient create(String baseUrl) {
        RestClient instance = new RestClient();
        instance.baseUrl = baseUrl;
        return instance;
    }

    public String get(String href) {
        logger.info("invoked with href..." + href);

        String getUrl = null;
        if (href == null || href.isEmpty()) {
            getUrl = baseUrl + "/";
        } else {
            getUrl = baseUrl + "/" + href.toLowerCase();
        }

        String reply = null;
        try {
            URL url = new URL(getUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json;charset=UTF-8");

            InputStream inputStream;
            if (httpConnection.getResponseCode() >= 400) {
                inputStream = httpConnection.getErrorStream();
            } else {
                inputStream = httpConnection.getInputStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
            reply = br.lines().collect(Collectors.joining());

            httpConnection.disconnect();

        } catch (ConnectException e) {
            logger.severe("CONNECTION PROBLEM");
            return null;

        } catch (MalformedURLException e) {
            logger.severe("MALFORMED URI");
            return null;

        } catch (IOException e) {
            logger.severe("I/O ERROR");
            return null;
        }

        return reply;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getDaGoogle(String href) {
        logger.info("invoked with href..." + href);

        String getUrl = null;
        if (href == null || href.isEmpty()) {
            getUrl = baseUrl + "";
        } else {
            getUrl = baseUrl + "" + href.toLowerCase();
        }

        String reply = null;
        try {
            URL url = new URL(getUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json;charset=UTF-8");

            InputStream inputStream;
            if (httpConnection.getResponseCode() >= 400) {
                inputStream = httpConnection.getErrorStream();
            } else {
                inputStream = httpConnection.getInputStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
            reply = br.lines().collect(Collectors.joining());

            httpConnection.disconnect();

        } catch (ConnectException e) {
            logger.severe("CONNECTION PROBLEM");
            return null;

        } catch (MalformedURLException e) {
            logger.severe("MALFORMED URI");
            return null;

        } catch (IOException e) {
            logger.severe("I/O ERROR");
            return null;
        }

        return reply;
    }

    public String post(String resource, String input) {
        if (resource == null || resource.isEmpty()) {
            resource = "/";
        }

        String reply = null;
        try {
            URL url = new URL(baseUrl + "/" + resource);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            OutputStream os = httpConnection.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            InputStream inputStream;
            if (httpConnection.getResponseCode() != 200) {
                inputStream = httpConnection.getErrorStream();
            } else {
                inputStream = httpConnection.getInputStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
            reply = br.lines().collect(Collectors.joining());

            httpConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return reply;
    }
}
