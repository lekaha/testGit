package avermedia.com.ipd1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;

import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MediaPlayerActivity extends Activity implements MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener{
	
	private ImageButton mList;
	private EditText mPath;
	private ImageButton mbackward;
	private ImageButton mforward;
	private ImageButton mPlay;
	private ImageButton mPause;
	private ImageButton mStop;
	private LinearLayout mlayout;
	
	
	private MediaPlayer mp = null;
	
	CustomVideoView mVideoView;
	private DisplayMetrics dm;
	private int lastX , lastY;
	private int ScreenWidth , ScreenHeight;
	private String currentPath;
	private int mPositionWhenPaused = -1;
	private OnCompletionListener onCL;
	private TextView mtext;
    
	
	
	private static boolean IS_PLAYING = false;
	private static boolean IS_PAUSING = false;
	private static boolean IS_CURRENT_PATH = true;
	private static boolean IS_AUDIO_PATH = false;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //hide notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        mVideoView = (CustomVideoView) findViewById(R.id.VideoView);
        dm = getResources().getDisplayMetrics();	//Get screen's information
        ScreenWidth = dm.widthPixels;	  
        ScreenHeight = dm.heightPixels; 
        
        mtext = (TextView)findViewById(R.id.ja);
        
        
        ArrayList<String> partFile = new ArrayList();
        partFile.add("fonts/tmp1.dat");
        partFile.add("fonts/tmp2.dat");
        partFile.add("fonts/tmp3.dat");
        partFile.add("fonts/tmp4.dat");
        partFile.add("fonts/tmp5.dat");
        partFile.add("fonts/tmp6.dat");
        
        mergeAssetsFile("/sdcard/avermedia/wada.ttf",partFile);
        
        
        
//        String fontName = resFont.getString(R.styleable.UnicodeTextView_font);
        Toast.makeText(MediaPlayerActivity.this, this.getDir("fonts", 0).toString(), Toast.LENGTH_LONG).show();
//        mtext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/wada.ttf"));
        mtext.setTypeface(Typeface.createFromFile("/sdcard/avermedia/wada.ttf"));
        mtext.setText("線 總 "+getString(R.string.path));
        
        mlayout = (LinearLayout) findViewById(R.id.layout2);
        mlayout.setPadding((int)(ScreenWidth*0.05), 10, 0, 0);
        
        mList = (ImageButton) findViewById(R.id.btn_list);
        mList.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				mVideoView.stopPlayback();
				mVideoView.pause();
				IS_PAUSING = true;
				IS_PLAYING = false;
				
				getFilePath(v);
				
			}
        	
        });

        onCL = new OnCompletionListener()
        {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
				mp = null;
			}
        	
        };
        
        
        
        mbackward = (ImageButton) findViewById(R.id.back);
		mbackward.getBackground().setAlpha(120);
		mbackward.setEnabled(false);
		mforward = (ImageButton) findViewById(R.id.forward);
		mforward.getBackground().setAlpha(120);
		mforward.setEnabled(false);
		mStop = (ImageButton) findViewById(R.id.stop);
		mStop.getBackground().setAlpha(100);
		mStop.setKeepScreenOn(true);
		mPause = (ImageButton) findViewById(R.id.pause);
		mPause.getBackground().setAlpha(100);
		mPause.setKeepScreenOn(true);
		mPlay = (ImageButton) findViewById(R.id.play);
		mPlay.getBackground().setAlpha(100);
		mPlay.setKeepScreenOn(true);

		  
        MediaController controller = new MediaController(this, false);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);
//        mVideoView.setVideoPath("/sdcard/DCIM/Camera/VID_20111007_152037.3gp");
        
