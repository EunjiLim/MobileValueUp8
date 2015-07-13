package com.example.sweet;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fragment.FragmentHome;
import com.example.fragment.FragmentProfile;
import com.example.fragment.FragmentSearch;
import com.example.fragment.FragmentSetting;

public class FragmentBaseActivity extends ActionBarActivity {
	
	FragmentTabHost tabHost;
    private static final String TAB1 = "Home";
    private static final String TAB2 = "Search";
    private static final String TAB3 = "Profile";
    private static final String TAB4 = "Setting";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_base);
		
		tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(),R.id.realtabcontent);
        tabHost.addTab(tabHost.newTabSpec(TAB1).setIndicator("홈"), FragmentHome.class ,null);
        tabHost.addTab(tabHost.newTabSpec(TAB2).setIndicator("검색"), FragmentSearch.class ,null);
        tabHost.addTab(tabHost.newTabSpec(TAB3).setIndicator("프로필"), FragmentProfile.class ,null);
        tabHost.addTab(tabHost.newTabSpec(TAB4).setIndicator("설정"), FragmentSetting.class ,null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fragment_base, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
