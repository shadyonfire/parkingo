package com.example.shady.parkingo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class dashboard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Are you sure ? you want to exit.");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();

    }

    public void openNew(View v){
        int id= v.getId();
        Intent intent=null;
        if(id==R.id.check_in)
                intent = new Intent(dashboard.this, checkin.class);
        else if(id==R.id.availability)
                intent = new Intent(dashboard.this, developers.class);
        else if(id==R.id.reprint)
                intent = new Intent(dashboard.this, ticket.class);
        //else if(id==R.id.search)
         //       intent = new Intent(dashboard.this, search.class);
        else if(id==R.id.checkout)
                intent = new Intent(dashboard.this, checkout.class);
        else if(id==R.id.vehicle)
                intent = new Intent(dashboard.this, category_wise_availability.class);
        else if(id== R.id.logout)
                intent = new Intent(dashboard.this, login_activity.class);
        else if(id==R.id.curparks)
            intent = new Intent(dashboard.this, currentParks.class);

        if(intent!=null) {
            startActivity(intent);
            //finish();
            if(id==R.id.logout){
                Toast.makeText(this,"Thank You for coming",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
            }
}
