package com.example.pcdas.moviee.activities;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pcdas.moviee.R;
import com.example.pcdas.moviee.adapters.MoviesAdapter;
import com.example.pcdas.moviee.favorite.FavoriteDbHelper;
import com.example.pcdas.moviee.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=f7d80154217d9b13ee6a925e7c5a4277";
    private RequestQueue requestQueue;
    public static List<Item> items;
    private RecyclerView recyclerView;
    private MoviesAdapter movsAdapter;
    private FavoriteDbHelper favoriteDbHelper;
    private AppCompatActivity activity = MainActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_movies);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        movsAdapter = new MoviesAdapter(this, new ArrayList<Item>(

        ));
        recyclerView.setAdapter(movsAdapter);
        movsAdapter.updateList(items);

        jsonrequest();

    }

    protected void onStart ()
    {
        super.onStart();
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        movsAdapter = new MoviesAdapter(this, new ArrayList<Item>(
        ));
        recyclerView.setAdapter(movsAdapter);
        movsAdapter.notifyDataSetChanged();

         jsonrequest();

    }

    public boolean onCreateOptionsMenu(Menu menu)

    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Home:
                Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
                Homee();
                break;
            case R.id.Favorite:
                Toast.makeText(this, "Favorite Selected", Toast.LENGTH_SHORT).show();

                FavoriteList();
                break;
                default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void FavoriteList() {
        recyclerView =  findViewById(R.id.rv_movies);

        items = new ArrayList<>();
        movsAdapter = new MoviesAdapter(this, items);
        getAllFavorite();


        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movsAdapter);
        movsAdapter.notifyDataSetChanged();
        favoriteDbHelper = new FavoriteDbHelper(activity);


    }

    private void Homee()
    {
        recyclerView =  findViewById(R.id.rv_movies);

        items = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_movies);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        movsAdapter = new MoviesAdapter(this, new ArrayList<Item>(

        ));
        recyclerView.setAdapter(movsAdapter);
        jsonrequest();

    }

    private void jsonrequest() {
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            Item b;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                b = new Item();
                                b.setId(((JSONObject) jsonArray.get(i)).getInt("id"));
                                b.setName(((JSONObject) jsonArray.get(i)).getString("original_title"));
                                b.setrdate(((JSONObject) jsonArray.get(i)).getString("release_date"));
                                b.setImage(((JSONObject) jsonArray.get(i)).getString("poster_path"));
                                b.setRating(((JSONObject) jsonArray.get(i)).getString("vote_average"));
                                b.setDescription(((JSONObject) jsonArray.get(i)).getString("overview"));

                                items.add(b);
                            }
                            movsAdapter.updateList(items);
                            favoriteDbHelper = new FavoriteDbHelper(activity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Log.i("error", "there is error here");
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }


    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;

    }



    private void getAllFavorite()
    {
        new AsyncTask<Void , Void , Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                items.clear();
                items.addAll(favoriteDbHelper.getAllFavorite());


                return null;

            }

            @Override
           protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);

            }
        }.execute();
    }
}

