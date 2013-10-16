package com.josh.googlesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
	private String fullUrl;
	private String thumbUrl;
	
	public ImageResult(JSONObject json){
		try{
			this.fullUrl = json.getString("url");
			System.out.println("URL: " + fullUrl);
			this.thumbUrl = json.getString("tbUrl");
		}catch (JSONException e){
			System.out.println("Could not parse JSON...");
			this.fullUrl = null;
			this.thumbUrl = null;
		}
	}
	
	
	public String getFullUrl() {
		return fullUrl;
	}
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	
	public String toString(){
		return this.thumbUrl;
	}


	public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for(int i = 0; i < array.length(); i++){
			try{
				results.add(new ImageResult(array.getJSONObject(i)));
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		return results;
	}
	
}
