package com.example.shady.parkingo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class currentParks extends Activity {
    ListView curparks;
    int[] ids={R.id.name,R.id.vehicle,R.id.slot,R.id.mobile};
    String[] keys={"name","vehicle","slot","mobile"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_parks);


        sqlite_ops so=new sqlite_ops(this);
        ArrayList al=so.getAllFilledSlotDetails();

        Log.d(TAG, "onCreate: "+al.get(0));
        curparks=findViewById(R.id.curparks);
        SimpleAdapter sa=new SimpleAdapter(this,al,R.layout.current_park_listview,keys,ids);
        curparks.setAdapter(sa);
    }
}
