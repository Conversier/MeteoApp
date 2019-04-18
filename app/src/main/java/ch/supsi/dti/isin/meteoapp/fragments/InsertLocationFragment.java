package ch.supsi.dti.isin.meteoapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class InsertLocationFragment extends DialogFragment {

    Button b_addCity;
    EditText et_cityname;

    private void sendResult(int resultCode, String name){
        if(getTargetFragment()==null)
            return;
        Intent intent=new Intent();
        intent.putExtra("tag",name);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.add_location,null);
        et_cityname=v.findViewById(R.id.editText_cityname);
        b_addCity=v.findViewById(R.id.button_addCity);

        b_addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //String name= Location.autoCompleteName(et_cityname.getText().toString());
                    //System.out.println(et_cityname.getText().toString());
                    String name=et_cityname.getText().toString();
                    sendResult(Activity.RESULT_OK,name);
                    dismiss();


            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }


}
