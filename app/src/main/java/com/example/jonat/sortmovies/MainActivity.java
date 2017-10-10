package com.example.jonat.sortmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter.Callbacks mCallbacks;
    public final static String MOST_POPULAR = "popular";
    public final static String TOP_RATED = "top_rated";
    private String mSortBy = MOST_POPULAR;
    private ApiInterface apiService;
    private RecyclerView recyclerView;


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "e568b8a0746be29e194efdcf43151703";



    public MainActivity() {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }
        recyclerView = (RecyclerView) findViewById(R.id.rv_peoples);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.number)));
        mCallback();

/**
        apiService =
         ApiClient.getClient().create(ApiInterface.class);

         Call<MovieResponse> call = apiService.getTopRatedMovies(mSortBy, API_KEY);
         call.enqueue(new Callback<MovieResponse>() {
        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
        int statusCode = response.code();
        List<Movie> movies = response.body().getResults();
        recyclerView.setAdapter(new MovieAdapter(movies, R.layout.content_container, getApplicationContext(), mCallbacks));
        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
        // Log error here since request failed
        Log.e(TAG, t.toString());
        }
        });


 **/
        fetchFilms(mSortBy);

    }


    private void fetchFilms(String mSortBy) {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(mSortBy, API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, R.layout.content_container, getApplicationContext(), mCallbacks));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_list, menu);

        switch (mSortBy) {
            case MOST_POPULAR:
                menu.findItem(R.id.sort_by_most_popular).setChecked(true);
                break;
            case TOP_RATED:
                menu.findItem(R.id.sort_by_top_rated).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_top_rated:
                mSortBy = TOP_RATED;
                fetchFilms(mSortBy);
                item.setChecked(true);
                return true;
            case R.id.sort_by_most_popular:
                mSortBy = MOST_POPULAR;
                fetchFilms(mSortBy);
                item.setChecked(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void mCallback() {
        mCallbacks = new MovieAdapter.Callbacks() {
            @Override
            public void onItemCompleted(Movie movie, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.Args, movie);
                startActivity(intent);


            }
        };

    }





}

