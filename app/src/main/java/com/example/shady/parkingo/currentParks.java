package com.example.shady.parkingo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class currentParks extends Activity {
    ListView curparks;
    ArrayList al;
    LinearLayout ll;
    int[] ids={R.id.name,R.id.vehicle,R.id.slot,R.id.mobile};
    String[] keys={"name","vehicle","slot","mobile"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_parks);
        ll=findViewById(R.id.ll);

        sqlite_ops so=new sqlite_ops(this);
        al=so.getAllFilledSlotDetails();

        curparks=findViewById(R.id.curparks);
        if(al.size()>0){
            SimpleAdapter sa=new SimpleAdapter(this,al,R.layout.current_park_listview,keys,ids);
            curparks.setAdapter(sa);
        }
        else{
            Toast.makeText(this,"Currently No Parking",Toast.LENGTH_SHORT  ).show();
        }

        curparks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String slot_number=((HashMap)al.get(i)).get("slot").toString();
                Intent intent=new Intent(currentParks.this,checkout.class);
                intent.putExtra("slot",slot_number);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,dashboard.class));
    }
}
