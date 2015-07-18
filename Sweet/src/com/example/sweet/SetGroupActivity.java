package com.example.sweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SetGroupActivity extends ActionBarActivity {
	
	Spinner locationSpinner;
	Spinner numberSpinner;
	EditText groupName;
	EditText groupInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_group);
		
		getSupportActionBar().setTitle("모임 만들기");
		
		settingSetUp();
		
		groupName = (EditText) findViewById(R.id.EditText_groupName);
		groupInfo = (EditText) findViewById(R.id.EditText_info);
	}
	
	private void settingSetUp(){

		//spinner 만들기
		locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
		ArrayAdapter locationAdapter = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
		locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		locationSpinner.setAdapter(locationAdapter);
		
		numberSpinner = (Spinner )findViewById(R.id.numberSpinner);
		ArrayAdapter numberAdapter = ArrayAdapter.createFromResource(this, R.array.group_number, android.R.layout.simple_spinner_item);
		locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numberSpinner.setAdapter(numberAdapter);
	}
	
	public void setGroupBtn(View v){
		String location = locationSpinner.getSelectedItem().toString();
		String groupNumber = numberSpinner.getSelectedItem().toString();
		String group_name = groupName.getText().toString();
		String group_info = groupInfo.getText().toString();
		
		Intent intent = new Intent(this, FragmentBaseActivity.class);
		GroupData data = new GroupData(group_name, group_info, location, groupNumber);
		intent.putExtra("data", data);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		Log.d("setGroup", location+", "+groupNumber+", "+group_name+", "+group_info);
		
		startActivity(intent);
	}

}
