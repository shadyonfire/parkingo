package com.example.shady.parkingo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class checkout extends Activity {
    EditText tkt_no,slot_no;
    TextView name,mobile,date_tv,slot,vehicle,tkt_tv,ischeckedout;
    Button checkout;
    String slot_number,ticket_number;
    LinearLayout innerll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_layout);
        tkt_no=findViewById(R.id.tkt);
        slot_no=findViewById(R.id.slot_no);

        name=findViewById(R.id.name);
        slot=findViewById(R.id.slot);
        mobile=findViewById(R.id.mobile);
        date_tv=findViewById(R.id.date_tv);
        vehicle=findViewById(R.id.vehicle);
        tkt_tv=findViewById(R.id.tkt_tv);
        ischeckedout=findViewById(R.id.ischeckedout);
        checkout=findViewById(R.id.checkout);
        innerll=findViewById(R.id.innerll);


        Intent i=getIntent();
        if(i.hasExtra("slot")){
            slot_no.setText(i.getExtras().get("slot").toString());

            getSlotDetails(null);
        }
    }
    //abcdefghijklmnopqrstuvwxyzchecking
    @Override
    public void onBackPressed() {
        Intent i=getIntent();
        if(i.hasExtra("slot")){
            startActivity(new Intent(this,currentParks.class));
        }
        else {
            startActivity(new Intent(this, dashboard.class));
            finish();
        }
        //super.onBackPressed();
    }

    public void confirm_checkout(){
        if(ticket_number!=null && slot_number!=null){
            // Log.e(TAG, "checkout: "+slot_number );
            if(slot_number.equals("none")){
                Toast.makeText(this,"Select Valid Ticket",Toast.LENGTH_SHORT).show();
            }
            else{
                sqlite_ops so=new sqlite_ops(this);
                if(so.check_out(slot_number,Long.parseLong(ticket_number))){
                    Toast.makeText(this,"Successfully,Checked Out",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"Error! Contact Developer",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void checkout(View view) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Do you want to Checkout?");
        builder.setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                confirm_checkout();
            }
        })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    public void reprint(View view) {
        //Log.e(TAG, "reprint: "+ticket_number );
        if(ticket_number!=null){
            if(slot_number.equals("none")){
                Toast.makeText(this,"Select Valid Ticket",Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(this, ticket.class);
                i.putExtra("ticket_no", Long.parseLong(ticket_number));
                startActivity(i);
            }
        }
    }

    public void getTktDetails(View view) {
        String tkt=tkt_no.getText().toString().trim();
        if(!tkt.isEmpty()){
            long tktlong=Long.parseLong(tkt);
            sqlite_ops so=new sqlite_ops(this);
            ArrayList data=so.fetch_ticket_detail(tktlong);
            name.setText(data.get(4).toString());
            slot.setText(data.get(1).toString());
            date_tv.setText(data.get(3).toString());
            vehicle.setText(data.get(2).toString());
            mobile.setText(data.get(5).toString());
            tkt_tv.setText(data.get(0).toString());
            String checkout_status=data.get(6).toString();
            if(checkout_status.equals("1")){
                ischeckedout.setText("Checked Out!!!");
                ischeckedout.setVisibility(View.VISIBLE);
                checkout.setVisibility(View.GONE);
            }
            slot_number=data.get(1).toString();
            ticket_number=data.get(0).toString();
            innerll.setVisibility(View.VISIBLE);
        }
    }

    public void getSlotDetails(View view) {
        String slotStr=slot_no.getText().toString().trim();
        if(!slotStr.isEmpty()){

            sqlite_ops so=new sqlite_ops(this);
            ArrayList data=so.fetch_slot_details(slotStr.toUpperCase());
            name.setText(data.get(4).toString());
            slot.setText(data.get(1).toString());
            date_tv.setText(data.get(3).toString());
            vehicle.setText(data.get(2).toString());
            mobile.setText(data.get(5).toString());
            tkt_tv.setText(data.get(0).toString());

            slot_number=data.get(1).toString();
            ticket_number=data.get(0).toString();
            innerll.setVisibility(View.VISIBLE);
        }
    }
}
