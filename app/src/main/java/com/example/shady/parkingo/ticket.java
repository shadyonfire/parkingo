package com.example.shady.parkingo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ticket extends Activity {
    TextView slot,tkt,time_tv,date_tv,vehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_layout);

        slot=findViewById(R.id.slot);
        vehicle=findViewById(R.id.vehicle);
        tkt=findViewById(R.id.ticket);
        time_tv=findViewById(R.id.time_tv);
        date_tv=findViewById(R.id.date_tv);
        Intent i=getIntent();
        long ticket_no;
        if(i.hasExtra("ticket_no")){
            Bundle b=i.getExtras();
            ticket_no=b.getLong("ticket_no");
        }
        else{
            ticket_no=10000;
        }

        sqlite_ops so=new sqlite_ops(this);
        ArrayList data=so.getTicketDetails(ticket_no);
        slot.setText(data.get(1).toString());
        tkt.setText(data.get(0).toString());
        String date_data=data.get(3).toString();
        String[] dateandtime=date_data.split(" ");
        date_tv.setText(dateandtime[0]);
        time_tv.setText(dateandtime[1]);
        vehicle.setText(data.get(2).toString());

    }

    public void Print(View view) {
        Toast.makeText(this,"Printing Ticket",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,dashboard.class));
        finish();
    }
}
