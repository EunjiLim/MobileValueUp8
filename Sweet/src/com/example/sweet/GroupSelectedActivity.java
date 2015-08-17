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
	 * url 모임 게시판을 클릭했을 때 모임 정보 및 댓글 내용이 출력된다.
	 ***********************/
	private static String urlBoard = "http://52.69.67.4/printing.php";
	/************************
	 * url 입력된 댓글을 등록해준다.
	 ***********************/
	private static String urlReply = "http://52.69.67.4/reply.php";
	/************************
	 * url ?
	 ***********************/
	private static String urlJoin = "http://52.69.67.4/tes4.php";
	/************************
	 * url 가입된 모임을 탈퇴시킨다.
	 ***********************/
	private static String urlDelete = "http://52.69.67.4/delete.php";

	/************************
	 * View 데이터 불러올 변수들 선언
	 ***********************/
	TextView mSex, mID, mBirthday, mKakaoID, mPhone;
	TextView mTitle, mLocation, mDate, mPeople, mContents;
	EditText editComment;
	Button joinButton, commentRegister, joinerInfo;

	// context
	Context context;

	// intent로 전달된 listNo 변수
	String listNo;

	// 받은 데이터 저장할 String변수
	static String myResult;

	// id
	String id;

	// AsyncTasks
	private commentReg commentBoard;
	private join sweetJoin;
	EditText comment;

	// JSON Node Names
	// TAG_OS : 모임 내용
	// TAG_OS2 : 글쓴이 정보
	// TAG_REPLY : 댓글 정보
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
		// intent로 전달된 게시판 번호
		listNo = getIntent().getExtras().getString("listNo");
		Log.i("selected", "listNo=" + listNo);

		// shardPreferences에서 아이디 받아오기
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
	 * class boardData 게시물 번호 전달 > 게시물 내용, 글쓴이 프로필,가입 여부, 댓글 내용 받아옴
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
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					/********************
					 * 게시물 번호와 전달
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
				 * 모임 정보 받아오기
				 **************************/
				Log.i("selected","모임정보받아오기");
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

					// 액션바 제목 모임 제목으로 변경
					getSupportActionBar().setTitle(title);
				}

				/***************************
				 * 가입 여부 받아오기
				 **************************/
				boardObj = json.getJSONObject(TAG_FLAG);
				flag = boardObj.getString("f");
				Log.i("selected","??");
				if (flag.equals("1")) {
					// 가입하기 버튼을 탈퇴하기 버튼으로 바꿔주기
					joinButton.setText("탈퇴하기");
				}

				Log.i("selected", "가입 여부 받아오기 끝");
				/****************************
				 * 댓글 받아서 출력하기
				 ****************************/
				boardArray = json.getJSONArray(TAG_REPLY);
				Log.i("selected",boardArray.toString());
				Resources res = getResources();
				String cId, cContents, cDate;

				for (int i = 0; i < boardArray.length(); i++) {
					JSONObject c = boardArray.getJSONObject(i);
					Log.i("selected", "댓글 for문");
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
	 * 댓글 등록 스레드
	 * 댓글 등록 버튼 눌렀을 때
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
				Log.i("selected", "comment doInBackground시작");
				JSONParser jParser = new JSONParser();

				URL myUrl = new URL(urlReply);
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				Log.i("selected", "ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
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
					// 서버에서 전송받기
					// -------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));

					br.close();
				}
				http.disconnect();

				// 서버에서 받은 string에서 jsonObject를 분리해 내는 함수 호출
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
			// 댓글 게시판 내용 업데이트
			Resources res = getResources();
			adapter.addItem(new CommentItem(res
					.getDrawable(R.drawable.profile_icon), id, editComment.getText().toString(), getNow()));
			commentList.setAdapter(adapter);
			// EditText 기존 내용 없애기
			editComment.setText("");

		}
	}

	/******************************
	 * 꾸 가입하기 / 탈퇴하기 버튼
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
				Log.i("join", "join doInBackground시작");
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
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
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
					// 서버에서 전송받기
					// -------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));

					br.close();
				}
				http.disconnect();

				// 서버에서 받은 string에서 jsonObject를 분리해 내는 함수 호출
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
				joinButton.setText("가입하기");
			} else {
				joinButton.setText("탈퇴하기");
			}
		}
	}

	/******************************
	 * 가입하기 버튼 클릭 시 호출되는 함수
	 * 
	 * @param v
	 ******************************/
	public void joinBtn(View v) {
		if (flag.equals("0")) {
			Toast.makeText(getApplicationContext(), "가입되었습니다.", 1500).show();
		} else {
			Toast.makeText(getApplicationContext(), "탈퇴되었습니다.", 1500).show();
		}
		sweetJoin = new join();
		sweetJoin.execute();
	}
	
	/******************************
	 * 가입자 정보 버튼 클릭 시 호출되는 함수
	 * 
	 * @param v
	 ******************************/
	public void joinInfo(View v) {
		Intent intent = new Intent(context, JoinerInfoActivity.class);
		
		//인텐트에 list No. 정보를 넣어서 전달한다.
		intent.putExtra("listNo", listNo);
		startActivity(intent);
	}
	/**************************
	 * SharedPreferences 이용 로그인 시 사용했던 아이디를 받아온다.
	 * 
	 * @return id
	 **************************/
	public String getPreferences() {
		SharedPreferences pref = getSharedPreferences("idStorage", MODE_PRIVATE);
		return pref.getString("id", "");
	}
	
	/**************************
	 * 오늘 날짜와 현재 시간 받아오기
	 **************************/
	public String getNow(){
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
				"yyyy.MM.dd HH:mm:ss", Locale.KOREA);
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format(currentTime);
		return mTime;
	}
}
