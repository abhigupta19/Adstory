package com.sars.user.adstory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class save extends AppCompatActivity {
    ImageView imageView;
    TextView t1, t2;
    String code,filepath;
    Button buttonn;
    String videopath,ab;
    EditText editText9;
    Spinner spinner;
    Information info;
    EditText editTextw,editTexti,editTextf;
    private StorageReference mStorageRef;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    String[] country = { "Whatsapp","Instagram","Facebook"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttonn=findViewById(R.id.buttonn);
        Intent isavee = getIntent();
        editText9=findViewById(R.id.editText9);
            spinner=findViewById(R.id.spinner3);
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.bgg,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
           spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ab=spinner.getSelectedItem().toString();

               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });
        filepath = isavee.getStringExtra("filepath");

        editTextw=(EditText)findViewById(R.id.editText2);
        database = FirebaseDatabase.getInstance();

        final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://adstory.appspot.com/global");
        myRef=database.getReference(uid);
        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ab.equals("Whatsapp"))
                   {
                       myRef.child("views").push().setValue("whatsapp -"+getCurSysDate()+"**"+editTextw.getText().toString());

                   }
                if(ab.equals("Instagram"))
                {
                    myRef.child("views").push().setValue("insta -"+getCurSysDate()+"**"+editTextw.getText().toString());

                }
                if(ab.equals("Facebook"))
                {
                    myRef.child("views").push().setValue("fb -"+getCurSysDate()+"**"+editTextw.getText().toString());

                }



                Intent pay=new Intent(save.this,com.sars.user.adstory.pay.class);
               String a=editText9.getText().toString();
                if(a.isEmpty())
                {
                    Toast.makeText(save.this,"Please enter the code of poster",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pay.putExtra("videopath",filepath);
                    pay.putExtra("code",editText9.getText().toString());
                    pay.putExtra("views",editTextw.getText().toString());
                    pay.putExtra("vsource",ab);
                    startActivity(pay);
                }





            }
        });




    }
    public String getCurSysDate() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), screen.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

}












