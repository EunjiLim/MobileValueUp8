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
	// ����Ʈ�並 ���� �����
	IconTextListAdapter adapter;
	// EditText ��ü ����
	EditText searchText;
	String searchString;

	// Url ������ ����
	String urlString;
	
	// Button ��ü ����
	Button searchButton;

	//�������� �޾ƿ� ��� string
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

		// ���ǳ� ��ü �����ؼ� ���ҽ� �������� , �˻��� �׸� ����
		ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
				getApplicationContext(), R.array.spinner_boardSearch,
				android.R.layout.simple_spinner_item);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrayAdapter);

		// �˻� ��� ����Ʈ�� ����
		listview = (ListView) findViewById(R.id.listView_boardSearch);
		adapter = new IconTextListAdapter(this);

		// �˻� ��ư�� ������ ��
		searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (searchText.getText().toString().equals("")) {
					Toast.makeText(context, "�˻�� �Է��ϼ���.", Toast.LENGTH_SHORT)
							.show();
				} else {
					// �˻��� ���ڿ�
					searchString = searchText.getText().toString();
					Log.i("search",searchString);
					new JSONParse().execute();
				}
			}
		});

		// ����Ʈ �� �׸� Ŭ������ ��
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Ŭ���� ����Ʈ ������ ���ο� IconTextItem�� ����
				IconTextItem item = (IconTextItem) adapter.getItem(position);
				String[] data = item.getData();
				/*******************************
				 * �Խ��� ��ȣ�� IconTextItem�� item���� 6��°�� ����Ǿ� ������ toast�� Ȯ���� �� �ִ�.
				 */
				// Toast.makeText(getActivity(), data[5],
				// Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(context, GroupSelectedActivity.class);
				
				//����Ʈ�� list No. ������ �־ �����Ѵ�.
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
				Log.i("search","doInBackground����");
				JSONParser jParser = new JSONParser();

				// �˻� ��ư�� ������ spinner�� ���� string�� ���� url�� ���Ѵ�.
				if (spinner.getSelectedItem().toString().equals("ID")) {
					urlString = "http://52.69.67.4/findid.php";
					searchString = "temp=" + searchString;
				} else if (spinner.getSelectedItem().toString().equals("����")) {
					urlString = "http://52.69.67.4/findtitle.php";
					searchString = "temp=" + searchString;
				} else if (spinner.getSelectedItem().toString().equals("����")) {
					urlString = "http://52.69.67.4/findlocation.php";
					searchString = "temp=" + searchString;
				}

				Log.i("search","urlString = " +urlString);
				
				URL myUrl = new URL(urlString);
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				
				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // �������� �б� ��� ����
					http.setDoOutput(true); // ������ ���� ��� ����
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
					//   �������� ���۹ޱ�
					//-------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));
					StringBuilder strBuilder = new StringBuilder();
					String str;
					//�������� ���δ����� �����ִ� ��Ʈ���� �д´�.
					while ((str = br.readLine()) != null) {
						Log.i("search", str);
						strBuilder.append(str);
					}
					
					//���� ����� ���� string myResult�� ����
					myResult = strBuilder.toString();
					Log.i("search", myResult);
					br.close();
				}
				http.disconnect();

				// �������� ���� string���� jsonObject�� �и��� ���� �Լ� ȣ��
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
				Log.i("TAG", "onPostExecute����");

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
					// ī�װ��� �Խ��� ������ ����
					if (category.equals("����")) {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.house_coloricon),
								title, current, location, date, people, no));
						listview.setAdapter(adapter);
					} else if (category.equals("����")) {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.leisure_coloricon),
								title, current, location, date, people, no));
						listview.setAdapter(adapter);
					} else if (category.equals("�Ļ�")) {
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
