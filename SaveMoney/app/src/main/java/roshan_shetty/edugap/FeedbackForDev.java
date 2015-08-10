package roshan_shetty.edugap;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roshan on 5/19/2015.
 */

public class FeedbackForDev extends Fragment implements View.OnClickListener {

    EditText msg;
    Button clearButton, sendButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.feedback_for_dev, container, false);

        // set the background color of fragment
        rootView.setBackgroundColor(Color.WHITE);

        sendButton = (Button) rootView.findViewById(R.id.buttonSend);
        clearButton = (Button) rootView.findViewById(R.id.clear);
        sendButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);

        // get the reference of the editText
        msg = (EditText)rootView.findViewById(R.id.msgText);

        return rootView;
    }

    public void sendFeedbackToDev() {

        String tag_json_obj = "json_obj_req";

        String url = "http://ec2-52-10-219-46.us-west-2.compute.amazonaws.com/PHPMailer-master/examples/feedbackForDev.php"; //String url = "http://api.androidhive.info/volley/person_array.json";

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Sending message...");
        pDialog.show();

        Map<String, String> params = new HashMap<String, String>();
        params.put("message", msg.getText().toString());
        params.put("messageFrom", "roshu");


        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d("Success: " + "message sent");
                Toast.makeText(getActivity().getApplicationContext(), "Thanks for your feedback !!", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: ", error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),"Sorry not sent !!", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonSend:
                sendFeedbackToDev();
                break;
            case R.id.clear:
                msg.setText("");
                break;
        }
    }
}
