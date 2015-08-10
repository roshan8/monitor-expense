package roshan_shetty.edugap.LoginScreens;




import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import roshan_shetty.edugap.R;
import roshan_shetty.edugap.adapter.MyListCursorAdapter;
import roshan_shetty.edugap.helper.DatabaseHelper;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by roshan on 5/6/2015.
 */
public class shareThisApp extends Fragment implements View.OnClickListener{

    Button btnShare;
    EditText shareMsg;

    PackageManager pm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.share_this_app, container, false);


        YoYo.with(Techniques.BounceInUp)
                .duration(700)
                .playOn(rootView.findViewById(R.id.msgText));

        YoYo.with(Techniques.BounceInUp)
                .duration(700)
                .playOn(rootView.findViewById(R.id.buttonShare));

        shareMsg = (EditText) rootView.findViewById(R.id.msgText);
        btnShare = (Button) rootView.findViewById(R.id.buttonShare);
        btnShare.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {

        // now send messages using whatsapp
        pm = getActivity().getPackageManager();

        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            String text = shareMsg.getText().toString() ;

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
        }

    }
}
