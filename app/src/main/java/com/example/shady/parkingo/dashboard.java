package com.example.shady.parkingo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class dashboard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
    }
    public void openNew(View v){
        int id= v.getId();
        Intent intent=null;
        if(id==R.id.check_in)
                intent = new Intent(dashboard.this, checkin.class);
        else if(id==R.id.availability)
                intent = new Intent(dashboard.this, total_slot_status.class);
        else if(id==R.id.reprint)
                intent = new Intent(dashboard.this, ticket.class);
        else if(id==R.id.search)
                intent = new Intent(dashboard.this, search.class);
        else if(id==R.id.checkout)
                intent = new Intent(dashboard.this, checkout.class);
        else if(id==R.id.vehicle)
                intent = new Intent(dashboard.this, category_wise_availability.class);
        else if(id== R.id.logout)
                intent = new Intent(dashboard.this, login_activity.class);

        if(intent!=null) {
            startActivity(intent);
            finish();
        }
    }
}
