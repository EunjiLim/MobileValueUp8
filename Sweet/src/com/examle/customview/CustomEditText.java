package com.examle.customview;

import com.example.sweet.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class CustomEditText extends EditText{

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setBackground(getResources().getDrawable(R.drawable.text_largebox));
	}

}
