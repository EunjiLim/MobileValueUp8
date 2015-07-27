package com.example.searchtabs;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listview.IconTextItem;
import com.example.listview.IconTextListAdapter;
import com.example.sweet.R;

public class AccompanyTab extends Fragment{
	
	ListView listView1;
	IconTextListAdapter adapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_accompany, container, false);
        
        listView1 = (ListView) v.findViewById(R.id.listView_accompanyFragment);
        adapter = new IconTextListAdapter(getActivity());
        
        Resources res = getResources();
        
        
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
        
        return v;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
    }
    
    public void setData(String title, String description, String location, String date, int people){
    	Toast.makeText(getActivity().getApplicationContext(), ""+title+people, Toast.LENGTH_SHORT).show();
    }


}
