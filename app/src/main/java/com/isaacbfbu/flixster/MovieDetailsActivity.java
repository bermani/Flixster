package com.isaacbfbu.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.isaacbfbu.flixster.databinding.ActivityMovieDetailsBinding;
import com.isaacbfbu.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";

    // the movie to display
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        // unwrap the movie passed in via intent using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s", movie.getTitle()));

        binding.tvTitle.setText(movie.getTitle());
        binding.tvOverview.setText(movie.getOverview());

        float voteAverage = movie.getVoteAverage().floatValue();
        binding.rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        Glide.with(this).load(movie.getBackdropPath())
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .into(binding.videoImage);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getVideosUrl(movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject video =  getFirstYoutubeVideo(jsonObject.getJSONArray("results"));
                    if (video == null) {
                        binding.videoLoading.setVisibility(View.GONE);
                        return;
                    }
                    final String key = video.getString("key");
                    binding.videoImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startVideoActivity(key);
                        }
                    });
                    binding.videoPlay.setVisibility(View.VISIBLE);
                    binding.videoLoading.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Log.e(TAG,"Hit json exception", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
    }

    private String getVideosUrl(Integer id) {
        return String.format(VIDEOS_URL, id);
    }

    private JSONObject getFirstYoutubeVideo(JSONArray results) {
        JSONObject current;
        for (int i = 0; i < results.length(); ++i) {
            try {
                current = results.getJSONObject(i);
                if (current.getString("site").equals("YouTube")) {
                    return current;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void startVideoActivity(String key) {
        Intent intent = new Intent(this, MovieTrailerActivity.class);
        intent.putExtra("key", key);
        this.startActivity(intent);
    }
}