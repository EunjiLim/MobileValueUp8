package com.example.commentlistview;

import com.example.sweet.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentView extends LinearLayout{
	
	private ImageView mIcon;
	private TextView name;
	private TextView comment;
	private TextView date;


	public CommentView(Context context, CommentItem aItem) {
		super(context);
		// TODO Auto-generated constructor stub
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.commentitem, this, true);
		
		mIcon = (ImageView) findViewById(R.id.ImageView_commentIcon);
		mIcon.setImageDrawable(aItem.getIcon());
		
		name = (TextView) findViewById(R.id.TextView_commentID);
		name.setText(aItem.getData(0));
		
		date = (TextView) findViewById(R.id.TextView_commentDate);
		date.setText(aItem.getData(2));
		// Set Text number of member
		comment = (TextView) findViewById(R.id.TextView_commentContents);
		comment.setText(aItem.getData(1));
	}
	
	public void setText(int index, String data) {
		if (index == 0) {
			name.setText(data);
		} else if (index == 1) {
			comment.setText(data);
		} else if (index == 2) {
			date.setText(data);
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	public void setIcon(Drawable icon) {
		mIcon.setImageDrawable(icon);
	}
}
