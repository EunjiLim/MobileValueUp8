package com.example.fragment;

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

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.commentlistview.CommentItem;
import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.sweet.R;

public class FragmentProfile extends Fragment {

	private profile myProfile;

	TextView mID, mSex, mBirthday, mKakaoID, mPhone;
	ListView groupMade;
	ListView groupJoined;
	IconTextListAdapter madeAdapter;
	IconTextListAdapter joinedAdapter;

	String myResult;

	/*******************************************
	 * TAG
	 *******************************************/
	private static final String TAG_PHONE = "PHONE";
	private static final String TAG_ID = "ID";
	private static final String TAG_KAKAOID = "FACEBOOK";
	private static final String TAG_BIRTHDAY = "BIRTHDAY";
	private static final String TAG_SEX = "SEX";

	private static final String TAG_NO = "NO";
	private static final String TAG_CATEGORY = "category";
	private static final String TAG_TITLE = "title";
	private static final String TAG_LOCATION = "location";
	private static final String TAG_DATE = "date";
	private static final String TAG_PEOPLE = "people";
	private static final String TAG_CURRENT = "current";

	/********************************************
	 * JSON Node NAmes TAG_OS : 내 프로필 TAG_BANGjANG : 내가 만든 모임 목록 TAG_JOIN : 내가
	 * 가입한 모임
	 *******************************************/
	private static final String TAG_OS = "board";
	private static final String TAG_OS2 = "bangjang";
	private static final String TAG_JOIN = "join";

