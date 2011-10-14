package avermedia.com.ipd1;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

public class CustomVideoView extends VideoView
{
	private int mVideoWidth;
    private int mVideoHeight;
    
    private DisplayMetrics dm;
    
 public CustomVideoView(Context context)
 {
  super(context);
  // TODO Auto-generated constructor stub
  
 }
 
 public CustomVideoView(Context context, AttributeSet attrs)
 {
  super(context, attrs);
  // TODO Auto-generated constructor stub
  mVideoWidth = 0;
  mVideoHeight = 0;
 } 
  
 protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
 {
	 dm = getResources().getDisplayMetrics();	//Get screen's information
	 int ScreenWidth = dm.widthPixels;	  
     int ScreenHeight = dm.heightPixels;
     
//     widthMeasureSpec % 1000
     
     this.setMeasuredDimension(ScreenWidth, ScreenHeight);
	 
//	 int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
//     int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
//     setMeasuredDimension(width,height);
     
     Log.d("[CustomVideoView]", "Screen size = " + ScreenWidth + "*" + ScreenHeight);
        
     Toast.makeText(CustomVideoView.this.getContext(), "TEST: Screen Orientation." + "Screen size = " + ScreenWidth + "*" + ScreenHeight, Toast.LENGTH_SHORT).show();
 }
 
// protected void onWindowVisibilityChanged(int visibility)
// {
//	 Toast.makeText(CustomVideoView.this.getContext(), "TEST: Screen Orientation.", Toast.LENGTH_LONG).show();
// }
  
 
}
