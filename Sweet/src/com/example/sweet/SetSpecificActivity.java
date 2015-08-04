package com.example.sweet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commentlistview.CommentItem;
import com.example.commentlistview.CommentView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SetSpecificActivity extends ActionBarActivity implements OnMapLongClickListener{
	
	ActionBar actionBar;
	GoogleMap map;
	MarkerOptions marker1;
	Geocoder coder;
	EditText addressEdit;
	ListView addressList;
	RelativeLayout layout;
	ArrayList<String> addressInfo;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_specific);
		
		addressList = (ListView) findViewById(R.id.ListView_address);
		addressEdit = (EditText) findViewById(R.id.editText1);
		layout = (RelativeLayout) findViewById(R.id.RelativeLayout_ListLayout);
		
		addressInfo = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addressInfo);
		
		Intent intent = getIntent();
		double latitude = intent.getDoubleExtra("latitude", 0);
		double longitude = intent.getDoubleExtra("longitude", 0);
		int zoomLevel = intent.getIntExtra("zoomLevel", 0);
		
		
		map =  ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setOnMapLongClickListener(this);
        marker1 = new MarkerOptions();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),zoomLevel));
        
        coder = new Geocoder(this, Locale.KOREAN);
		
		actionBar = getSupportActionBar();
		actionBar.hide();

	}

	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub
		Point screenPt = map.getProjection().toScreenLocation(point);
		LatLng latLng = map.getProjection().fromScreenLocation(screenPt);

        marker1.position(point);
        marker1.title("선택된 지역");
        marker1.draggable(true);
		
		if(marker1==null){
			map.addMarker(marker1);
		}else{
			map.clear();
			map.addMarker(marker1);
		}
		
	}
	
	public void searchBtn (View v){
		layout.setVisibility(View.VISIBLE);
		String address = addressEdit.getText().toString();
		String test = null;
		double searchLatitude = 0;
		double searchLongitude = 0;
        if(addressInfo!=null){
        	addressInfo.clear();
        }
        try{
            List<Address> addressList =  coder.getFromLocationName(address, 3);
            if(addressList!=null){
                for(int i=0; i<addressList.size(); i++){
                    Address curAddress = addressList.get(i);
                    StringBuffer buffer =new StringBuffer();
                    for(int k =0; k<=curAddress.getMaxAddressLineIndex(); k++){
                        buffer.append(curAddress.getAddressLine(k));
                        test = buffer.toString();
                        addressInfo.add(test);
                    }
                    searchLatitude = curAddress.getLatitude();
                    searchLongitude = curAddress.getLongitude();
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(searchLatitude, searchLongitude),13));
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
		addressList.setAdapter(adapter);
		addressList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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
	
	public void setBtn(View v){
		double lat = 0;
		double lon = 0;
		if(marker1.getPosition()!=null){
			LatLng point = marker1.getPosition();
			lat = point.latitude;
			lon = point.longitude;
			Intent resultIntent = new Intent();
			resultIntent.putExtra("lat", lat);
			resultIntent.putExtra("lon", lon);
			setResult(1, resultIntent);
			finish();
		} else{
			Toast.makeText(this, "위치를 지정해주세요", Toast.LENGTH_SHORT).show();
		}
	}

}
