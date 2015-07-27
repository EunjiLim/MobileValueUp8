package com.example.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.searchtabs.AccommodationTab;
import com.example.searchtabs.AccompanyTab;
import com.example.searchtabs.AllTab;
import com.example.searchtabs.LeisureTab;
import com.example.searchtabs.MealTab;
import com.example.sweet.R;
import com.example.sweet.SetGroupActivity;

public class FragmentSearch extends Fragment {

	FragmentTabHost tabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_search, container, false);

		tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.listView_allFragment);
        
        //�� ����
        tabHost.addTab(tabHost.newTabSpec("All").setIndicator("��ü"),
                AllTab.class, null);
        tabHost.addTab(tabHost.newTabSpec("Accomodation").setIndicator("����"),
                AccommodationTab.class, null);
        tabHost.addTab(tabHost.newTabSpec("Leisure").setIndicator("����"),
                LeisureTab.class, null);
        tabHost.addTab(tabHost.newTabSpec("Meal").setIndicator("�Ļ�"),
                MealTab.class, null);
        tabHost.addTab(tabHost.newTabSpec("Accompany").setIndicator("����"),
                AccompanyTab.class, null);
        
        return tabHost;
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
	}

	public void setData(String title, String description, String location,
			String date, int people) {
		Toast.makeText(getActivity().getApplicationContext(),
				"" + title + people, Toast.LENGTH_SHORT).show();
	}
}