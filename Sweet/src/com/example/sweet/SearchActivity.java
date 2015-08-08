package com.example.sweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.sweet.LoginActivity.logIn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends ActionBarActivity {

	Spinner spinner;
	ListView listview;
	// 리스트뷰를 위한 어댑터
	IconTextListAdapter adapter;
	// EditText 객체 선언
	EditText searchText;
	// Button 객체 선언
	Button searchButton;
	// 검색 결과를 불러올 URL
	private static String url;
	// Context
	Context context;
	// JsonArray
	JSONArray boardArray = null;

	// JSON Node Names
	private static final String TAG_OS = "board";
	private static final String TAG_ID = "ID";
	private static final String TAG_CATEGORY = "category";
	private static final String TAG_TITLE = "title";
	private static final String TAG_LOCATION = "location";
	private static final String TAG_DATE = "date";
	private static final String TAG_PEOPLE = "people";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		spinner = (Spinner) findViewById(R.id.Spinner_boardSearch);
		searchText = (EditText) findViewById(R.id.EditText_boardSearch);
		searchButton = (Button) findViewById(R.id.Button_boardSearch);
		context = this;

		// 스피너 객체 생성해서 리소스 가져오기 , 검색할 항목 선택
		ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
				getApplicationContext(), R.array.spinner_boardSearch,
				android.R.layout.simple_spinner_item);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrayAdapter);

		// 검색 결과 리스트뷰 설정
		listview = (ListView) findViewById(R.id.listView_boardSearch);
		adapter = new IconTextListAdapter(this);
		// 리스트뷰에 어댑더 설정
		listview.setAdapter(adapter);

/*		Resources res = getResources();
		adapter.addItem(new IconTextItem(res
				.getDrawable(R.drawable.profileicon), "title", "1", "location",
				"date", "people"));
		listview.setAdapter(adapter);*/

		// 검색 버튼이 눌렸을 때
		searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (searchText.getText().toString().equals("")) {
					Toast.makeText(context, "검색어를 입력하세요.", Toast.LENGTH_SHORT)
							.show();
				} else
					new JSONParse().execute();
			}
		});

		// 리스트 뷰 항목 클릭했을 때
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(context, "You Clicked", 0).show();

			}
		});
	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(null);
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected JSONObject doInBackground(String... args) {

			Log.i("TAG", "#");
			JSONParser jParser = new JSONParser();

			// 검색 버튼이 눌리고 spinner에 들어온 string에 따라 url이 변한다.
			if (spinner.getSelectedItem().toString().equals("ID"))
				url = "http://52.69.67.4/findid.php";
			else if (spinner.getSelectedItem().toString().equals("제목"))
				url = "http://52.69.67.4/findtitle.php";
			else if (spinner.getSelectedItem().toString().equals("지역"))
				url = "http://52.69.67.4/findlocation.php";

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
					String id = c.getString(TAG_ID);
					String category = c.getString(TAG_CATEGORY);
					String title = c.getString(TAG_TITLE);
					String location = c.getString(TAG_LOCATION);
					String date = c.getString(TAG_DATE);
					String people = c.getString(TAG_PEOPLE);

					Resources res = getResources();

					Log.i("TAG", "*");
					//카테고리별 게시판 아이콘 적용
					if (category.equals("숙박")) {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.house_coloricon), title,
								"1", location, date, people));
						listview.setAdapter(adapter);
					}else if(category.equals("레저")){
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.leisure_coloricon), title,
								"1", location, date, people));
						listview.setAdapter(adapter);
					}else if(category.equals("식사")){
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.eat_coloricon), title,
								"1", location, date, people));
						listview.setAdapter(adapter);
					}else {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.with_coloricon), title,
								"1", location, date, people));
						listview.setAdapter(adapter);
					}
					
					listview.setAdapter(adapter);
					Log.i("TAG", "(");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

}
