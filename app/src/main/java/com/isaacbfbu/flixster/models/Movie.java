package com.isaacbfbu.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Parcel // annotation indicates class is Parcelable
public class Movie {

    // fields must be public for Parceler
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    Double voteAverage;
    Integer id;
    String releaseDate;
    ArrayList<Integer> genreIds;

    // no-arg, empty constructor required for Parceler
    public Movie() {
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");

        releaseDate = jsonObject.getString("release_date");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy");
        try {
            Date date = inputFormat.parse(releaseDate);
            releaseDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray results = jsonObject.getJSONArray("genre_ids");
        genreIds = new ArrayList<>();
        for (int i = 0; i < results.length(); ++i) {
            genreIds.add(results.getInt(i));
        }
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }
}
