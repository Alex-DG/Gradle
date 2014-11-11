package com.project.picgallery.helper;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.project.picgallery.R;
 
/*
 * Utilities methods class for the app.
 */
public class Utils {
 
    private Context _context;
    
    public Utils(Context context) {
        this._context = context;
    }
    
    /*
     * Creates display image options for custom display task
     */
    public DisplayImageOptions DisplayImgOptions(){
    	DisplayImageOptions options = null;
    	
    	options = new DisplayImageOptions.Builder() 
					.showImageForEmptyUri(R.drawable.stub)
			        .cacheInMemory(true)
			        .cacheOnDisc(true)
			        .bitmapConfig(Bitmap.Config.RGB_565)
			        .build();
    	
    	return options;
    }
    
    /*
 	 * Create configuration for ImageLoader
     */
    public ImageLoaderConfiguration ImgLoaderConfiguration(){
    	ImageLoaderConfiguration config = null;
    	
    	  // Setting cache directory
    	File cacheDir = new File(Environment.getExternalStorageDirectory(), "CachePicsGallery");
    	
    	// Create configuration for ImageLoader
        config = new ImageLoaderConfiguration.Builder(_context)
                    .threadPoolSize(5)
                    .threadPriority(Thread.MIN_PRIORITY + 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .memoryCache(new UsingFreqLimitedMemoryCache(2000000)) // You can pass your own memory cache implementation
                    .discCache(new UnlimitedDiscCache(cacheDir)) // You can pass your own disc cache implementation
                    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                    .build();
    	
    	return config;
    }
    
    /*
     * Progress dialog box
     */
    public AlertDialog callProgressDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(_context);    	
    	View progressView = LayoutInflater.from(_context).inflate(R.layout.layout_custom_progress_dialog, null);
    	builder.setView(progressView);    	
    	
    	AlertDialog progress = builder.create();    	
    	/*progress.requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = progress.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 20;   //x position
        wmlp.y = 400;*/

    	return progress;
    }
    
    /*
     * Init. Grid layout
     */
    public void InitilizeGridLayout(Activity activity, GridView gridView/*, int columnWidth*/) {
        Resources r = _context.getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstant.GRID_PADDING, r.getDisplayMetrics());
 
        int columnWidth = (int) ((getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);
 
        gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,(int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }
    
    /*
     * Getting screen width to initialize the grid
     */
    @SuppressWarnings("deprecation")
	public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
 
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}
