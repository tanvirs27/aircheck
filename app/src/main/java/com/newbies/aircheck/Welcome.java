package com.newbies.aircheck;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Welcome extends Activity implements AnimationListener {

    TextView txtMessage;

    // Animation
    Animation animFadein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txtMessage = (TextView) findViewById(R.id.welcomeMsg);


        // load the animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        // set animation listener
        animFadein.setAnimationListener(this);

        Runnable greet = new Runnable() {
            @Override
            public void run() {
                txtMessage.setVisibility(View.VISIBLE);

                // start the animation
                txtMessage.startAnimation(animFadein);
            }
        };
        Thread welcome = new Thread(greet);
        welcome.start();

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent=new Intent(Welcome.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

}