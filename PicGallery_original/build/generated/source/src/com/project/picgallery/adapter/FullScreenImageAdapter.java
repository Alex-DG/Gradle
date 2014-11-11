package com.project.picgallery.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.project.picgallery.R;
import com.project.picgallery.helper.Utils;
import com.project.picgallery.model.Wallpaper;

/*
 * Image FullScreen Adapter class
 */
public class FullScreenImageAdapter extends PagerAdapter {
 
    private Activity _activity;
    private ArrayList<Wallpaper> _imagePaths;
    private LayoutInflater myInflater;
    
    private ImageLoader imageLoader;
    private Utils utils;
    private ImageLoaderConfiguration config;
    private DisplayImageOptions options;

    public FullScreenImageAdapter(Activity activity, ArrayList<Wallpaper> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
        myInflater = LayoutInflater.from(activity);
       	imageLoader = ImageLoader.getInstance();
       	utils = new Utils(activity);
        
        //Create configuration for ImageLoader
        config = utils.ImgLoaderConfiguration();

        //Creates display image options for custom display task
        options = utils.DisplayImgOptions();

       //Initialize ImageLoader with created configuration. Do it once.
       imageLoader.init(config);
    }
 
    @Override
    public int getCount() {
        return this._imagePaths.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
     
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	ViewHolder holder = new ViewHolder();
        Button btnClose; 
        
        View viewLayout = myInflater.inflate(R.layout.layout_fullscreen_image, container, false);
  
        holder.fullscreen = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        final ProgressBar spinner = (ProgressBar) viewLayout.findViewById(R.id.loading);
        
        holder.fullscreen.setScaleType(ImageView.ScaleType.FIT_XY);
        //imgDisplay.setAdjustViewBounds(true);
        
        Wallpaper wallpaper = (Wallpaper) _imagePaths.get(position);
        
        if(holder.fullscreen != null){
        	imageLoader.displayImage(wallpaper.getFullscreen(), holder.fullscreen, options, new SimpleImageLoadingListener(){
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
        }
        
         
        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });
  
        ((ViewPager) container).addView(viewLayout);
  
        return viewLayout;
    }
    
    static class ViewHolder {
        ImageView fullscreen;
    }
     
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
  
    }
}
