package com.aripir.nytimessearch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aripir.nytimessearch.models.FilterPreferences;

/**
 * Created by saripirala on 9/21/17.
 */

public class UserPreferencesDBHelper extends SQLiteOpenHelper {

    private static UserPreferencesDBHelper sInstance;

    public static synchronized UserPreferencesDBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new UserPreferencesDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private UserPreferencesDBHelper(Context context) {
        super(context, "user_preferences", null, 2);
    }

    @Override
    public void onCreate( SQLiteDatabase db) {
        db.execSQL("CREATE TABLE filter_preferences( id INTEGER PRIMARY KEY AUTOINCREMENT,  begin_date varchar(32),  sort varchar(32),  arts tinyint(1) DEFAULT 1,  fashion tinyint(1) DEFAULT 1,  sports tinyint(1) DEFAULT 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no impl
        db.execSQL("DROP TABLE IF EXISTS filter_preferences;");
        onCreate(db);
    }


    public void insertFilterPreferences(FilterPreferences filterPreferences)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("begin_date", filterPreferences.getBeginDate());
        contentValues.put("sort", filterPreferences.getSort());
        contentValues.put("arts", filterPreferences.isArts());
        contentValues.put("fashion", filterPreferences.isFashion());
        contentValues.put("sports", filterPreferences.isSports());

        this.getWritableDatabase().insertOrThrow("filter_preferences", "", contentValues);
    }

    public void insertFilterPreferences(String beginDate, String timeFrame, boolean arts, boolean fashion, boolean sports)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("begin_date", beginDate);
        contentValues.put("sort", timeFrame);
        contentValues.put("arts", arts);
        contentValues.put("fashion", fashion);
        contentValues.put("sports", sports);

        this.getWritableDatabase().insertOrThrow("filter_preferences", "", contentValues);
    }


    public FilterPreferences getUserPreferences()
    {
        FilterPreferences filterPreferences = new FilterPreferences();

        //Get the last inserted value
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM filter_preferences ORDER BY id DESC LIMIT 1", null);

        while (cursor.moveToNext()){
            filterPreferences.setBeginDate(cursor.getString(1));
            filterPreferences.setSort(cursor.getString(2));
            filterPreferences.setArts(cursor.getInt(3) > 0);
            filterPreferences.setFashion(cursor.getInt(4) > 0);
            filterPreferences.setSports(cursor.getInt(5) > 0);
            return filterPreferences;
        }

        return filterPreferences; // If nothing is found in DB, we return filterPreferences with defaults
    }


    public boolean isDBEmpty()
    {
        //Get the last inserted value
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM filter_preferences ORDER BY id DESC LIMIT 1", null);

        while (cursor.moveToNext()){
            return false;
        }

        return true; // Indicates that there are no records in the DB
    }

}
