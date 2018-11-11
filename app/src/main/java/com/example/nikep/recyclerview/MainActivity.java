package com.example.nikep.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //Variable
    private RecyclerView mRecyclerview;
    private RecyclerViewAdapter mRecyclerviewAdapter;
    private RequestQueue mRequestqueue;
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

        mRequestqueue = Volley.newRequestQueue(this);
        pasteJSON();
/*
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
        */
    }

    private void pasteJSON(){
        Log.d(TAG, "pasteJSON: Json pasting");
        final String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=20&regionCode=US&key="+YoutubeConfig.getApiKey();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: JSON respond");
                        try {
                            JSONArray jsonArray = response.getJSONArray("items"); //root in JSON file

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject item = jsonArray.getJSONObject(i);
                                String videadd = item.getString("id");            //add Video url
                                Log.d(TAG, "onResponse: video ID :"+ (i+1)+" "+videadd);

                                JSONObject snippet = item.getJSONObject("snippet");
                                String nameVideo = snippet.getString("title");
                                Log.d(TAG, "onResponse: Video name :"+ (i+1)+" "+nameVideo);

                                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                                JSONObject resolution = thumbnails.getJSONObject("maxres");
                                String quality = resolution.getString("url");
                                Log.d(TAG, "onResponse: image url :"+ (i+1)+" "+quality);

                                mVideoUrl.add(videadd);
                                mName.add(nameVideo);
                                mImageUrl.add(quality);

                                initRecycleView();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: Error appear");
                        error.printStackTrace();
            }
        });

        mRequestqueue.add(request);
    }

    private void initRecycleView(){
        Log.d(TAG, "initRecycleView: initRecycleview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mName,mImageUrl,mVideoUrl);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
