package com.example.GoToMarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    public DatabaseManager(Context context) {

        super(context, "user_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE USERS(" +
                "ID INTEGER PRIMARY KEY, " +
                " USER_ID TEXT, "+
                " NAME TEXT, "+
                " PASSWORD TEXT,"+
                " EMAIL TEXT,"+
                " ADDRESS TEXT,"+
                " PAYMENT_ID TEXT,"+
                " PAYMENT_KEY TEXT"+
                ")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser(User user){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues register = new ContentValues();

        register.put("USER_ID", user.getId());

        register.put("NAME", user.getName());

        register.put("PASSWORD", user.getPassword());

        register.put("EMAIL", user.getEmail());

        register.put("ADDRESS", user.getAddress());

        register.put("PAYMENT_ID", user.getPayment_id());

        register.put("PAYMENT_KEY", user.getPayment_key());

        db.insert("USERS", null, register);

    }

    public User loadUser(){

        SQLiteDatabase db = getReadableDatabase();

        String cols[] = new String[8];

        cols[0] = "ID";
        cols[1] = "USER_ID";
        cols[2] = "NAME";
        cols[3] = "PASSWORD";
        cols[4] = "EMAIL";
        cols[5] = "ADDRESS";
        cols[6] = "PAYMENT_ID";
        cols[7] = "PAYMENT_KEY";

        Cursor cursor = db.query("USERS", cols,
                null, null, null, null, null);

        Boolean next;

        if(cursor == null){
            return null;
        }else{

            next = cursor.moveToFirst();

            while(next){

                String userId = cursor.getString(1);
                String name = cursor.getString(2);
                String password = cursor.getString(3);
                String email = cursor.getString(4);
                String address = cursor.getString(5);
                String paymentId = cursor.getString(6);
                String paymentKey = cursor.getString(7);

                return new User(userId, name, password, email, address, paymentId, paymentKey);
            }
        }

        return null;

    }

    public void clearData(){

        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM USERS";

        db.execSQL(sql);

    }

}