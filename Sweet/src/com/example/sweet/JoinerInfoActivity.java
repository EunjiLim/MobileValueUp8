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
import com.example.libriary.JSONParser;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

public class JoinerInfoActivity extends ActionBarActivity{
	
	// JSON Node Names TAG
	private static String TAG_OS = "board";
	
	//TAG
	private static final String TAG_ID = "ID";
	private static final String TAG_SEX = "SEX";
	private static final String TAG_BIRTHDAY = "BIRTHDAY";
	private static final String TAG_PHONE = "PHONE";
	private static final String TAG_KAKAOID = "FACEBOOK";
	
	ListView listview;
	String listNo, myResult;
	JSONArray boardArray;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_info);
		
		listview = (ListView) findViewById(R.id.listView_joinInfo);
		
		// intent로 전달된 게시판 번호
		listNo = getIntent().getExtras().getString("listNo");
	}
	
	/****************************************************
	 * 모임 번호를 넘긴다.
	 * 그 모임에 가입한 가입자 정보를 list로 보여준다.
	 * @author Eunji
	 *
	 */
	private class joiner extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				JSONParser jParser = new JSONParser();
				URL myUrl = new URL("http://52.69.67.4/list.php");
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					/********************
					 * 게시물 번호와 전달
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
					// 서버에서 데이터 받기
					// -------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));
					StringBuilder strBuilder = new StringBuilder();
					String str;
					// 서버에서 라인단위로 보내주는 스트링을 읽는다.
					while ((str = br.readLine()) != null) {
						Log.i("selected", str);
						strBuilder.append(str);
					}

					// 수신 결과를 전역 string myResult에 저장
					myResult = strBuilder.toString();
					Log.i("selected", myResult);
					br.close();
				}
				http.disconnect();

				// 서버에서 받은 string에서 jsonObject를 분리해 내는 함수 호출
				JSONObject json = jParser.getJSONObject(myResult);
				Log.i("selected", json.toString());
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
				 * 모임 만든 사람 정보 받아오기
				 **************************/
				String id, phone, kakaoid, birthday, sex;
				boardArray = json.getJSONArray(TAG_OS);

				// ID, PHONE, KAKAOID, BIRTHDAY, NAME, SEX
				for (int i = 0; i < boardArray.length(); i++) {
					JSONObject c = boardArray.getJSONObject(i);
					Log.i("selected","hmm...");
					// Storing JSON item in a Variable
					id = c.getString(TAG_ID);
					sex = c.getString(TAG_SEX);
					phone = c.getString(TAG_PHONE);
					kakaoid = c.getString(TAG_KAKAOID);
					birthday = c.getString(TAG_BIRTHDAY);

/*					mID.setText(id);
					mPhone.setText(phone);
					mKakaoID.setText(kakaoid);
					mBirthday.setText(birthday);
					mSex.setText(sex);*/
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
