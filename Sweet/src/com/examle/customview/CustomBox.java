package com.examle.customview;

import com.example.sweet.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class CustomBox extends RelativeLayout{

	public CustomBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setBackground(getResources().getDrawable(R.drawable.white_box));
	}

}
