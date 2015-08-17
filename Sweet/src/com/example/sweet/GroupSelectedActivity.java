package com.example.sweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commentlistview.CommentItem;
import com.example.commentlistview.CommentListAdapter;
import com.example.libriary.JSONParser;

public class GroupSelectedActivity extends ActionBarActivity {

	/************************
	 * url 乞績 惟獣毒聖 適遣梅聖 凶 乞績 舛左 貢 奇越 鎧遂戚 窒径吉陥.
	 ***********************/
	private static String urlBoard = "http://52.69.67.4/printing.php";
	/************************
	 * url 脊径吉 奇越聖 去系背層陥.
	 ***********************/
	private static String urlReply = "http://52.69.67.4/reply.php";
	/************************
	 * url ?
	 ***********************/
	private static String urlJoin = "http://52.69.67.4/tes4.php";
	/************************
	 * url 亜脊吉 乞績聖 纏盗獣轍陥.
	 ***********************/
	private static String urlDelete = "http://52.69.67.4/delete.php";

	/************************
	 * View 汽戚斗 災君臣 痕呪級 識情
	 ***********************/
	TextView mSex, mID, mBirthday, mKakaoID, mPhone;
	TextView mTitle, mLocation, mDate, mPeople, mContents;
	EditText editComment;
	Button joinButton, commentRegister, joinerInfo;

	// context
	Context context;

	// intent稽 穿含吉 listNo 痕呪
	String listNo;

	// 閤精 汽戚斗 煽舌拝 String痕呪
	static String myResult;

	// id
	String id;

	// AsyncTasks
	private commentReg commentBoard;
	private join sweetJoin;
	EditText comment;

	// JSON Node Names
	// TAG_OS : 乞績 鎧遂
	// TAG_OS2 : 越彰戚 舛左
	// TAG_REPLY : 奇越 舛左
	private static final String TAG_OS = "board";
	private static final String TAG_OS2 = "board2";
	private static final String TAG_REPLY = "reply";
	private static final String TAG_FLAG = "flag";

	// TAG
	private static final String TAG_TITLE = "title";
	private static final String TAG_CATEGORY = "category";
	private static final String TAG_LOCATION = "location";
	private static final String TAG_CONTENTS = "contents";
	private static final String TAG_CURRENT = "current";
	private static final String TAG_DATE = "date";
	private static final String TAG_PEOPLE = "people";
	private static final String TAG_CDATE = "DATE";

	private static final String TAG_PHONE = "PHONE";
	private static final String TAG_ID = "ID";
	private static final String TAG_KAKAOID = "FACEBOOK";
	private static final String TAG_BIRTHDAY = "BIRTHDAY";
	private static final String TAG_SEX = "SEX";

	JSONArray boardArray = null;
	JSONObject boardObj;
	String flag = "0";
	String title, location, date, contents, people, category;

