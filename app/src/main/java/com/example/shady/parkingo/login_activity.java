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
import android.widget.Toast;

public class login_activity extends Activity {
    EditText username,password;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login_btn=findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(login_activity.this,"button clicked",Toast.LENGTH_SHORT).show();

                String uname=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                if(uname.isEmpty() || pass.isEmpty()){
                    username.setError("Fields cannot be Empty");
                    username.requestFocus();
                }
                else{
                        sqlite_ops so=new sqlite_ops(login_activity.this);
                        //so.insert_login_details();
                    login li=new login();
                    li.setPass(uname);
                    li.setUname(pass);
                    if(so.fetch_login_details(li)){
                        Toast.makeText(login_activity.this,"Login_Success",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login_activity.this,dashboard.class));
                        finish();
                    }

                    else{
                        Toast.makeText(login_activity.this,"failed",Toast.LENGTH_SHORT).show();
                        username.setText("");
                        username.setError("Wrong Credentials");
                        password.setText("");
                        username.requestFocus();
                    }
                }
            }
        });
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
}
