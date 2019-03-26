package ch.supsi.dti.isin.meteoapp.fragments;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import ch.supsi.dti.isin.meteoapp.HTTPRequest;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class DetailLocationFragment extends Fragment{
    private static final String ARG_LOCATION_ID = "location_id";

    private Location mLocation;
    private TextView mIdTextView;

    public static DetailLocationFragment newInstance(UUID locationId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCATION_ID, locationId);

        DetailLocationFragment fragment = new DetailLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID locationId = (UUID) getArguments().getSerializable(ARG_LOCATION_ID);
        mLocation = LocationsHolder.get(getActivity()).getLocation(locationId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_location, container, false);




        mLocation.setmName("Rome,it");
        HTTPRequest t = new HTTPRequest();
        try {
            t.execute(mLocation).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mIdTextView = v.findViewById(R.id.city_name);
        mIdTextView.setText(mLocation.getmName());
        mIdTextView = v.findViewById(R.id.temp);
        mIdTextView.setText(mLocation.getWeather().getTemperature()+"");
        mIdTextView = v.findViewById(R.id.temp_min);
        mIdTextView.setText(mLocation.getWeather().getMinTemperature()+"");
        mIdTextView = v.findViewById(R.id.temp_max);
        mIdTextView.setText(mLocation.getWeather().getMaxTemperature()+"");

        switch (mLocation.getWeather().getDescrition().toLowerCase()){
            case "rain":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.rain));
                break;
            case "sunny":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.sunny));
                break;
            case "snow":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.snow));
                break;
            case "clouds":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.clouds));
                break;
            case "thunderstorm":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.thunderstorm));
                break;
        }


        return v;
    }
}

