package com.example.appmusic;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;


public class MyService extends Service {
    public static MediaPlayer mp;
    private Item mSong;
    @Override

    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            Item song = (Item) bundle.get("object_song");

            if(song != null) {
                mSong = song;
                sendNotification(song);
            }
        }
        return START_NOT_STICKY;
    }

    private void sendNotification(Item song) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),song.getHinhAnh());
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);
        remoteViews.setTextViewText(R.id.tv_title_song,song.getName());
        remoteViews.setTextViewText(R.id.tv_single_song, song.getCaption());
        remoteViews.setImageViewBitmap(R.id.img_song,bitmap);

        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();
        startForeground(1, notification);
    }

    public class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        };
    }
    public void nghenhac(int resource){
        mp = MediaPlayer.create(getApplicationContext(), resource);
        mp.setLooping(false);
        mp.start();
    }
    public void Tnghenhac(){
        mp.pause();
    }
}
