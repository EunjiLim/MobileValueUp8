package com.examle.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;
import com.example.sweet.R;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class CustomButton extends Button{

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setBackground(getResources().getDrawable(R.drawable.text_box));
	}


}
