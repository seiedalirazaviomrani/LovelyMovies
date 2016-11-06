package com.ali_razavi.lovelymovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Movie movie = bundle.getParcelable(getString(R.string.put_extra_text_value));
            ((TextView) findViewById(R.id.detail_item_mTitle)).setText(movie.getOriginalTitle());
            ((TextView) findViewById(R.id.detail_item_mRate)).setText(String.valueOf(movie.getVoteAverage()));
            ((TextView) findViewById(R.id.detail_item_mPopularity)).setText(String.valueOf(movie.getPopularity()));
            ImageView posterView = (ImageView) findViewById(R.id.detail_item_mPoster);
            Picasso.with(this).load(movie.getPosterImageUrl()).into(posterView);
            ((TextView) findViewById(R.id.detail_item_mReleaseDate)).setText(movie.getReleaseDate());
            ((TextView) findViewById(R.id.detail_item_mOverview)).setText(movie.getOverview());
            ((TextView) findViewById(R.id.detail_item_mLanguage)).setText(movie.getOriginalLanguage());

        } else {
            Toast.makeText(this, "Bundle is Null ?!", Toast.LENGTH_SHORT).show();
        }
    }
}
