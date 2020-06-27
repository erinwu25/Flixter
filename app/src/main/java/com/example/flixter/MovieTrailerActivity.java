package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.model.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        // temp test video ID (will change later)
        final String[] videoId = {"tKodtNFpzBA"};
        final String[] vID = new String[1];


        // resolve the player view from the layout
        final YouTubePlayerView[] playerView = {(YouTubePlayerView) findViewById(R.id.player)};

        // url
//        Integer vidId = getIntent().getExtras().getInt("id");
//        String vidUrl = String.format("https://api.themoviedb.org/3/movie/%1$s/videos?api_key=%2$s&language=en-US", vidId.toString(), "a07e22bc18f5cb106bfe4cc1f83ad8ed");

        // log
//        Log.d("YT", vidUrl);
        System.out.println("what");





        // initialize with API key (from secrets.xml)
        playerView[0].initialize(getString(R.string.youtube_apiKey), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                // work for cuing video and playing video etc.

                youTubePlayer.cueVideo(getIntent().getStringExtra("id"));
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                // Log error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}