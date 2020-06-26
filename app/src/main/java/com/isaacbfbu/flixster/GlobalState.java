package com.isaacbfbu.flixster;

import android.app.Application;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Headers;

public class GlobalState extends Application
{
    private HashMap<Integer,String> genreMap;

    @Override
    public void onCreate() {
        super.onCreate();
        AsyncHttpClient client = new AsyncHttpClient();
        genreMap = new HashMap<>();
        client.get("https://api.themoviedb.org/3/genre/movie/list?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray genres = jsonObject.getJSONArray("genres");
                    for (int i = 0; i < genres.length(); ++i) {
                        JSONObject genre = genres.getJSONObject(i);
                        genreMap.put(genre.getInt("id"),genre.getString("name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }

    public String getGenre(Integer id) {
        return genreMap.get(id);
    }
}
