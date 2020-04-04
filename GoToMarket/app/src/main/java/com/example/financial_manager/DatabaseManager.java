package com.example.financial_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    public DatabaseManager(Context context) {

        super(context, "expenses_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE EXPENSES(" +
                "ID INTEGER PRIMARY KEY, " +
                " NAME TEXT, "+
                " CATEGORY TEXT,"+
                " VALUE INTEGER,"+
                " STARTDATE TEXT,"+
                " INSTALLMENTS INTEGER,"+
                " INSTALLMENT INTEGER,"+
                " MONTH INTEGER,"+
                " YEAR INTEGER"+
                ")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Expense expense){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues register = new ContentValues();

        register.put("NAME", expense.getName());

        register.put("CATEGORY", expense.getCategory());

        register.put("VALUE", expense.getValue());

        register.put("STARTDATE", expense.getStartDate());

        register.put("INSTALLMENTS", expense.getInstallments());

        register.put("INSTALLMENT", expense.getInstallment());

        register.put("MONTH", expense.getMonth());

        register.put("YEAR", expense.getYear());

        db.insert("EXPENSES", null, register);

    }

    public long load(ArrayList expenseList){

        long total = 0;

        expenseList.clear();

        SQLiteDatabase db = getReadableDatabase();

        String cols[] = new String[9];

        cols[0] = "ID";
        cols[1] = "NAME";
        cols[2] = "CATEGORY";
        cols[3] = "VALUE";
        cols[4] = "STARTDATE";
        cols[5] = "INSTALLMENTS";
        cols[6] = "INSTALLMENT";
        cols[7] = "MONTH";
        cols[8] = "YEAR";

        Cursor cursor = db.query("EXPENSES", cols,
                null, null, null, null, null);

        Boolean next;

        if(cursor == null){
            return 0;
        }else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int value = cursor.getInt(3);
                String startDate = cursor.getString(4);
                int installments = cursor.getInt(5);
                int installment = cursor.getInt(6);
                int month = cursor.getInt(7);
                int year = cursor.getInt(8);

                Expense expense = new Expense(id, name, category, value, startDate,
                        installments, installment, month, year);

                total = total + value;

                expenseList.add(expense.toString());

                next = cursor.moveToNext();

            }
        }

        return total;

    }

    public long search(ArrayList expenseList, String parameter){

        long total = 0;

        expenseList.clear();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;

        if(parameter.equals("")){

            String cols[] = new String[9];

            cols[0] = "ID";
            cols[1] = "NAME";
            cols[2] = "CATEGORY";
            cols[3] = "VALUE";
            cols[4] = "STARTDATE";
            cols[5] = "INSTALLMENTS";
            cols[6] = "INSTALLMENT";
            cols[7] = "MONTH";
            cols[8] = "YEAR";

            cursor = db.query("EXPENSES", cols,
                    null, null, null, null, null);
        }
        else {
            String[] args = {parameter};

            String sql = "SELECT * FROM EXPENSES WHERE NAME = ? ORDER BY ID ASC";

            cursor = db.rawQuery(sql, args);
        }

        Boolean next;

        if(cursor == null){
            return 0;
        }
        else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int value = cursor.getInt(3);
                String startDate = cursor.getString(4);
                int installments = cursor.getInt(5);
                int installment = cursor.getInt(6);
                int month = cursor.getInt(7);
                int year = cursor.getInt(8);

                Expense expense = new Expense(id, name, category, value, startDate,
                        installments, installment, month, year);

                total = total + value;

                expenseList.add(expense.toString());

                next = cursor.moveToNext();

            }
        }

        return total;

    }

    public long searchByMonth(ArrayList expenseList, String monthParam){

        long total = 0;

        long param = parseMonth(monthParam);

        expenseList.clear();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;

        String[] args = {String.valueOf(param)};

        String sql = "SELECT * FROM EXPENSES WHERE MONTH = ? ORDER BY ID ASC";

        cursor = db.rawQuery(sql, args);

        Boolean next;

        if(cursor == null){
            return 0;
        }
        else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int value = cursor.getInt(3);
                String startDate = cursor.getString(4);
                int installments = cursor.getInt(5);
                int installment = cursor.getInt(6);
                int month = cursor.getInt(7);
                int year = cursor.getInt(8);

                Expense expense = new Expense(id, name, category, value, startDate,
                        installments, installment, month, year);

                total = total + value;

                expenseList.add(expense.toString());

                next = cursor.moveToNext();

            }
        }

        return total;

    }

    public long searchByMonth(String monthParam){

        long total = 0;

        long param = parseMonth(monthParam);

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;

        String[] args = {String.valueOf(param)};

        String sql = "SELECT * FROM EXPENSES WHERE MONTH = ? ORDER BY ID ASC";

        cursor = db.rawQuery(sql, args);

        Boolean next;

        if(cursor == null){
            return 0;
        }
        else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int value = cursor.getInt(3);
                String startDate = cursor.getString(4);
                int installments = cursor.getInt(5);
                int installment = cursor.getInt(6);
                int month = cursor.getInt(7);
                int year = cursor.getInt(8);

                Expense expense = new Expense(id, name, category, value, startDate,
                        installments, installment, month, year);

                total = total + value;

                next = cursor.moveToNext();

            }
        }

        return total;

    }

    public ArrayList<Expense> search(String col, String parameter){

        ArrayList<Expense> expenseList = new ArrayList<Expense>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;

        if(parameter.equals("")){

            String cols[] = new String[6];

            cols[0] = "ID";
            cols[1] = "NAME";
            cols[2] = "CATEGORY";
            cols[3] = "VALUE";
            cols[4] = "STARTDATE";
            cols[5] = "INSTALLMENTS";
            cols[6] = "INSTALLMENT";
            cols[7] = "MONTH";
            cols[8] = "YEAR";

            cursor = db.query("EXPENSES", cols,
                    null, null, null, null, null);
        }
        else {
            String[] args = {col, parameter};

            String sql = "SELECT * FROM EXPENSES WHERE " + col +
                    " = " + parameter + " ORDER BY ID ASC";

            cursor = db.rawQuery(sql,null);
        }

        Boolean next;

        if(cursor == null){
            return expenseList;
        }
        else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int value = cursor.getInt(3);
                String startDate = cursor.getString(4);
                int installments = cursor.getInt(5);
                int installment = cursor.getInt(6);
                int month = cursor.getInt(7);
                int year = cursor.getInt(8);

                Expense expense = new Expense(id, name, category, value, startDate,
                        installments, installment, month, year);

                expenseList.add(expense);

                next = cursor.moveToNext();

            }
        }

        return expenseList;
    }

    public void updateItem(Expense expense){

        SQLiteDatabase db = getWritableDatabase();

        String sql =
                "UPDATE EXPENSES SET" +
                " NAME = " + expense.getName() +
                ", CATEGORY = " + expense.getCategory() +
                ", VALUE = " + expense.getValue() +
                ", STARTDATE = " + expense.getStartDate() +
                ", INSTALLMENTS = " + expense.getInstallments() +
                ", INSTALLMENT = " + expense.getInstallment() +
                ", MONTH = " + expense.getMonth() +
                ", YEAR = " + expense.getYear() +
                " WHERE ID = " + expense.getId();

        ContentValues cv = new ContentValues();
        cv.put("NAME",expense.getName());
        cv.put("CATEGORY",expense.getCategory());
        cv.put("VALUE",expense.getValue());
        cv.put("STARTDATE",expense.getStartDate());
        cv.put("INSTALLMENTS",expense.getInstallments());
        cv.put("INSTALLMENT",expense.getInstallment());
        cv.put("MONTH",expense.getMonth());
        cv.put("YEAR",expense.getYear());

        db.update("EXPENSES", cv, "ID = ?", new String[]{String.valueOf(expense.getId())});

        //db.execSQL(sql);

    }

    public void clearData(){

        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM EXPENSES";

        db.execSQL(sql);

    }

    public long parseMonth(String month){

        month = month.toLowerCase();

        if(month.equals("january"))
            return 1;
        else if(month.equals("february"))
            return 2;
        else if(month.equals("march"))
            return 3;
        else if(month.equals("april"))
            return 4;
        else if(month.equals("may"))
            return 5;
        else if(month.equals("june"))
            return 6;
        else if(month.equals("july"))
            return 7;
        else if(month.equals("august"))
            return 8;
        else if(month.equals("september"))
            return 9;
        else if(month.equals("october"))
            return 10;
        else if(month.equals("november"))
            return 11;
        else if(month.equals("december"))
            return 12;
        return 0;
    }
}
