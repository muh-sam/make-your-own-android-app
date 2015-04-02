package com.muh_sam.studentinformationsystem.Datesheet;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muh_sam.studentinformationsystem.R;

/**
 * Created by muh_s_000 on 17/03/2015.
 */
public class DatesheetAdapter extends CursorAdapter {

    private final int VIEW_TYPE_LIST= 0;



    public DatesheetAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public int getItemViewType(int position) {
        return (position == 0) ?  VIEW_TYPE_LIST: 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;

        if(viewType == VIEW_TYPE_LIST)
        {
            layoutId = R.layout.list_item_datesheet;
            View view =  LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
            return view;
        }
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int viewType = getItemViewType(cursor.getPosition());
        if(viewType == VIEW_TYPE_LIST)
        {
            ViewHolder viewHolder = (ViewHolder)view.getTag();
            int Id = cursor.getInt(DatesheetFragment.COL_DATESHEET_ID);
            viewHolder.id.setText(String.valueOf(Id));
            String subject = cursor.getString(DatesheetFragment.COL_DATESHEET_SUBJECT);
            viewHolder.subject.setText(subject);
            String date = cursor.getString(DatesheetFragment.COL_DATESHEET_DATE);
            viewHolder.date.setText(date);
            String time = cursor.getString(DatesheetFragment.COL_DATESHEET_TIME);
            viewHolder.time.setText(time);
        }
    }

    private class ViewHolder {
        public final TextView id;
        public final TextView subject;
        public final TextView date;
        public final TextView time;

        public ViewHolder(View view) {
            this.id = (TextView)view.findViewById(R.id.list_item_datesheet_id);
            this.subject = (TextView)view.findViewById(R.id.list_item_datesheet_subject);
            this.date = (TextView)view.findViewById(R.id.list_item_datesheet_date);
            this.time = (TextView)view.findViewById(R.id.list_item_datesheet_time);
        }
    }


}
