package com.example.sweet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SetGroupActivity extends ActionBarActivity {

	private Context context;
	
	// 날짜 선택을 위한 변수들
	private EditText dateEditText;
	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;
	
	//제목, 내용, 정원
	private EditText title;
	private EditText description;
	private EditText number;
	private TextView letterNumber;
	
	//지역선택 스피너
	Spinner regionSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_group);

		context = this;
		
		getSupportActionBar().setTitle("모임 만들기");
		
		//제목, 내용, 정원
		title = (EditText) findViewById(R.id.EditText_title);
		description = (EditText) findViewById(R.id.EditText_description);
		number = (EditText) findViewById(R.id.EditText_fixedNumber);
		letterNumber = (TextView) findViewById(R.id.TextView_letterNumber);
		letterNumber.setText("0/300");
		
		description.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				int len = description.length();
				letterNumber.setText(len+"/300");
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				description.setFilters(new InputFilter[]{
					new InputFilter.LengthFilter(300)
				});
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
	}

	private void setDateTimeField() {
		dateEditText.setOnClickListener(new OnClickListener(){
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
		Toast.makeText(context, "모임 만들기가 완료되었습니다.", Toast.LENGTH_SHORT).show();
		
		//값 받아오기
		String titleInfo = title.getText().toString();
		String descriptionInfo = description.getText().toString();
		String region = regionSpinner.getSelectedItem().toString();
		String date = dateEditText.getText().toString();
		int people = Integer.parseInt(number.getText().toString());
		
		
		
		finish();
	}
}
