package com.sars.user.adstory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class pshare extends AppCompatActivity {
ImageView imageView;
TextView t1,t2,t3,t4,t18,t25,t23,t26,t21;
    String code;
    Button button,buttonr;
    Information info;

private DatabaseReference f;
private FirebaseDatabase d;
    private static Uri imageUri = null;
    private RadioGroup radioGroup;
    private EditText editTextp;
    private FirebaseUser muserpaytm;
    private FirebaseDatabase databasepaytm;
    private DatabaseReference mypaytm;
    private FirebaseUser muserpho;
    private FirebaseDatabase databasepho;
    private DatabaseReference mypho;
    private FirebaseUser mgoogle;
    private FirebaseDatabase databasegoogle;
    private DatabaseReference mygoogle;
    private FirebaseUser mother;
    private FirebaseDatabase databaseother;
    private DatabaseReference myother;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pshare);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent ipshare=getIntent();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        code=ipshare.getStringExtra("code");
        databasepho = FirebaseDatabase.getInstance();
        databasepaytm = FirebaseDatabase.getInstance();
        databasegoogle = FirebaseDatabase.getInstance();
        databaseother= FirebaseDatabase.getInstance();
         radioGroup=(RadioGroup)findViewById(R.id.rado);
        buttonr=(Button)findViewById(R.id.buttonr);
        buttonr.setVisibility(View.INVISIBLE);
        imageView=(ImageView)findViewById(R.id.imageView2);
        t18=(TextView)findViewById(R.id.textView18);
        t1=(TextView)findViewById(R.id.textView3);
        t2=(TextView)findViewById(R.id.textView4);
        t3=(TextView)findViewById(R.id.textView33);
        t25=(TextView)findViewById(R.id.textView25);
        t23=(TextView)findViewById(R.id.textView23);
        t26=(TextView)findViewById(R.id.textView26);
        t21=(TextView)findViewById(R.id.textView21);
        //   t4=(TextView)findViewById(R.id.textView34);
        button=(Button)findViewById(R.id.button);

        button.setVisibility(View.INVISIBLE);
        d=FirebaseDatabase.getInstance();
        f=d.getReference().child("global");

        showdata();







    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), bar.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

    private void showdata() {

            info=new Information();

            f.child(code).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    info.setImage(dataSnapshot.getValue(Information.class).getImage());
                    info.setTitle(dataSnapshot.getValue(Information.class).getTitle());
                    info.setRate(dataSnapshot.getValue(Information.class).getRate());
                    info.setZomato(dataSnapshot.getValue(Information.class).getZomato());
                    info.setShar(dataSnapshot.getValue(Information.class).getShar());
                    info.setSlink(dataSnapshot.getValue(Information.class).getSlink());
                    info.setInst(dataSnapshot.getValue(Information.class).getInst());
                    String inst=info.getInst().toString();
                 t3.setText(inst);
                final String zom=info.getZomato().toString();
                String shar=info.getShar().toString();
                if(shar.equals("yes"))
                {
                    t18.setText("1.A poster must be shared on a single platform(Whatsapp,instagram,Facebook).");
                    t25.setText("2.Make sure you submit the  video within 24 hours of uploading any poster.");
                    t23.setText("3.A maximum of Rs 30 per poster will be given irrespective of more views.");
                    t26.setText("4.You will be paid for sharing a maximum of 3 posters per day i.e. you will be paid for 1st 3 poster you upload.");
                    t21.setText("5.Still if you have any queries feel free to contact us.");
                    button.setVisibility(View.VISIBLE);
                }
                if(zom.equals("yes"))
                {
                    t18.setText("1.Review should  be unique and genuine. For eg. you can appreciate some of the dishes so that your review looks genuine.");
                    t25.setText("2.Your review will be crosschecked and you will be paid only if your review is correct according to us. ");
                    t23.setText("3.Make sure you submit the screenshot of published review as soon as you post it.");
                    t26.setText("4.You will be paid for posting a maximum of 4 reviews per day i.e. you will be paid for 1st 4 reviews you post.");
                    t21.setText("5.Still if you have any queries feel free to contact us.");
                    buttonr.setVisibility(View.VISIBLE);
                    buttonr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse(info.getSlink()));
                            startActivity(intent);
                        }
                    });
                }

                    Picasso.with(pshare.this).load(info.getImage()).into(imageView);
                    t1.setText(info.getTitle());
                    t2.setText(info.getRate());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ifull = new Intent(pshare.this, fullp.class);
                    ifull.putExtra("url", info.getImage());
                    startActivity(ifull);
                }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetWorldReadable")
            @Override
            public void onClick(View view) {
                String url=info.getImage();
                Picasso.with(getApplicationContext()).load(url).into(new Target() {
                    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        myRef=database.getReference("global");

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("image/*");
                        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                        i.putExtra(Intent.EXTRA_TEXT,  "Download adstory app and earn money");
                        startActivity(Intent.createChooser(i, "Share Image"));
                        myRef.child(code).child("users").push().setValue(uid);
                    }
                    @Override public void onBitmapFailed(Drawable errorDrawable) { }
                    @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
                });
              /*
               Bitmap bitmap =getBitmapFromView(idForSaveView);
               try {
                   File file = new File(getFilesDir(),info.getImage()+".png");
                   ByteArrayOutputStream out = new ByteArrayOutputStream();
                   bitmap.compress(Bitmap.CompressFormat.PNG, 100,out);

                   file.setReadable(true, true);
                   final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                   intent.setType("image/png");
                   startActivity(Intent.createChooser(intent, "Share image via"));
               } catch (Exception e) {
                   e.printStackTrace();
               }
               */

            }
            public Uri getLocalBitmapUri(Bitmap bmp)
            {
                Uri bmpUri = null;
                try {
                    File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
                    FileOutputStream out = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.close();
                    bmpUri = Uri.fromFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bmpUri;
            }
            public boolean onOptionsItemSelected(MenuItem item){
                Intent myIntent = new Intent(getApplicationContext(), bar.class);
                startActivityForResult(myIntent, 0);
                return true;

            }
        });

    }




}
