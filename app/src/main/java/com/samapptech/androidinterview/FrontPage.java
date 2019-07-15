package com.samapptech.androidinterview;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Objects;

public class FrontPage extends AppCompatActivity implements View.OnClickListener {
    Button b_simple,b_tough,b_other,b_rate;
    String tag=FrontPage.class.getSimpleName();

//private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //toolbar=findViewById(R.id.ques_toolbar);
        //setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
        Objects.requireNonNull(getSupportActionBar()).setIcon(R.drawable.a);


        b_simple = findViewById(R.id.b_sp);
        b_tough = findViewById(R.id.b_tq);
        b_other = findViewById(R.id.b_oa);
        b_rate = findViewById(R.id.b_rate);

        b_simple.setOnClickListener(this);
        b_other.setOnClickListener(this);
        b_rate.setOnClickListener(this);
        b_tough.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_sp:
                Log.d(tag, "simple question button clicked");
                Intent i = new Intent(FrontPage.this, Simple_ques.class);
                startActivity(i);
                break;
            case R.id.b_tq:
                Log.d(tag, "tough ques button clicked");
                Intent j = new Intent(FrontPage.this, Tough_ques.class);
                startActivity(j);
                break;
            case R.id.b_oa:
                Log.d(tag, "other apps  button clicked");
                try {
                    Uri uri0 = Uri.parse("market://search?q=Samuelrajasingh");
                    Intent gotoMarket1 = new Intent(Intent.ACTION_VIEW, uri0);
                    startActivity(gotoMarket1);
                }catch (ActivityNotFoundException e){

                    Uri uri1 = Uri.parse("https:play.google.com/store/search?q=Samuelrajasingh");
                    Intent gotoMarket1 = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(gotoMarket1);
                }
                break;
            case R.id.b_rate:
                Log.d(tag,"rate button clicked");
               try {
                   Uri uri1 = Uri.parse("market://details?id=" + getPackageName());
                   Intent gotoMarket1 = new Intent(Intent.ACTION_VIEW, uri1);
                   startActivity(gotoMarket1);
               }catch (ActivityNotFoundException e){

                   Uri uri1 = Uri.parse("https:play.google.com/store/apps/details?id=" + getPackageName());
                   Intent gotoMarket1 = new Intent(Intent.ACTION_VIEW, uri1);
                   startActivity(gotoMarket1);
               }
                break;
        }
    }
}