	// Comment Listview
	ListView commentList;
	CommentListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_selected);

		mID = (TextView) findViewById(R.id.TextView_selectedGroupIDResult);
		mSex = (TextView) findViewById(R.id.TextView_selectedGroupSexResult);
		mBirthday = (TextView) findViewById(R.id.TextView_selectedGroupBirthdayResult);
		mKakaoID = (TextView) findViewById(R.id.TextView_selectedGroupKakaoIDResult);
		mPhone = (TextView) findViewById(R.id.TextView_selectedGroupPhoneResult);
		mLocation = (TextView) findViewById(R.id.TextView_groupSelectedLocationResult);
		mDate = (TextView) findViewById(R.id.TextView_groupSelectedDateResult);
		mPeople = (TextView) findViewById(R.id.TextView_groupSelectedFixedNumberResult);
		mContents = (TextView) findViewById(R.id.TextView_groupSeletedContent);

		joinButton = (Button) findViewById(R.id.Button_groupSelected1);
		editComment = (EditText) findViewById(R.id.EditText_comment);
		commentRegister = (Button) findViewById(R.id.Button_commentRegister);
		joinerInfo = (Button) findViewById(R.id.Button_groupSelected2);
		// intent稽 穿含吉 惟獣毒 腰硲
		listNo = getIntent().getExtras().getString("listNo");
		Log.i("selected", "listNo=" + listNo);

		// shardPreferences拭辞 焼戚巨 閤焼神奄
		id = getPreferences();
		Log.i("selected", "id=" + id);

		commentRegister.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				commentBoard = new commentReg();
				commentBoard.execute();

			}
		});

		commentList = (ListView) findViewById(R.id.ListView_comment);
		adapter = new CommentListAdapter(this);

		new boardData().execute();
	}

	/*******************************************
	 * class boardData 惟獣弘 腰硲 穿含 > 惟獣弘 鎧遂, 越彰戚 覗稽琶,亜脊 食採, 奇越 鎧遂 閤焼身
	 * 
	 * @author Eunji
	 *
	 *******************************************/

	private class boardData extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				JSONParser jParser = new JSONParser();
				URL myUrl = new URL(urlBoard);
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 辞獄拭辞 石奄 乞球 走舛
					http.setDoOutput(true); // 辞獄稽 床奄 乞球 走舛
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					/********************
					 * 惟獣弘 腰硲人 穿含
					 *******************/
					StringBuffer buffer = new StringBuffer();
					buffer.append("no").append("=").append(listNo).append("&");
					buffer.append("id").append("=").append(id);

					OutputStreamWriter outStream = new OutputStreamWriter(
							http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					outStream.close();

					// -------------------------
					// 辞獄拭辞 汽戚斗 閤奄
					// -------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));
					StringBuilder strBuilder = new StringBuilder();
					String str;
					// 辞獄拭辞 虞昔舘是稽 左鎧爽澗 什闘元聖 石澗陥.
					while ((str = br.readLine()) != null) {
						Log.i("selected", str);
						strBuilder.append(str);
					}

					// 呪重 衣引研 穿蝕 string myResult拭 煽舌
					myResult = strBuilder.toString();
					Log.i("selected", myResult);
					br.close();
				}
				http.disconnect();

				// 辞獄拭辞 閤精 string拭辞 jsonObject研 歳軒背 鎧澗 敗呪 硲窒
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
				 * 乞績 幻窮 紫寓 舛左 閤焼神奄
				 **************************/
				String id, phone, kakaoid, birthday, sex;
				boardArray = json.getJSONArray(TAG_OS2);

				// ID, PHONE, KAKAOID, BIRTHDAY, NAME, SEX
				for (int i = 0; i < boardArray.length(); i++) {
					JSONObject c = boardArray.getJSONObject(i);
					Log.i("selected","hmm...");
					// Storing JSON item in a Variable
					id = c.getString(TAG_ID);
					phone = c.getString(TAG_PHONE);
					kakaoid = c.getString(TAG_KAKAOID);
					birthday = c.getString(TAG_BIRTHDAY);
					sex = c.getString(TAG_SEX);

					mID.setText(id);
					mPhone.setText(phone);
					mKakaoID.setText(kakaoid);
					mBirthday.setText(birthday);
					mSex.setText(sex);
				}

				/***************************
				 * 乞績 舛左 閤焼神奄
				 **************************/
				Log.i("selected","乞績舛左閤焼神奄");
				boardArray = json.getJSONArray(TAG_OS);
				// title, location, contents current, ID, date, people
				for (int i = 0; i < boardArray.length(); i++) {
					JSONObject c = boardArray.getJSONObject(i);

					// Storing JSON item in a Variable
					title = c.getString(TAG_TITLE);
					location = c.getString(TAG_LOCATION);
					date = c.getString(TAG_DATE);
					people = c.getString(TAG_PEOPLE);
					contents = c.getString(TAG_CONTENTS);
					category = c.getString(TAG_CATEGORY);

					Log.i("selected", contents);
					mLocation.setText(location);
					mPeople.setText(people);
					mDate.setText(date);
					mContents.setText(contents);

					// 衝芝郊 薦鯉 乞績 薦鯉生稽 痕井
					getSupportActionBar().setTitle(title);
				}

				/***************************
				 * 亜脊 食採 閤焼神奄
				 **************************/
				boardObj = json.getJSONObject(TAG_FLAG);
				flag = boardObj.getString("f");
				Log.i("selected","??");
				if (flag.equals("1")) {
					// 亜脊馬奄 獄動聖 纏盗馬奄 獄動生稽 郊蚊爽奄
					joinButton.setText("纏盗馬奄");
				}

				Log.i("selected", "亜脊 食採 閤焼神奄 魁");
				/****************************
				 * 奇越 閤焼辞 窒径馬奄
				 ****************************/
				boardArray = json.getJSONArray(TAG_REPLY);
				Log.i("selected",boardArray.toString());
				Resources res = getResources();
				String cId, cContents, cDate;

				for (int i = 0; i < boardArray.length(); i++) {
					JSONObject c = boardArray.getJSONObject(i);
					Log.i("selected", "奇越 for庚");
					cId = c.getString(TAG_ID);
					cContents = c.getString(TAG_CONTENTS);
					cDate = c.getString(TAG_CDATE);
					Log.i("selected", cId+cContents+cDate);
					// Storing JSON item in a Variable
					adapter.addItem(new CommentItem(res
							.getDrawable(R.drawable.profile_icon), cId,
							cContents, cDate));
					commentList.setAdapter(adapter);
				}
				//commentList.setAdapter(adapter);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/******************************
	 * 奇越 去系 什傾球
	 * 奇越 去系 獄動 喚袈聖 凶
	 * @author Eunji
	 *
	 ******************************/
	private class commentReg extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("selected", "comment onPreExecute()");
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				Log.i("selected", "comment doInBackground獣拙");
				JSONParser jParser = new JSONParser();

				URL myUrl = new URL(urlReply);
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				Log.i("selected", "ししししししししししししししし");

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 辞獄拭辞 石奄 乞球 走舛
					http.setDoOutput(true); // 辞獄稽 床奄 乞球 走舛
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					StringBuffer buffer = new StringBuffer();
					buffer.append("no").append("=").append(listNo).append("&");
					buffer.append("id").append("=").append(id).append("&");
					buffer.append("contents").append("=").append(editComment.getText()).append("&");
					buffer.append("date").append("=").append(getNow());

					Log.i("selected", buffer.toString());
					OutputStreamWriter outStream = new OutputStreamWriter(
							http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					outStream.close();

					// -------------------------
					// 辞獄拭辞 穿勺閤奄
					// -------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));

					br.close();
				}
				http.disconnect();

				// 辞獄拭辞 閤精 string拭辞 jsonObject研 歳軒背 鎧澗 敗呪 硲窒
				Log.i("selected", "$");
				JSONObject json = jParser.getJSONObject(myResult);
				// Log.i("selected", json.toString());
				return json;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("search", "beforeReturnNull");
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			// 奇越 惟獣毒 鎧遂 穣汽戚闘
			Resources res = getResources();
			adapter.addItem(new CommentItem(res
					.getDrawable(R.drawable.profile_icon), id, editComment.getText().toString(), getNow()));
			commentList.setAdapter(adapter);
			// EditText 奄糎 鎧遂 蒸蕉奄
			editComment.setText("");

		}
	}

	/******************************
	 * 荷 亜脊馬奄 / 纏盗馬奄 獄動
	 * 
	 * 
	 * @author Eunji
	 *
	 ******************************/
	private class join extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("selected", "join onPreExecute()");
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				Log.i("join", "join doInBackground獣拙");
				JSONParser jParser = new JSONParser();
				URL myUrl;
				if (flag.equals("0")) {
					myUrl = new URL(urlJoin);
					flag = "1";
				} else {
					myUrl = new URL(urlDelete);
					flag = "0";
				}

				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				Log.i("join", "joindfsdfsdf");

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 辞獄拭辞 石奄 乞球 走舛
					http.setDoOutput(true); // 辞獄稽 床奄 乞球 走舛
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					StringBuffer buffer = new StringBuffer();
					buffer.append("no").append("=").append(listNo).append("&");
					buffer.append("id").append("=").append(id).append("&");
					buffer.append("ROOM").append("=").append(listNo)
							.append("&");
					buffer.append("location").append("=").append(location)
							.append("&");
					buffer.append("title").append("=").append(title)
							.append("&");
					buffer.append("contents").append("=").append(contents)
							.append("&");
					buffer.append("date").append("=").append("2015-09-13")
							.append("&");
					buffer.append("people").append("=").append(people)
							.append("&");
					buffer.append("category").append("=").append(category)
							.append("&");

					Log.i("join", buffer.toString());
					OutputStreamWriter outStream = new OutputStreamWriter(
							http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					outStream.close();

					// -------------------------
					// 辞獄拭辞 穿勺閤奄
					// -------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));

					br.close();
				}
				http.disconnect();

				// 辞獄拭辞 閤精 string拭辞 jsonObject研 歳軒背 鎧澗 敗呪 硲窒
				Log.i("join", "$");
				JSONObject json = jParser.getJSONObject(myResult);
				// Log.i("selected", json.toString());
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
			if (flag.equals("0")) {
				joinButton.setText("亜脊馬奄");
			} else {
				joinButton.setText("纏盗馬奄");
			}
		}
	}

	/******************************
	 * 亜脊馬奄 獄動 適遣 獣 硲窒鞠澗 敗呪
	 * 
	 * @param v
	 ******************************/
	public void joinBtn(View v) {
		if (flag.equals("0")) {
			Toast.makeText(getApplicationContext(), "亜脊鞠醸柔艦陥.", 1500).show();
		} else {
			Toast.makeText(getApplicationContext(), "纏盗鞠醸柔艦陥.", 1500).show();
		}
		sweetJoin = new join();
		sweetJoin.execute();
	}
	
	/******************************
	 * 亜脊切 舛左 獄動 適遣 獣 硲窒鞠澗 敗呪
	 * 
	 * @param v
	 ******************************/
	public void joinInfo(View v) {
		Intent intent = new Intent(context, JoinerInfoActivity.class);
		
		//昔度闘拭 list No. 舛左研 隔嬢辞 穿含廃陥.
		intent.putExtra("listNo", listNo);
		startActivity(intent);
	}
	/**************************
	 * SharedPreferences 戚遂 稽益昔 獣 紫遂梅揮 焼戚巨研 閤焼紳陥.
	 * 
	 * @return id
	 **************************/
	public String getPreferences() {
		SharedPreferences pref = getSharedPreferences("idStorage", MODE_PRIVATE);
		return pref.getString("id", "");
	}
	
	/**************************
	 * 神潅 劾促人 薄仙 獣娃 閤焼神奄
	 **************************/
	public String getNow(){
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
				"yyyy.MM.dd HH:mm:ss", Locale.KOREA);
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format(currentTime);
		return mTime;
	}
}
