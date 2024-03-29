package ch.supsi.dti.isin.meteoapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import ch.supsi.dti.isin.meteoapp.HTTPRequest;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class DetailLocationFragment extends Fragment {
    private static final String ARG_LOCATION_ID = "location_id";
    private static final String ARG_LOCATION_NAME = "location_name";
    private Location mLocation;
    private TextView mIdTextView;


    public static DetailLocationFragment newInstance(Location location) {
        Bundle args = new Bundle();
        args.putSerializable("Location", location);
        DetailLocationFragment fragment = new DetailLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = (Location) getArguments().getSerializable("Location");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_location, container, false);
        ImageView imageView = v.findViewById(R.id.image_view);
        serWeatherInformations(v);
        setBackgroundWeather(v, imageView);
        return v;
    }


    private void serWeatherInformations(View v) {
        mIdTextView = v.findViewById(R.id.city_name);
        if (mLocation.getWeather().getCityName() != null)
            mIdTextView.setText(mLocation.getWeather().getCityName());
        else
            mIdTextView.setText(mLocation.getName());
        mIdTextView = v.findViewById(R.id.temp);
        mIdTextView.setText(mLocation.getWeather().getTemperature() + "");
        mIdTextView = v.findViewById(R.id.temp_min);
        mIdTextView.setText(mLocation.getWeather().getMinTemperature() + "");
        mIdTextView = v.findViewById(R.id.temp_max);
        mIdTextView.setText(mLocation.getWeather().getMaxTemperature() + "");
        mIdTextView = v.findViewById(R.id.weatherDescription);
        mIdTextView.setText(mLocation.getWeather().getDescription());
    }

    private void setBackgroundWeather(View v, ImageView imageView) {
        switch (mLocation.getWeather().getName().toLowerCase()) {
            case "rain":
            case "drizzle":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.rain));
                imageView.setImageResource(R.drawable.iconrain);
                break;
            case "clear":
            case "mist":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.clear));
                imageView.setImageResource(R.drawable.iconclear);
                break;
            case "snow":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.snow));
                imageView.setImageResource(R.drawable.iconsnow);
                break;
            case "clouds":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.clouds));
                imageView.setImageResource(R.drawable.iconclouds);

                break;
            case "thunderstorm":
            case "tornado":
            case "sand":
            case "ash":
            case "squall":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.thunderstorm));
                imageView.setImageResource(R.drawable.iconthunderstorm);
                break;
            case "fog":
            case "haze":
            case "dust":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.fog));
                imageView.setImageResource(R.drawable.iconclouds);
        }
    }
}

