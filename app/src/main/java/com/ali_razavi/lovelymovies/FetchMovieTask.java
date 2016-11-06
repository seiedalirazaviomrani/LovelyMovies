package com.ali_razavi.lovelymovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by seiedalirazaviomrani on 11/6/16.
 */

class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {
    private static final int NUMBER_OF_MOVIES = 20;
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
    ArrayList<Movie> movies = new ArrayList<>(NUMBER_OF_MOVIES);

    private OnTaskCompleted mListener;

    FetchMovieTask(OnTaskCompleted listener) {
        this.mListener = listener;
    }

    /**
     * This method is for get movie data from JSON String
     *
     * @param movieJSONstr String
     * @return popMovie object
     * @throws JSONException
     */
    private ArrayList<Movie> getMovieDataFromJSON(String movieJSONstr) throws JSONException {

        //JSON object that need to extract
        final String J_RESULTS = "results";
        final String J_POSTER_PATH = "poster_path";
        final String J_ORIGINAL_TITLE = "original_title";
        final String J_POPULARITY = "popularity";
        final String J_VOTE_AVERAGE = "vote_average";
        final String J_RELEASE_DATE = "release_date";
        final String J_OVERVIEW = "overview";
        final String J_ORG_LANGUAGE = "original_language";


        JSONObject movieJSON = new JSONObject(movieJSONstr);
        JSONArray moviesARRAY = movieJSON.getJSONArray(J_RESULTS);

        for (int i = 0; i < NUMBER_OF_MOVIES; i++) {

            String posterUrl;
            String title;
            double pop;
            double rate;
            String moviePosterBaseURL = "http://image.tmdb.org/t/p/w154/";
            String releasedate;
            String overview;
            String orgLanguage;

            JSONObject aMovies = moviesARRAY.getJSONObject(i);
            posterUrl = aMovies.getString(J_POSTER_PATH);
            title = aMovies.getString(J_ORIGINAL_TITLE);
            pop = aMovies.getDouble(J_POPULARITY);
            rate = aMovies.getDouble(J_VOTE_AVERAGE);
            overview = aMovies.getString(J_OVERVIEW);
            releasedate = aMovies.getString(J_RELEASE_DATE);
            orgLanguage = aMovies.getString(J_ORG_LANGUAGE);


            movies.add(new Movie(title, pop, rate, moviePosterBaseURL + posterUrl, releasedate, overview, orgLanguage));

        }
        return movies;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        if (params.length == 0) {
            Log.e(LOG_TAG, "Empty parameters");
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        String fetchMovieJSONString = null;
        final String API_KEY_STR = "api_key";
        try {
            final String movieBaseURL =
                    "http://api.themoviedb.org/3/movie/";

            Uri builtUri = Uri.parse(movieBaseURL).buildUpon()
                    .appendPath(params[0])
                    .appendQueryParameter(API_KEY_STR, BuildConfig.MY_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            if (inputStream == null) {
                //Nothing to do
                return null;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String debugLine = null;
            while ((debugLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(debugLine + "\n");
            }

            if (stringBuffer.length() == 0) {
                //Stream was empty
                return null;
            }
            fetchMovieJSONString = stringBuffer.toString();


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }

        }
        try {
            return getMovieDataFromJSON(fetchMovieJSONString);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        if (movies != null) {
            mListener.onTaskCompleted(movies);
        }
    }
}
