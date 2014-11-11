package com.project.picgallery;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.project.picgallery.adapter.FullScreenImageAdapter;
import com.project.picgallery.model.Wallpaper;

/*
 * Image FullScreen Activity class
 */
public class FullScreenViewActivity extends Activity{

	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
	
	ArrayList<Wallpaper> wallpapersList = new ArrayList<Wallpaper>();

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);

		viewPager = (ViewPager) findViewById(R.id.pager);

		Intent i = getIntent();		
		int position = i.getIntExtra("position", 0);
		wallpapersList = (ArrayList<Wallpaper>) i.getExtras().get("wallpapersArray"); 
		
		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, wallpapersList);

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);
	}
}
