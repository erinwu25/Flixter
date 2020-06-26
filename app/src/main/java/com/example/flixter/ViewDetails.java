package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixter.model.Movie;

import org.parceler.Parcels;

public class ViewDetails extends AppCompatActivity {

    // movie to display
    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        // resolve the view objects
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);

        // unwrap the movie passed in by intent, using its simple name (Parcels) as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        // log statement for sanity check
        Log.d("ViewDetails", String.format("Showing details for '%s'", movie.getTitle()));

        // set movie details
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        // set rating
        float voteAverage = movie.getVoteAverage().floatValue();
        if(voteAverage > 0){
            voteAverage = voteAverage/ 2.0f;
        }
        rbVoteAverage.setRating(voteAverage);

    }
}