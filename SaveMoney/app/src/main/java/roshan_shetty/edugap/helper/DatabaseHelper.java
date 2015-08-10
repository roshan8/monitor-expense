package roshan_shetty.edugap.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import roshan_shetty.edugap.model.postModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "postedMessageManager";

    // Table Names
    private static final String TABLE_HOMEWORK = "TABLE_HOMEWORK";
    private static final String TABLE_EVENTS = "TABLE_EVENTS";
    private static final String TABLE_FORUM = "TABLE_FORUM";

    // Common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_POSTED_MESSAGE = "postMessage";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_TITLE = "postTitle";
    private static final String KEY_POSTED_BY = "postedBy";

    // NOTES Table - column nmaes
    // TODO declare table specific column names in future iff necessary (roshu)

    // Table Create Statements
    private static final String CREATE_TABLE_HOMEWORK = "CREATE TABLE IF NOT EXISTS "
            + TABLE_HOMEWORK + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_POSTED_MESSAGE
            + " TEXT," + KEY_TITLE + " TEXT," + KEY_CREATED_AT + "  DATETIME " +  ")";

    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_EVENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_POSTED_MESSAGE
            + " TEXT," + KEY_TITLE + " TEXT," + KEY_CREATED_AT + "  DATETIME, " + KEY_POSTED_BY + " TEXT" + ")";

    private static final String CREATE_TABLE_FORUM = "CREATE TABLE IF NOT EXISTS "
            + TABLE_FORUM + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_POSTED_MESSAGE
            + " TEXT," + KEY_TITLE + " TEXT," + KEY_CREATED_AT + "  DATETIME, " + KEY_POSTED_BY + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_HOMEWORK);
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_FORUM);
    }

    // TODO important check this if my program crash in future usage (roshu), because why droping Database here, didnt get the overiden method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {              // TODO IMPORTANT check this how does on Upgrade works, will it delete all data
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOMEWORK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORUM);

        // create new tables
        onCreate(db);
    }

     /**
     * Creating postModel element and inserting it to homework table
     */
    public long insertHomework(postModel postedMessage) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_CREATED_AT, postedMessage.getPostedTime());
        values.put(KEY_POSTED_MESSAGE, postedMessage.getPostMessage());
        values.put(KEY_TITLE, postedMessage.getPostTitle());

        // insert row
        long tag_id = db.insert(TABLE_HOMEWORK, null, values);

        return tag_id;
    }


    /**
     * getting all tags
     * */
    public List<postModel> getAllpostedHomework() {
        List<postModel> tags = new ArrayList<postModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_HOMEWORK;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                postModel t = new postModel();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setPostMessage(c.getString(c.getColumnIndex(KEY_POSTED_MESSAGE)));

                // adding to tags list
                tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}