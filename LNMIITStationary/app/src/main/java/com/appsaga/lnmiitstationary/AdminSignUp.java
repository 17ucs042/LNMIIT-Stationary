package com.appsaga.lnmiitstationary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AdminSignUp extends AppCompatActivity{

    Button email_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        email_sign_up=(Button)findViewById(R.id.admin_email_sign_up_button);

        email_sign_up.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(AdminSignUp.this,com.appsaga.lnmiitstationary.Orders.class);
                startActivity(intent);
            }
        });
    }
}

