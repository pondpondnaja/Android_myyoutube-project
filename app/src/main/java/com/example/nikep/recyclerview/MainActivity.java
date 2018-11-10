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
        initRecycleView();
    }

    private void pasteJSON(){
        Log.d(TAG, "pasteJSON: Json pasting");
        String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=10&regionCode=JP&key=AIzaSyDIS87lftBJA1lrSp4umI3t3rr6tfiyByY";
        //String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=10&regionCode=JP&key="+YoutubeConfig.getApiKey();
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
                                mVideoUrl.add(videadd);

                                JSONArray jsonROW = item.getJSONArray("snippet"); //find path snippet

                                for(int j = 0; j < jsonROW.length(); j++){
                                    JSONObject snippet = jsonROW.getJSONObject(j);
                                    String name = snippet.getString("title");     //add video name
                                    mName.add(name);

                                    JSONArray jsonCOL = snippet.getJSONArray("thumbnails"); //find path thumbnails

                                    for(int k = 0; k < jsonCOL.length(); k++){
                                        JSONObject thumbnails = jsonCOL.getJSONObject(k);
                                        JSONArray resolution = thumbnails.getJSONArray("maxres");  //find path maxres

                                        for(int l = 0; l < resolution.length(); l++){
                                            JSONObject maxres = resolution.getJSONObject(l);
                                            String JImage = maxres.getString("url");   //add video thumbnails pic.
                                            mImageUrl.add(JImage);
                                        }
                                    }
                                }
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
