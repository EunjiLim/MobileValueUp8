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

import com.example.commentlistview.CommentItem;
import com.example.joinerlist.JoinerInfoItem;
import com.example.joinerlist.JoinerInfoListAdapter;
import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class JoinerInfoActivity extends ActionBarActivity{
	
	// JSON Node Names TAG
	private static String TAG_OS = "board";
	
	//TAG
	private static final String TAG_ID = "ID";
	private static final String TAG_SEX = "SEX";
	private static final String TAG_NAME = "NAME";
	private static final String TAG_BIRTHDAY = "BIRTHDAY";
	private static final String TAG_PHONE = "PHONE";
	
	ListView listview;
	JoinerInfoListAdapter adapter;
	
	String listNo, myResult;
	JSONArray boardArray;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_info);
	
		Log.i("joiner","2");
		listview = (ListView) findViewById(R.id.listView_joinInfo);
		adapter = new JoinerInfoListAdapter(this);
		// intent�� ���޵� �Խ��� ��ȣ
		listNo = getIntent().getExtras().getString("listNo");
		
		new joiner().execute();
	}
	
	/****************************************************
	 * ���� ��ȣ�� �ѱ��.
	 * �� ���ӿ� ������ ������ ������ list�� �����ش�.
	 * @author Eunji
	 *
	 */
	private class joiner extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("joiner","3");
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				Log.i("joiner","4");
				JSONParser jParser = new JSONParser();
				URL myUrl = new URL("http://52.69.67.4/list.php");
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // �������� �б� ��� ����
					http.setDoOutput(true); // ������ ���� ��� ����
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					/********************
					 * �Խù� ��ȣ�� ����
					 *******************/
					StringBuffer buffer = new StringBuffer();
					buffer.append("no").append("=").append(listNo);

					OutputStreamWriter outStream = new OutputStreamWriter(
							http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					outStream.close();

					// -------------------------
					// �������� ������ �ޱ�
					// -------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));
					StringBuilder strBuilder = new StringBuilder();
					String str;
					// �������� ���δ����� �����ִ� ��Ʈ���� �д´�.
					while ((str = br.readLine()) != null) {
						Log.i("selected", str);
						strBuilder.append(str);
					}

					// ���� ����� ���� string myResult�� ����
					myResult = strBuilder.toString();
					Log.i("selected", myResult);
					br.close();
				}
				http.disconnect();

				// �������� ���� string���� jsonObject�� �и��� ���� �Լ� ȣ��
				JSONObject json = jParser.getJSONObject(myResult);
				Log.i("joiner","5" + json.toString());
				return json;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				/***************************
				 * ���� ���� ��� ���� �޾ƿ���
				 **************************/
				
				Log.i("joiner","6");
				String id, name, phone, kakaoid, birthday, sex;
				boardArray = json.getJSONArray(TAG_OS);
				Resources res = getResources();
				
				// ID, PHONE, KAKAOID, BIRTHDAY, NAME, SEX
				for (int i = 0; i < boardArray.length(); i++) {
					JSONObject c = boardArray.getJSONObject(i);
					Log.i("selected","hmm...");
					// Storing JSON item in a Variable
					id = c.getString(TAG_ID);
					sex = c.getString(TAG_SEX);
					name = c.getString(TAG_NAME);
					phone = c.getString(TAG_PHONE);
					birthday = c.getString(TAG_BIRTHDAY);
					Log.i("joiner","7");
					adapter.addItem(new JoinerInfoItem(res
							.getDrawable(R.drawable.profile_icon), id, sex,
							name, birthday, phone));
					listview.setAdapter((ListAdapter) adapter);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
