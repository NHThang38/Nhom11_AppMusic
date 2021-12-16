package com.example.appmusic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListMusic extends AppCompatActivity {
    RecyclerView recyclerView;
    static  List<Item> items = new ArrayList<>();;
    IteamAdapter adt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_music);
        recyclerView = findViewById(R.id.recyclerView);

        items.add(new Item("Như Ngày Hôm Qua"," Sơn Tùng M-TP",R.drawable.nhungayhomqua,R.raw.nhungayhomqua,"Cuối con đường là bầu trời xanh ấm êm\n" +
                "Bên tôi mỗi khi buồn lặng lẽ xóa tan âu lo\n" +
                "Gạt giọt nước mắt thăng trầm\n" +
                "Niềm tin mãi luôn đong đầy\n" +
                "Bài ca hát trọn đêm nay dành tặng bạn tôi..."));
        items.add(new Item("Bao Lâu Ta Lại Yêu Một Ng","Doãn Hiếu",R.drawable.baolauta,R.raw.baolaulaiyeumotnguoi,"Người buồn vì mưa hay mưa vì buồn\n" +
                "Chiều nay thành phố như có mình ta…\n" +
                "Gom từng ánh nắng, hết ngày đem cất\n" +
                "Đôi khi cần nhất người ta lại đi mất…"));
        items.add(new Item("Gác lại âu lo","Da LAB ft. Miu Lê",R.drawable.gaclaiaulo,R.raw.gaclaiaulo,"Anh đi lạc trong sóng gió cuộc đời\n" +
                "Nào biết đâu sớm mai liệu bình yên có tới\n" +
                "Âu lo chạy theo những ánh sao đêm\n" +
                "Ngày cứ trôi chớp mắt thành phố đã sáng đèn"));
        items.add(new Item("Willow","Taylor Swift",R.drawable.n3,R.raw.willow,"I'm like the water when your ship rolled in that night\n" +
                "Rough on the surface but you cut through like a knife\n" +
                "And if it was an open-shut case\n" +
                "I never would've known from that look on your face\n" +
                "Lost in your current like a priceless wine\n" +
                "The more that you say"));
        adt = new IteamAdapter(ListMusic.this,items);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(adt);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
