package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sweet.R;
import com.example.sweet.SetGroupActivity;
import com.example.sweet.SetLocationActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class FragmentHome extends Fragment{
	
	GoogleMap map;
	MarkerOptions marker;
	
	private Button setGroupBtn;
	int locationRequestCode = 1001;
	LatLng startingPoint;
	double startingLat;
	double startingLon;
	private Button mapLocationBtn;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        return v;
    }


	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        

        map =  ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        startingLat= 36.1138582;
        startingLon = 128.1728974;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(startingLat, startingLon),(float)6.5));
        
        setGroupBtn = (Button) view.findViewById(R.id.setGroupBtn);
        setGroupBtn.setOnClickListener(new OnClickListener() {
			
        
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SetGroupActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
        
        mapLocationBtn = (Button) view.findViewById(R.id.mapLocationBtn);
        mapLocationBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SetLocationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent, locationRequestCode);
			}
		});
        
        putMarker();
    }


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		map.setMyLocationEnabled(true);
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		map.setMyLocationEnabled(false);
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode==locationRequestCode){
			if(resultCode==1){
				double latitude = data.getDoubleExtra("lat", 0.0);
				double longitude = data.getDoubleExtra("lon", 0.0);
				startingLat = latitude;
				startingLon = longitude;
		        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(startingLat, startingLon),(float)11));
				
			} else if(resultCode==2){
				double latitude = data.getDoubleExtra("lat", 0.0);
				double longitude = data.getDoubleExtra("lon", 0.0);
				startingLat = latitude;
				startingLon = longitude;
		        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(startingLat, startingLon),(float)9));
			}
		}
	}
	
	public void putMarker(){
		LatLng chuncheon = new LatLng(37.875101, 127.735783);
        
        map.addMarker(new MarkerOptions().position(chuncheon).title("자전거 여행").
        		snippet("춘천 자전거 여행하실 분~").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
	}
	
}
