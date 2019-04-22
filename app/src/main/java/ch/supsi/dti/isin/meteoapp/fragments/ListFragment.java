package ch.supsi.dti.isin.meteoapp.fragments;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import ch.supsi.dti.isin.meteoapp.HTTPRequest;
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
    private static final int REQ_CODE = 0; //I use this variable to manage the asking of gps permission ?????????????????
    static boolean locationFound = false;
    private RecyclerView mLocationRecyclerView;
    private LocationAdapter mAdapter;
    private SQLiteDatabase mDatabase;


    public void clearDatabase(SQLiteDatabase db, String TABLE_NAME) {
        int count=db.delete(TABLE_NAME,"1",null);
        Toast.makeText(getContext(),"Deleted  " + count + " items, Restart app to apply changes!",Toast.LENGTH_LONG).show();
    }

    //Method called by the constructor of MainActivity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getActivity();
        mDatabase = new DbHelper(context).getWritableDatabase();
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        startGpsListener();
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mLocationRecyclerView = view.findViewById(R.id.recycler_view);
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLocationsList();
        return view;
    }

    private void refreshLocationsList() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                LinearLayoutManager.VERTICAL
        );
        //leggere da database passando il db come argomento al metodo get() di locationHolder
        List<Location> locations = LocationsHolder.get(getActivity(), mDatabase).getLocations();
        mLocationRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new LocationAdapter(locations);
        mLocationRecyclerView.setAdapter(mAdapter);
    }

    //This function allows me to manage the input inserted in the dialog, to add a new location
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == 0) {
            Location locationReceived = (Location) data.getSerializableExtra("Location");
            System.out.println("Valore ricevuto: " + locationReceived.getName());
            addLocation(locationReceived, 123);
            insertData(locationReceived);
        }

    }

    //Method to add a Location to the initial list
    private void addLocation(String valore, int i) {
        Location location = new Location();
        location.setName(valore);
        //If i is 0, the GPS location is updated
        if (i == 0) {
            LocationsHolder.get(getActivity()).getLocations().set(0, location);
        }
        //Add the city to the list
        else {
            LocationsHolder.get(getActivity()).getLocations().add(location);
        }

        mAdapter = new LocationAdapter(LocationsHolder.get(getActivity()).getLocations());
        mLocationRecyclerView.setAdapter(mAdapter);
    }

    private void addLocation(Location location, int i) {
        //If i is 0, the GPS location is updated
        if (i == 0) {
            LocationsHolder.get(getActivity()).getLocations().set(0, location);
        }
        //Add the city to the list
        else {
            LocationsHolder.get(getActivity()).getLocations().add(location);
        }

        mAdapter = new LocationAdapter(LocationsHolder.get(getActivity()).getLocations());
        mLocationRecyclerView.setAdapter(mAdapter);
    }

    //Method to manage the answer of the request of permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permessions obtained");
                }
                return;
            }
        }
    }


    void startGpsListener() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_CODE);
            Toast.makeText(getContext(), "Per confermare la modifica, riavvia l'app", Toast.LENGTH_LONG).show();
        } else

            System.out.println("ho i permessi");
        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(LocationAccuracy.HIGH)
                .setInterval(60000)
                .setDistance(0);
        SmartLocation.with(getContext()).location().continuous().config(builder.build())
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(android.location.Location location) {
                        Location gpsLoc = LocationsHolder.get(getContext()).getLocations().get(0);
                        gpsLoc.setLat(location.getLatitude());
                        gpsLoc.setLon(location.getLongitude());
                        System.out.println("MYLOC" + location.getLatitude() + "," + location.getLongitude());
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


    private void insertData(String city_name) {
        Location entry = new Location();
        entry.setName(city_name);
        ContentValues values = Location.getContentValues(entry);
        mDatabase.insert(DbSchema.DbTable.NAME, null, values);
    }

    private void insertData(Location location) {
        System.out.println(mDatabase.isOpen());
        ContentValues values = Location.getContentValues(location);
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
        switch (item.getItemId()){
            case R.id.menu_add:
                List<Location> locations = LocationsHolder.get(getActivity(), mDatabase).getLocations();
                System.out.println("Dimensione: "+locations.size());
                FragmentManager fm = getFragmentManager();
                InsertLocationFragment il = new InsertLocationFragment();
                il.setTargetFragment(this, 0);
                il.show(fm, null);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.show();
                break;
            case R.id.menu_cleardb:
                new AlertDialog.Builder(getContext())
                        .setTitle("Clear list")
                        .setMessage("Do you really want to reset the list?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getContext(), "List empty", Toast.LENGTH_SHORT).show();
                                clearDatabase(mDatabase, DbSchema.DbTable.NAME);
                                refreshLocationsList();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    // Holder

    private class LocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private Location mLocation;

        public LocationHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.name);
        }

        //This method is called when i click on a city to view Detail
        @Override
        public void onClick(View view) {
            System.out.println("Clicked : " + mLocation.getName());
            HTTPRequest.doRequest(mLocation); //
            if (mLocation.getWeather() != null) {
                Intent intent = DetailActivity.newIntent(getActivity(), mLocation);
                startActivity(intent);
            }
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
