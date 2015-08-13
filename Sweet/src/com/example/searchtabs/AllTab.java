package com.example.searchtabs;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.sweet.GroupSelectedActivity;
import com.example.sweet.R;

public class AllTab extends Fragment {

	ListView listView1;
	IconTextListAdapter adapter;

	// 상세검색 버튼
	Button descriptionButton;

	// 게시판 리스트 불러올 url
	private static String url = "http://52.69.67.4/make.php";

	JSONArray boardArray = null;

	// JSON Node Names
	private static final String TAG_OS = "board";

	private static final String TAG_NO = "NO";
	private static final String TAG_ID = "ID";
	private static final String TAG_CATEGORY = "category";
	private static final String TAG_TITLE = "title";
	private static final String TAG_LOCATION = "location";
	private static final String TAG_DATE = "date";
	private static final String TAG_PEOPLE = "people";
	private static final String TAG_CURRENT = "current";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tab_all, container, false);

		listView1 = (ListView) v.findViewById(R.id.listView_allFragment);
		adapter = new IconTextListAdapter(getActivity());

		// 리스트뷰에 어댑더 설정
		listView1.setAdapter(adapter);

		new JSONParse().execute();

		return v;
	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected JSONObject doInBackground(String... args) {

			Log.i("TAG", "#");
			JSONParser jParser = new JSONParser();

			// Getting JSON from URL
			Log.i("TAG", "$");
			JSONObject json = jParser.getJSONFromUrl(url);
			Log.i("TAG", "%");
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			try {
				Log.i("TAG", "onPostExecute시작");

				// Getting JSON Array from URL
				boardArray = json.getJSONArray(TAG_OS);

				for (int i = 0; i < boardArray.length(); i++) {
					JSONObject c = boardArray.getJSONObject(i);

					// Storing JSON item in a Variable
					String no = c.getString(TAG_NO);
					String id = c.getString(TAG_ID);
					String category = c.getString(TAG_CATEGORY);
					String title = c.getString(TAG_TITLE);
					String location = c.getString(TAG_LOCATION);
					String date = c.getString(TAG_DATE);
					String people = c.getString(TAG_PEOPLE);
					String current = c.getString(TAG_CURRENT);

					Resources res = getResources();

					Log.i("TAG", "*");
					// 카테고리별 게시판 아이콘 적용
					if (category.equals("숙박")) {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.house_coloricon),
								title, current, location, date, people, no));
						listView1.setAdapter(adapter);
					} else if (category.equals("레저")) {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.leisure_coloricon),
								title, current, location, date, people, no));
						listView1.setAdapter(adapter);
					} else if (category.equals("식사")) {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.eat_coloricon), title,
								current, location, date, people, no));
						listView1.setAdapter(adapter);
					} else {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.with_coloricon), title,
								current, location, date, people, no));
						listView1.setAdapter(adapter);
					}
					Log.i("TAG", "(");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		// 리스트 뷰 항목 클릭했을 때
		listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// 클릭된 리스트 정보를 새로운 IconTextItem에 저장
				IconTextItem item = (IconTextItem) adapter.getItem(position);
				String[] data = item.getData();
				/*******************************
				 * 게시판 번호가 IconTextItem의 item에서 6번째로 저장되어 있음을 toast로 확인할 수 있다.
				 */
				// Toast.makeText(getActivity(), data[5],
				// Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(getActivity(), GroupSelectedActivity.class);
				
				//인텐트에 list No. 정보를 넣어서 전달한다.
				intent.putExtra("listNo", data[5]);
				startActivity(intent);

			}
		});

	}

	public void setData(String title, String description, String location,
			String date, int people) {
		Toast.makeText(getActivity().getApplicationContext(),
				"" + title + people, Toast.LENGTH_SHORT).show();
	}

}
