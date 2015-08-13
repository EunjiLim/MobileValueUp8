package com.example.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import android.app.ProgressDialog;
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

import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.sweet.R;

public class FragmentProfile extends Fragment{
	
	private profile myProfile;
	
	TextView name, sex, birthday, KakaoID, phone;
	ListView groupMade;
	ListView groupJoined;
	IconTextListAdapter madeAdapter;
	IconTextListAdapter joinedAdapter;
	
	String myResult;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        
        name = (TextView) v.findViewById(R.id.TextView_nameResult);
        sex = (TextView) v.findViewById(R.id.TextView_SexResult);
        birthday = (TextView) v.findViewById(R.id.TextView_profileBirthdayResult);
        KakaoID = (TextView) v.findViewById(R.id.TextView_profileKakaoIDResult);
        phone = (TextView) v.findViewById(R.id.TextView_profilePhoneResult);
        
        //���� ����
        groupMade = (ListView) v
				.findViewById(R.id.ListView_groupMade);
        madeAdapter = new IconTextListAdapter(getActivity());
        madeAdapter.addItem(new IconTextItem( getResources().getDrawable(R.drawable.house_coloricon), "1", "1", "2", "1", "1","0"));
		groupMade.setAdapter(madeAdapter);
		
		
		//������ ����
		groupJoined = (ListView) v
				.findViewById(R.id.ListView_groupJoined);
		joinedAdapter = new IconTextListAdapter(getActivity());
		joinedAdapter.addItem(new IconTextItem( getResources().getDrawable(R.drawable.eat_coloricon), "1", "1", "2", "1", "1","1"));
		groupJoined.setAdapter(joinedAdapter);
        
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
			Log.i("profile","onPreExecute()");

		}

		@Override
		protected JSONObject doInBackground(String... args) {
			try {
				Log.i("profile","doInBackground����");
				JSONParser jParser = new JSONParser();
				
				URL myUrl = new URL("http://52.69.67.4/bangjang.php");
				HttpURLConnection http = (HttpURLConnection) myUrl
						.openConnection();
				
				if (http != null) {
					http.setUseCaches(false);
					http.setDoInput(true); // �������� �б� ��� ����
					http.setDoOutput(true); // ������ ���� ��� ����
					http.setRequestMethod("POST");
					http.setRequestProperty("charset", "UTF-8");
					
					StringBuffer buffer = new StringBuffer();
					buffer.append("id").append("=").append("1");

					OutputStreamWriter outStream = new OutputStreamWriter(
							http.getOutputStream());
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					outStream.close();

					//-------------------------
					//   �������� ���۹ޱ�
					//-------------------------
					BufferedReader br = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"UTF-8"));
					StringBuilder strBuilder = new StringBuilder();
					String str;
					//�������� ���δ����� �����ִ� ��Ʈ���� �д´�.
					while ((str = br.readLine()) != null) {
						Log.i("profile", str);
						strBuilder.append(str);
					}
					
					//���� ����� ���� string myResult�� ����
					myResult = strBuilder.toString();
					Log.i("profile", myResult);
					br.close();
				}
				http.disconnect();

				// �������� ���� string���� jsonObject�� �и��� ���� �Լ� ȣ��
				Log.i("profile", "$");
				JSONObject json = jParser.getJSONObject(myResult);
				Log.i("profile", json.toString());
				//Log.i("selected", json.toString());
				return json;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("profile","beforeReturnNull");
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
		}
	}
}