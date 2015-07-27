package com.example.searchtabs;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.libriary.JSONParser;
import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.sweet.R;

public class LeisureTab extends Fragment{
	
	ListView listView1;
	IconTextListAdapter adapter;
	
	// 데이터를 읽어올 때 사용할 변수
	ArrayList<HashMap<String, String>> boardlist = new ArrayList<HashMap<String, String>>();

	// 상세검색 버튼
	Button descriptionButton;

	// 게시판 리스트 불러올 url
	private static String url = "http://52.69.67.4/leisure.php";

	JSONArray boardArray = null;

	// JSON Node Names
	private static final String TAG_OS = "board";

	private static final String TAG_ID = "ID";
	private static final String TAG_TITLE = "title";
	private static final String TAG_LOCATION = "location";
	private static final String TAG_DATE = "date";
	private static final String TAG_PEOPLE = "people";

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_leisure, container, false);
        
        listView1 = (ListView) v.findViewById(R.id.listView_leisureFragment);
        adapter = new IconTextListAdapter(getActivity());
        
        //리스트뷰에 어댑더 설정
        listView1.setAdapter(adapter);
        
        //새로 정의한 리스너로 객체를 만들어 설정
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IconTextItem curItem = (IconTextItem) adapter.getItem(position);
                String[] curData = curItem.getData();

                Toast.makeText(getActivity().getApplicationContext(), "Selected : " + curData[0], Toast.LENGTH_LONG).show();

            }
        });

		new JSONParse().execute();
        
        return v;
    }
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected JSONObject doInBackground(String... args) {

			Log.i("TAG", "#");
			JSONParser jParser = new JSONParser();

			// Getting JSON from URL
			Log.i("TAG", "$");
			JSONObject json = jParser.getJSONFromUrl(url);
			Log.i("TAG", "%");
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			try {
				Log.i("TAG", "onPostExecute시작");

				// Getting JSON Array from URL
				boardArray = json.getJSONArray(TAG_OS);

				for (int i = 0; i < boardArray.length(); i++) {
					JSONObject c = boardArray.getJSONObject(i);

					// Storing JSON item in a Variable
					String id = c.getString(TAG_ID);
					String title = c.getString(TAG_TITLE);
					String location = c.getString(TAG_LOCATION);
					String date = c.getString(TAG_DATE);
					String people = c.getString(TAG_PEOPLE);
					// Adding value HashMap key => value

					/*
					 * HashMap<String, String> map = new HashMap<String,
					 * String>();
					 * 
					 * map.put(TAG_ID, id); map.put(TAG_TITLE, title);
					 * map.put(TAG_DATE, date); map.put(TAG_PEOPLE, people);
					 */

					/*
					 * Log.i("TAG", "*"); boardlist.add(map);
					 */
					/*
					 * adapter = new SimpleAdapter(getActivity(), boardlist,
					 * R.layout.list_v, new String[] { TAG_VER,TAG_NAME, TAG_API
					 * }, new int[] { R.id.vers,R.id.name, R.id.api});
					 */

					Resources res = getResources();

					Log.i("TAG", "*");
					adapter.addItem(new IconTextItem(res
							.getDrawable(R.drawable.profileicon), title, "1",
							location, date, people));
					listView1.setAdapter(adapter);
					Log.i("TAG", "(");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

		//리스트 뷰 항목 클릭했을 때
		listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						"You Clicked",0).show();

			}
		});
		
    }
    
    public void setData(String title, String description, String location, String date, int people){
    	Toast.makeText(getActivity().getApplicationContext(), ""+title+people, Toast.LENGTH_SHORT).show();
    }

}
