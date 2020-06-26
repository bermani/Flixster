package com.isaacbfbu.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.isaacbfbu.flixster.MovieDetailsActivity;
import com.isaacbfbu.flixster.R;
import com.isaacbfbu.flixster.databinding.ActivityMovieDetailsBinding;
import com.isaacbfbu.flixster.databinding.ItemMovieBinding;
import com.isaacbfbu.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder");
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemMovieBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            binding = ItemMovieBinding.bind(itemView);
        }

        public void bind(Movie movie) {
            binding.tvTitle.setText(movie.getTitle());
            binding.tvOverview.setText(movie.getOverview());
            String imageUrl;
            @DrawableRes int placeholderImage;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
                placeholderImage = R.drawable.flicks_backdrop_placeholder;
            } else {
                imageUrl = movie.getPosterPath();
                placeholderImage = R.drawable.flicks_movie_placeholder;
            }

            Glide.with(context).load(imageUrl).transform(new RoundedCorners(30)).placeholder(placeholderImage).into(binding.ivPoster);
        }

        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);

            }
        }
    }
}
