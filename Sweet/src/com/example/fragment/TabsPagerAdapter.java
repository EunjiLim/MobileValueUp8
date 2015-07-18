package com.example.fragment;

import com.example.sweet.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
 
	Context mContext;
 
    public TabsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
 
 
    @Override
 
    public Fragment getItem(int index) {
 
 
 
        switch (index) {
 
        case 0:
 
            // Top Rated fragment activity
 
            return new FragmentHome();
 
        case 1:
 
            // Games fragment activity
 
            return new FragmentSearch();
 
        case 2:
 
            // Movies fragment activity
 
            return new FragmentProfile();
            
        case 3:
        	
        	return new FragmentSetting();
 
        }
 
 
 
        return null;
 
    }
    

	@Override
 
    public int getCount() {
 
        // get item count - equal to number of tabs
 
        return 4;
 
    }
}
