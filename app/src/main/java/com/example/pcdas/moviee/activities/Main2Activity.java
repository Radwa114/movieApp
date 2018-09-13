package com.example.pcdas.moviee.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pcdas.moviee.R;
import com.example.pcdas.moviee.adapters.MoviesAdapter;
import com.example.pcdas.moviee.favorite.FavoriteDbHelper;
import com.example.pcdas.moviee.model.Item;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {
    private FavoriteDbHelper favoriteDbHelper;
    private Item favorite;
    private MoviesAdapter moviesAdapter;
    private final AppCompatActivity activity = Main2Activity.this;
    TextView   movieName2  , RDate , movieRate2 , description2 ;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().hide();


        String name = getIntent().getExtras().getString("original_title");
        String id = getIntent().getExtras().getString("id");
        String description = getIntent().getExtras().getString("overview");
        String releasedate = getIntent().getExtras().getString("release_date");
        String rating = getIntent().getExtras().getString("vote_average");
        String mg = getIntent().getExtras().getString("poster_path");
        //CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
     //   collapsingToolbarLayout.setTitleEnabled(true);


         img = findViewById(R.id.iv_poster2);
        movieName2 = findViewById(R.id.tv_Movie_name);
         RDate = findViewById(R.id.tv_date);
         movieRate2 = findViewById(R.id.tv_movie_rating);
         description2 = findViewById(R.id.tv_discription);
        movieName2.setText(name);
        movieRate2.setText(rating);
        description2.setText(description);
        RDate.setText(releasedate);
        Picasso.with(this).load(this.getResources().getString(R.string.image_url) + mg).into(img);
        MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.favorite_button);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        materialFavoriteButton.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            int movi_id = getIntent().getExtras().getInt("id");
                            favoriteDbHelper= new FavoriteDbHelper( Main2Activity.this);
                            if(favoriteDbHelper.searchFavorite(movi_id)==0) {
                                SharedPreferences.Editor editor = getSharedPreferences("com.example.pcdas.moviee.activities.Main2Activity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Added", true);
                                editor.commit();
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to favorite", Snackbar.LENGTH_SHORT).show();
                           }
                            else
                            {
                               Snackbar.make(buttonView, "Already added to favorite", Snackbar.LENGTH_SHORT).show();
                           }

                        } else {
                            int movi_id = getIntent().getExtras().getInt("id");
                            favoriteDbHelper= new FavoriteDbHelper( Main2Activity.this);
                            favoriteDbHelper.deleteFavorite(movi_id);
                            SharedPreferences.Editor editor = getSharedPreferences("com.example.pcdas.moviee.activities.Main2Activity", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite Removed", true);
                            editor.commit();
                            Snackbar.make(buttonView, "Removed from favorite", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }
        public void saveFavorite ()
        {
            favoriteDbHelper = new FavoriteDbHelper(activity);
            favorite = new Item();
            int movie_id =getIntent().getExtras().getInt("id");
            String rate = getIntent().getExtras().getString("vote_average");
            String poster = getIntent().getExtras().getString("poster_path");
            favorite.setId(movie_id);
            favorite.setName(movieName2.getText().toString().trim());
            favorite.setRating(rate);
            favorite.setImage(poster);
            favorite.setDescription(description2.getText().toString().trim());
            favoriteDbHelper.addFavorite(favorite);
        }
    }


