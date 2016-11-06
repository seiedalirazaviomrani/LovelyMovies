package com.ali_razavi.lovelymovies;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by seiedalirazaviomrani on 11/5/16.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    MovieAdapter(Activity context, List<Movie> popMovies) {
        super(context, 0, popMovies);
    }
    private static String posterImageUrl;
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie currentMovieItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }

        ImageView posterView = (ImageView) convertView.findViewById(R.id.list_item_mPoster);
        Picasso.with(getContext()).load(currentMovieItem.getPosterImageUrl())
                .placeholder(R.drawable.placeholder_movie)
                .error(R.drawable.error_placeholder)
                .into(posterView);

        TextView movieNameView = (TextView) convertView.findViewById(R.id.list_item_mTitle);
        movieNameView.setText(currentMovieItem.getOriginalTitle());

        return convertView;
    }
}
