package com.appsaga.lnmiitstationary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WhoYou extends AppCompatActivity {

    Button user;
    Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_you);

        user= findViewById(R.id.user);
        admin=findViewById(R.id.admin);

        user.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WhoYou.this,com.appsaga.lnmiitstationary.UserLogin.class);
                startActivity(intent);
            }
        });

        admin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent intent = new Intent(WhoYou.this,com.appsaga.lnmiitstationary.AdminLogin.class);
                startActivity(intent);
            }
        });
    }
}
