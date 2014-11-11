package com.project.picgallery.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.project.picgallery.FullScreenViewActivity;
import com.project.picgallery.R;
import com.project.picgallery.helper.Utils;
import com.project.picgallery.model.Wallpaper;
//import com.project.picgallery.asynctask.ImageLoader;
 
/*
 * Image Preview Adapter class
 */
public class GridViewImageAdapter extends BaseAdapter {
 
    private Activity _activity;
    private ArrayList<Wallpaper> _wallPArray;
    
    private LayoutInflater myInflater;
    
    private ImageLoader imageLoader;
    private Utils utils;
    private ImageLoaderConfiguration config;
    private DisplayImageOptions options;
 
    public GridViewImageAdapter(Activity activity, ArrayList<Wallpaper> filePaths) {
        this._activity = activity;
        this._wallPArray = filePaths;
        myInflater = LayoutInflater.from(activity);
        imageLoader = ImageLoader.getInstance();     	
        utils = new Utils(activity);
        
        //Create configuration for ImageLoader
        config = utils.ImgLoaderConfiguration();

        // Creates display image options for custom display task
        options = utils.DisplayImgOptions();

        // Initialize ImageLoader with created configuration. Do it once.
        imageLoader.init(config);
    }
 
    @Override
    public int getCount() {
        return this._wallPArray.size();
    }
 
    @Override
    public Object getItem(int position) {
        return this._wallPArray.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder;

        if (convertView == null) {
            convertView = myInflater.inflate(R.layout.layout_gridview_image, null);
            holder = new ViewHolder();
            holder.preview = (ImageView) convertView.findViewById(R.id.grid_item_image);

            convertView.setTag(holder);
            
        } else {
            holder = (ViewHolder) convertView.getTag();
        }        
        
        Wallpaper wallpaper = (Wallpaper) _wallPArray.get(position);
        
        final ProgressBar spinner = (ProgressBar) convertView.findViewById(R.id.loading);
        
        // Load and display image
        imageLoader.displayImage(wallpaper.getPreview(), holder.preview, options, new SimpleImageLoadingListener(){
    		@Override
			public void onLoadingStarted(String imageUri, View view) {
    			
				spinner.setVisibility(View.VISIBLE);
			}
    		
    		@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case DECODING_ERROR:
						message = "Image can't be decoded";
						break;
					case NETWORK_DENIED:
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:
						message = "Out Of Memory error";
						break;
					case UNKNOWN:
						message = "Unknown error";
						break;
				}
				Toast.makeText(_activity, message, Toast.LENGTH_SHORT).show();

				spinner.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				spinner.setVisibility(View.GONE);
			}    		
    	});
        
        holder.preview.setOnClickListener(new OnImageClickListener(position, _wallPArray));

        return convertView;
    }
    
    static class ViewHolder {
        ImageView preview;
    }
 
    /*
     * Event listener to display fullscreen view
     */
    class OnImageClickListener implements OnClickListener {
 
    	int _position;
        ArrayList<Wallpaper> _wallpapersArray;
 
        // constructor
        public OnImageClickListener(int position, ArrayList<Wallpaper> wallpapersArray) {
            this._position = position;
            this._wallpapersArray = wallpapersArray;
        }
 
        /* On clicking on thumb_image
         * start fullscreen activity 
         */
        @Override
        public void onClick(View v) {
            Intent i = new Intent(_activity, FullScreenViewActivity.class);
            i.putExtra("position", _position);
            i.putExtra("wallpapersArray", _wallpapersArray);
            _activity.startActivity(i);
        }
 
    }
}
