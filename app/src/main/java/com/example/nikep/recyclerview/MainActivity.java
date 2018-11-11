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
    private ArrayList<String> mVideoLike = new ArrayList<>();
    private ArrayList<String> mViewCount = new ArrayList<>();

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
    }

    private void pasteJSON(){
        Log.d(TAG, "pasteJSON: Json pasting");
        final String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&chart=mostPopular&maxResults=20&regionCode=US&key="+YoutubeConfig.getApiKey();
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

                                JSONObject snippet = item.getJSONObject("snippet");    //find snippet Obj.
                                String nameVideo = snippet.getString("title");    //get string "title" from snippet Obj.
                                Log.d(TAG, "onResponse: Video name :"+ (i+1)+" "+nameVideo);

                                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                                JSONObject resolution = thumbnails.getJSONObject("maxres");
                                String quality = resolution.getString("url");
                                Log.d(TAG, "onResponse: image url :"+ (i+1)+" "+quality);

                                JSONObject statistics = item.getJSONObject("statistics");
                                String likecount = statistics.getString("likeCount");
                                String viewcount = statistics.getString("viewCount");
                                Log.d(TAG, "onResponse: likecount :"+(i+1)+" "+likecount);
                                Log.d(TAG, "onResponse: viewcount :"+(i+1)+" "+viewcount);

                                mVideoUrl.add(videadd);
                                mName.add(nameVideo);
                                mImageUrl.add(quality);
                                mVideoLike.add(likecount);
                                mViewCount.add(viewcount);

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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mName,mImageUrl,mVideoUrl,mVideoLike,mViewCount);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