//        mVideoView.requestFocus();
//        mVideoView.start();
        
        
        mPath = (EditText) findViewById(R.id.edt1);        
		currentPath = mPath.getText().toString();
		
        mPath.setOnTouchListener(new OnTouchListener()
        {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mPath.selectAll();
//				mPath.requestFocus();
				return false;
			}
        	
        });
        mPath.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPath.selectAll();
			}
        	
        });
        

        mPlay.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				
				String pathValue = currentPath = MediaPlayerActivity.this.getString(R.string.edit_path);
//				Toast.makeText(MediaPlayerActivity.this, "TEST: pathValue = " + currentPath, Toast.LENGTH_LONG).show();
				
				
				if(pathValue.intern().equalsIgnoreCase((String) mPath.getText().toString()))
				{
//					Toast.makeText(MediaPlayerActivity.this, "TEST: pathValue = " + pathValue, Toast.LENGTH_LONG).show();
					//mPath.requestFocus();
					IS_CURRENT_PATH = false;
					mPath.requestFocus();
					mPath.selectAll();
				}
				else
				{
					if(!IS_AUDIO_PATH)
					{
						if(!IS_PAUSING)
						{
							IS_CURRENT_PATH = true;
							currentPath =  mPath.getText().toString();
//							Toast.makeText(MediaPlayerActivity.this, "TEST: mPath = " + currentPath, Toast.LENGTH_LONG).show();
							if(!IS_PLAYING)
							{
//								mVideoView.setVideoPath("http://10.1.9.197/stream/prog_index.m3u8");
//								Uri video = Uri.parse("http://10.1.9.197/stream/prog_index.m3u8");
//								mVideoView.setVideoURI(video);
//								mVideoView.requestFocus();
//								mVideoView.start();
								try {
									mVideoView.setVideoPath(getDataSource(currentPath).intern());
									mVideoView.requestFocus();
									mVideoView.start();
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							IS_PLAYING = true;
							mVideoView.start();
							mVideoView.start();
//							onStart();
						}
						else
						{
							IS_PAUSING = false;
							IS_PLAYING = true;
							Toast.makeText(MediaPlayerActivity.this, "TEST: Play ", Toast.LENGTH_LONG).show();
//							onResume();
							mVideoView.start();
					}
					}
					else
					{
						if(mp == null)
						{
							currentPath =  mPath.getText().toString();
							
							mp = new MediaPlayer();
							mp.setOnCompletionListener(onCL);
							
							try {
								mp.setDataSource(currentPath);
								mp.prepare();
								mp.start();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
						{
							mp.start();
						}
						
					}
					
				}
				
			}
		});
        
        
        
        
        
        mPause.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if(!IS_AUDIO_PATH)
				{
				
					IS_PAUSING = true;
					IS_PLAYING = false;
					mPositionWhenPaused = mVideoView.getCurrentPosition();
					Toast.makeText(MediaPlayerActivity.this, "TEST: Pause ", Toast.LENGTH_LONG).show();
			    	mVideoView.pause();
//					onPause();
				
//				mVideoView.pause();
				}
				else
				{
					if(mp.isPlaying())
						mp.pause();
					else
						mp.start();
//					mp.getCurrentPosition();
				}
				
			}
		});

		mStop.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if(!IS_AUDIO_PATH)
				{
					mVideoView.stopPlayback();
					IS_PLAYING = false;
					IS_PAUSING = false;
				}
				else
				{
					mp.stop();
					mp.release();
					mp = null;
				}
				
			}
		});
	    
		
		
		
    }
    
    
    private void mergeAssetsFile(String path, ArrayList<String> partFileList)
    {
    	if(!new File(path).exists())
    	{
    		try {
				OutputStream out = new FileOutputStream(path);
				byte[] buf = new byte[1024];
				
				InputStream in;
				int readLen = 0;
				
				for(int i=0; i<partFileList.size(); i++)
				{
					in = getAssets().open(partFileList.get(i));
					
					while((readLen = in.read(buf)) != -1)
					{
						out.write(buf, 0, readLen);
					}
					out.flush();
					in.close();
				}
				
				
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
    	}
    }
    
    private void saveRecentPlayList()
    {
    	
    }
    
    
    private void getFilePath(View v)
    {
    	Intent browser = new Intent(v.getContext(), FileBrowser.class);
		startActivityForResult(browser, 0);
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	switch(resultCode)
    	{
    	case RESULT_OK:
    		Bundle bd = data.getExtras();
    		String itemPath = bd.getString("ITEM");
    		mPath.setText(itemPath);
    		
    		if(itemPath.equalsIgnoreCase(currentPath.intern()))
    		{
    			IS_PAUSING = true;
    			IS_PLAYING = false;
    			Toast.makeText(MediaPlayerActivity.this, "TEST: Same Path ", Toast.LENGTH_LONG).show();
    			mVideoView.start();
    			
    		}
    		else
    		{
    			IS_PAUSING = false;
    			IS_PLAYING = false;
    			Toast.makeText(MediaPlayerActivity.this, "TEST: Different Path ", Toast.LENGTH_LONG).show();
    			mVideoView.stopPlayback();
    		}
    		
    		currentPath = itemPath;
    		
    		IS_AUDIO_PATH = bd.getBoolean("MP3");
    		IS_CURRENT_PATH = true;
    		
    		break;
    	default:
    		break;
    	
    	}
    	
    	
    	
//    	Toast.makeText(MediaPlayerActivity.this, requestCode + resultCode + " TEST",
//				Toast.LENGTH_LONG).show();
    	
    }
    
	
	private String getDataSource(String path) throws IOException {
		if (!URLUtil.isNetworkUrl(path) ) {
			
			Toast.makeText(MediaPlayerActivity.this, "TEST:" + path, Toast.LENGTH_LONG).show();
			return path;
			
		} else 
		{
			
			Toast.makeText(MediaPlayerActivity.this, "TEST: URL = " + path, Toast.LENGTH_LONG).show();
			Log.d("[TestMediaPlayer]", "Open Connection by URL");
			
			
			URL url = new URL("http://10.1.9.197/stream/prog_index.m3u8");
//			URLConnection cn = url.openConnection();
//			cn.connect();
			HttpURLConnection cn = (HttpURLConnection)url.openConnection();
			cn.connect();
			
			InputStream stream = cn.getInputStream();
			if (stream == null)
				throw new RuntimeException("stream is null");
			
			File temp = File.createTempFile("mediaplayertmp", "dat");
			temp.deleteOnExit();
			
			String tempPath = temp.getAbsolutePath();
			FileOutputStream out = new FileOutputStream(temp);
			
			byte buf[] = new byte[128];
			do {
				int numread = stream.read(buf);
				if (numread <= 0)
					break;
				out.write(buf, 0, numread);
			} while (true);
			try {
				stream.close();
			} catch (IOException ex) {
				Log.e("[ERROR]", "error: " + ex.getMessage(), ex);
			}
			
			Toast.makeText(MediaPlayerActivity.this, "TEST: URL = " + path, Toast.LENGTH_LONG).show();
			
			return tempPath;
		}
	}

    public void onStart() {
    	
//    	mVideoView.start();
    	
    	super.onStart();
    }
    
    public void onPause() {
    	
//    	mPositionWhenPaused = mVideoView.getCurrentPosition();
//    	mVideoView.pause();

    	super.onPause();
    }
    
    public void onResume() {
    	// Resume video player
    	if(mPositionWhenPaused >= 0) {
    		mVideoView.seekTo(mPositionWhenPaused);
    		mPositionWhenPaused = -1;
    	}
    	
    	super.onResume();
    }
	
	@Override 
    protected void onDestroy() { 
		
        super.onDestroy(); 

    }

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	} 
	
	
//	public void onConfigurationChanged(Configuration newConfig)
//	{
//		Toast.makeText(MediaPlayerActivity.this, "TEST: Screen Orientation.", Toast.LENGTH_LONG).show();
//	}
}