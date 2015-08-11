package com.example.sweet;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.commentlistview.CommentItem;
import com.example.commentlistview.CommentListAdapter;

public class GroupSelectedActivity extends Activity {

	
	ListView commentList;
	CommentListAdapter adapter;
	EditText comment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_selected);
		
		comment = (EditText) findViewById(R.id.EditText_comment);
		commentList = (ListView) findViewById(R.id.ListView_comment);
		adapter = new CommentListAdapter(this);
		Resources res = getResources();
		adapter.addItem(new CommentItem(res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		adapter.addItem(new CommentItem(res.getDrawable(R.drawable.profileicon), "이름", "내용내용"));
		commentList.setAdapter(adapter);
	}
	
	public void setCommentBtn(View v){
		Toast.makeText(getApplicationContext(), comment.getText().toString(), Toast.LENGTH_SHORT).show();;
	}
	
	public void joinBtn(View v){
		Toast.makeText(getApplicationContext(), "가입되었습니다.", 1500).show();
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
}
