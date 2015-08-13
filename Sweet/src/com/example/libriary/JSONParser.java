package com.example.libriary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 
/*****************************************
 * 
 * URL로부터 JSON 데이터를 받고 JSON Object를 return
 * 해주는 JSONParser class를 생성
 * [] 은 JSON형식에서 배열인데 이것도 객체로 받을 수 있는지가 의문임.
 */
public class JSONParser {
	 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JSONParser() {
 
    }
 
    public JSONObject getJSONFromUrl(String url) {
 
        // Making HTTP request
        try {
        	Log.i("TAG","JSONObject 1");
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            
        	Log.i("TAG","JSONObject 2");
            HttpPost httpPost = new HttpPost(url);
            
        	Log.i("TAG","JSONObject 3");
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            
        	Log.i("TAG","JSONObject 4");
            HttpEntity httpEntity = httpResponse.getEntity();
            
        	Log.i("TAG","JSONObject 5");
            is = httpEntity.getContent();
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
        	Log.i("TAG","JSONObject 6");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "utf-8"), 8);
            
        	Log.i("TAG","JSONObject 7");
            StringBuilder sb = new StringBuilder();
        	Log.i("TAG","JSONObject 8");
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        	Log.i("TAG","JSONObject 9");
            is.close();
            json = sb.toString();
            //////////////////////
            String firstS = "{\"board\":";
            json = firstS + json + "}";
            
            Log.i("TAG", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
        	Log.i("TAG","JSONObject 10");
            jObj = new JSONObject(json);
        	Log.i("TAG","JSONObject 11");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
    	Log.i("TAG","JSONObject 12");
        // return JSON String
        return jObj;
    }
    
    public JSONObject getJSONObject(String str){
    	json = "{\"board\":" + str + "}";
    	Log.i("profile", json);
    	try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			e.printStackTrace();
		}
  
    	return jObj;
    	
    }
}
