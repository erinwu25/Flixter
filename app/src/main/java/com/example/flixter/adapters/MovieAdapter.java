package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixter.R;
import com.example.flixter.ViewDetails;
import com.example.flixter.model.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    // inflate layout return inside view holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // populate data into item through view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // get movie at position
        Movie movie = movies.get(position);
        // bind movie data into the view holder
        holder.bind(movie);

    }

    // return total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);

            //itemView's onClickListener
            itemView.setOnClickListener(this);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            // set color of text
            tvTitle.setTextColor(Color.rgb(190, 190, 190));
            tvOverview.setTextColor(Color.rgb(190, 190, 190));

            String imageUrl;
            int placeholder;

            // if phone in landscape, set imageUrl to backdrop image
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                imageUrl = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;
            }
            // else default to poster image
            else
            {
                imageUrl = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
            }

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeholder) // have placeholder img
                    .transform(new RoundedCornersTransformation(15, 3))
                    .into(ivPoster);

        }

        @Override
        public void onClick(View view) {
            // get item position
            int position = getAdapterPosition();

            // ensure position is valid (actually exists in view)
            if (position != RecyclerView.NO_POSITION)
            {
                Movie movie = movies.get(position); // get the movie at the position
                Intent intent = new Intent(context, ViewDetails.class); // create intent for new activity
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie)); // serialize movie (check what this means) using parceler
                context.startActivity(intent); // show the activity
            }



        }
    }
}
