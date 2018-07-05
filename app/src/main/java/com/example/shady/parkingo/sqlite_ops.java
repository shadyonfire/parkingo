package com.example.shady.parkingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class sqlite_ops extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="parkingo.db";

    public sqlite_ops(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table admin(username varchar unique,password varchar)");
        db.execSQL("create table slots(id integer primary key autoincrement ,slot varchar unique,status int,category text)");
        db.execSQL("create table records(tkt_no integer  primary key autoincrement,name varchar,mobile int,slot varchar,vehicle varchar,check_in timestamp,check_out timestamp,isempty int)");
        db.execSQL("insert into admin values('shady','shady')");
        db.execSQL("insert into records values(10000,'Sample',100000000,'Sample','UP51SAMPLE',current_timestamp,null,1)");
        for(int i=0;i<5;i++){
            for(int j=0;j<15;j++){
                char s=(char)(i+65);
                String slot=Character.toString(s);
                if(j<10)
                    slot+="0";
                slot+=j;
                if(i<2)
                    db.execSQL("insert into slots values(null,'"+slot+"',0,'BIKE')");
                else
                    db.execSQL("insert into slots values(null,'"+slot+"',0,'CAR')");
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists admin ; ");
        sqLiteDatabase.execSQL("drop table if exists slots;");
        sqLiteDatabase.execSQL("drop table if exists records;");

        onCreate(sqLiteDatabase);

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
                db.close();
                return true;

            } else {
                Log.d(TAG, "fetch_login_details: not equals "+pass);
                db.close();
                return false;
            }
            }
            else {
                db.close();
                return false;
            }
        }
        else {
            Log.d(TAG, "fetch_login_details: nothing selected");
            db.close();
            return false;
        }
    }

    public boolean check_in(checkin_details cd){
        SQLiteDatabase db= this.getWritableDatabase();
        String INSERT_RECORDS="insert into records values(null,'"+cd.getName()+"',"+ cd.getMobile()+",'"+cd.getSlot()+"','"+cd.getVehicle_no()+"',current_timestamp,null,0)";
        db.execSQL(INSERT_RECORDS);
        String FILL_SLOT="update slots set status=1 where slot='"+cd.getSlot()+"'";
        db.execSQL(FILL_SLOT);
        Log.d(TAG, "check_in: data inserted");
        db.close();
        return true;
    }
    public boolean check_out(checkin_details cd){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.query("records",new String[]{"slot"},"tkt_no"+ "=?",
                new String[]{String.valueOf(cd.getTicket())}, null, null, null, null);
        String slot="0";
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                slot= cursor.getString(cursor.getColumnIndex("slot"));
            }
        }

                String UPDATE_RECORDS="update records set isempty=1 where slot='"+slot+"'";
        db.execSQL(UPDATE_RECORDS);
        String UNFILL_SLOT="update records set status=0 where slot='"+slot+"'";
        db.execSQL(UNFILL_SLOT);
        Log.d(TAG, "check_in: data inserted");
        db.close();
        return true;
    }
    public ArrayList getFreeSlots(String category){
        String[] cat=new String[]{category.toUpperCase(),"0"};
        ArrayList slots=new ArrayList();
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.query("slots",new String[]{"slot"},"category=? and status=?",
                cat, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.getCount()!=0){
            do{
                String data = cursor.getString(cursor.getColumnIndex("slot"));
                slots.add(data);
                // do what ever you want here
            }while(cursor.moveToNext());
            }

        }
        db.close();
        return slots;
    }
    public ArrayList getAllSlots(String category){
        String[] cat=new String[]{category.toUpperCase()};
        ArrayList slots=new ArrayList();
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.query("slots",new String[]{"slot","status"},"category=?",
                cat, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.getCount()!=0){
                do{
                    HashMap hm=new HashMap();
                    hm.put("slot", cursor.getString(cursor.getColumnIndex("slot")));
                    hm.put("status",cursor.getString(cursor.getColumnIndex("status")));
                    slots.add(hm);
                    // do what ever you want here
                }while(cursor.moveToNext());
            }

        }
        db.close();
        return slots;
    }

    public ArrayList getTicketDetails(long ticket_no) {
        ArrayList al=new ArrayList();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cr=db.query("records",new String []{"tkt_no","slot","vehicle","check_in"},"tkt_no=?",new String[]{Long.toString(ticket_no)},null,null,null,null);
        if(cr!=null){
            cr.moveToFirst();
            if(cr.getCount()>0){
                al.add(0,cr.getString(cr.getColumnIndex("tkt_no")));
                al.add(1,cr.getString(cr.getColumnIndex("slot")));
                al.add(2,cr.getString(cr.getColumnIndex("vehicle")));
                al.add(3,cr.getString(cr.getColumnIndex("check_in")));
            }
            else{
                al.add(0,ticket_no);
                al.add("");
                al.add("");
                al.add("");

            }

        }
        db.close();
        return al;
    }

    public  long lastIndexRecords(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.query("records",new String[]{"tkt_no"},"",new String[]{},null,null,"tkt_no DESC","1");
        c.moveToFirst();
        Long res=Long.parseLong(c.getString(c.getColumnIndex("tkt_no")));
        db.close();
        return res;

    }
}