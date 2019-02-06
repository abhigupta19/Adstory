 package com.sars.user.adstory;

 import android.annotation.SuppressLint;
 import android.content.Intent;
 import android.graphics.Bitmap;
 import android.net.Uri;
 import android.os.Bundle;
 import android.provider.MediaStore;
 import android.support.v7.app.AppCompatActivity;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ImageView;
 import android.widget.Toast;

 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.storage.FirebaseStorage;
 import com.google.firebase.storage.StorageReference;

 import java.io.IOException;
 import java.text.SimpleDateFormat;
 import java.util.Date;

 public class screenshot extends AppCompatActivity {
      ImageView imageView7;
      Button buttonu,buttons;
      int PICK_IMAGE_REQUEST=1;
     Uri filePath;
     private StorageReference mStorageRef;
     String uid;
     private FirebaseDatabase database;
     EditText editText14;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        buttons=findViewById(R.id.buttons);
        buttonu=findViewById(R.id.buttonu);
        imageView7=findViewById(R.id.imageView7);
        editText14=findViewById(R.id.editText8);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttons.setVisibility(View.INVISIBLE);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://adstory.appspot.com/global");
        database = FirebaseDatabase.getInstance();
        buttonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePath != null)
                {
                    /*final ProgressDialog progressDialog = new ProgressDialog(screenshot.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    */
                    if(editText14.getText().toString().isEmpty())
                    {
                        Toast.makeText(screenshot.this,"Please enter the code of poster",Toast.LENGTH_SHORT).show();

                    }
                    else {


                        StorageReference ref = mStorageRef.child("images/" + editText14.getText() + "---" + getCurSysDate() + "--" + uid);
                        ref.putFile(filePath);
                          /*  .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast.makeText(screenshot.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(screenshot.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })

                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });*/
                        Intent ipayshot = new Intent(screenshot.this, payshot.class);
                        startActivity(ipayshot);
                    }

                }
            }
        });

    }
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                 && data != null && data.getData() != null )
         {
              filePath = data.getData();
             try {
                 Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                 imageView7.setImageBitmap(bitmap);
                 if(imageView7.getDrawable()!=null)
                 {
                     buttons.setVisibility(View.VISIBLE);

                 }
             }
             catch (IOException e)
             {
                 e.printStackTrace();
             }
         }
     }
     public String getCurSysDate() {
         return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
     }
     public boolean onOptionsItemSelected(MenuItem item){
         Intent myIntent = new Intent(getApplicationContext(), bar.class);
         startActivityForResult(myIntent, 0);
         return true;

     }
}
