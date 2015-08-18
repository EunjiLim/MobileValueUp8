package com.example.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
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
	JSONArray boardArray;
	
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
        
        JSONParse json = new JSONParse();
        json.execute();
        
        //맵 초기화, 처음 화면 위치 설정
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
        
        //지역 선택(손가락 줌 사용 안하고 그 지역으로 빠르게 이동)
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
        
        //지도 위에 마커 올려놓기(서버에서 경도, 위도, 제목, 내용)<- 여기!
        //putMarker(double lat, double lon, String title, String text);
    }

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected JSONObject doInBackground(String... args) {

			JSONParser jParser = new JSONParser();

			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl("http://52.69.67.4/longlang.php");
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			try {
				Log.i("TAG", "onPostExecute시작");

				// Getting JSON Array from URL
				boardArray = json.getJSONArray("board");

				for (int i = 0; i < boardArray.length(); i++) {
					JSONObject c = boardArray.getJSONObject(i);

					// Storing JSON item in a Variable
					String lati = c.getString("lang");
					String longi = c.getString("lang2");
					String title = c.getString("title");
					String contents = c.getString("contents");
					double lati2 = Double.valueOf(lati).doubleValue();
					double longi2 = Double.valueOf(longi).doubleValue();
					putMarker(lati2, longi2, title, contents);
					Log.d("Home", "lati2, longi2, title, contents");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
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
		
		//도시 위치 받아서 이동
		if(requestCode==locationRequestCode){
			if(resultCode==1){
				double latitude = data.getDoubleExtra("lat", 0.0);
				double longitude = data.getDoubleExtra("lon", 0.0);
				startingLat = latitude;
				startingLon = longitude;
		        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(startingLat, startingLon),(float)11));
				
		//도 위치 받아서 이동        
			} else if(resultCode==2){
				double latitude = data.getDoubleExtra("lat", 0.0);
				double longitude = data.getDoubleExtra("lon", 0.0);
				startingLat = latitude;
				startingLon = longitude;
		        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(startingLat, startingLon),(float)9));
			}
		}
	}
	
	public void putMarker(double lat, double lon, String title, String text){
		LatLng chuncheon = new LatLng(37.875101, 127.735783);
        
		
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(title).
        		snippet(text).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
        Log.d("Home", "마커");
	}
	
}
