package avermedia.com.ipd1;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ConfigurationList extends ListActivity {
	
	private ListView lv;
	private TextView sample;
	
	private ArrayList<HashMap<String, Object>>   listItems;
	private SimpleAdapter listItemAdapter;
	private DisplayMetrics dm;
	private Button chg_fnt;
	private Button chg_save;
	private Button chg_back;
	
	
	
	private static boolean flag = false;
	
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.configlist);
	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);
	  
	  dm = getResources().getDisplayMetrics();	//Get screen's information
	  int ScreenWidth = dm.widthPixels;	  
	  int ScreenHeight = dm.heightPixels;

	  sample = (TextView)findViewById(R.id.sample);
	  
	  chg_fnt = (Button) findViewById(R.id.btn_font);
	  chg_save = (Button) findViewById(R.id.btn_save);
	  chg_save.setWidth(ScreenWidth/2);
	  chg_back = (Button) findViewById(R.id.btn_back);
	  chg_back.setWidth(ScreenWidth/2);
	  
//	  setListAdapter(new ArrayAdapter<String>(this, R.layout.configltem, COUNTRIES));
//	  this.setListAdapter(listItemAdapter);
	  
	  
	  chg_fnt.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			
			
			if(!flag)
			{
				sample.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
				bundle.putBoolean("FONT", flag);
				flag = true;
			}
			else
			{
				sample.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
				bundle.putBoolean("FONT", flag);
				flag = false;
			}
			
			Intent config = new Intent(v.getContext(), FileBrowser.class);
			config.putExtras(bundle);
			
			ConfigurationList.this.setResult(RESULT_OK, config);
			ConfigurationList.this.finish();
			
			
		}});
	  
	  
//	  lv = (ListView)findViewById(android.R.id.list);
	  
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.configltem));
//	  lv.setTextFilterEnabled(true);

//	  lv.setOnItemClickListener(new OnItemClickListener() {
//	    
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int postion,long id) {
//			// TODO Auto-generated method stub
//			Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
//			          Toast.LENGTH_SHORT).show();
//		}
//	  });
	}
	
	@Override
	    protected void onListItemClick(ListView l, View v, int position, long id)  {
	        // TODO Auto-generated method stub
	        Log.e("position", "" + position);
	        setTitle("你点击第"+position+"行");
	    }  
	
	private void initConfigList()
	{
//		listItems = new ArrayList<HashMap<String, Object>>();
//		//for(int i=0;i<10;i++)    
//		{   
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            
//            map.put("ConfigName", "Font： ");
//            map.put("Config", R.id.spinner1);
//            
//            listItems.add(map);   
//        } 
		
//		
//        //生成适配器的Item和动态数组对应的元素   
//        listItemAdapter = new SimpleAdapter(this,listItems,   // listItems数据源    
//                R.layout.configltem,  //ListItem的XML布局实现  
//                new String[] {"ConfigName", "Config"},     //动态数组与ImageItem对应的子项         
//                new int[ ] {R.id.cfigname, R.id.spinner1}      //list_item.xml布局文件里面的一个ImageView的ID,一个TextView 的ID  
//        );
//		
	}

}
