package com.appsaga.lnmiitstationary;

        import android.animation.Animator;
        import android.animation.AnimatorListenerAdapter;
        import android.annotation.TargetApi;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.support.annotation.NonNull;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.app.LoaderManager.LoaderCallbacks;

        import android.content.CursorLoader;
        import android.content.Loader;
        import android.database.Cursor;
        import android.net.Uri;
        import android.os.AsyncTask;

        import android.os.Build;
        import android.os.Bundle;
        import android.provider.ContactsContract;
        import android.text.TextUtils;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.inputmethod.EditorInfo;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

        import static android.Manifest.permission.READ_CONTACTS;

public class UserSignUp extends AppCompatActivity{

    Button email_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        email_sign_up=(Button)findViewById(R.id.user_email_sign_up_button);

        email_sign_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(UserSignUp.this,com.appsaga.lnmiitstationary.ItemsList.class);
                startActivity(intent);
            }
        });
    }
}

