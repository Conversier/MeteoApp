package ch.supsi.dti.isin.meteoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.OnTaskCompleted;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class DetailActivity extends SingleFragmentActivity implements OnTaskCompleted {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";

    public static Intent newIntent(Context packageContext, Location mLocation) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra("Location", mLocation.getName());
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID locationId = (UUID) getIntent().getSerializableExtra(EXTRA_LOCATION_ID);
        String locationname=getIntent().getStringExtra("Location");
        return new DetailLocationFragment().newInstance(locationname);
    }

    @Override
    public void onTaskCompleted(Location locationRequired) {


    }
}
