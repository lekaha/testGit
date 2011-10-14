package avermedia.com.ipd1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileBrowser extends ListActivity {
	
//	private static final String PATH = new String("/sdcard/sounds/Simple/");
	private List<String> medias;
	private List<String> paths;
	private String rootPath = "/";
	private String sdcardPath = "/sdcard";
	
	private TextView mPath;
	private int currentPosition = 0;
	
	MyAdapter myadp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fileslist);
        
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        mPath=(TextView)findViewById(R.id.path);
        mPath.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/WadaLabMaruGo2004ARIB.ttf"));
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        DisplayList(rootPath);
    }
    
    
    protected void onResume()
    {
    	Toast.makeText(FileBrowser.this, "DISPLAY LIST",Toast.LENGTH_SHORT).show();
    	DisplayList(rootPath);
    	super.onResume();
    }
    
    protected static final int MENU_BUTTON_1 = Menu.FIRST;
    //protected static final int MENU_BUTTON_2 = Menu.FIRST + 1;
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, MENU_BUTTON_1, 0, getString(R.string.config)).setIcon(R.drawable.config);
//        menu.add(0, MENU_BUTTON_2, 0, "按鈕2");
        return super.onCreateOptionsMenu(menu);
    }
 
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()) {
        	case MENU_BUTTON_1:
        		Intent config = new Intent(FileBrowser.this, ConfigurationList.class);
        		startActivityForResult(config, 0);
        		break;
        	default:
        		break;
        }
 
        return super.onOptionsItemSelected(item);
    }
    
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
            currentPosition = position;
            File file = new File(paths.get(currentPosition));
            
            Bundle bundle = new Bundle();
            
            if(file.isDirectory())
            {
            	DisplayList(paths.get(currentPosition));
            }
            else
            {
            	
            	bundle = new Bundle();
                bundle.putString("ITEM", paths.get(currentPosition));
                
                boolean isMP3 = false;
                MediaFilter mf = new MediaFilter();
                if(mf.accept(file, file.getName()))
                {
                	Toast.makeText(FileBrowser.this, "TEST: FILE's ENDWISH IS MP3.", Toast.LENGTH_LONG).show();
                	isMP3 = mf.accept(file, file.getName());
                }
                bundle.putBoolean("MP3", isMP3);
                
                
                Intent intent = new Intent(v.getContext(), MediaPlayerActivity.class);
                intent.putExtras(bundle);
                
                FileBrowser.this.setResult(RESULT_OK, intent);
                FileBrowser.this.finish();

                
            }
            
            
//            Toast.makeText(FileBrowser.this, PATH + medias.get(position),Toast.LENGTH_LONG).show();
             
    }
    
    private void DisplayList(String filePath)
    {
    	medias = new ArrayList<String>();
        paths = new ArrayList<String>();
        
    	mPath.setText("線 總 "+getString(R.string.path)+filePath);
        
    	File file = new File(filePath);
    	File [] files = file.listFiles();
    	if(!filePath.equals(rootPath))
    	{
    		medias.add("B1");
    		paths.add(rootPath);
    		medias.add("B2");
    		paths.add(file.getParent());
    	}
    	
    	
    	for (int i = 0; i<files.length; i++) {
    		File f = files[i];
    		medias.add(f.getName());
    		paths.add(f.getPath());
    		
    	}
	    	
    	Toast.makeText(FileBrowser.this, "DISPLAY LIST",Toast.LENGTH_LONG).show();
    	
    	myadp = new MyAdapter(this, medias, paths);
    	setListAdapter(myadp);
    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	switch(resultCode)
    	{
    	case RESULT_OK:
    		Bundle bd = data.getExtras();
    		Boolean fontFlag = bd.getBoolean("FONT");
    		
    		if(!fontFlag)
    		{
    			TextView text = (TextView)findViewById(R.id.text1);
    			text.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
    			
    			myadp.filename.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
    			Toast.makeText(FileBrowser.this, "CHANGE FONT: MONOSPACE "+fontFlag,Toast.LENGTH_LONG).show();
    		}
    		else
    		{
    			TextView text = (TextView)findViewById(R.id.text1);
    			text.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
    			myadp.filename.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
    			Toast.makeText(FileBrowser.this, "CHANGE FONT: DEFAULT "+fontFlag,Toast.LENGTH_LONG).show();
    		}
    		
    		
    		
    		break;
    	default:
    		break;
    	
    	}
    	

    	
    }
	
}
