package com.josh.googlesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class ChooseParamActivity extends Activity {
	private Spinner sizeSpinner;
	private Spinner colorSpinner;
	private Spinner typeSpinner;
	private EditText websiteText;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_param);
		setupViews();
	}

	private void setupViews() {
		sizeSpinner = (Spinner) findViewById(R.id.sizeSpinner);
		colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
		typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
		websiteText = (EditText) findViewById(R.id.siteFilterText);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_param, menu);
		return true;
	}
	
	public void onSaveClick(View v){
		Intent i = new Intent(getApplicationContext(), SearchActivity.class);
		i.putExtra("size", sizeSpinner.getSelectedItem().toString());
		i.putExtra("color", colorSpinner.getSelectedItem().toString());
		i.putExtra("type", typeSpinner.getSelectedItem().toString());
		i.putExtra("url", websiteText.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}

}
