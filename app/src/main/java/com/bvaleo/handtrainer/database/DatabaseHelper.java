package com.bvaleo.handtrainer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.bvaleo.handtrainer.model.Statistic;
import com.bvaleo.handtrainer.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valery on 18.03.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "statistics";

    private static final String USER_TABLE = "Users";
    private static final String USER_ID = "id";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASS = "password";

    private static final String STATISTIC_TABLE = "Statistics";
    private static final String STATISTIC_ID = "id";
    private static final String STATISTIC_ID_USER = "id_user";
    private static final String STATISTIC_COUNT = "count";
    private static final String STATISTIC_DESCRIPTION = "description";
    private static final String STATISTIC_TIME = "start_time";
    private static final String STATISTIC_DURATION = "duration";

    //create tables

    private static final String CREATE_USERS = "CREATE TABLE "
            + USER_TABLE + "(" + USER_ID + " INTEGER PRIMARY KEY," + USER_LOGIN
            + " TEXT," + USER_PASS + " TEXT" + ")";

    private static final String CREATE_STATISTICS = "CREATE TABLE "
            + STATISTIC_TABLE + "(" + STATISTIC_ID + " INTEGER PRIMARY KEY," + STATISTIC_ID_USER
            + " INTEGER," + STATISTIC_COUNT + " INTEGER," + STATISTIC_DESCRIPTION + " TEXT," + STATISTIC_TIME
            + " DATETIME," + STATISTIC_DURATION + " TEXT" + ")";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_USERS);
        sqLiteDatabase.execSQL(CREATE_STATISTICS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long createUser(String login, String pass){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_LOGIN, login);
        values.put(USER_PASS, pass);

        long userId = db.insert(USER_TABLE, null, values);

        Toast.makeText(context, "Регистрация прошла успешно.", Toast.LENGTH_SHORT).show();

        return userId;
    }

    public User getUser(String login, String pass){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = null;

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_LOGIN + " = '" + login + "' AND " + USER_PASS + " = '" + pass + "'";

        c = db.rawQuery(query, null);

        if(c != null && c.moveToFirst()) return new User(c.getLong(c.getColumnIndex(USER_ID)),
                c.getString(c.getColumnIndex(USER_LOGIN)),
                c.getString(c.getColumnIndex(USER_PASS)));
        else {

            return null;
        }

    }

    public void createStatistic(long userId, long count, String desc, String timestamp, String duration){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATISTIC_ID_USER, userId);
        values.put(STATISTIC_DESCRIPTION, desc);
        values.put(STATISTIC_COUNT, count);
        values.put(STATISTIC_TIME, timestamp);
        values.put(STATISTIC_DURATION, duration);
        db.insert(STATISTIC_TABLE, null, values);

        Log.d("DBHelper", "Статистика тренировки сохранена");

    }


    public List<Statistic> getStatisticByUserId(long id){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + STATISTIC_TABLE + " WHERE " + STATISTIC_ID_USER + " = " + id;

        Cursor c = db.rawQuery(query, null);

        List<Statistic> statistics = new ArrayList<>();

        if(c == null) return null;

        if (c.moveToFirst()) {
            do {
                Statistic stat = new Statistic();
                stat.setId(c.getLong(c.getColumnIndex(STATISTIC_ID)));
                stat.setUserId(id);
                stat.setCount(c.getLong(c.getColumnIndex(STATISTIC_COUNT)));
                stat.setComment(c.getString(c.getColumnIndex(STATISTIC_DESCRIPTION)));
                stat.setDate(c.getString(c.getColumnIndex(STATISTIC_TIME)));
                stat.setDuration(c.getString(c.getColumnIndex(STATISTIC_DURATION)));

                statistics.add(stat);
            } while (c.moveToNext());
        }

        return statistics;
    }
}
