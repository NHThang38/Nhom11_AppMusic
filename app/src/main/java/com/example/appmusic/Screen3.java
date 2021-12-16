package com.example.appmusic;

import static com.example.appmusic.ListMusic.items;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Screen3 extends AppCompatActivity {

    RecyclerView recyclerView;
    IteamAdapter adt;
    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection serviceConnection;
    List<Item> mSongs;
    private boolean isConnected;
    private Handler handler;
    private SeekBar seekBar;
    ImageButton btnTL;
    private ImageView imageView,next,back,btOn;
    private TextView ten,caption,timeplay,timesong,loibh;

    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);
        seekBar = findViewById(R.id.seekBar);
        btOn=  findViewById(R.id.imageButton);
        imageView =findViewById(R.id.detalAnh);
        ten = findViewById(R.id.detailname);
        caption = findViewById(R.id.detailcamption);
        timeplay = findViewById(R.id.timeplay);
        timesong = findViewById(R.id.timesong);
        next = findViewById(R.id.imageView7);
        back = findViewById(R.id.imageView6);
        loibh = findViewById(R.id.tvloi);
        recyclerView = findViewById(R.id.recyclerView2);
        adt = new IteamAdapter(Screen3.this,items);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(adt);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent1 = getIntent();
        Item item = (Item) intent1.getSerializableExtra("item");
        mSongs = (List<Item>) getIntent().getSerializableExtra("listMusic");
        currentIndex =  getIntent().getIntExtra("index",0);
        imageView.setImageResource(item.getHinhAnh());
        caption.setText(item.getCaption());
        ten.setText(item.getName());
        loibh.setText(item.getLoi());


        handler =  new Handler();


        connectService();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myService.mp!=null){
                    btOn.setImageResource(R.drawable.play);
                }
                if(currentIndex < mSongs.size() - 1){
                    currentIndex++;
                }else{
                    currentIndex = 0;
                }
                if (myService.mp.isPlaying()){
                    myService.mp.stop();
                }
                clickStartService();
                thanhchay();
                myService.mp = MediaPlayer.create(getApplicationContext(),mSongs.get(currentIndex).getSongResource());
                myService.mp.start();
                SongNames();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myService.mp!=null){
                    btOn.setImageResource(R.drawable.play);
                }
                if(currentIndex > 0){
                    currentIndex--;
                }else{
                    currentIndex = mSongs.size()-1;
                }
                if (myService.mp.isPlaying()){
                    myService.mp.stop();
                }
                clickStartService();
                thanhchay();
                myService.mp = MediaPlayer.create(getApplicationContext(),mSongs.get(currentIndex).getSongResource());
                myService.mp.start();
                SongNames();
            }
        });

        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(myService.mp!=null &&myService.mp.isPlaying()){
                    myService.Tnghenhac();

                    btOn.setImageResource(R.drawable.paush);
                }else{
                    clickStartService();
                    myService.nghenhac(item.getSongResource());
                    seekBar.setMax(myService.mp.getDuration());
                    timesong.setText(TimeSong(item.getSongResource()));

                    btOn.setImageResource(R.drawable.play);
                    isBound = false;
                }

            }
        });
        update();
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();

        //below methods are used for adding enter and exit transition.
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                if(myService.mp!=null &&myService.mp.isPlaying()){
                    myService.Tnghenhac();

                    btOn.setImageResource(R.drawable.play);
                }



            }
        });
        thanhchay();

    }

    private void connectService() {

        Intent intent = new Intent(this, MyService.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBinder myBinder = (MyService.MyBinder) service;

                myService = myBinder.getService();
                isConnected = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConnected = false;
                myService = null;
            }
        };
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
    private void SongNames() {
        Item item= mSongs.get(currentIndex);
        imageView.setImageResource(item.getHinhAnh());
        caption.setText(item.getCaption());
        ten.setText(item.getName());
    }


    private void clickStartService() {

        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();

        Item song = mSongs.get(currentIndex);
        bundle.putSerializable("object_song",song);
        intent.putExtras(bundle);

        startService(intent);

    }
    private void update() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss") ;
                timeplay.setText(dateFormat.format(myService.mp.getCurrentPosition()));
                seekBar.setProgress(myService.mp.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        }, 500);
    }
    private void thanhchay(){
        Intent intent1 = getIntent();
        Item item = (Item) intent1.getSerializableExtra("item");
        myService.mp = MediaPlayer.create(getApplicationContext(),mSongs.get(currentIndex).getSongResource());
        seekBar.setMax(myService.mp.getDuration());
        timesong.setText(TimeSong(item.getSongResource()));
    }
    private String  TimeSong(int Song){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss") ;
        String timeSong =  dateFormat.format(myService.mp.getDuration());
        return timeSong;
    }
}