package roshan_shetty.edugap.LoginScreens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import roshan_shetty.edugap.MainActivity;
import roshan_shetty.edugap.R;

/**
 * Created by roshan on 5/4/2015.
 */
public class FlashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.flashscreen_layout);

        // remove title bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);     // need to check this

        try {
            SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            if(!prefs.contains("AlreadyLogin")){
                forvardToLoginActivity();
            }
            else {
                String restoredText = prefs.getString("AlreadyLogin", null);

                if (restoredText.equalsIgnoreCase("yup")) {
                    forvardToMainActivity();
                }
                else {
                    forvardToLoginActivity();
                }
            }
        }
        catch (Exception e) {

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void forvardToMainActivity() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(FlashScreen.this, MainActivity.class);
                Log.e("testing..............", "executing................");
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void forvardToLoginActivity() {

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(FlashScreen.this, LoginActivity.class);
                Log.e("testing..............", "executing................");
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

}
