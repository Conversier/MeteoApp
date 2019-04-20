package ch.supsi.dti.isin.meteoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.OnTaskCompleted;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class DetailActivity extends SingleFragmentActivity implements OnTaskCompleted {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";

    public static Intent newIntent(Context packageContext, Location mLocation) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra("Location",mLocation);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Location mLocation = (Location) getIntent().getExtras().getSerializable("Location");
        return new DetailLocationFragment().newInstance(mLocation);

    }

    @Override
    public void onTaskCompleted(Location locationRequired) {


    }
}
