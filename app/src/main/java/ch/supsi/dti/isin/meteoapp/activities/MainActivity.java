package ch.supsi.dti.isin.meteoapp.activities;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import ch.supsi.dti.isin.meteoapp.fragments.ListFragment;

public class MainActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new ListFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
