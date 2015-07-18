package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sweet.GroupData;
import com.example.sweet.R;

public class FragmentSearch extends Fragment{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
	
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
    }

	public void SetGroupInfo(Bundle bundle) {
		GroupData data = bundle.getParcelable("data");
		data.getLocation();
		data.getName();
		data.getInfo();
	}
}