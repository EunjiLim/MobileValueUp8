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

public class SetGroupActivity extends ActionBarActivity {
	private table making;
	private Context context;
	
	String line;
	String temp;
	
	// �����ͺ��̽��� ���� String
	String resultText = "";
	String resultText1 = "";
	String resultText2 = "";
	String resultText3 = "";

	// ��¥ ������ ���� ������
	private EditText dateEditText;
	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;

	// ����, ����, ����
	private EditText title;
	private EditText description;
	private EditText number;

	// �Է��� �� �ִ� �ִ� ���� ��
	private TextView letterNumber;

	// �������� ���ǳ�
	Spinner regionSpinner;

	// ī�װ��� ������ư��
	RadioButton radio_accommodation, radio_meal, radio_leisure,
			radio_accompany;

	//���� ��Ŀ ��ġ
	double markerLat;
	double markerLon;
	
	//���� ��ư
	String radioChecked =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_group);

		context = this;

		getSupportActionBar().setTitle("���� �����");

		// ����, ����, ����
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

		// ���� ������ ���� spinner �����
		regionSpinner = (Spinner) findViewById(R.id.Spinner_region);
		ArrayAdapter regionAdapter = ArrayAdapter.createFromResource(this,
				R.array.region, android.R.layout.simple_spinner_item);
		regionAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		regionSpinner.setAdapter(regionAdapter);

		// ��¥ ����
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		dateEditText = (EditText) findViewById(R.id.EditText_selectedDate);
		dateEditText.setInputType(InputType.TYPE_NULL);
		dateEditText.requestFocus();

		setDateTimeField();

		// ���� ��ư�鿡 Ŭ�� ������ ���
		radio_accommodation = (RadioButton) findViewById(R.id.RadioButton_accommodation);
		radio_meal = (RadioButton) findViewById(R.id.RadioButton_meal);
		radio_leisure = (RadioButton) findViewById(R.id.RadioButton_leisure);
		radio_accompany = (RadioButton) findViewById(R.id.RadioButton_accompany);
		radio_accommodation.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				radioChecked = "����";
				checkChecked();
			}
		});
		radio_meal.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				radioChecked = "�Ļ�";
				checkChecked();
			}
		});
		radio_leisure.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				radioChecked = "����";
				checkChecked();
			}
		});
		radio_accompany.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				radioChecked = "����";
				checkChecked();
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
	
	public void checkChecked(){
		if(radioChecked!=null) {
			resultText1 = "category=" + radioChecked + "&";
			Log.d("Main", radioChecked);
		}
	}

	public void onClickedSetGroup(View v) {
		
		String lat = Double.toString(markerLat);
		String lon = Double.toString(markerLon);

		// �� �޾ƿ���
		resultText2 = "title=" + title.getText().toString() + "&"
				+ "contents=" + description.getText().toString() + "&"
				+ "location=" + regionSpinner.getSelectedItem().toString() + "&"
				+ "date=" + dateEditText.getText().toString() + "&"
				+ "people=" + Integer.parseInt(number.getText().toString()) +"&";

		making = new table();
		making.execute();
		
		Toast.makeText(context, "���� ����Ⱑ �Ϸ�Ǿ����ϴ�. " + resultText, Toast.LENGTH_SHORT).show();
		finish();
	}
	
	
	
	public class table extends AsyncTask<String, Void, String> {

		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... params) {

			try {
				SharedPreferences pref = getSharedPreferences("idStorage",MODE_PRIVATE);
				
				URL myUrl = new URL("http://52.69.67.4/table.php");
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				Log.e("tag", "2");
				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // �������� �б� ��� ����
					http.setDoOutput(true); // ������ ���� ��� ����
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					// ����Ǿ��� �ڵ尡 ���ϵǸ�.

					//id = ID.getText().toString();
					//////////////////////////////////////////////////////////
					resultText3 ="id="+pref.getString("id","");
					///////////////////////////////////////////////////////////
					resultText = resultText1 + resultText2 +resultText3;
					Log.e("tag111", resultText);
					

					OutputStreamWriter outStream = new OutputStreamWriter(
							http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(resultText);
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
	public void specificBtn(View v){
		//�� ���� ����
		double latitude= 0;
		double longitude = 0;
		int zoomLevel = 0;
		String location = regionSpinner.getSelectedItem().toString();
		Log.d("Main", location+"�� ������");
		if(location=="����"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="��⵵"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 13;
		}else if(location=="������"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="���"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="����"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="��û��"){
			latitude= 37.5666102;
			longitude = 126.9783881;
			zoomLevel = 11;
		}else if(location=="���ֵ�"){
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
