package com.PrayerTimes;
import android.app.*;
import android.media.*;
import android.content.res.*;
import android.content.*;
import android.os.*;
import java.io.*;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import java.io.IOException;

public class AdhanService extends Service
{
	MediaPlayer create;

    MediaPlayer mediaplayer = new MediaPlayer();
	 
    int[] resID = new int[]{R.raw.athan,R.raw.abdulb};
    public int onStartCommand(Intent intent, int i, int i2)
	{
		int requestCode1 = intent.getExtras().getInt("salatIntent",0);
		if(requestCode1==0){
			
			create = mediaplayer.create(this, resID[0]);
		}
		else if(requestCode1>0){
			
			create = mediaplayer.create(this, resID[1]);
		}
		


		{
			mediaplayer = create;

			if (create.isPlaying())
			{
				try
				{
					mediaplayer.prepare();
					mediaplayer.stop();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				mediaplayer.start();
			}}


        return START_NOT_STICKY;
    }
	@Override
    public void onDestroy()
	{
        if (mediaplayer != null)
		{
            mediaplayer.stop();
            mediaplayer.release();
        }
        super.onDestroy();
    }
	public IBinder onBind(Intent intent)
	{
        return null;
    }

}


 




