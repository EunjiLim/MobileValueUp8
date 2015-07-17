package com.example.sweet;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

import com.example.fragment.TabsPagerAdapter;

public class FragmentBaseActivity extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener{


    private ViewPager tabsviewPager;
     private ActionBar mActionBar;
     private TabsPagerAdapter mTabsAdapter;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fragment_base);
            
            tabsviewPager = (ViewPager) findViewById(R.id.viewpager);
            mTabsAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());
            tabsviewPager.setAdapter(mTabsAdapter);
            
            mActionBar = getSupportActionBar();
            mActionBar.setHomeButtonEnabled(false);
            mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            
            
            Tab HomeTab = getSupportActionBar().newTab().setTabListener(this);
            Tab SearchTab = getSupportActionBar().newTab().setTabListener(this);
            Tab ProfileTab = getSupportActionBar().newTab().setTabListener(this);
            Tab SettingTab = getSupportActionBar().newTab().setTabListener(this);
            HomeTab.setIcon(R.drawable.home);
            
            getSupportActionBar().addTab(HomeTab);
            getSupportActionBar().addTab(SearchTab);
            getSupportActionBar().addTab(ProfileTab);
            getSupportActionBar().addTab(SettingTab);
            
            tabsviewPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int position) {
					// TODO Auto-generated method stub
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