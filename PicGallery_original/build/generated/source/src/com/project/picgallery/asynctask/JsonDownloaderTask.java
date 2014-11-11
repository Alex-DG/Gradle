package com.project.picgallery.asynctask;

import java.io.IOException;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.os.AsyncTask;

import com.project.picgallery.GridViewActivity;
import com.project.picgallery.helper.JSONParser;
import com.project.picgallery.helper.Utils;

/*
 * Asynchronous Task download manager for JSON data
 */
public class JsonDownloaderTask extends AsyncTask<String, String, JSONArray> {


	private GridViewActivity _mainActivity;
	private AlertDialog progress;
	private Utils _utils;

    public JsonDownloaderTask(GridViewActivity mainActivity){
        this._mainActivity = mainActivity;
        this._utils = new Utils(mainActivity);        
    }
    
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
    	super.onPreExecute(); 
    	progress = _utils.callProgressDialog();
    	progress.show();
    }

    @Override
    protected JSONArray doInBackground(String... url) {  	   	
    	JSONParser jParser = new JSONParser();
        // Getting JSON from URL
    	JSONArray jsonArray = null;
		try {
			jsonArray = jParser.getJSONFromUrl(url[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return jsonArray;        
    }

    protected void onPostExecute(JSONArray data){
    	// Dismiss the dialog after getting all products
    	progress.dismiss();
    	
    	// Pass data to the activity
        _mainActivity.jsonTaskComplete(data);
    }    
    
}
