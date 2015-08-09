package com.example.sweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {

	EditText ID;
	EditText PW;
	TextView loginWithoutId;
	Button login;
	String id, pw;
	String line = "", temp = "";
	private logIn loging;

	// context for Toast.makeText
	private Context context;

	// boolean variable for login
	boolean loginFlag = false;

	//액션바
	private ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		startActivity(new Intent(this, SplashActivity.class));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//액션바
		mActionBar = getSupportActionBar();
		
		ID = (EditText) findViewById(R.id.emailAddress);
		PW = (EditText) findViewById(R.id.pw);
		login = (Button) findViewById(R.id.button_login);
		loginWithoutId = (TextView) findViewById(R.id.button3);
				
		context = this;

		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loging = new logIn();
				loging.execute();

			}
		});
		
		loginWithoutId.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), FragmentBaseActivity.class);
				startActivity(intent);
			}
		});
	}

	public class logIn extends AsyncTask<String, Void, String> {

		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... params) {

			try {
				URL myUrl = new URL("http://52.69.67.4/login.php");
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				Log.e("tag", "2");
				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					// 연결되었음 코드가 리턴되면.

					id = ID.getText().toString();
					pw = PW.getText().toString();

					StringBuffer buffer = new StringBuffer();
					buffer.append("id").append("=").append(id).append("&"); // php
																			// 변수에
																			// 값
																			// 대입
					buffer.append("pw").append("=").append(pw);

					OutputStreamWriter outStream = new OutputStreamWriter(
							http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					outStream.close();

					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));

					for (;;) {
						line = br.readLine();

						if (line != null) {
							Log.e("tag", line);
							temp += line;
						}
						if (line == null)
							break;
					}

					if (temp.equals("1")) {
						loginFlag = true;
						Log.e("tag", "exi"); // toast, 페이지 전환
					} else {
						loginFlag = false;
						Log.e("tag", "no"); // toast ID 또는 PW워드가 틀립니다.
					}
					temp = "";
					br.close();
				}
				http.disconnect();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (loginFlag == false) {
				Toast.makeText(context, "ID 또는 PW가 틀립니다.", Toast.LENGTH_SHORT)
						.show();
			} else {
				//로그인 완료 후 화면 전환 전 sharedPreferences에 아이디 저장
				savePreferences(id);
				// login 완료 되면 FragmentBaseActivity로 화면 전환
				Intent intent = new Intent(context, FragmentBaseActivity.class);
				startActivity(intent);
			}
		}
	}

	public void onClickSignIn(View v) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
	}

	 // sharedPreference이용해서 값 저장하기
    private void savePreferences(String id){
        SharedPreferences idStorage = getSharedPreferences("idStorage", MODE_PRIVATE);
        SharedPreferences.Editor editor = idStorage.edit();
        editor.putString("id", id);
        editor.commit();
    }

}
