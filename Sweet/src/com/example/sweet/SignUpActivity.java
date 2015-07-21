package com.example.sweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	private MyAsyncTask myAsyncTask;
	private checkup checking;
	EditText ID, PW;
	EditText NAME, BIRTHDAY, SEX, PHONE, FACEBOOK;
	Button finish, duplicationCheck;
	String id, pw, name, birthday, sex, phone, facebook;
	String temp1 = "", temp2 = "";
	String line = "";

	// context for toast.maketext
	private Context context;
	
	//ID Duplication check을 위한 변수
	boolean checkPoint=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		ID = (EditText) findViewById(R.id.EditText_id);
		PW = (EditText) findViewById(R.id.EditText_password);
		NAME = (EditText) findViewById(R.id.EditText_name);
		BIRTHDAY = (EditText) findViewById(R.id.EditText_dateOfBirth);
		SEX = (EditText) findViewById(R.id.EditText_sex);
		PHONE = (EditText) findViewById(R.id.EditText_cellNumber);
		FACEBOOK = (EditText) findViewById(R.id.EditText_facebookId);
		finish = (Button) findViewById(R.id.Button_finishSignUp);
		duplicationCheck = (Button) findViewById(R.id.Button_duplicationCheck);

		context = this;

		duplicationCheck.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checking = new checkup();
				checking.execute();
			}
		});

		finish.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				myAsyncTask = new MyAsyncTask();
				myAsyncTask.execute();
			}
		});
	}

	public class MyAsyncTask extends AsyncTask<String, Void, String> {
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... params) {
			try {
				URL myUrl = new URL("http://52.69.67.4/signup.php");
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					// 연결되었음 코드가 리턴되면.

					id = ID.getText().toString();
					pw = PW.getText().toString();
					name = NAME.getText().toString();
					birthday = BIRTHDAY.getText().toString();
					sex = SEX.getText().toString();
					phone = PHONE.getText().toString();
					facebook = FACEBOOK.getText().toString();

					// php변수에 값을 대입하고 buffer에 잇는 과정
					StringBuffer buffer = new StringBuffer();
					buffer.append("id").append("=").append(id).append("&");
					buffer.append("pw").append("=").append(pw).append("&");
					buffer.append("name").append("=").append(name).append("&");
					buffer.append("birthday").append("=").append(birthday)
							.append("&");
					buffer.append("sex").append("=").append(sex).append("&");
					buffer.append("phone").append("=").append(phone)
							.append("&");
					buffer.append("facebook").append("=").append(facebook);
					Log.e("tag", buffer.toString());

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

						String line = br.readLine();

						temp1 += line;

						if (line == null)
							break;
					}

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
			// 회원가입 완료 되면 LoginActivity로 화면 전환
			Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
		}
	}

	// 아이디 중복 확인
	public class checkup extends AsyncTask<String, Void, String> {

		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... params) {
			try {
				URL myUrl = new URL("http://52.69.67.4/checking.php");
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					// 연결되었음 코드가 리턴되면.

					id = ID.getText().toString();

					StringBuffer buffer = new StringBuffer();
					buffer.append("id").append("=").append(id).append("");

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

					// 서버로부터 중복 확인 결과 값 받기
					for (;;) {
						line = br.readLine();

						if (line != null) {
							Log.e("tag", line);
							temp2 += line;
						}
						if (line == null)
							break;
					}

					if (temp2.equals("1")) {
						checkPoint = false;
						Log.e("tag", "이미 ID가 존재합니다."); 
					} else {
						checkPoint = true;
						Log.e("tag", "you can use the ID"); 
					}
					temp2 = "";
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
			if (checkPoint == true) {
				Log.e("tag", "onPostExecute1");
				Toast.makeText(context, "사용할 수 있는 ID입니다.", 0).show();
			} else {
				Log.e("tag", "onPostExecute2");
				Toast.makeText(SignUpActivity.this, "이미 존재하는 ID입니다.", 0).show();
			}
		}
	}
}
