package com.project.picgallery.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Wallpaper object class
 * Which implements 'Parcelable' to pass custom object between activities
 */
public class Wallpaper implements Parcelable {
	
	String id;
	String preview;
	String fullscreen;
	String size;	
    String tags;
    
    public Wallpaper(){}

    public String getId(){
    	return id;
    }
    
    public void setId(String id){
    	this.id = id;
    }
    
    public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(String fullscreen) {
        this.fullscreen = fullscreen;
    }
    
    public String getSize() {
		return size;
	}

    public void setSize(String size) {
		this.size = size;
	}

    @Override
    public String toString() {
        return "[ id = " + id + ", preview = " + preview + ", fullscreen = " + fullscreen + ", size = " + size + ", tags = " + tags + " ]";
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel pc, int flags) {
		pc.writeString(id);
		pc.writeString(preview);
		pc.writeString(fullscreen);
		pc.writeString(size);
		pc.writeString(tags);	
	}
	
	public static final Parcelable.Creator<Wallpaper> CREATOR
	    = new Parcelable.Creator<Wallpaper>() {
		
		public Wallpaper createFromParcel(Parcel in) {
		    return new Wallpaper(in);
	}

		public Wallpaper[] newArray(int size) {
			    return new Wallpaper[size];
			}
		};

	private Wallpaper(Parcel in) {
		id = in.readString();
		preview = in.readString();
		fullscreen = in.readString();
		size = in.readString();
		tags = in.readString();
	}
}
