package com.example.shady.parkingo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class checkin extends Activity {
    EditText name,mobile,vehicle,slot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin_layout);
        name=findViewById(R.id.name);
        slot=findViewById(R.id.slot);
        mobile=findViewById(R.id.mobile);
        vehicle=findViewById(R.id.vehicle);

    }

    public void getslot( View v) {
        Log.d(TAG, "getSlot: going to take result");
        Intent getslot = new Intent(checkin.this, category_wise_availability.class);
        getslot.putExtra("callingActivity","checkin");
        startActivityForResult(getslot, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: "+resultCode);
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                String res=data.getStringExtra("result");
                slot.setText(res);
            }
            else{
                slot.setError("no slot free");
            }
        }
    }
    public  void check_in_data(View view){
        checkin_details cd=new checkin_details();
        cd.setName(name.getText().toString().trim());
        cd.setMobile(Long.parseLong( mobile.getText().toString().trim()));
        cd.setVehicle_no(vehicle.getText().toString().trim());
        cd.setSlot(slot.getText().toString().trim());

        if(!cd.getName().isEmpty() && !cd.getSlot().isEmpty() && !cd.getVehicle_no().isEmpty()){
            sqlite_ops so=new sqlite_ops(this);
            if(so.check_in(cd)){
                Intent i=new Intent(checkin.this,ticket.class);
                i.putExtra("ticket_no",so.lastIndexRecords());
                startActivity(i);
            }
            else{
                Toast.makeText(this,"Error in inserting data",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"Fields can not be empty",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,dashboard.class));
        finish();
    }
}
