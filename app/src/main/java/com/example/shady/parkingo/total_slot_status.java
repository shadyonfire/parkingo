package com.example.shady.parkingo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class total_slot_status extends Activity {
    RadioGroup rg;
    String result;
    ListView lv;
    ArrayList slots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_slot_status_layout);
        rg=findViewById(R.id.rg);
        lv=findViewById(R.id.lv);
    }

    public void getTotalSlots(View v) {
        boolean checked=((RadioButton)v).isChecked();
        if(v.getId()==R.id.car){
            if(checked){
                sqlite_ops so=new sqlite_ops(this);
                slots=so.getAllSlots("car");

                if(slots.size()>0){
                    ArrayList values=new ArrayList();
                    for(int i=0;i<slots.size();i++){
                        values.add(((HashMap)(slots.get(i))).get("slot"));
                    }
                    ArrayAdapter aa=new ArrayAdapter(this,R.layout.spinner_layout,values);
                    lv.setAdapter(aa);
                }


            }

        }
        else if(v.getId()==R.id.bike){
            if(checked){
                sqlite_ops so=new sqlite_ops(this);
                slots=so.getAllSlots("bike");
                if(slots.size()>0){
                    ArrayList values=new ArrayList();
                    for(int i=0;i<slots.size();i++){
                        values.add(((HashMap)(slots.get(i))).get("slot"));
                    }
                    ArrayAdapter aa=new ArrayAdapter(this,R.layout.spinner_layout,values);
                    lv.setAdapter(aa);

                }


            }
        }

    }
}
