package ch.supsi.dti.isin.meteoapp.fragments;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.HTTPRequest;
import ch.supsi.dti.isin.meteoapp.OnTaskCompleted;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.Weather;

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

        mIdTextView = v.findViewById(R.id.id_textView);
        mIdTextView.setText(mLocation.getId().toString());


//        mLocation.setmName("Rome,it");
//        HTTPRequest t = new HTTPRequest(DetailLocationFragment.this,"Rome,it");
//        t.execute();
//
//        Location required = t.getInfo();




        //mLocation.setWeather(new Weather("rain",4.0,8.0,3.0));

        switch (mLocation.getWeather().getDescrition()){
            case "rain":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.rain));
                break;
            case "sunny":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.sunny));
                break;
            case "snow":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.snow));
                break;
            case "cloudy":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.cloudy));
                break;
            case "thunderstorm":
                v.setBackground(getActivity().getResources().getDrawable(R.drawable.thunderstorm));
                break;
        }


        System.out.println(mLocation);


        return v;
    }
}

