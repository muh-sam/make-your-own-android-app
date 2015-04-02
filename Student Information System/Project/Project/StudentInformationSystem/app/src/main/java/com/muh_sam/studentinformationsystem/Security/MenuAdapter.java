package com.muh_sam.studentinformationsystem.Security;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muh_sam.studentinformationsystem.R;
import com.muh_sam.studentinformationsystem.StudentInformationSystemFragment;

/**
 * Created by muh_s_000 on 14/03/2015.
 */
public class MenuAdapter extends CursorAdapter {
    /*
    private final int VIEW_TYPE_DATA = 0;
    private final int VIEW_TYPE_MENU = 1;
    */
    public MenuAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /*
    @Override
    public int getItemViewType(int position) {
        return (position==0) ? VIEW_TYPE_DATA : VIEW_TYPE_MENU;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_sis, viewGroup, false);

        /*
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;

        if(viewType == VIEW_TYPE_TODAY)
        {
            layoutId = R.layout.list_item_forecast_today;
        }else if(viewType == VIEW_TYPE_FUTURE_DAY)
        {
            layoutId = R.layout.list_item_forecast;
        }

        View view =  LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
        */
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String menuText = cursor.getString(StudentInformationSystemFragment.COL_MENU_TEXT);
        TextView menuView = (TextView)view.findViewById(R.id.list_item_sis_textview);
        menuView.setText(menuText);

        /*
        ViewHolder viewHolder = (ViewHolder)view.getTag();


        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_ID);

        viewHolder.iconView.setImageResource(R.drawable.ic_launcher);

        String dateString = cursor.getString(ForecastFragment.COL_WEATHER_DATE);
        viewHolder.dateView.setText(Utility.formatDate(dateString));

        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        viewHolder.descriptionView.setText(description);

        boolean isMetric = Utility.IsMetric(context);

        float high = cursor.getFloat(ForecastFragment.COL_WEATHER_MAX_TEMP);
        viewHolder.highTempView.setText(Utility.formatTemperature(context ,high, isMetric));

        float low = cursor.getFloat(ForecastFragment.COL_WEATHER_MIN_TEMP);
        viewHolder.lowTempView.setText(Utility.formatTemperature( context ,low, isMetric));
        */
    }


    /*
    public static class ViewHolder{
        public final TextView menuView;

        public ViewHolder(View view) {
            this.menuView = (TextView)view.findViewById(R.id.list_item_sis_textview);
        }
    }
    */
}
