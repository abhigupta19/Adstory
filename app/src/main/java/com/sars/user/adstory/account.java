package com.sars.user.adstory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class account extends AppCompatActivity {
 private String mautha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mautha=FirebaseAuth.getInstance().getCurrentUser().getUid();
       final TextView textView10=(TextView)findViewById(R.id.textView10);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mostafa = ref.child(mautha).child("acc");

        mostafa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.getValue(String.class);
                //do what you want with the email



                     textView10.setHint("Wait for server");

                     textView10.setText(title);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), bar.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
    }

