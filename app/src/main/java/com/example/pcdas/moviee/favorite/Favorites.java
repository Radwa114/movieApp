package com.example.pcdas.moviee.favorite;
import android.provider.BaseColumns;
public class Favorites {
    public static final class FavoriteEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_DESCRIPTION = "overview";
    }
}
