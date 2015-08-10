package roshan_shetty.edugap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import roshan_shetty.edugap.helper.DatabaseHelper;
import roshan_shetty.edugap.model.postModel;

/**
 * Created by roshu on 3/31/15.
 */

public class PostFragment extends Fragment implements View.OnClickListener {

    View rootView;
    EditText postTextContent, postTextTitle, postCurSavings;

    // Database Helper
    DatabaseHelper db;
    // Shared preference
    SharedPreferences sharedpreferences;

    public PostFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_post, container, false);

        // set the background color of fragment
        rootView.setBackgroundColor(Color.WHITE);

        Button sendTestButton = (Button) rootView.findViewById(R.id.send);
        Button sendStatusButton = (Button) rootView.findViewById(R.id.clear1);
        sendTestButton.setOnClickListener(this);
        sendStatusButton.setOnClickListener(this);

        // get the reference of the editText
        postTextTitle = (EditText)rootView.findViewById(R.id.postTitle);
        postTextContent = (EditText)rootView.findViewById(R.id.postContent);
        postCurSavings = (EditText)rootView.findViewById(R.id.cur_savings);

        int balance = updateBalanceLabel();

        return rootView;
    }

    public int updateBalanceLabel() {
        int balance = 0;
        try {

            // get the reference to shared preference
            Context context = getActivity();
            SharedPreferences sharedPref = context.getSharedPreferences(
                    "BalPrefs", Context.MODE_PRIVATE);

            // retrieve the score from shared preference
            balance = sharedPref.getInt("balance", 0); //getResources().getInteger("balance");

            // set the label to retrived value
            postCurSavings.setText("Current Balance : " + balance + " Rs");

        }
        catch (Exception e) {
            Log.d("Debug", "Problem with shared preference");
        }
        return balance;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.send:
                sendStatusDude();
                break;
            case R.id.clear1:
                postTextTitle.setText("");
                postTextContent.setText("");
                break;
        }
    }

    public void sendStatusDude() {

        // validation for blank edit text
        if (postTextContent.getText().toString().equals("") || postTextTitle.getText().toString().equals("")) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Invalid entry")
                    .setMessage("Please enter all the required fields")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            return;
        }

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Updating...");
        pDialog.show();

        // Retrieve values
        String postedTime = getDateTime();
        String expense = postTextTitle.getText().toString();        // TODO need to check for integer
        String purpose = postTextContent.getText().toString();

        int exp = Integer.parseInt(expense);

        // Update shared preference value
        try {

            // get the reference to shared preference
            Context context = getActivity();
            SharedPreferences sharedPref = context.getSharedPreferences(
                    "BalPrefs", Context.MODE_PRIVATE);

            int curBalance = updateBalanceLabel();      // get the current shared preference value

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("balance", curBalance - exp);   // all cases not considered (for negative value)
            editor.commit();

            curBalance = updateBalanceLabel();   // update the balance label with new value

        }
        catch (Exception e){}

        updateBalanceLabel();
        saveToTable("debit : " + expense, purpose, postedTime);

        pDialog.dismiss();
        Toast.makeText(getActivity(), "successfully updated", Toast.LENGTH_LONG).show();

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void saveToTable(String expense, String purpose, String postedTime) {//(String msgTitle, String msgContent, String msgType, String postedTime, String postedBy) {

        // updating SQLite table
        Log.d("Saving", "Updating SQLite table");
        db = new DatabaseHelper(getActivity());

        // Creating tags
        postModel tag1 = new postModel(expense, purpose, postedTime);

        long tag1_id = db.insertHomework(tag1);

        // close database connection
        db.close();
    }
}