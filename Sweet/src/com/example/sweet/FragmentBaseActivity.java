package com.example.sweet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fragment.FragmentSearch;
import com.example.fragment.TabsPagerAdapter;

public class FragmentBaseActivity extends ActionBarActivity implements
		android.support.v7.app.ActionBar.TabListener {

	private ViewPager tabsviewPager;
	private ActionBar mActionBar;
	private TabsPagerAdapter mTabsAdapter;

	Tab SearchTab;
	Tab HomeTab;
	Tab ProfileTab;
	Tab SettingTab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_base);

		//SearchFragment 액션바의 EditText를 위한 custom view
		LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.search_actionbar, null);
		
		tabsviewPager = (ViewPager) findViewById(R.id.viewpager);
		mTabsAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());
		tabsviewPager.setAdapter(mTabsAdapter);
		tabsviewPager.setOffscreenPageLimit(4);

		mActionBar = getSupportActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);

		HomeTab = getSupportActionBar().newTab().setTabListener(this);
		SearchTab = getSupportActionBar().newTab().setTabListener(this);
		ProfileTab = getSupportActionBar().newTab().setTabListener(this);
		SettingTab = getSupportActionBar().newTab().setTabListener(this);
		
		//탭에 아이콘 넣기

		HomeTab.setIcon(R.drawable.home_checkbutton);
		SearchTab.setIcon(R.drawable.search_checkbutton);
		ProfileTab.setIcon(R.drawable.mypage_checkbutton);
		SettingTab.setIcon(R.drawable.set_checkbutton);


		getSupportActionBar().addTab(HomeTab);
		getSupportActionBar().addTab(SearchTab);
		getSupportActionBar().addTab(ProfileTab);
		getSupportActionBar().addTab(SettingTab);
		
		getSupportActionBar().setTitle("홈");
		
		tabsviewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				
				if (position == 0)
					getSupportActionBar().setTitle("홈");
				else if(position == 1){
					getSupportActionBar().setTitle("검색");
				}
				else if(position == 2)
					getSupportActionBar().setTitle("내 정보");
				else
					getSupportActionBar().setTitle("설정");
				getSupportActionBar().setSelectedNavigationItem(position);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab selectedtab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		tabsviewPager.setCurrentItem(selectedtab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

}