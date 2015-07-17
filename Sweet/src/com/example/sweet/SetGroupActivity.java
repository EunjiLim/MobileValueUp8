package com.example.sweet;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SetGroupActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_group);
		
		getSupportActionBar().setTitle("모임 만들기");
		
		//spinner 만들기
		Spinner locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
		ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		locationSpinner.setAdapter(adapter1);
	}

}
