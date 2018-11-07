package com.example.nikep.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //Variable
    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> mImageUrl = new ArrayList<>();
    private ArrayList<String> mVideoUrl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        initImagebitmap();
    }

    private void initImagebitmap(){
        Log.d(TAG, "initImagebitmap: preparing bitmap.");

        mImageUrl.add("https://i.pinimg.com/originals/32/d8/fe/32d8fea5ce212f8c76f894c60a909487.jpg");
        mName.add("Animegirl 1");
        mVideoUrl.add("K36sEFvFHXk");

        mImageUrl.add("https://i.pinimg.com/originals/d2/9e/87/d29e8717a894e4407e154e5c226292fe.jpg");
        mName.add("Animegirl 2");
        mVideoUrl.add("ZSDftSCslAg");

        mImageUrl.add("https://2.bp.blogspot.com/-vDjfBpwLMO4/WNfKtumKD2I/AAAAAAAAAoo/RYc4X5CyuckvkMlAh5Gtvh9Y0oe1be1ngCLcB/s1600/Anime%2BGirl%2BSnowfall.jpg");
        mName.add("Kuro neko");
        mVideoUrl.add("99-Hzo5n2Mk");

        mImageUrl.add("http://www.wallpapermaiden.com/image/2018/02/02/darling-in-the-franxx-zero-two-long-pink-hair-anime-19728.jpg");
        mName.add("Zero two");
        mVideoUrl.add("2oQffkFWK2Y");

        mImageUrl.add("http://www.wallpapermaiden.com/wallpaper/20113/download/2560x1600/darling-in-the-franxx-ichigo-blue-hair-coffee.jpg");
        mName.add("Ichigo");
        mVideoUrl.add("q6dXsIvOl-M");

        initRecycleView();
    }

    private void initRecycleView(){
        Log.d(TAG, "initRecycleView: initRecycleview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mName,mImageUrl,mVideoUrl);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
