package com.example.jonat.sortmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonat on 10/8/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> moviesList;
    private int rowLayout;
    private Context context;
    private final Callbacks mCallbacks;



    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView sub;
        TextView movieDescription;
        ImageView thumbnail;
        public Movie movies;
        View mView;


        public MovieViewHolder(View v) {
            super(v);
            mView = v;
            movieTitle = (TextView) v.findViewById(R.id.title);
            sub = (TextView) v.findViewById(R.id.sub_title);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            movieDescription = (TextView) v.findViewById(R.id.description);
        }
    }

    public MovieAdapter(List<Movie> movies, int rowLayout, Context context, Callbacks mCallbacks) {
        this.moviesList = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        this.mCallbacks = mCallbacks;
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {

        final Movie mMovie = moviesList.get(position);
        holder.movies = mMovie;

        String poster = Bitmap.buildPosterUrl(mMovie.getPosterPath());


        if (!TextUtils.isEmpty(mMovie.getPosterPath())) {
            Picasso.with(context).load(poster)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.thumbnail,
                            new Callback() {
                                @Override
                                public void onSuccess() {
                                    if (holder.thumbnail != null) {
                                        holder.thumbnail.setVisibility(View.VISIBLE);
                                    } else {
                                        holder.thumbnail.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onError() {
                                    holder.thumbnail.setVisibility(View.VISIBLE);
                                }
                            });


        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onItemCompleted(mMovie, holder.getAdapterPosition());
            }
        });

        holder.movieTitle.setText(mMovie.getTitle());
        holder.sub.setText(mMovie.getOriginalTitle());
        holder.movieDescription.setText(mMovie.getOverview());
    }



    @Override
    public int getItemCount() {
        return moviesList.size();
    }




    public interface Callbacks {
        void onItemCompleted(Movie movie, int position);


    }
}

