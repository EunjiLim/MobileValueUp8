package com.example.sweet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SetGroupActivity extends ActionBarActivity {

	private Context context;
	
	// ��¥ ������ ���� ������
	private EditText dateEditText;
	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;
	
	//����, ����, ����
	private EditText title;
	private EditText description;
	private EditText number;
	
	//�������� ���ǳ�
	Spinner regionSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_group);

		context = this;
		
		getSupportActionBar().setTitle("���� �����");
		
		//����, ����, ����
		title = (EditText) findViewById(R.id.EditText_title);
		description = (EditText) findViewById(R.id.EditText_description);
		number = (EditText) findViewById(R.id.EditText_fixedNumber);

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
		Toast.makeText(context, "���� ����Ⱑ �Ϸ�Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
		
		//�� �޾ƿ���
		String titleInfo = title.getText().toString();
		String descriptionInfo = description.getText().toString();
		String region = regionSpinner.getSelectedItem().toString();
		String date = dateEditText.getText().toString();
		int people = Integer.parseInt(number.getText().toString());
		
//		Intent intent = new Intent(this, FragmentBaseActivity.class);
//		intent.putExtra("title", titleInfo);
//		intent.putExtra("description", descriptionInfo);
//		intent.putExtra("region", region);
//		intent.putExtra("date", date);
//		intent.putExtra("number", people);
//		Log.d("Sweet", "����Ʈ");
//		startActivity(intent);
		
		finish();
	}
}
