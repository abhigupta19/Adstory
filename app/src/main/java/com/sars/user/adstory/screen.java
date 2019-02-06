package com.sars.user.adstory;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class screen extends AppCompatActivity {
    private VideoView videoView;
    private ToggleButton t;
    private String videouri = "";
    private static final int REQ = 1000;
    private static final int REP = 1001;

    public static final int RECORD_AUDIO = 0;

    private static final SparseIntArray a = new SparseIntArray();
    private MediaProjectionManager ma;
    private MediaProjection mediaProjection;
    private VirtualDisplay virtualDisplay;
    private MediaProjectionCALLBACK mediaProjectionCALLBACK;
    private int mscreendensity;
    private static int displaywidth = 720;
    private static int displayheight = 1280;
    private MediaRecorder mediaRecorder;
    private RelativeLayout relativeLayout;
    private MediaProjectionManager mediaProjectionManager;
    private String filePath;
   private  Button button2;
    private TextView textView35;
    static {
        a.append(Surface.ROTATION_0, 90);
        a.append(Surface.ROTATION_90, 0);
        a.append(Surface.ROTATION_180, 270);
        a.append(Surface.ROTATION_270, 180);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        videoView = (VideoView) findViewById(R.id.vid);
        t = (ToggleButton) findViewById(R.id.togg);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mscreendensity = displayMetrics.densityDpi;
        Log.i("ram",String.valueOf(mscreendensity));
        mediaRecorder = new MediaRecorder();
        mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        displaywidth = displayMetrics.widthPixels;
        displayheight = displayMetrics.heightPixels;
         button2=(Button)findViewById(R.id.button24);
        button2.setVisibility(View.INVISIBLE);
         textView35=findViewById(R.id.textView35);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(screen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                        {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(screen.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            ) {
                        Snackbar.make(relativeLayout, "permissions", Snackbar.LENGTH_INDEFINITE)
                                .setAction("ENABLE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(screen.this, new String[] {
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                                REP);
                                    }
                                }).show();
                    } else {
                        ActivityCompat.requestPermissions(screen.this,
                                new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                REP);
                    }
                 /*   if (ActivityCompat.checkSelfPermission(screen.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(screen.this, new String[]{Manifest.permission.RECORD_AUDIO},
                                0);

                    } else {*/
                        togglescreenshare(v);

                }




                else {
                    togglescreenshare(v);
                }




            }
        });

    }



    private void initRecord() {
        try {
           /* final String directory = Environment.getExternalStorageDirectory() + File.separator + "Recordings";
            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                Toast.makeText(this, "Failed to get External Storage", Toast.LENGTH_SHORT).show();
                return;
            }
            final File folder = new File(directory);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }

            if (success) {
                String videoName = ("capture_" + getCurSysDate() + ".mp4");
                filePath = directory + File.separator + videoName;
            } else {
                Toast.makeText(this, "Failed to create Recordings directory", Toast.LENGTH_SHORT).show();
                return;
            }
*/

          //  mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            videouri=getFilesDir().getAbsoluteFile()+ new StringBuilder("/EDMTRecord_").append(new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss").format(new Date())).append(".mp4").toString();
          //  mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mediaRecorder.setVideoSize(displaywidth, displayheight);
            mediaRecorder.setVideoFrameRate(30);
            mediaRecorder.setOutputFile(videouri);
            mediaRecorder.setVideoEncodingBitRate(512 * 1000);


          /*  String state = Environment.getExternalStorageState();
            if(Environment.MEDIA_MOUNTED.equals(state)) {


                mediaRecorder.setOutputFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/video.mp4");
                Log.i("kanuuuuuuu", "yes");
            }
            else
            {
                mediaRecorder.setOutputFile(getFilesDir().getAbsolutePath() + "/video.mp4");
                Log.i("kanuuuuuuu", "nio");
            }
 */




            int roation = getWindowManager().getDefaultDisplay().getRotation();
            int orenation = a.get(roation + 90);
            mediaRecorder.setOrientationHint(orenation);

            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode != REQ) {
            Toast.makeText(MainActivity.this, "Ukn error", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == RESULT_OK) {
            Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
            t.setChecked(false);
            return;
        }
 */
        if(requestCode!=RESULT_OK) {
            mediaProjectionCALLBACK = new MediaProjectionCALLBACK();
            mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
            mediaProjection.registerCallback(mediaProjectionCALLBACK, null);
            virtualDisplay = createVirtualDisplay();
            mediaRecorder.start();
        }

    }

    private void recordScreen() {
        if (mediaProjection == null) {
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQ);
            return;
        }
        virtualDisplay = createVirtualDisplay();
        mediaRecorder.start();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private VirtualDisplay createVirtualDisplay() {

        return mediaProjection.createVirtualDisplay("Mainactivity", displaywidth, displayheight, mscreendensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,mediaRecorder.getSurface(), null, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class MediaProjectionCALLBACK extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if (t.isChecked() == true) {
                t.setChecked(false);
                mediaRecorder.stop();
                mediaRecorder.reset();
            }
            mediaProjection = null;
            stoprecordscreen();
            super.onStop();
        }
    }

    private void stoprecordscreen() {
        if (virtualDisplay == null)
            return;
        virtualDisplay.release();
        destroyMediaProjection();




    }

    private void destroyMediaProjection() {
        mediaProjection.unregisterCallback(mediaProjectionCALLBACK);
        mediaProjection.stop();
        mediaProjection = null;

    }
    private void togglescreenshare(View v) {
        if (((ToggleButton) v).isChecked()) {
            initRecord();
            recordScreen();
            textView35.setText("Recording is start please press home button and go on status");
        } else {
            try {
                mediaRecorder.stop();

            }
            catch (RuntimeException e)
            {

            }

            button2.setVisibility(View.VISIBLE);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent isave=new Intent(screen.this,save.class);
                    isave.putExtra("filepath",videouri);
                    startActivity(isave);
                }
            });
             textView35.setVisibility(View.INVISIBLE);
            mediaRecorder.reset();
            stoprecordscreen();
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(Uri.parse(videouri));
            videoView.start();


        }

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REP: {
                if ((grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    togglescreenshare(t);
                } else {
                    t.setChecked(true);
                    Snackbar.make(relativeLayout, "Permission", Snackbar.LENGTH_INDEFINITE).
                            setAction("ENABLE", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(screen.this, new String[]{
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REP);
                                }

                            }).show();

                }
                return;
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

