package com.sars.user.adstory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class fullp extends AppCompatActivity {
ImageView imageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView3=(ImageView)findViewById(R.id.imageView3);
        Intent ifull=getIntent();
        String url=ifull.getStringExtra("url");
        Picasso.with(this).load(url).into(imageView3);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), bar
                .class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
