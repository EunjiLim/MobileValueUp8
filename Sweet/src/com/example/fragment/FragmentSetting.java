package com.example.fragment;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.sweet.FragmentBaseActivity;
import com.example.sweet.LoginActivity;
import com.example.sweet.R;
import com.example.sweet.LoginActivity.logIn;

public class FragmentSetting  extends Fragment{
	
	Button logout, signout;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        
        logout = (Button) v.findViewById(R.id.Button_logout);
        signout = (Button) v.findViewById(R.id.Button_signout);
        
        logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
				startActivity(intent);
			}
		});
        
        signout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
				startActivity(intent);
			}
		});
        
        return v;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
    }

}