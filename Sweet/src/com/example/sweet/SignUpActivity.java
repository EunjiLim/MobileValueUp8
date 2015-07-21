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
	
	//ID Duplication check�� ���� ����
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
					http.setDoInput(true); // �������� �б� ��� ����
					http.setDoOutput(true); // ������ ���� ��� ����
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					// ����Ǿ��� �ڵ尡 ���ϵǸ�.

					id = ID.getText().toString();
					pw = PW.getText().toString();
					name = NAME.getText().toString();
					birthday = BIRTHDAY.getText().toString();
					sex = SEX.getText().toString();
					phone = PHONE.getText().toString();
					facebook = FACEBOOK.getText().toString();

					// php������ ���� �����ϰ� buffer�� �մ� ����
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
			// ȸ������ �Ϸ� �Ǹ� LoginActivity�� ȭ�� ��ȯ
			Toast.makeText(context, "ȸ�������� �Ϸ�Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
		}
	}

	// ���̵� �ߺ� Ȯ��
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
					http.setDoInput(true); // �������� �б� ��� ����
					http.setDoOutput(true); // ������ ���� ��� ����
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					// ����Ǿ��� �ڵ尡 ���ϵǸ�.

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

					// �����κ��� �ߺ� Ȯ�� ��� �� �ޱ�
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
						Log.e("tag", "�̹� ID�� �����մϴ�."); 
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
				Toast.makeText(context, "����� �� �ִ� ID�Դϴ�.", 0).show();
			} else {
				Log.e("tag", "onPostExecute2");
				Toast.makeText(SignUpActivity.this, "�̹� �����ϴ� ID�Դϴ�.", 0).show();
			}
		}
	}
}
