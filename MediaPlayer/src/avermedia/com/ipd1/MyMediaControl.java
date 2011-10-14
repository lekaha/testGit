package avermedia.com.ipd1;

import android.media.MediaPlayer;
import android.widget.MediaController.MediaPlayerControl;

public class MyMediaControl implements MediaPlayerControl {
	
	private MediaPlayer mp_ = null;
	
	private static boolean STATE_IS_PLAYING = false;
	private static boolean STATE_IS_PREPARING = false;
	private static boolean STATE_IS_PAUSE = false;
	
	private static final int STATE_IDLE = 1;
	private static final int STATE_RESUME = 2;
	private static final int STATE_PASUED = 3;
	private static final int STATE_SUSPEND = 4;
	private static final int STATE_PLAYBACK_COMPLETED = 5;
	
	private boolean mCanSeekBack;
	private boolean mCanSeekForward;
	private int mCurrentBufferPercentage;
	private int mVideoWidth;
	private int mVideoHeight;
	
	private MediaPlayer.OnPreparedListener mOnPreparedListener;
	
	
	
	
	public MyMediaControl(MediaPlayer mp)
	{
		this.mp_ = mp;
		
		mCanSeekBack = true;
		mCanSeekForward = true;
		
		mp_.setOnBufferingUpdateListener(mBufferingUpdateListener);
		
	}

	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		STATE_IS_PLAYING = mp_.isPlaying();
		
		return STATE_IS_PLAYING;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return mCanSeekBack;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return mCanSeekForward;
	}

	
	private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener =
        new MediaPlayer.OnBufferingUpdateListener() {
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            mCurrentBufferPercentage = percent;
        }
    };
    
	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return mCurrentBufferPercentage;
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return mp_.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return mp_.getDuration();
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		STATE_IS_PLAYING = mp_.isPlaying();
		
		return STATE_IS_PLAYING;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		mp_.pause();

	}

	@Override
	public void seekTo(int pos) {
		// TODO Auto-generated method stub
		mp_.seekTo(pos);

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		mp_.start();
	}

}
