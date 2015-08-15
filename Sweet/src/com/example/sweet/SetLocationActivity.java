package com.example.sweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/***********************************
 * 구글 플레이 서비스 라이브러리를 이용해서 
 * 구글 지도에서 선택한 위치의 위도와 경도를 얻어온다.
 * @author Eunji
 *
 */
public class SetLocationActivity extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_set_location);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.set_location, menu);
      return true;
   }
   
   public void seoulBtn(View v){
      Intent resultIntent = new Intent();
      resultIntent.putExtra("lat", 37.5666102);
      resultIntent.putExtra("lon", 126.9783881);
      setResult(1, resultIntent);
      finish();
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
}