package com.example.listview;

import com.example.fragment.FragmentSearch;
import com.example.sweet.R;
import com.example.sweet.R.id;
import com.example.sweet.R.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IconTextView extends LinearLayout{
	/**
	 * Icon
	 */
	private ImageView mIcon;

	/**
	 * TextView title
	 */
	private TextView mText_title;

	/**
	 * TextView number of member
	 */
	private TextView mText_numberOfMember;

	/**
	 * TextView region
	 */
	private TextView mText_region;
	/**
	 * TextView date
	 */
	private TextView mText_date;
	/**
	 * TextView fixed number
	 */
	private TextView mText_fixedNumber;
	
	public IconTextView(Context mContext, IconTextItem aItem) {
		super(mContext);

		// Layout Inflation
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.listitem, this, true);

		// Set Icon
		mIcon = (ImageView) findViewById(R.id.ImageView_profile);
		mIcon.setImageDrawable(aItem.getIcon());

		// Set Text title
		mText_title = (TextView) findViewById(R.id.TextView_listTitle);
		mText_title.setText(aItem.getData(0));

		// Set Text number of member
		mText_numberOfMember = (TextView) findViewById(R.id.TextView_listNumberOfMember);
		mText_numberOfMember.setText(aItem.getData(1));

		// Set Text region
		mText_region = (TextView) findViewById(R.id.TextView_listRegion);
		mText_region.setText(aItem.getData(2));
		
		// Set Text date
		mText_date = (TextView) findViewById(R.id.TextView_listDate);
		mText_date.setText(aItem.getData(3));
		
		// Set Text fixed number
		mText_fixedNumber = (TextView) findViewById(R.id.TextView_listFixedNumber);
		mText_fixedNumber.setText(aItem.getData(4));
	}
	
	/**
	 * set Text
	 *
	 * @param index
	 * @param data
	 */
	public void setText(int index, String data) {
		if (index == 0) {
			mText_title.setText(data);
		} else if (index == 1) {
			mText_numberOfMember.setText(data);
		} else if (index == 2) {
			mText_region.setText(data);
		} else if (index == 3) {
			mText_date.setText(data);
		} else if (index == 4) {
			mText_fixedNumber.setText(data);
		} else {
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
