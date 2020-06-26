package com.isaacbfbu.flixster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.tabs.TabLayout;
import com.isaacbfbu.flixster.adapters.MovieAdapter;
import com.isaacbfbu.flixster.databinding.ActivityMainBinding;
import com.isaacbfbu.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String API_URL = "https://api.themoviedb.org/3/movie/%s?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String[] ROUTES = {"now_playing","top_rated","upcoming"};
    public static final String TAG = "MainActivity";
    private static final String SELECTED_TAB_POSITION = "TabPosition";

    List<Movie> movies;

    AsyncHttpClient client;
    ActivityMainBinding binding;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new AsyncHttpClient();
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        movies = new ArrayList<>();
        // Create the adapter
        movieAdapter = new MovieAdapter(this, movies);

        // Set the adapter on the recycler view
        binding.rvMovies.setAdapter(movieAdapter);

        // Set a Layout Manager on the recycler view
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(this));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateMovies();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_TAB_POSITION)) {
            binding.tabLayout.getTabAt(savedInstanceState.getInt(SELECTED_TAB_POSITION)).select();
        }

        updateMovies();
    }

    private void updateMovies() {
        movies.clear();
        movieAdapter.notifyDataSetChanged();
        binding.progressBar.setVisibility(View.VISIBLE);
        client.get(String.format(API_URL, ROUTES[binding.tabLayout.getSelectedTabPosition()]), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    binding.progressBar.setVisibility(View.GONE);
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: "+results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies:" + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG,"Hit json exception", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SELECTED_TAB_POSITION, binding.tabLayout.getSelectedTabPosition());
        super.onSaveInstanceState(outState);
    }
}