package roshan_shetty.edugap.adapter;

/**
 * Created by roshan on 5/2/2015.
 */
import roshan_shetty.edugap.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder>{

    public MyListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextTitle;
        public TextView mTextContent;

        public TextView mTextPostedOn;

        public ViewHolder(View view) {
            super(view);
            mTextTitle = (TextView) view.findViewById(R.id.msgTitle);
            mTextContent = (TextView) view.findViewById(R.id.msgContent);

            mTextPostedOn = (TextView) view.findViewById(R.id.msgPostedOn);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

        String postedMessage = cursor.getString(cursor.getColumnIndexOrThrow("postMessage"));
        String postedTitle = cursor.getString(cursor.getColumnIndexOrThrow("postTitle"));

        String postedOn = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));


        viewHolder.mTextTitle.setText(postedTitle);
        viewHolder.mTextContent.setText(postedMessage);

        viewHolder.mTextPostedOn.setText(postedOn);
    }
}