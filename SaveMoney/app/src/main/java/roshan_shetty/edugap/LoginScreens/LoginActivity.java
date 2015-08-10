package roshan_shetty.edugap.LoginScreens;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import roshan_shetty.edugap.AppController;
import roshan_shetty.edugap.CustomRequest;
import roshan_shetty.edugap.MainActivity;
import roshan_shetty.edugap.R;

/**
 * Created by roshan on 6/7/2015.
 */

public class LoginActivity extends Activity {

    private EditText usernameField,passwordField;
    private TextView status,role,method;
    String resp;
    public static final String MyPREFERENCES = "LoginPrefs" ;
    SharedPreferences sharedpreferences, sharedpreferences1;

    String alreadyLogon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = (EditText)findViewById(R.id.editText2);
        passwordField = (EditText)findViewById(R.id.editText3);

        // Create a shared preference for balance
        // save shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("BalPrefs", MODE_PRIVATE).edit();
        editor.putInt("balance", 5000);
        editor.commit();
    }

    // Forward it to next screen and do other task
    public void forvardToDashboard() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void loginPost(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        String tag_json_obj = "json_obj_req";

        String url = "http://ec2-52-10-219-46.us-west-2.compute.amazonaws.com/harshita/connect.php";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Authenticating...");
        pDialog.show();

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d("Success: " + "got response from server");

                resp = response.optString("success");

                if (resp.equalsIgnoreCase("true")) {
                    Toast.makeText(getApplicationContext(), "Authentication successful !!", Toast.LENGTH_LONG).show();

                    // save shared preferences
                    SharedPreferences.Editor editor = getSharedPreferences("LoginPrefs", MODE_PRIVATE).edit();
                    editor.putString("AlreadyLogin", "Yup");
                    //editor.putInt("idName", 12);
                    editor.commit();

                    // Forward it to next screen
                    forvardToDashboard();

                }
                else {
                    // do nothing
                    Toast.makeText(getApplicationContext(),"Invalid credencials :(", Toast.LENGTH_LONG).show();
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),"Sorry, Unable to verify your login !!", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }
}
