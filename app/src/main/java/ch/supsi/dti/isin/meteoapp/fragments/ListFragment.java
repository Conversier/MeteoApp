package ch.supsi.dti.isin.meteoapp.fragments;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.activities.DetailActivity;
import ch.supsi.dti.isin.meteoapp.db.DbHelper;
import ch.supsi.dti.isin.meteoapp.db.DbSchema;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class ListFragment extends Fragment {
    private static final int REQ_CODE =0; //I use this variable to manage the asking of gps permission ?????????????????
    private RecyclerView mLocationRecyclerView;
    private LocationAdapter mAdapter;
    private SQLiteDatabase mDatabase;

    //Method called by the constructor of MainActivity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getActivity();
        mDatabase = new DbHelper(context).getWritableDatabase();
        setHasOptionsMenu(true);
    }

    //This function allows me to manage the input inserted in the dialog, to add a new location
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK)
            return;
        if(requestCode==0){
            String valore=(String)data.getSerializableExtra("tag");
            addLocation(valore, 1);
        }

    }

    //Method to add a Location to the initial list
    private void addLocation(String valore, int i) {
        Location location=new Location();
        location.setName(valore);
        //If i is 0, the GPS location is updated
        if(i==0){
            LocationsHolder.get(getActivity()).getLocations().set(0,location);
        }
        //Add the city to the list
        else{
            LocationsHolder.get(getActivity()).getLocations().add(location);
        }

        mAdapter=new LocationAdapter(LocationsHolder.get(getActivity()).getLocations());
        mLocationRecyclerView.setAdapter(mAdapter);
    }

    //Method to manage the answer of the request of permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQ_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permessions obtained");
                }
                return;
            }
        }
    }

    //This method manage the GPS by giving our position
    private void startLocationListener(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_CODE);
        }
        else
            System.out.println("ho i permessi");
        LocationParams.Builder builder=new LocationParams.Builder()
                .setAccuracy(LocationAccuracy.HIGH)
                .setDistance(0)
                .setInterval(5000);
        SmartLocation.with(getContext()).location().continuous().config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(android.location.Location location) {
                        addLocation(location.getLatitude()+","+location.getLongitude(),0);
                    }
                });
    }


    //This method is called after onCreate(), it allows us to do any graphical initialisations
    //It should return a view that is the main UI of the view

    @Override
    public void onStop() {
        super.onStop();
        mDatabase.close();
    }

    //insert method


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //startLocationListener();
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mLocationRecyclerView = view.findViewById(R.id.recycler_view);
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                LinearLayoutManager.VERTICAL
        );
        //leggere da database passando il db come argomento al metodo get() di locationHolder
        List<Location> locations = LocationsHolder.get(getActivity(),mDatabase).getLocations();
        mLocationRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new LocationAdapter(locations);
        mLocationRecyclerView.setAdapter(mAdapter);

        insertData();

        return view;
    }

    private void insertData() {
        Location entry = new Location();
        entry.setName("prova");
        ContentValues values = Location.getContentValues(entry);
        mDatabase.insert(DbSchema.DbTable.NAME, null, values);
    }

    // Menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_list, menu);
    }

    //This is called when i click on the plus button on the top in the main view
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm=getFragmentManager();
        InsertLocationFragment il=new InsertLocationFragment();
        il.setTargetFragment(this,0);
        //il.setTargetFragment(this,0);
        il.show(fm,null);

        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());
        mBuilder.show();

        return super.onOptionsItemSelected(item);

    }

    // Holder

    private class LocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private Location mLocation;
        private ImageView mImageView;

        public LocationHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.name);
            mImageView=itemView.findViewById(R.id.item_image);
        }

        @Override
        public void onClick(View view) {
            mImageView.setBackgroundResource(R.drawable.common_google_signin_btn_icon_dark);
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            final EditText input=new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            Intent intent = DetailActivity.newIntent(getActivity(), mLocation.getId());
            startActivity(intent);
        }

        public void bind(Location location) {
            mLocation = location;
            mNameTextView.setText(mLocation.getName());
        }
    }

    // Adapter

    private class LocationAdapter extends RecyclerView.Adapter<LocationHolder> {
        private List<Location> mLocations;

        public LocationAdapter(List<Location> locations) {
            mLocations = locations;
        }

        @Override
        public LocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LocationHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(LocationHolder holder, int position) {
            Location location = mLocations.get(position);
            holder.bind(location);
        }

        @Override
        public int getItemCount() {
            return mLocations.size();
        }
    }
}
