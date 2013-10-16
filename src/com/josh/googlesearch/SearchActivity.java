package com.josh.googlesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	
	private EditText searchText;
	private GridView gvResults;
	private Button searchBtn;
	private ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	private ImageResultsArrayAdapter imageAdapter;
	private String size = "Any Size";
	private String color = "Any Color";
	private String type = "Any Type";
	private String url = "";
	private EndlessScrollListener scrollListener = new EndlessScrollListener() {
	    @Override
	    public void loadMore(int page, int totalItemsCount) {
            // whatever code is needed to append new items to your AdapterView
	        search(page);
	    }
    };
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageAdapter = new ImageResultsArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        
        gvResults.setOnScrollListener(scrollListener);
        gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int pos, long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(pos);
				i.putExtra("url", imageResult.getFullUrl());
				startActivity(i);
			}
        	
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    
    private void setupViews(){
    	searchText = (EditText)findViewById(R.id.searchText);
    	gvResults = (GridView)findViewById(R.id.gvResults);
    	searchBtn = (Button)findViewById(R.id.searchBtn);
    }
    
    public void onImageSearch(View v){
    	imageResults.clear();
    	scrollListener.reset();
    	search(0);
    }
    
    private void search(int page){
    	System.out.println("Going to start the get request...");
    	AsyncHttpClient client = new AsyncHttpClient();
    	//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=fuzzy%20monkey
    	String completeUrl = compileUrl(page);
    	System.out.println("URL: " + completeUrl);
    	client.get(completeUrl,
    			new JsonHttpResponseHandler(){
    		@Override
    		public void onSuccess(JSONObject response){
    			JSONArray imageJsonResults = null;
    			try{
    				imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
    				imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    			}catch(JSONException e){
    				System.out.println("Could not parse json package..");
    			}
    		}
    	});
    	
    }
    
    private String compileUrl(int page){
    	String query = searchText.getText().toString();
    	String u ="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
    	u += "&start=" +page*8;
    	u += "&q=" + Uri.encode(query);
    	if(size.equals("Huge")){
    		u += "&imgsz=huge";
    	} else if(size.equals("Icon")){
    		u += "&imgsz=icon";
    	} else if(size.equals("Medium")){
    		u += "&imgsz=medium";
    	}
    	
    	if(color.equals("Black and White")){
    		u += "&imgc=gray";
    	} else if(color.equals("Red")){
    		u += "&imgcolor=red";
    	} else if(color.equals("Orange")){
    		u += "&imgcolor=orange";
    	}else if(color.equals("Blue")){
    		u += "&imgcolor=blue";
    	}else if(color.equals("Yellow")){
    		u += "&imgcolor=yellow";
    	}else if(color.equals("Black")){
    		u += "&imgcolor=black";
    	}else if(color.equals("White")){
    		u += "&imgcolor=white";
    	}
    	
    	if(type.equals("Faces")){
    		u += "&imgtype=face";
    	} else if(type.equals("Photos")){
    		u += "&imgtype=photo";
    	} else if(type.equals("Clip Art")){
    		u += "&imgtype=clipart";
    	} else if(type.equals("Line Drawing")){
    		u += "&imgtype=lineart";
    	}
    	
    	if(url != null && !url.equals("")){
    		u += "&as_sitesearch="+url;
    	}
    	
    	return u;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { 
        case R.id.searchParams:
            startActivityForResult(new Intent(this, ChooseParamActivity.class), 1);
            break;
        default:
            break;
        }
        return true;
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
            	color = data.getStringExtra("color");
            	size = data.getStringExtra("size");
            	type = data.getStringExtra("type");
            	url = data.getStringExtra("url");
            	System.out.println(color + "   " + size + "   " + type + "   " + url);
            }
        }
    }
    
}
