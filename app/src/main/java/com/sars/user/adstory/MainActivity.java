package com.sars.user.adstory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editTextMobile;
    private EditText editTextMobile1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(otp.PREFS_NAME, 0);
//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if(hasLoggedIn)
        {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,splash.class);
            startActivity(intent);
            MainActivity.this.finish();
            //Go directly to main activity.
        }






        editTextMobile = findViewById(R.id.editTextMobile);
        editTextMobile1 = findViewById(R.id.editTextMobile1);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();
                String name = editTextMobile1.getText().toString().trim();


                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, otp.class);
                intent.putExtra("mobile", mobile);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }
}
