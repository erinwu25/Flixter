package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ViewDetails extends AppCompatActivity {

    // movie to display
    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    TextView popularity;
    TextView popTitle;
    ImageView trailer;
    Integer id;
    String imageUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        // resolve the view objects
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        popularity = findViewById(R.id.popularity);
        popTitle = findViewById(R.id.popTitle);
        trailer = findViewById(R.id.mvTrailer);




        // url
        Integer vidId; // get id


        // unwrap the movie passed in by intent, using its simple name (Parcels) as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        // log statement for sanity check
        Log.d("ViewDetails", String.format("Showing details for '%s'", movie.getTitle()));

        vidId = movie.getId();
        final String vidUrl = String.format("https://api.themoviedb.org/3/movie/%1$s/videos?api_key=%2$s&language=en-US", vidId.toString(), "a07e22bc18f5cb106bfe4cc1f83ad8ed");


        // set movie details
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        // set rating
        float voteAverage = movie.getVoteAverage().floatValue();
        if(voteAverage > 0){
            voteAverage = voteAverage/ 2.0f;
        }
        rbVoteAverage.setRating(voteAverage);
        popularity.setText(movie.getPopularity().toString());

        imageUrl = movie.getBackdropPath();

        Glide.with(this)
                .load(imageUrl)
                .into(trailer);



        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] vidID = new String[1];
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(vidUrl, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        System.out.println("huh");
                        Log.d("MovieTrailerActivity", "onSuccess");
                        JSONObject jsonObject = json.jsonObject;

                        // 0th key of result
                        JSONObject res0;

                        try {
                            JSONArray results = jsonObject.getJSONArray("results");
                            Log.i("MovieTrailerActivity", "results" + results.toString());
                            res0 = results.getJSONObject(0);
                            vidID[0] = res0.getString("key");
                            Log.d("YT", vidID[0]);

                            Intent intent = new Intent(ViewDetails.this, MovieTrailerActivity.class);
                            intent.putExtra("id", vidID[0]);
                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e("YT", response);
                    }
                });



            }
        });


        // set text color
        tvTitle.setTextColor(Color.rgb(190, 190, 190));
        tvOverview.setTextColor(Color.rgb(190, 190, 190));
        popularity.setTextColor(Color.rgb(190, 190, 190));
        popTitle.setTextColor(Color.rgb(190, 190, 190));

    }

}