package com.samapptech.androidinterview;


import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Objects;


public class Tough_ques extends AppCompatActivity implements View.OnClickListener,TextToSpeech.OnInitListener {
    String[] tuf_ques, tuf_ans;
    String tag = FrontPage.class.getSimpleName();
    TextView t_ques, t_ans, t_length, t_index;
    Button b_left, b_show, b_right, b_play, b_stop;
    int index;

    //TTS object
    private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        index = 0;

//Initialization of textview
        tuf_ques = getResources().getStringArray(R.array.tough_ques);
        tuf_ans = getResources().getStringArray(R.array.tough_ans);
        t_index = findViewById(R.id.xx);
        t_length = findViewById(R.id.yy);
//Initialization of buttons
        b_left = findViewById(R.id.button_left);
        b_right = findViewById(R.id.button_right);
        b_show = findViewById(R.id.answer_button);
        b_play = findViewById(R.id.play_button);
        b_stop = findViewById(R.id.stop_button);
        b_show.setOnClickListener(this);
        b_left.setOnClickListener(this);
        b_right.setOnClickListener(this);
        b_play.setOnClickListener(this);
        b_stop.setOnClickListener(this);

        t_ques = findViewById(R.id.questions);
        t_ans = findViewById(R.id.key_text);
        t_index = findViewById(R.id.xx);
        t_length = findViewById(R.id.yy);

        t_ques.setText(tuf_ques[index]);
        t_index.setText(String.valueOf(index + 1));
        t_length.setText(String.valueOf(tuf_ques.length));

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

    }

    @Override
    public void onClick(View v) {
        if (!((index < 0) || (index > tuf_ques.length))) {

            switch (v.getId()) {
                case R.id.button_left:

                    if ((index == 0)) break;
                    index--;
                    t_ans.setText(R.string.key);
                    t_ques.setText(tuf_ques[index]);
                    t_index.setText(String.valueOf(index + 1));

                    Log.d(tag, "index in butt-left after calc: " + index);

                    break;
                case R.id.button_right:
                    Log.d(tag, "index in butt-right before calc : " + index);

                    if (index == (tuf_ques.length) - 1) break;
                    t_ans.setText(R.string.key);
                    index++;
                    t_ques.setText(tuf_ques[index]);
                    t_index.setText(String.valueOf(index + 1));

                    Log.d(tag, "index in butt-right after calc : " + index);

                    break;
                case R.id.answer_button:
                    t_ans.setText(tuf_ans[index]);
                    Log.d(tag, "index in ans : " + index);

                    break;
                case R.id.play_button:
                    if (t_ans.getText().toString().equals("Press A for answer")) {
                        Toast.makeText(this, "See The answer first", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    String words = tuf_ans[index];
                    speakWords(words);
                    if (isFinishing()) myTTS.shutdown();
                    break;

                case R.id.stop_button:
                    if (myTTS.isSpeaking())
                        myTTS.stop();
                    break;

            }
        }

    }

    private void speakWords(String speech) {

        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
            } else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    @Override
    public void onInit(int initStatus) {
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.getDefault());
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myTTS.isSpeaking()) myTTS.shutdown();

    }
}