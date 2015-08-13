package com.example.sweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.sweet.LoginActivity.logIn;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*****************************
 * 모임 만들기
 * 
 * 서버에 전송하는 값 : id, 카테고리, 제목, 상세내용, 지역, 날짜, 인원, 경도, 위도
 * @author Eunji
 *
 */
public class SetGroupActivity extends ActionBarActivity {
	private table making;
	private Context context;
	
	String line;
	String temp;

	// 날짜 선택을 위한 변수들
	private EditText dateEditText;
	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;

	// 제목, 내용, 정원
	private EditText title;
	private EditText description;
	private EditText number;

	// 입력할 수 있는 최대 글자 수
	private TextView letterNumber;

	// 지역선택 스피너
	Spinner regionSpinner;

	// 카테고리의 라디오버튼들
	RadioButton radio_accommodation, radio_meal, radio_leisure,
			radio_accompany;

	//지도 마커 위치
	double markerLat;
	double markerLon;
	
	//서버에 넘길 String data 변수들
	String id, mTitle, mContents, mLocation, mDate, mPeople, mLang, mLong;
	String radioChecked=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_group);

		context = this;

		//액션바 타이틀 설정
		getSupportActionBar().setTitle("모임 만들기");

		// 제목, 내용, 정원
		title = (EditText) findViewById(R.id.EditText_title);
		description = (EditText) findViewById(R.id.EditText_description);
		number = (EditText) findViewById(R.id.EditText_fixedNumber);
		letterNumber = (TextView) findViewById(R.id.TextView_letterNumber);
		letterNumber.setText("0/300");

		description.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				int len = description.length();
				letterNumber.setText(len + "/300");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				description
						.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
								300) });
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		// 지역 선택을 위한 spinner 만들기
		regionSpinner = (Spinner) findViewById(R.id.Spinner_region);
		ArrayAdapter regionAdapter = ArrayAdapter.createFromResource(this,
				R.array.region, android.R.layout.simple_spinner_item);
		regionAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		regionSpinner.setAdapter(regionAdapter);

		// 날짜 선택
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		dateEditText = (EditText) findViewById(R.id.EditText_selectedDate);
		dateEditText.setInputType(InputType.TYPE_NULL);
		dateEditText.requestFocus();

		setDateTimeField();

		// 라디오 버튼들에 클릭 리스너 등록
		radio_accommodation = (RadioButton) findViewById(R.id.RadioButton_accommodation);
		radio_meal = (RadioButton) findViewById(R.id.RadioButton_meal);
		radio_leisure = (RadioButton) findViewById(R.id.RadioButton_leisure);
		radio_accompany = (RadioButton) findViewById(R.id.RadioButton_accompany);
		
		radio_accommodation.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				radioChecked = "숙박";
			}
		});
		radio_meal.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				radioChecked = "식사";
			}
		});
		radio_leisure.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				radioChecked = "레저";
			}
		});
		radio_accompany.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				radioChecked = "동행";
			}
		});
	}

	private void setDateTimeField() {
		dateEditText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view == dateEditText) {
					datePickerDialog.show();
				}
			}
		});

		Calendar newCalendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				dateEditText.setText(dateFormatter.format(newDate.getTime()));
			}

		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH));

}

	public void onClickedSetGroup(View v) {
		
		String lat = Double.toString(markerLat);
		String lon = Double.toString(markerLon);

		//id 저장되어 있는 sharedPreferences 가져오기
		SharedPreferences pref = getSharedPreferences("idStorage",MODE_PRIVATE);

		// 서버에 넘길 변수 값 저장
		id = pref.getString("id", "");
		mTitle = title.getText().toString();
		mContents = description.getText().toString();
		mLocation = regionSpinner.getSelectedItem().toString();
		mDate = dateEditText.getText().toString();
		mPeople = number.getText().toString();
		mLang = lat;
		mLong = lon;

		making = new table();
		making.execute();
		
		Toast.makeText(context, "모임 만들기가 완료되었습니다. ", Toast.LENGTH_LONG).show();
		finish();
	}
	
	
	
	public class table extends AsyncTask<String, Void, String> {

		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... params) {

			try {
				URL myUrl = new URL("http://52.69.67.4/table.php");
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				Log.e("tag", "2");
				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");
					
					StringBuffer buffer = new StringBuffer();
					buffer.append("category").append("=").append(radioChecked).append("&");
					buffer.append("title").append("=").append(mTitle).append("&");
					buffer.append("contents").append("=").append(mContents).append("&");
					buffer.append("location").append("=").append(mLocation).append("&");
					buffer.append("date").append("=").append(mDate).append("&");
					buffer.append("people").append("=").append(mPeople).append("&");
					buffer.append("id").append("=").append(id).append("&");
					buffer.append("lang").append("=").append(mLang).append("&");
					buffer.append("lang2").append("=").append(mLong);

					Log.i("SetGroupActivity", buffer.toString());
					OutputStreamWriter outStream = new OutputStreamWriter(
							http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					outStream.close();

					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));

					br.close();
				}
				http.disconnect();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);

		}
	}
	
	/*************
	 * 상세지역 설정
	 * @param v
	 */
	public void specificBtn(View v){
		double latitude= 0;
		double longitude = 0;
		int zoomLevel = 0;
		String location = regionSpinner.getSelectedItem().toString();
		Log.d("Main", location+"이 설정됨");
		if(location=="서울"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="경기도"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 13;
		}else if(location=="강원도"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="경상도"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="전라도"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="충청도"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="제주도"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else{
			latitude= 36.1138582;
	        longitude = 128.1728974;
	        zoomLevel = 6;
		}
		
		Intent intent = new Intent(this, SetSpecificActivity.class);
		intent.putExtra("latitude", latitude);
		intent.putExtra("longitude", longitude);
		intent.putExtra("zoomLevel", zoomLevel);
		startActivityForResult(intent, 1002);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode==1002){
			if(resultCode==1){
				double latitude = data.getDoubleExtra("lat", 0.0);
				double longitude = data.getDoubleExtra("lon", 0.0);
				markerLat = latitude;
				markerLon = longitude;
				
			}
		}
	}
	
	
}
