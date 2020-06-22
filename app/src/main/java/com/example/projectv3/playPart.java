package com.example.projectv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class playPart extends AppCompatActivity {
    public ConstraintLayout parentLinearLayout2;
    public SpeechRecognizer speechRecognizer3;
    public Intent speechRecognizerIntent3;
    public  String keeper3="";
    MediaPlayer player;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("fir-py-fe8f6").child("Veri").child("-M86Ickq4GpVKVTzd-P-").child("Data");
    TextView a;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_part);

        checkVoiceCommandPermission1();
        parentLinearLayout2=findViewById(R.id.parentLinearLayout2);
        a = (TextView) findViewById(R.id.cemal_text);
        vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);


        speechRecognizer3=speechRecognizer3.createSpeechRecognizer(playPart.this);
        speechRecognizerIntent3= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());



    }

    @Override
    protected void onStart() {
        super.onStart();
        a.setText("Waiting!!!");
    }

    @Override
    protected void onStop() {

        super.onStop();
        stopPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        speechRecognizer3.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matchesFound3 =bundle.getStringArrayList(speechRecognizer3.RESULTS_RECOGNITION);
                if(matchesFound3 != null){

                    keeper3 = matchesFound3.get(0);
                    if(keeper3.equals("play")==true){

                        if (player == null) {
                            player = MediaPlayer.create(playPart.this,R.raw.song);
                            Toast.makeText(playPart.this, "MediaPlayer Playing", Toast.LENGTH_SHORT).show();
                            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    stopPlayer();
                                }
                            });
                        }

                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String cemal = dataSnapshot.getValue().toString();

                                a.setText(cemal);
                                int i=0;
                                int foo = Integer.parseInt(cemal);
                                if(foo>=1) {
                                    player.start();
                                    vibrator.vibrate(1000);

                                    try {
                                        //set time in mili
                                        Thread.sleep(1000);

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }



                                }

                                else if(foo==0){
                                    if(player.isPlaying()==false) {

                                        a.setText("Waiting");

                                    }
                                    if(player.isPlaying()==true) {
                                        player.pause();
                                        a.setText("Paused");

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    else if(keeper3.equals("stop")==true){

                        player.stop();
                        a.setText("Stopped");
                        Intent kayit5=new Intent(getApplicationContext(),Main2Activity.class);
                        startActivity(kayit5);


                    }



                    else if(keeper3.equals("pause")==true){

                        player.pause();
                        a.setText("Paused");


                    }

                    else if(keeper3.equals("next")==true){

                        Intent kayit3=new Intent(getApplicationContext(),Main2Activity.class);
                        startActivity(kayit3);

                    }

                    else if(keeper3.equals("previous")==true){

                        Intent kayit4=new Intent(getApplicationContext(),page2.class);
                        startActivity(kayit4);

                    }

                }

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        parentLinearLayout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent3) {
                switch (motionEvent3.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        speechRecognizer3.startListening(speechRecognizerIntent3);
                        keeper3="";
                        break;

                    case MotionEvent.ACTION_UP:
                        speechRecognizer3.stopListening();
                        break;
                }
                return false;
            }
        });
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(playPart.this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    private  void checkVoiceCommandPermission1(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

            if(!(ContextCompat.checkSelfPermission(playPart.this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)){
                Intent intent3=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" +getPackageName()));
                startActivity(intent3);
                finish();

            }
        }
    }
}
