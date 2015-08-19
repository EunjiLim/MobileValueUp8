package com.example.joinerlist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.listview.IconTextItem;
import com.example.sweet.R;

public class JoinerInfoView extends LinearLayout{
	/**
	 * Icon
	 */
	private ImageView mIcon;

	/**
	 * TextView ID
	 */
	private TextView mText_ID;

	/**
	 * TextView sex
	 */
	private TextView mText_Sex;

	/**
	 * TextView name
	 */
	private TextView mText_name;
	/**
	 * TextView birthday
	 */
	private TextView mText_birthday;
	/**
	 * TextView phone
	 */
	private TextView mText_phone;
	/**
	 * TextView list No.
	 */
	private TextView mText_listNo;
	
	public JoinerInfoView(Context mContext, JoinerInfoItem aItem) {
		super(mContext);

		// Layout Inflation
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.joinerinfolist, this, true);

		// Set Icon
		mIcon = (ImageView) findViewById(R.id.ImageView_joiner);
		mIcon.setImageDrawable(aItem.getIcon());

		// Set Text title
		mText_ID = (TextView) findViewById(R.id.TextView_joinerID);
		mText_ID.setText(aItem.getData(0));
		
		mText_Sex = (TextView) findViewById(R.id.TextView_joinerSex);
		mText_Sex.setText(aItem.getData(1));
		
		mText_name = (TextView) findViewById(R.id.TextView_joinerName);
		mText_name.setText(aItem.getData(2));
		
		mText_birthday = (TextView) findViewById(R.id.TextView_joinerBirthday);
		mText_birthday.setText(aItem.getData(3));
		
		mText_phone = (TextView) findViewById(R.id.TextView_joinerPhone);
		mText_phone.setText(aItem.getData(4));

	}
	
	/**
	 * set Text
	 *
	 * @param index
	 * @param data
	 */
	public void setText(int index, String data) {
		if (index == 0) {
			mText_ID.setText(data);
		} else if (index == 1) {
			mText_Sex.setText(data);
		} else if (index == 2) {
			mText_name.setText(data);
		} else if (index == 3) {
			mText_birthday.setText(data);
		} else if (index == 4) {
			mText_phone.setText(data);
		}else{
			throw new IllegalArgumentException();
		}
	}

	/**
	 * set Icon
	 *
	 * @param icon
	 */
	public void setIcon(Drawable icon) {
		mIcon.setImageDrawable(icon);
	}
}
