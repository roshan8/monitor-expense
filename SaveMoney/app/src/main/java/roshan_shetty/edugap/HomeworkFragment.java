package roshan_shetty.edugap;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.SlideInRightAnimationAdapter;
import roshan_shetty.edugap.adapter.MyListCursorAdapter;
import roshan_shetty.edugap.helper.DatabaseHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by roshu on 3/31/15.
 */

public class HomeworkFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final int SCALE_DELAY = 30;

    private LinearLayout rowContainer;


    public HomeworkFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_homework, container, false);

        // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
        DatabaseHelper handler = new DatabaseHelper(getActivity());

        // Get access to the underlying writeable database
        SQLiteDatabase db = handler.getWritableDatabase();

        // Query for items from the database and get a cursor back
        Cursor todoCursor = db.rawQuery("SELECT  * FROM TABLE_HOMEWORK", null);


        /////// Don't forget to add setfixed line for performance from  google dev site

        /////// Try to create a separate thread and notify once loaded method to SQLite query operation

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.lvItems);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Setup cursor adapter using cursor from last step
        MyListCursorAdapter todoAdapter = new MyListCursorAdapter(getActivity(), todoCursor);



        // Row Container
        rowContainer = (LinearLayout) rootView.findViewById(R.id.row_container);

        for (int i = 1; i < rowContainer.getChildCount(); i++) {
            View rowView = rowContainer.getChildAt(i);
            rowView.animate().setStartDelay(100 + i * SCALE_DELAY).scaleX(1).scaleY(1);
        }


        SlideInRightAnimationAdapter alphaAdapter = new SlideInRightAnimationAdapter(todoAdapter);
        alphaAdapter.setFirstOnly(false);
        alphaAdapter.setDuration(500);
        alphaAdapter.setInterpolator(new OvershootInterpolator());

        // Attach cursor adapter to the ListView
        mRecyclerView.setAdapter(alphaAdapter);

        return rootView;
    }



}