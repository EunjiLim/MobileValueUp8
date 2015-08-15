package com.example.sweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/***********************************
 * 구글 플레이 서비스 라이브러리를 이용해서 
 * 구글 지도에서 선택한 위치의 위도와 경도를 얻어온다.
 * @author Eunji
 *
 */
public class SetLocationActivity extends ActionBarActivity {
	
	ActionBar mActionBar;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_set_location);
      
      mActionBar= getSupportActionBar();
      mActionBar.setTitle("지역 선택");
   }

   
   public void seoulBtn(View v){
      Intent resultIntent = new Intent();
      resultIntent.putExtra("lat", 37.5666102);
      resultIntent.putExtra("lon", 126.9783881);
      setResult(1, resultIntent);
      finish();
   }

   public void incheonBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 37.450001);
	      resultIntent.putExtra("lon", 126.699997);
	      setResult(1, resultIntent);
	      finish();
	   }

   public void daejeonBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 36.349998);
	      resultIntent.putExtra("lon", 127.383331);
	      setResult(1, resultIntent);
	      finish();
	   }

   public void daeguBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 35.866669);
	      resultIntent.putExtra("lon", 128.600006);
	      setResult(1, resultIntent);
	      finish();
	   }

   public void ulsanBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 35.533333);
	      resultIntent.putExtra("lon", 129.316666);
	      setResult(1, resultIntent);
	      finish();
	   }

   public void busanBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 35.166668);
	      resultIntent.putExtra("lon", 129.066666);
	      setResult(1, resultIntent);
	      finish();
	   }

   public void kwangjuBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 35.150002);
	      resultIntent.putExtra("lon", 126.849998);
	      setResult(1, resultIntent);
	      finish();
	   }

   public void KyeongBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 37.41379999999999);
	      resultIntent.putExtra("lon", 127.51829999999995);
	      setResult(2, resultIntent);
	      finish();
	   }

   public void KangBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 37.8228);
	      resultIntent.putExtra("lon", 128.15549999999996);
	      setResult(2, resultIntent);
	      finish();
	   }

   public void JNorthBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 35.71750000000001);
	      resultIntent.putExtra("lon", 127.15300000000002);
	      setResult(2, resultIntent);
	      finish();
	   }

   public void GNorthBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 36.4919);
	      resultIntent.putExtra("lon", 128.88890000000004);
	      setResult(2, resultIntent);
	      finish();
	   }

   public void GSouthBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 35.4606);
	      resultIntent.putExtra("lon", 128.21320000000003);
	      setResult(2, resultIntent);
	      finish();
	   }

   public void CNorthBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 36.8);
	      resultIntent.putExtra("lon", 127.70000000000005);
	      setResult(1, resultIntent);
	      finish();
	   }

   public void CSouthBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 36.5184);
	      resultIntent.putExtra("lon", 126.79999999999995);
	      setResult(2, resultIntent);
	      finish();
	   }

   public void JSouthBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 34.8679);
	      resultIntent.putExtra("lon", 126.99099999999999);
	      setResult(2, resultIntent);
	      finish();
	   }
   
   public void JejuBtn(View v){
	      Intent resultIntent = new Intent();
	      resultIntent.putExtra("lat", 33.4890113);
	      resultIntent.putExtra("lon", 126.49830229999998);
	      setResult(2, resultIntent);
	      finish();
	   }
}