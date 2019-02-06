package com.sars.user.adstory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class payshot extends AppCompatActivity {
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
    private StorageReference mStorageRef;
    String uid;
    File file;
    String code;
    String videopath;
    ProgressDialog progressDoalog;
    TextInputLayout inputLayoutName;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payshot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databasepho = FirebaseDatabase.getInstance();
        databasepaytm = FirebaseDatabase.getInstance();
        databasegoogle = FirebaseDatabase.getInstance();
        databaseother = FirebaseDatabase.getInstance();
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        radioGroup = (RadioGroup) findViewById(R.id.rado);
        Intent pay= getIntent();
        videopath = pay.getStringExtra("videopath");
        code=pay.getStringExtra("code");


        file = new File(String.valueOf(videopath));
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://adstory.appspot.com/global");

        database = FirebaseDatabase.getInstance();

        final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Button button2 = findViewById(R.id.button28);
        final Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDoalog.incrementProgressBy(1);
            }
        };

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDoalog = new ProgressDialog(payshot.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage("Its loading....");
                progressDoalog.setTitle("ProgressDialog");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDoalog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (progressDoalog.getProgress() <= progressDoalog
                                    .getMax()) {

                                if(progressDoalog.getProgress()==99)
                                {
                                    Intent ibarr=new Intent(payshot.this,bar.class);
                                    startActivity(ibarr);
                                }

                                Thread.sleep(70);
                                handle.sendMessage(handle.obtainMessage());
                                if (progressDoalog.getProgress() == progressDoalog
                                        .getMax()) {
                                    progressDoalog.dismiss();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

              //  Toast.makeText(payshot.this,"Done",Toast.LENGTH_SHORT).show();


            }
        });
    }
    public void payo(View view) {
        int rid = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(rid);
        switch (rid) {
            case R.id.paytm:
                AlertDialog.Builder builder = new AlertDialog.Builder(payshot.this);
                View mview = getLayoutInflater().inflate(R.layout.paytm, null);


                editTextp = (EditText) mview.findViewById(R.id.editText4);

                editTextp.addTextChangedListener(new MyTextWatcher(editTextp));


                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=editTextp.getText().toString().trim();
                        if (s.isEmpty() || s.length() < 10) {
                            editTextp.setError("Enter a valid mobile");
                            Toast.makeText(payshot.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                            editTextp.requestFocus();
                            return;
                        }
                        Toast.makeText(payshot.this, "Done", Toast.LENGTH_SHORT).show();
                        String ui = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        mypaytm = databasepaytm.getReference(ui);
                        mypaytm.child("paytm").push().setValue(editTextp.getText().toString());
                    }
                });


                builder.setView(mview);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                break;
            case R.id.bank:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(payshot.this);
                View mview1 = getLayoutInflater().inflate(R.layout.bank, null);
                final EditText editTextb1 = (EditText) mview1.findViewById(R.id.editText44);
                editTextb1.addTextChangedListener(new MyTextWatcher(editTextb1));
                final EditText editTextb2 = (EditText) mview1.findViewById(R.id.editText5);
                final EditText editTextb3 = (EditText) mview1.findViewById(R.id.editText6);
                editTextb2.addTextChangedListener(new MyTextWatcher(editTextb2));
                editTextb3.addTextChangedListener(new MyTextWatcher(editTextb3));
                builder1.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(payshot.this, "Done", Toast.LENGTH_SHORT).show();
                        String ui = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        mypho = databasepho.getReference(ui);
                        mypho.child("Account no").push().setValue(editTextb1.getText().toString());
                        mypho.child("IFSC code").push().setValue(editTextb2.getText().toString());
                        mypho.child("Account name").push().setValue(editTextb3.getText().toString());

                    }
                });


                builder1.setView(mview1);
                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();

                break;

            case R.id.phonepay:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(payshot.this);
                View mview2 = getLayoutInflater().inflate(R.layout.phonepe, null);
                final EditText editTextp2 = (EditText) mview2.findViewById(R.id.editText45);
                editTextp2.addTextChangedListener(new MyTextWatcher(editTextp2));
                builder2.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=editTextp2.getText().toString().trim();
                        if (s.isEmpty() || s.length() < 10) {
                            editTextp2.setError("Enter a valid mobile");
                            Toast.makeText(payshot.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                            editTextp2.requestFocus();
                            return;
                        }
                        Toast.makeText(payshot.this, "Done", Toast.LENGTH_SHORT).show();
                        String ui = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        mypho = databasepho.getReference(ui);
                        mypho.child("phonepay").push().setValue(editTextp2.getText().toString());
                    }
                });


                builder2.setView(mview2);
                AlertDialog alertDialog2 = builder2.create();
                alertDialog2.show();

                break;
            case R.id.googlepay:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(payshot.this);
                View mview3 = getLayoutInflater().inflate(R.layout.googlepay, null);
                final EditText editTextp3 = (EditText) mview3.findViewById(R.id.editText46);
                editTextp3.addTextChangedListener(new MyTextWatcher(editTextp3));
                builder3.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=editTextp3.getText().toString().trim();
                        if (s.isEmpty() || s.length() < 10) {
                            editTextp3.setError("Enter a valid mobile");
                            Toast.makeText(payshot.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                            editTextp3.requestFocus();
                            return;
                        }
                        Toast.makeText(payshot.this, "Done", Toast.LENGTH_SHORT).show();
                        String ui = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        mygoogle = databasegoogle.getReference(ui);
                        mygoogle.child("tez").push().setValue(editTextp3.getText().toString());

                    }
                });


                builder3.setView(mview3);
                AlertDialog alertDialog3 = builder3.create();
                alertDialog3.show();

                break;
            case R.id.other:
                AlertDialog.Builder builder4 = new AlertDialog.Builder(payshot.this);
                View mview4 = getLayoutInflater().inflate(R.layout.other, null);
                final EditText editTextp4 = (EditText) mview4.findViewById(R.id.editText47);
                editTextp4.addTextChangedListener(new MyTextWatcher(editTextp4));
                builder4.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=editTextp4.getText().toString().trim();
                        if (s.isEmpty() || s.length() < 10) {
                            editTextp4.setError("Enter a valid mobile");
                            Toast.makeText(payshot.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                            editTextp4.requestFocus();
                            return;
                        }
                        Toast.makeText(payshot.this, "Done", Toast.LENGTH_SHORT).show();
                        String ui = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        myother = databaseother.getReference(ui);
                        myother.child("other").push().setValue(editTextp4.getText().toString());
                    }
                });


                builder4.setView(mview4);
                AlertDialog alertDialog4 = builder4.create();
                alertDialog4.show();
                break;
        }

    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), screenshot.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
