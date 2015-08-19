package com.example.sweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.sweet.LoginActivity.logIn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
	String searchString;

	// Url 저장할 변수
	String urlString;
	
	// Button 객체 선언
	Button searchButton;

	//서버에서 받아온 결과 string
	String myResult;
	
	// Context
	Context context;
	// JsonArray
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

		// 검색 버튼이 눌렸을 때
		searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (searchText.getText().toString().equals("")) {
					Toast.makeText(context, "검색어를 입력하세요.", Toast.LENGTH_SHORT)
							.show();
				} else {
					// 검색할 문자열
					searchString = searchText.getText().toString();
					Log.i("search",searchString);
					new JSONParse().execute();
				}
			}
		});

		// 리스트 뷰 항목 클릭했을 때
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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

				Intent intent = new Intent(context, GroupSelectedActivity.class);
				
				//인텐트에 list No. 정보를 넣어서 전달한다.
				intent.putExtra("listNo", data[5]);
				startActivity(intent);

			}
		});
	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("search","onPreExecute()");

		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				Log.i("search","doInBackground시작");
				JSONParser jParser = new JSONParser();

				// 검색 버튼이 눌리고 spinner에 들어온 string에 따라 url이 변한다.
				if (spinner.getSelectedItem().toString().equals("ID")) {
					urlString = "http://52.69.67.4/findid.php";
					searchString = "temp=" + searchString;
				} else if (spinner.getSelectedItem().toString().equals("제목")) {
					urlString = "http://52.69.67.4/findtitle.php";
					searchString = "temp=" + searchString;
				} else if (spinner.getSelectedItem().toString().equals("지역")) {
					urlString = "http://52.69.67.4/findlocation.php";
					searchString = "temp=" + searchString;
				}

				Log.i("search","urlString = " +urlString);
				
				URL myUrl = new URL(urlString);
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				
				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					OutputStreamWriter outStream = new OutputStreamWriter(
							http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(searchString);
					writer.flush();
					writer.close();
					outStream.close();

					//-------------------------
					//   서버에서 전송받기
					//-------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));
					StringBuilder strBuilder = new StringBuilder();
					String str;
					//서버에서 라인단위로 보내주는 스트링을 읽는다.
					while ((str = br.readLine()) != null) {
						Log.i("search", str);
						strBuilder.append(str);
					}
					
					//수신 결과를 전역 string myResult에 저장
					myResult = strBuilder.toString();
					Log.i("search", myResult);
					br.close();
				}
				http.disconnect();

				// 서버에서 받은 string에서 jsonObject를 분리해 내는 함수 호출
				Log.i("search", "$");
				JSONObject json = jParser.getJSONObject(myResult);
				Log.i("search", json.toString());
				return json;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("search","beforeReturnNull");
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				Log.i("TAG", "onPostExecute시작");

				adapter = new IconTextListAdapter(context);
				
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
						listview.setAdapter(adapter);
					} else if (category.equals("레저")) {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.leisure_coloricon),
								title, current, location, date, people, no));
						listview.setAdapter(adapter);
					} else if (category.equals("식사")) {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.eat_coloricon), title,
								current, location, date, people, no));
						listview.setAdapter(adapter);
					} else {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.with_coloricon), title,
								current, location, date, people, no));
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
