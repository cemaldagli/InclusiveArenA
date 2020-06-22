package com.example.projectv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public TextToSpeech mTTS1;
    public ConstraintLayout parentLinearLayout1;
    Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLinearLayout1=findViewById(R.id.parentLinearLayout1);
        vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);

        parentLinearLayout1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent1) {
                switch (motionEvent1.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("Burada","onTouch");
                        if(mTTS1!=null){
                            mTTS1.shutdown();}

                        Intent kayit=new Intent(getApplicationContext(),page2.class);


                        startActivity(kayit);

                        break;

                    case MotionEvent.ACTION_UP:

                        break;
                }
                return false;
            }
        });







    }
    @Override
    protected void onDestroy() {
        Log.d("Mesaj","Destroy");

        super.onDestroy();





    }


    @Override
    protected void onStart() {
        super.onStart();

        mTTS1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int result= mTTS1.setLanguage(Locale.ENGLISH);
                    if(result== TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language Not Supported");

                    }



                }
            }
        });

        Log.d("Mesaj","Start");

        new Handler().postDelayed(new Runnable(){
            public void run(){

                Speak("Welcome");

                vibrator.vibrate(500);



            }
        }, 1000);

    }

    @Override
    protected void onStop() {


        super.onStop();
    }

    @Override
    protected void onPause() {

        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Mesaj","Resume");


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void Speak(String text){

        mTTS1.speak(text, TextToSpeech.QUEUE_FLUSH,null);
        while(true) {

            if(mTTS1.isSpeaking()!=true)
                break;

            else continue;

        }


    }
}