	JSONArray boardArray = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_profile, container, false);

		mID = (TextView) v.findViewById(R.id.TextView_IDResult);
		mSex = (TextView) v.findViewById(R.id.TextView_SexResult);
		mBirthday = (TextView) v.findViewById(R.id.TextView_profileBirthdayResult);
		mKakaoID = (TextView) v.findViewById(R.id.TextView_profileKakaoIDResult);
		mPhone = (TextView) v.findViewById(R.id.TextView_profilePhoneResult);

		// 만든 모임
		groupMade = (ListView) v.findViewById(R.id.ListView_groupMade);
		madeAdapter = new IconTextListAdapter(getActivity());
		/*madeAdapter.addItem(new IconTextItem(getResources().getDrawable(
				R.drawable.house_coloricon), "1", "1", "2", "1", "1", "0"));
		groupMade.setAdapter(madeAdapter);*/

		// 가입한 모임
		groupJoined = (ListView) v.findViewById(R.id.ListView_groupJoined);
		joinedAdapter = new IconTextListAdapter(getActivity());
		/*joinedAdapter.addItem(new IconTextItem(getResources().getDrawable(
				R.drawable.eat_coloricon), "1", "1", "2", "1", "1", "1"));
		groupJoined.setAdapter(joinedAdapter);*/

		myProfile = new profile();
		myProfile.execute();

		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	private class profile extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("profile", "onPreExecute()");

		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				Log.i("profile", "doInBackground시작");
				JSONParser jParser = new JSONParser();

				URL myUrl = new URL("http://52.69.67.4/bangjang.php");
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();

				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // 서버에서 읽기 모드 지정
					http.setDoOutput(true); // 서버로 쓰기 모드 지정
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");

					StringBuffer buffer = new StringBuffer();
					String myID = getPreferences();
					Log.i("profile", myID);
					buffer.append("id").append("=").append(myID);

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
					StringBuilder strBuilder = new StringBuilder();
					String str;
					// 서버에서 라인단위로 보내주는 스트링을 읽는다.
					while ((str = br.readLine()) != null) {
						Log.i("profile", str);
						strBuilder.append(str);
					}

					// 수신 결과를 전역 string myResult에 저장
					myResult = strBuilder.toString();
					Log.i("profile", myResult);
					br.close();
				}
				http.disconnect();

				// 서버에서 받은 string에서 jsonObject를 분리해 내는 함수 호출
				Log.i("profile", "$");
				JSONObject json = jParser.getJSONObject(myResult);
				Log.i("profile", json.toString());
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
			try {
				/*********************************
				 * 내 프로필 가져오기
				 *********************************/
				String id, phone, kakaoid, birthday, sex;
				boardArray = json.getJSONArray(TAG_OS);

				Log.i("profile", "boardArray.length: " + boardArray.length());
				// ID, PHONE, KAKAOID, BIRTHDAY, SEX
				for (int i = 0; i < boardArray.length(); i++) {
					
					Log.i("profile", "for문 안에 들어왔음");
					JSONObject c = boardArray.getJSONObject(i);
					Log.i("selected", "hmm...");
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

				/*********************************
				 * 내가 만든 모임 정보 가져오기
				 *********************************/
				boardArray = json.getJSONArray(TAG_OS2);
				Resources res = getResources();
				String no, id2, category, title, location, date, people, current;
				Log.i("selected", "내가 만든 모임 정보 가져오기");
				for (int i = 0; i < boardArray.length(); i++) {
					
					Log.i("profile", "내가 만든 모임 정보 for문");
					JSONObject c = boardArray.getJSONObject(i);
					no = c.getString(TAG_NO);
					id2 = c.getString(TAG_ID);
					category = c.getString(TAG_CATEGORY);
					title = c.getString(TAG_TITLE);
					location = c.getString(TAG_LOCATION);
					date = c.getString(TAG_DATE);
					people = c.getString(TAG_PEOPLE);
					current = c.getString(TAG_CURRENT);
					Log.i("profile", category);
					// 카테고리별 게시판 아이콘 적용
					if (category.equals("숙박")) {
						Log.i("profile", "숙박일때");
						madeAdapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.house_coloricon),
								title, current, location, date, people, no));
						groupMade.setAdapter(madeAdapter);
					} else if (category.equals("레저")) {
						madeAdapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.leisure_coloricon),
								title, current, location, date, people, no));
						groupMade.setAdapter(madeAdapter);
					} else if (category.equals("식사")) {
						madeAdapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.eat_coloricon), title,
								current, location, date, people, no));
						groupMade.setAdapter(madeAdapter);
					} else {
						madeAdapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.with_coloricon), title,
								current, location, date, people, no));
						groupMade.setAdapter(madeAdapter);
					}
				}
				
				/*********************************
				 * 내가 가입한 모임 가져오기
				 *********************************/
				boardArray = json.getJSONArray(TAG_JOIN);
				Log.i("profile", "내가 가입한 모임 정보 가져오기");
				for (int i = 0; i < boardArray.length(); i++) {
					
					Log.i("profile", "내가 가입한 모임 정보 가져오기 for문");
					JSONObject c = boardArray.getJSONObject(i);
					no = c.getString(TAG_NO);
					id2 = c.getString(TAG_ID);
					category = c.getString(TAG_CATEGORY);
					title = c.getString(TAG_TITLE);
					location = c.getString(TAG_LOCATION);
					date = c.getString(TAG_DATE);
					people = c.getString(TAG_PEOPLE);
					current = c.getString(TAG_CURRENT);
					Log.i("profile", category);
					// 카테고리별 게시판 아이콘 적용
					if (category.equals("숙박")) {
						Log.i("profile", "숙박일때");
						madeAdapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.house_coloricon),
								title, current, location, date, people, no));
						groupMade.setAdapter(madeAdapter);
					} else if (category.equals("레저")) {
						madeAdapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.leisure_coloricon),
								title, current, location, date, people, no));
						groupMade.setAdapter(madeAdapter);
					} else if (category.equals("식사")) {
						madeAdapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.eat_coloricon), title,
								current, location, date, people, no));
						groupMade.setAdapter(madeAdapter);
					} else {
						madeAdapter.addItem(new IconTextItem(res
								.getDrawable(R.drawable.with_coloricon), title,
								current, location, date, people, no));
						groupMade.setAdapter(madeAdapter);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**************************
	 * SharedPreferences 이용 로그인 시 사용했던 아이디를 받아온다.
	 * 
	 * @return id
	 **************************/
	public String getPreferences() {
		SharedPreferences pref = getActivity().getSharedPreferences("idStorage", getActivity().MODE_PRIVATE);
		return pref.getString("id", "");
	}
}