package roshan_shetty.edugap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import roshan_shetty.edugap.LoginScreens.shareThisApp;
import roshan_shetty.edugap.helper.DatabaseHelper;


public class MainActivity extends MaterialNavigationDrawer {

    Boolean doubleBackToExitPressedOnce = false;

    @Override
    public void init(Bundle bundle) {

        MaterialSection secPost = newSection("Post expense", R.drawable.post, new PostFragment());
        MaterialSection secHomework = newSection("Log", R.drawable.homework, new HomeworkFragment());
        MaterialSection secAttnd = newSection("Update balance", R.drawable.attandance, new updateBalance());
        MaterialSection secFeedbackForDev = newSection("Feedback about app", R.drawable.feedback1, new FeedbackForDev());
        MaterialSection secShareApp = newSection("Share via Whatsapp", R.drawable.share, new shareThisApp());

        addSection(secAttnd);
        addSection(secPost);
        addSection(secHomework);
        addSection(secFeedbackForDev);
        addSection(secShareApp);

        //disableLearningPattern();

        setDrawerHeaderImage(R.drawable.drawer);

        // test for internet connection
        boolean netCon = isNetworkAvailable();
        if (netCon == false) {
            new AlertDialog.Builder(this)
                    .setTitle("No Internet")
                    .setMessage("No Internet Connection Available.\nContinue offline ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // close app
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }


    // test for internet connectivity
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    // Double back button click to exit the application

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}