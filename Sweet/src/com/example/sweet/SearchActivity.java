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
	// ����Ʈ�並 ���� �����
	IconTextListAdapter adapter;
	// EditText ��ü ����
	EditText searchText;
	// Button ��ü ����
	Button searchButton;
	// �˻� ����� �ҷ��� URL
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
		// ����Ʈ�信 ���� ����
		listview.setAdapter(adapter);

/*		Resources res = getResources();
		adapter.addItem(new IconTextItem(res
				.getDrawable(R.drawable.profileicon), "title", "1", "location",
				"date", "people"));
		listview.setAdapter(adapter);*/

		// �˻� ��ư�� ������ ��
		searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (searchText.getText().toString().equals("")) {
					Toast.makeText(context, "�˻�� �Է��ϼ���.", Toast.LENGTH_SHORT)
							.show();
				} else
					new JSONParse().execute();
			}
		});

		// ����Ʈ �� �׸� Ŭ������ ��
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

			// �˻� ��ư�� ������ spinner�� ���� string�� ���� url�� ���Ѵ�.
			if (spinner.getSelectedItem().toString().equals("ID"))
				url = "http://52.69.67.4/findid.php";
			else if (spinner.getSelectedItem().toString().equals("����"))
				url = "http://52.69.67.4/findtitle.php";
			else if (spinner.getSelectedItem().toString().equals("����"))
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
				Log.i("TAG", "onPostExecute����");

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
					//ī�װ��� �Խ��� ������ ����
					if (category.equals("����")) {
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.house_coloricon), title,
								"1", location, date, people));
						listview.setAdapter(adapter);
					}else if(category.equals("����")){
						adapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.leisure_coloricon), title,
								"1", location, date, people));
						listview.setAdapter(adapter);
					}else if(category.equals("�Ļ�")){
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
