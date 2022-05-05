package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    private static int SPLASH_SCREEN=2000;
    Animation ta,ba;
    ImageView image;
    TextView tag,appnam;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        ta= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        ba=AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        image=findViewById(R.id.logo);
        tag=findViewById(R.id.tag);
        appnam=findViewById(R.id.appnam);
        image.setAnimation(ta);
        appnam.setAnimation(ba);
        tag.setAnimation(ba);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(splash.this,logi.class);
                startActivity(i);
                finish();
            }
        },SPLASH_SCREEN);
    }
}
