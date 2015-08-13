package com.example.sweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.commentlistview.CommentItem;
import com.example.commentlistview.CommentListAdapter;
import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.sweet.LoginActivity.logIn;

public class GroupSelectedActivity extends Activity {

	EditText editComment;
	Button commentRegister;
	
	// context
	Context context;

	// intent로 전달된 listNo 변수
	String listNo;

	// 연결할 url
	private static String url = "http://52.69.67.4/printing.php";
	private static String urlReply = "http://52.69.67.4/reply.php";
	private static String urlJoin = "http://52.69.67.4/tes4.php";
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_selected);

		editComment = (EditText) findViewById(R.id.EditText_comment);
		commentRegister = (Button) findViewById(R.id.Button_commentRegister);
		
		// intent로 전달된 데이터 저장
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
		
		new JSONParse().execute();
	}
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("selected","onPreExecute()");

		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				Log.i("selected","doInBackground시작");
				JSONParser jParser = new JSONParser();
				
				URL myUrl = new URL(url);
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				
				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");
					
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
				
				
				URL myUrl = new URL(urlDelete);
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
					buffer.append("id").append("=").append(id);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_selected, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public String getPreferences(){
        SharedPreferences pref = getSharedPreferences("idStorage", MODE_PRIVATE);
        return pref.getString("id", "");
    }
}
