package com.project.picgallery;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.project.picgallery.adapter.GridViewImageAdapter;
import com.project.picgallery.asynctask.JsonDownloaderTask;
import com.project.picgallery.helper.Utils;
import com.project.picgallery.model.Wallpaper;
 
/*
 * Image Preview Activity class (MainActivity)
 */
public class GridViewActivity extends Activity { 
	private String url = "http://www.alexandrediguida.com/service_json_wallpapers.php";
	
    private Utils utils;    
    private GridViewImageAdapter adapter;
    private GridView gridView;
    
    public ArrayList<Wallpaper> wallpapersList = new ArrayList<Wallpaper>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        
        utils = new Utils(this);

        //Execute Asynchronous JSON task
        JsonDownloaderTask task = new JsonDownloaderTask(this);
        task.execute(url);   
        
        gridView = (GridView) findViewById(R.id.grid_view);
    }
    
    /*
     * Create Menu Option in action bar
     * And manage search event. 
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
 
        // Associate searchable configuration with the SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        
        // Manage search event on click device's keyboard
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
        	@Override
			public boolean onQueryTextSubmit(String query) {
        		if(wallpapersList != null){
	        		Intent intent = new Intent(GridViewActivity.this, SearchResultsActivity.class);
	        		intent.putParcelableArrayListExtra("WALLP_ARRAY", wallpapersList);
	        		intent.putExtra("USER_QUERY", query);     	

	        		startActivity(intent);
	       
        		}
				return true;
			}
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});

        return super.onCreateOptionsMenu(menu);
    } 
    
    /**
	 * JSON task completed 
	 * Manage Wallpapers objects
	 */
	public void jsonTaskComplete(JSONArray data)
	{
		Wallpaper w = null;
		
		String id = "";
		String preview = "";
		String fullscreen = "";
		String tags = "";
		String size = "";
		
		try {
			
			for(int indexData = 0; indexData < data.length(); indexData++)
			{
				w = new Wallpaper();			
						
				// Fetching data
				id = data.getJSONObject(indexData).getString("id");
				preview = data.getJSONObject(indexData).getString("preview");
				fullscreen = data.getJSONObject(indexData).getString("fullscreen");				
				tags = data.getJSONObject(indexData).getString("tags");
				size = data.getJSONObject(indexData).getString("size");
				
				// Setting data
				w.setId(id);
				w.setPreview(preview);
				w.setFullscreen(fullscreen);
				w.setSize(size);				
				w.setTags(tags);
				
				// Adding result as a wallpaper object in the list
				wallpapersList.add(w);					
			}			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// Init. GridView
		utils.InitilizeGridLayout(GridViewActivity.this, gridView);
		
		// Gridview adapter
        adapter = new GridViewImageAdapter(GridViewActivity.this, wallpapersList);
 
        // Setting grid view adapter
        gridView.setAdapter(adapter);		
	}	
}
 

