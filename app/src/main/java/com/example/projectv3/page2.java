package com.example.projectv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Locale;

public class page2 extends AppCompatActivity {
    public ConstraintLayout parentLinearLayout3;
    public SpeechRecognizer speechRecognizer2;
    public Intent speechRecognizerIntent2;
    public  String keeper2="";
    public TextToSpeech mTTS2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        checkVoiceCommandPermission();
        parentLinearLayout3=findViewById(R.id.parentLinearLayout3);

        speechRecognizer2=speechRecognizer2.createSpeechRecognizer(page2.this);
        speechRecognizerIntent2= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mTTS2 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result1 = mTTS2.setLanguage(Locale.ENGLISH);
                    if (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language Not Supported");

                    }


                }
            }
        });
        parentLinearLayout3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent1) {
                switch (motionEvent1.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        speechRecognizer2.startListening(speechRecognizerIntent2);
                        keeper2="";
                        break;

                    case MotionEvent.ACTION_UP:
                        speechRecognizer2.stopListening();
                        break;
                }
                return false;
            }
        });

        speechRecognizer2.setRecognitionListener(new RecognitionListener() {
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
                ArrayList<String> matchesFound1 =bundle.getStringArrayList(speechRecognizer2.RESULTS_RECOGNITION);
                if(matchesFound1!=null){

                    keeper2 = matchesFound1.get(0);

                    if(keeper2.equals("next")==true){

                        Intent kayit2=new Intent(getApplicationContext(),playPart.class);
                        startActivity(kayit2);

                    }

                    else if(keeper2.equals("previous")==true){

                        Intent kayit4=new Intent(getApplicationContext(),MainActivity.class);
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
        new Handler().postDelayed(new Runnable(){
            public void run(){

                Speak("if you are ready for the your, just press the screen and say: next");


            }
        }, 1000);

    }

    @Override
    protected void onStart() {
     super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mTTS2!=null){
            mTTS2.shutdown();
        }
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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private  void checkVoiceCommandPermission(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

            if(!(ContextCompat.checkSelfPermission(page2.this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)){
                Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" +getPackageName()));
                startActivity(intent);
                finish();

            }
        }
    }

    public void Speak(String text){

        mTTS2.speak(text,TextToSpeech.QUEUE_FLUSH,null);

        while(true) {

            if(mTTS2.isSpeaking()!=true)
                break;

            else continue;

        }


        }
    }

