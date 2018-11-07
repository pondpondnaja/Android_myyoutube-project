package com.example.nikep.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class GalleryActivity extends YouTubeBaseActivity {
    private static final String TAG = "GalleryActivity";
    YouTubePlayerView mYouTubePlayerView;
    Button btnplay;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Log.d(TAG, "onCreate: started.");
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intent.");
        if(getIntent().hasExtra("video_url")&& getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intent extra.");

            String video_url = getIntent().getStringExtra("video_url");
            String imageName = getIntent().getStringExtra("image_name");

            setImage(video_url,imageName);
        }
    }

    private void setImage(final String videoUrl, String videoName){
        Log.d(TAG, "setImage: setting the image and name to the widget.");
        btnplay = findViewById(R.id.btnplay);
        mYouTubePlayerView = findViewById(R.id.youtubeplayer);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b){
                Log.d(TAG, "onClick: Done OnInitialized");
                youTubePlayer.setFullscreen(true);
                youTubePlayer.loadVideo(videoUrl);
                youTubePlayer.play();

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult){
                Log.d(TAG, "onClick: Fail OnInitialized");
            }
        };

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYouTubePlayerView.initialize(YoutubeConfig.getApiKey(),mOnInitializedListener);
            }
        });
    }


}