package com.project.picgallery;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.GridView;

import com.project.picgallery.adapter.GridViewImageSearchResultsAdapter;
import com.project.picgallery.fragment.NoPicFoundDialogFragment;
import com.project.picgallery.helper.Utils;
import com.project.picgallery.model.Wallpaper;
 
/*
 * Image Preview Search Results Activity class
 */
public class SearchResultsActivity extends FragmentActivity {
    
    private GridView gridView;
    private Utils utils;
    private int columnWidth;
    private GridViewImageSearchResultsAdapter adapter;
    
    private ArrayList<Wallpaper> wArray = new ArrayList<Wallpaper>();
    private ArrayList<Wallpaper> wArrayFiltred = new ArrayList<Wallpaper>();
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_search_results);
        
        // Enabling Back navigation
        ActionBar actionBar = getActionBar();  
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();		
        wArray = this.getIntent().getParcelableArrayListExtra("WALLP_ARRAY");        
        String queryUser = (String) i.getExtras().get("USER_QUERY");

        if(wArray != null && queryUser != null){
        	applyFilter(wArray, queryUser);       	
        }
        
        gridView = (GridView) findViewById(R.id.grid_view_search_results);
        
        // Init GridView
        utils = new Utils(this);        
     	utils.InitilizeGridLayout(SearchResultsActivity.this, gridView);
     		
 		// Gridview adapter         
     	adapter = new GridViewImageSearchResultsAdapter(SearchResultsActivity.this, wArrayFiltred, columnWidth);  
        gridView.setAdapter(adapter);        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Apply filter to the wallpapers array list
     */
    public boolean applyFilter(ArrayList<Wallpaper> arrayL, String filter){
    	boolean onFilterChecked = false;
    	
    	for(Wallpaper w : wArray)
    	{	
        	String wTags = w.getTags();
        	String[] splitTags = wTags.split(",");

        	int indexTab = 0;
        	while(indexTab != splitTags.length)
        	{
        		String tag = splitTags[indexTab];
        		if(filter != null && filter.equalsIgnoreCase(tag))
        		{
        			onFilterChecked = true;       			
        		}

        		indexTab++;
        	}  
        	
        	if(onFilterChecked == true)
        	{
        		wArrayFiltred.add(w);
        		onFilterChecked = false;
        	}
        }    	
    	
    	if(wArrayFiltred.isEmpty())
    	{    	
    		showAlertDialogError();
    	}    	
    	return true;
    }
    
    public void showAlertDialogError(){
    	NoPicFoundDialogFragment dialog = new NoPicFoundDialogFragment();
    	dialog.show(getSupportFragmentManager(), "error");    	
    }
}
