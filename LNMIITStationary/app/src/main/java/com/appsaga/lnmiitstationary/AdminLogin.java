package com.appsaga.lnmiitstationary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AdminLogin extends AppCompatActivity {

    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        signup=(TextView)findViewById(R.id.admin_sign_up);

        signup.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminLogin.this,com.appsaga.lnmiitstationary.AdminSignUp.class);
                startActivity(intent);
            }
        });
    }

}

