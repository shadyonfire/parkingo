package com.example.shady.parkingo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class category_wise_availability extends Activity {
    RadioGroup rg;
    String result;
    ListView lv;
    ArrayList slots;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_wise_availability_layout);
        rg=findViewById(R.id.rg);
        lv=findViewById(R.id.lv);
    }
    public void getAvailability(View v){
        boolean checked=((RadioButton)v).isChecked();
        if(v.getId()==R.id.car){
            if(checked){
                sqlite_ops so=new sqlite_ops(this);
                slots=so.getFreeSlots("car");
                if(slots.size()>0){
                    ArrayAdapter aa=new ArrayAdapter(this,R.layout.spinner_layout,slots);
                    lv.setAdapter(aa);
                }
                else{
                    ArrayAdapter aa=new ArrayAdapter(this,R.layout.spinner_layout,new String[]{"No free slots"});
                    lv.setAdapter(aa);
                }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(slots.size()>0){
                        result=slots.get(i).toString();
                        sendResult();
                        }
                    }
                });
            }

        }
        else if(v.getId()==R.id.bike){
            if(checked){
                sqlite_ops so=new sqlite_ops(this);
                slots=so.getFreeSlots("bike");
                if(slots.size()>0){
                    ArrayAdapter aa=new ArrayAdapter(this,R.layout.spinner_layout,slots);
                    lv.setAdapter(aa);

                }
                else{
                    ArrayAdapter aa=new ArrayAdapter(this,R.layout.spinner_layout,new String[]{"No free slots"});
                    lv.setAdapter(aa);
                }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(slots.size()>0) {
                            result = slots.get(i).toString();
                            sendResult();
                        }
                    }
                });
            }
        }


    }
    public void sendResult(){
        Intent returnResult=getIntent();
        returnResult.putExtra("result",result);
        setResult(RESULT_OK,returnResult);
        if(returnResult.hasExtra("callingActivity")) {
            finish();
        }
    }
}
