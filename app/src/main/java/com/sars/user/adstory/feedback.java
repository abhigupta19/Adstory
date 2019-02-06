package com.sars.user.adstory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class feedback extends AppCompatActivity {
private FirebaseUser muser;
private FirebaseDatabase database;
private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        final EditText editText=(EditText)findViewById(R.id.editText);
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback=editText.getText().toString();
                 if(feedback.isEmpty())
                 {
                     Toast.makeText(com.sars.user.adstory.feedback.this,"Please give important feedback",Toast.LENGTH_SHORT).show();

                 }
                 else {
                     Log.i("feed", feedback);
                     myRef = database.getReference(uid);
                     myRef.child("feedback").push().setValue(feedback);
                     editText.setText(null);
                     Toast.makeText(com.sars.user.adstory.feedback.this, "Thanks for feedback", Toast.LENGTH_SHORT).show();
                 }

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), bar.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
