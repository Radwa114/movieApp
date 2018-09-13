package com.example.pcdas.moviee.favorite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pcdas.moviee.model.Item;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + Favorites.FavoriteEntry.TABLE_NAME + " (" +
                Favorites.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Favorites.FavoriteEntry.COLUMN_MOVIEID + " INTEGER, " +
                Favorites.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                Favorites.FavoriteEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                Favorites.FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                Favorites.FavoriteEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Favorites.FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addFavorite(Item movie){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Favorites.FavoriteEntry.COLUMN_MOVIEID, movie.getId());
        values.put(Favorites.FavoriteEntry.COLUMN_TITLE, movie.getName());
        values.put(Favorites.FavoriteEntry.COLUMN_RATING, movie.getRating());
        values.put(Favorites.FavoriteEntry.COLUMN_POSTER_PATH, movie.getImage());
        values.put(Favorites.FavoriteEntry.COLUMN_DESCRIPTION, movie.getDescription());
        db.insert(Favorites.FavoriteEntry.TABLE_NAME, null, values);
        db.close();


    }

    public int searchFavorite(int ID)
    {
        String[] columns = {
                Favorites.FavoriteEntry._ID,
                Favorites.FavoriteEntry.COLUMN_MOVIEID,
                Favorites.FavoriteEntry.COLUMN_TITLE,
                Favorites.FavoriteEntry.COLUMN_RATING,
                Favorites.FavoriteEntry.COLUMN_POSTER_PATH,
                Favorites.FavoriteEntry.COLUMN_DESCRIPTION

        };
        String sortOrder =
                Favorites.FavoriteEntry._ID+ " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Favorites.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        if(cursor.getCount() > 0){
            // means search has returned data

            if (cursor.moveToFirst()) {
                do {
                    int mo_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Favorites.FavoriteEntry.COLUMN_MOVIEID)));
                    if (mo_id == ID)
                    {
                        return 1;
                    }

                } while (cursor.moveToNext());
            }

        }
        cursor.close();
        return 0;
    }
    public void deleteFavorite(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Favorites.FavoriteEntry.TABLE_NAME, Favorites.FavoriteEntry.COLUMN_MOVIEID+ "=" + ID, null);

    }

    public List<Item> getAllFavorite(){
        String[] columns = {
                Favorites.FavoriteEntry._ID,
                Favorites.FavoriteEntry.COLUMN_MOVIEID,
                Favorites.FavoriteEntry.COLUMN_TITLE,
                Favorites.FavoriteEntry.COLUMN_RATING,
                Favorites.FavoriteEntry.COLUMN_POSTER_PATH,
                Favorites.FavoriteEntry.COLUMN_DESCRIPTION

        };
        String sortOrder =
                Favorites.FavoriteEntry._ID+ " ASC";
        List<Item> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Favorites.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                Item movie = new Item();
               movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Favorites.FavoriteEntry.COLUMN_MOVIEID))));
                movie.setName(cursor.getString(cursor.getColumnIndex(Favorites.FavoriteEntry.COLUMN_TITLE)));
                movie.setRating(cursor.getString(cursor.getColumnIndex(Favorites.FavoriteEntry.COLUMN_RATING)));
                movie.setImage(cursor.getString(cursor.getColumnIndex(Favorites.FavoriteEntry.COLUMN_POSTER_PATH)));
                movie.setDescription(cursor.getString(cursor.getColumnIndex(Favorites.FavoriteEntry.COLUMN_DESCRIPTION)));

                favoriteList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }

}

