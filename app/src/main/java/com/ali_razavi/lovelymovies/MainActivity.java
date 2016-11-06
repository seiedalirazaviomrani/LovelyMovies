package com.ali_razavi.lovelymovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int NUMBER_OF_MOVIES = 20;
    private ArrayList<Movie> movies = new ArrayList<>(NUMBER_OF_MOVIES);
    private MovieAdapter movieAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        if (isConnectedToNetwork()) {
            updateData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //put my saved "movies" ArrayList
        outState.putParcelableArrayList("movies", movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movies = new ArrayList<>(Arrays.<Movie>asList());
        } else {
            movies = savedInstanceState.getParcelableArrayList("movies");
        }

        movieAdapter = new MovieAdapter(this, movies);

        GridView gridView = (GridView) findViewById(R.id.gridView_Movie);
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(getString(R.string.put_extra_text_value), movies.get(position));
                startActivity(intent);
            }
        });
    }

    /**
     * This method is for check network status
     * if WiFi network and data connection
     *
     * @return network state (true=Connect / false=notConnect)
     */
    public boolean isConnectedToNetwork() {
        //Check device network access state
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            Toast.makeText(this, getString(R.string.msg_NetConnection_Err), Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, getString(R.string.msg_NetConnection_Err));
            return false;
        }
    }

    /**
     * This method is for loading shared preferences setting and
     * update app with that settings
     */
    private void updateData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String parameter = prefs.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_setType_DefaultValue));

        FetchMovieTask fetchMovieTask = new FetchMovieTask(this);
        fetchMovieTask.execute(parameter);
    }

    @Override
    public void onTaskCompleted(ArrayList<Movie> results) {
        movieAdapter.clear();
        for (Movie movieItem : results) {
            movieAdapter.add(movieItem);
        }
    }
}
