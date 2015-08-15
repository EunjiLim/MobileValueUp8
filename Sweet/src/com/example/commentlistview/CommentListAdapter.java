package com.example.commentlistview;

import java.util.ArrayList;
import java.util.List;

import com.example.fragment.FragmentSearch;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CommentListAdapter extends BaseAdapter {

		private Context mContext;

		private List<CommentItem> mItems = new ArrayList<CommentItem>();

		public CommentListAdapter(Context context) {
			mContext = context;
		}

		public void addItem(CommentItem it) {
			mItems.add(it);
		}

		public void setListItems(List<CommentItem> lit) {
			mItems = lit;
		}

		public int getCount() {
			return mItems.size();
		}

		public Object getItem(int position) {
			return mItems.get(position);
		}

		public boolean areAllItemsSelectable() {
			return false;
		}

		public boolean isSelectable(int position) {
			try {
				return mItems.get(position).isSelectable();
			} catch (IndexOutOfBoundsException ex) {
				return false;
			}
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			CommentView itemView;
			if (convertView == null) {
				itemView = new CommentView(mContext, mItems.get(position));
			} else {
				itemView = (CommentView) convertView;
				
				itemView.setIcon(mItems.get(position).getIcon());
				itemView.setText(0, mItems.get(position).getData(0));
				itemView.setText(1, mItems.get(position).getData(1));
				itemView.setText(2, mItems.get(position).getData(2));
			}

			return itemView;
		}

}
