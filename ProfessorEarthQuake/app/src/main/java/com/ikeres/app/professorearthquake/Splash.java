package com.ikeres.app.professorearthquake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Splash extends Activity {

    // Timer del splash
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // Este método se ejecuta cuando el timer termina
                // Vamos a la otra activity
                Intent i = new Intent(Splash.this, Menu.class);
                startActivity(i);

                // cerramos esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}


