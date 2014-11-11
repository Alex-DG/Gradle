package com.project.picgallery.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.project.picgallery.GridViewActivity;
import com.project.picgallery.R;

public class NoPicFoundDialogFragment extends DialogFragment {

	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_custom_alert_dialog, null))
               .setPositiveButton(R.string.ad_buttonLabel, new DialogInterface.OnClickListener() {
            	   
                   public void onClick(DialogInterface dialog, int id) {
                	   Intent intent = new Intent(getActivity(), GridViewActivity.class);
                	   startActivity(intent);
                   }
               });
        
        // Configuration of dialog's position
        AlertDialog alertDialogPosition = builder.create();
        /*alertDialogPosition.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alertDialogPosition.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 20;   //x position
        wmlp.y = 400;*/
        
        // Create the AlertDialog object and return it
        return alertDialogPosition;
    }
}
