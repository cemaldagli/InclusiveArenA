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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    public ConstraintLayout parentLinearLayout4;
    public SpeechRecognizer speechRecognizer4;
    public Intent speechRecognizerIntent4;
    public  String keeper4="";
    public TextToSpeech mTTS4;
    FirebaseDatabase database4 = FirebaseDatabase.getInstance();
    DatabaseReference myRef4 = database4.getReference().child("fir-py-fe8f6").child("Feedback");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        parentLinearLayout4=findViewById(R.id.parentLinearLayout4);

        speechRecognizer4=speechRecognizer4.createSpeechRecognizer(Main2Activity.this);
        speechRecognizerIntent4= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent4.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent4.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mTTS4 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result1 = mTTS4.setLanguage(Locale.ENGLISH);
                    if (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language Not Supported");

                    }


                }
            }
        });

        parentLinearLayout4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent1) {
                switch (motionEvent1.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        speechRecognizer4.startListening(speechRecognizerIntent4);
                        keeper4="";
                        break;

                    case MotionEvent.ACTION_UP:
                        speechRecognizer4.stopListening();
                        break;
                }
                return false;
            }
        });

        speechRecognizer4.setRecognitionListener(new RecognitionListener() {
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
                ArrayList<String> matchesFound1 =bundle.getStringArrayList(speechRecognizer4.RESULTS_RECOGNITION);
                if(matchesFound1!=null) {

                    keeper4 = matchesFound1.get(0);
                    myRef4.setValue(keeper4);
                    if(keeper4 != ""){
                        Speak("Thank you so much!");
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

                Speak("Could : you : share : your feedback : with : us?");

            }
        }, 500);

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkVoiceCommandPermission4();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
    private  void checkVoiceCommandPermission4(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

            if(!(ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)){
                Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" +getPackageName()));
                startActivity(intent);
                finish();

            }
        }
    }

    public void Speak(String text){

        mTTS4.speak(text, TextToSpeech.QUEUE_FLUSH,null);
        while(true) {

            if(mTTS4.isSpeaking()!=true)
                break;

            else continue;

        }


    }

}
