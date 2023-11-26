package com.aryan.javaminiproject.TicketBooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView lottieView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        lottieView = findViewById(R.id.lottieView);
        textView = findViewById(R.id.textView);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splashanim);
        lottieView.startAnimation(animation);
        Animation ani = AnimationUtils.loadAnimation(this,R.anim.textanim);
        textView.startAnimation(ani);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashScreen.this, Login.class);
                SplashScreen.this.startActivity(in);
                finish();
            }
        },2500);
    }
}