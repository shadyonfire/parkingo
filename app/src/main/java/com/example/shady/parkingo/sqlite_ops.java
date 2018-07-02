package com.example.shady.parkingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class sqlite_ops extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="parkingo.db";

    public sqlite_ops(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "sqlite_ops:database created ");
        insert_login_details();
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table admin(username varchar unique,password varchar)");
        db.execSQL("create table slots(id integer primary key autoincrement ,slot varchar unique,status int,category text)");
        db.execSQL("create table records(tkt_no integer  primary key autoincrement,name varchar,mobile int,slot varchar,check_in timestamp,check_out timestamp,isempty int)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists admin ; ");
        sqLiteDatabase.execSQL("drop table if exists slots;");
        sqLiteDatabase.execSQL("drop table if exists records;");

        onCreate(sqLiteDatabase);
    }

    public void insert_login_details(){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username","shady");
        values.put("password","shady");
        long id=db.insert("admin",null,values);
        if(id!=0){
            Log.d(TAG, "insert_login_details: inserted");
        }
    }

    public boolean fetch_login_details(login li){
        SQLiteDatabase db= this.getReadableDatabase();
        Log.d(TAG, "fetch_login_details: "+li.getUname()+" "+li.getPass());
        Cursor cursor = db.query("admin",new String[]{"username","password"},"username"+ "=?",
                new String[]{li.getUname()}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if(cursor.getCount()!=0){
            String pass = cursor.getString(cursor.getColumnIndex("password"));

            if (pass.equals(li.getPass())) {
                return true;
            } else {
                Log.d(TAG, "fetch_login_details: not equals "+pass);
                return false;
            }
            }
            else {
                return false;
            }
        }
        else {
            Log.d(TAG, "fetch_login_details: nothing selected");
            return false;
        }




    }
}