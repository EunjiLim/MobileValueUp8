package com.example.sweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.example.commentlistview.CommentItem;
import com.example.commentlistview.CommentListAdapter;
import com.example.libriary.JSONParser;

public class GroupSelectedActivity extends ActionBarActivity{

	EditText editComment;
	Button commentRegister;
	
	// context
	Context context;

	// intent로 전달된 listNo 변수
	String listNo;

	/************************
	 * url
	 * 모임 게시판을 클릭했을 때 모임 정보 및 댓글 내용이 출력된다.
	 ***********************/
	private static String urlBoard = "http://52.69.67.4/printing.php";
	/************************
	 * url
	 * 입력된 댓글을 등록해준다.
	 ***********************/
	private static String urlReply = "http://52.69.67.4/reply.php";
	/************************
	 * url
	 *  ?
	 ***********************/
	private static String urlJoin = "http://52.69.67.4/tes4.php";
	/************************
	 * url
	 * 가입된 모임을 탈퇴시킨다.
	 ***********************/
	private static String urlDelete = "http://52.69.67.4/delete.php";
	// 받은 데이터 저장할 String변수
	static String myResult;
	
	//id
	String id;
	
	//AsyncTasks
	private commentReg commentBoard;
	private join sweetJoin;
	ListView commentList;
	CommentListAdapter adapter;
	EditText comment;
	

	// JSON Node Names
	private static final String TAG_OS = "board";
	private static final String TAG_OS2 = "board2";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_selected);

		editComment = (EditText) findViewById(R.id.EditText_comment);
		commentRegister = (Button) findViewById(R.id.Button_commentRegister);
		
		// intent로 전달된 게시판 번호
		listNo = getIntent().getExtras().getString("listNo");
		Log.i("selected", "listNo=" + listNo);
		
		//shardPreferences에서 아이디 받아오기
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
		Resources res = getResources();
		adapter.addItem(new CommentItem(
				res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(
				res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(
				res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(
				res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(
				res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(
				res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(
				res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		commentList.setAdapter(adapter);
		
		new boardData().execute();
	}
	
	/***********************************
	 * 게시물 번호와 로그인한 id를 전달
	 * 
	 * @author Eunji
	 *
	 */
	
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
				HttpURLConnection http = (HttpURLConnection) myUrl.openConnection();
				
				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");
					
					/********************
					 * 게시물 번호와 내 아이디 전달
					 *******************/
					StringBuffer buffer = new StringBuffer();
					buffer.append("no").append("=").append(listNo).append("&");
					buffer.append("id").append("=").append(id).append("&");

					OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
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
						Log.i("selected", str);
						strBuilder.append(str);
					}
					
					//수신 결과를 전역 string myResult에 저장
					myResult = strBuilder.toString();
					Log.i("selected", myResult);
					br.close();
				}
				http.disconnect();

				// 서버에서 받은 string에서 jsonObject를 분리해 내는 함수 호출
				JSONObject json = jParser.getJSONObject(myResult);
				Log.i("SetGroupActivity", json.toString());
				return json;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("selected","beforeReturnNull");
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			
		}
	}

	private class commentReg extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("selected","comment onPreExecute()");
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				Log.i("selected","comment doInBackground시작");
				JSONParser jParser = new JSONParser();
				
				URL myUrl = new URL(urlReply);
				HttpURLConnection http = (HttpURLConnection) myUrl.openConnection();
				Log.i("selected","ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");

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
					buffer.append("date").append("=").append("1987-08-07");

					Log.i("selected",buffer.toString());
					OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					outStream.close();

					//-------------------------
					//   서버에서 전송받기
					//-------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));
				
					br.close();
				}
				http.disconnect();

				// 서버에서 받은 string에서 jsonObject를 분리해 내는 함수 호출
				Log.i("selected", "$");
				JSONObject json = jParser.getJSONObject(myResult);
				//Log.i("selected", json.toString());
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
			//댓글 게시판 내용 업데이트
			//EditText 기존 내용 없애기
			editComment.setText("");
		}
	}

	private class join extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("selected","join onPreExecute()");
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				Log.i("join","join doInBackground시작");
				JSONParser jParser = new JSONParser();
				
				
				URL myUrl = new URL(urlJoin);
				HttpURLConnection http = (HttpURLConnection) myUrl.openConnection();
				Log.i("join","joindfsdfsdf");

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");
					
					StringBuffer buffer = new StringBuffer();
					buffer.append("no").append("=").append(listNo).append("&");
					buffer.append("id").append("=").append(id).append("&");
					buffer.append("ROOM").append("=").append(listNo).append("&");
					buffer.append("location").append("=").append("서울").append("&");
					buffer.append("title").append("=").append("3번꺼").append("&");
					buffer.append("contents").append("=").append("3번내용").append("&");
					buffer.append("date").append("=").append("2015-09-13").append("&");
					buffer.append("people").append("=").append("4").append("&");
					buffer.append("category").append("=").append("동행").append("&");

					Log.i("join",buffer.toString());
					OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					outStream.close();

					//-------------------------
					//   서버에서 전송받기
					//-------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));
				
					br.close();
				}
				http.disconnect();

				// 서버에서 받은 string에서 jsonObject를 분리해 내는 함수 호출
				Log.i("join", "$");
				JSONObject json = jParser.getJSONObject(myResult);
				//Log.i("selected", json.toString());
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
		}
	}

	public void joinBtn(View v) {
		Toast.makeText(getApplicationContext(), "가입되었습니다.", 1500).show();
		sweetJoin = new join();
		sweetJoin.execute();
	}

	/*****************
	 * 로그인 시 사용했던 아이디를 받아온다.
	 * @return id
	 */
    public String getPreferences(){
        SharedPreferences pref = getSharedPreferences("idStorage", MODE_PRIVATE);
        return pref.getString("id", "");
    }
}
