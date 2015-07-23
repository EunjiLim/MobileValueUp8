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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SetGroupActivity extends ActionBarActivity {

	private Context context;

	// �����ͺ��̽��� ���� String
	String resultText = "";

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
				checkChecked(v);
			}
		});
		radio_meal.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				checkChecked(v);
			}
		});
		radio_leisure.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				checkChecked(v);
			}
		});
		radio_accompany.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				checkChecked(v);
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
	
	public void checkChecked(View v){
		RadioButton rb = (RadioButton) v;
		if(rb.isChecked()) {
			resultText = "category=" + rb.getText().toString() + "&";

		}
	}

	public void onClickedSetGroup(View v) {

		// �� �޾ƿ���
		resultText = "title=" + title.getText().toString() + "&"
				+ "contents=" + description.getText().toString() + "&"
				+ "location=" + regionSpinner.getSelectedItem().toString() + "&"
				+ "date=" + dateEditText.getText().toString() + "&"
				+ "people=" + Integer.parseInt(number.getText().toString());

		Toast.makeText(context, "���� ����Ⱑ �Ϸ�Ǿ����ϴ�. " + resultText, Toast.LENGTH_SHORT).show();
		finish();
	}
}
