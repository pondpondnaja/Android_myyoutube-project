package com.example.nikep.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class GalleryActivity extends YouTubeBaseActivity {
    private static final String TAG = "GalleryActivity";
    YouTubePlayerView mYouTubePlayerView;
    ImageButton btnplay;
    TextView title;
    TextView like;
    TextView view;
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
        if(getIntent().hasExtra("Video_url")&& getIntent().hasExtra("Video_name")){
            Log.d(TAG, "getIncomingIntent: found intent extra.");

            String Video_url = getIntent().getStringExtra("Video_url");
            String Video_name = getIntent().getStringExtra("Video_name");
            String Video_like = getIntent().getStringExtra("Video_like");
            String Video_view = getIntent().getStringExtra("Video_view");

            setImage(Video_url,Video_name,Video_like,Video_view);
        }
    }

    private void setImage(final String videoUrl, final String videoName, final String videolike, final String videoview){
        Log.d(TAG, "setImage: setting the image and name to the widget.");
        btnplay = findViewById(R.id.btnplay);
        title = findViewById(R.id.videoname);
        like = findViewById(R.id.vlike);
        view = findViewById(R.id.vView);
        mYouTubePlayerView = findViewById(R.id.youtubeplayer);

        title.setText(videoName);

        like.setText("Like : "+videolike+"K");
        view.setText(videoview +" Views");

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b){
                Log.d(TAG, "onClick: Done OnInitialized stage" + videoUrl);
                youTubePlayer.setFullscreen(true);
                youTubePlayer.loadVideo(videoUrl);
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult){
                Log.d(TAG, "onClick: Fail to OnInitialized stage");
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
