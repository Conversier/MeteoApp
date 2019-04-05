package ch.supsi.dti.isin.meteoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.Fragment;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.OnTaskCompleted;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;

public class DetailActivity extends SingleFragmentActivity implements OnTaskCompleted {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";

    public static Intent newIntent(Context packageContext, UUID locationId) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_LOCATION_ID, locationId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID locationId = (UUID) getIntent().getSerializableExtra(EXTRA_LOCATION_ID);
        return new DetailLocationFragment().newInstance(locationId);
    }

    @Override
    public void onTaskCompleted(Location locationRequired) {


    }
}
