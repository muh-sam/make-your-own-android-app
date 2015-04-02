package com.muh_sam.studentinformationsystem.timetable;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muh_sam.studentinformationsystem.R;

/**
 * Created by muh_s_000 on 16/03/2015.
 */
public class TimetableAdapter extends CursorAdapter {
    private final int VIEW_TYPE_LIST= 0;

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_LIST: 0;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    public TimetableAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;

        if(viewType == VIEW_TYPE_LIST)
        {
            layoutId = R.layout.list_item_timetable;
            View view =  LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
            return view;
        }
        return null;
    }

    @Override
    public View newDropDownView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newDropDownView(context, cursor, parent);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int viewType = getItemViewType(cursor.getPosition());

        if(viewType == VIEW_TYPE_LIST)
        {
            ViewHolder viewHolder = (ViewHolder)view.getTag();
            int Id = cursor.getInt(TimetableFragment.COL_TIMETABLE_ID);
            viewHolder.id.setText(String.valueOf(Id));
            String subject = cursor.getString(TimetableFragment.COL_TIMETABLE_SUBJECT);
            viewHolder.subject.setText(subject);
            String teacher = cursor.getString(TimetableFragment.COL_TIMETABLE_TEACHER);
            viewHolder.teacher.setText(teacher);
            String day = cursor.getString(TimetableFragment.COL_TIMETABLE_DAY);
            viewHolder.day.setText(day);
            String time = cursor.getString(TimetableFragment.COL_TIMETABLE_TIME);
            viewHolder.time.setText(time);
        }
    }
    private class ViewHolder {
        public final TextView id;
        public final TextView subject;
        public final TextView teacher;
        public final TextView day;
        public final TextView time;
        public ViewHolder(View view) {
            this.id = (TextView)view.findViewById(R.id.list_item_timetable_id);
            this.subject = (TextView)view.findViewById(R.id.list_item_timetable_subject);
            this.teacher = (TextView)view.findViewById(R.id.list_item_timetable_teacher);
            this.day = (TextView)view.findViewById(R.id.list_item_timetable_day);
            this.time = (TextView)view.findViewById(R.id.list_item_timetable_time);
        }
    }
}
