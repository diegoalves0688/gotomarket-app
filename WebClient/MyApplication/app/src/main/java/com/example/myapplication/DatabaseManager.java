package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    public DatabaseManager(Context context) {

        super(context, "inputs_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE INPUTS(" +
                "ID INTEGER PRIMARY KEY, " +
                " NAME TEXT, "+
                " DATE TEXT"+
                ")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Input expense){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues register = new ContentValues();

        register.put("NAME", expense.getName());

        register.put("DATE", expense.getDate());

        db.insert("INPUTS", null, register);

    }

    public void load(ArrayList contentList){

        contentList.clear();

        SQLiteDatabase db = getReadableDatabase();

        String cols[] = new String[9];

        cols[0] = "ID";
        cols[1] = "NAME";
        cols[2] = "DATE";

        Cursor cursor = db.query("INPUTS", cols,
                null, null, null, null, null);

        Boolean next;

        if(cursor == null){
            return;
        }else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String date = cursor.getString(2);

                Input input = new Input(id, name, date);

                contentList.add(input.toString());

                next = cursor.moveToNext();

            }
        }

    }

    public ArrayList<Input> search(String parameter){

        ArrayList<Input> contentList = new ArrayList<Input>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;

        if(parameter.equals("")){

            String cols[] = new String[6];

            cols[0] = "ID";
            cols[1] = "NAME";
            cols[2] = "DATE";

            cursor = db.query("INPUTS", cols,
                    null, null, null, null, null);
        }
        else {
            String[] args = {parameter};

            String sql = "SELECT * FROM INPUTS WHERE ID = ? ORDER BY ID ASC";

            cursor = db.rawQuery(sql, args);
        }

        Boolean next;

        if(cursor == null){
            return contentList;
        }
        else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String date = cursor.getString(2);

                Input expense = new Input(id, name, date);

                contentList.add(expense);

                next = cursor.moveToNext();

            }
        }

        return contentList;
    }

    public void updateItem(Input input){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("NAME",input.getName());
        cv.put("DATE",input.getDate());

        db.update("INPUTS", cv, "ID = ?", new String[]{String.valueOf(input.getId())});

    }

    public void clearData(){

        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM INPUTS";

        db.execSQL(sql);

    }

    public void deleteItem(String itemId){

        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM INPUTS WHERE ID = " +itemId;

        db.execSQL(sql);

    }
}
