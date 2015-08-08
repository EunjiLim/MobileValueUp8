package com.example.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.sweet.R;

public class FragmentProfile extends Fragment{
	
	ListView groupMade;
	ListView groupJoined;
	IconTextListAdapter madeAdapter;
	IconTextListAdapter joinedAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        
        //만든 모임
        groupMade = (ListView) v
				.findViewById(R.id.ListView_groupMade);
        madeAdapter = new IconTextListAdapter(getActivity());
        madeAdapter.addItem(new IconTextItem( getResources().getDrawable(R.drawable.house_coloricon), "1", "1", "2", "1", "1"));
		groupMade.setAdapter(madeAdapter);
		
		
		//가입한 모임
		groupJoined = (ListView) v
				.findViewById(R.id.ListView_groupJoined);
		joinedAdapter = new IconTextListAdapter(getActivity());
		joinedAdapter.addItem(new IconTextItem( getResources().getDrawable(R.drawable.eat_coloricon), "1", "1", "2", "1", "1"));
		groupJoined.setAdapter(joinedAdapter);
        
        return v;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
    }
}