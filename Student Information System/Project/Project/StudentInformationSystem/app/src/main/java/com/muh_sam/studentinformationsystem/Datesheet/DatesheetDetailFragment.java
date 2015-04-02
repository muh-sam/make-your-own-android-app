package com.muh_sam.studentinformationsystem.Datesheet;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muh_sam.studentinformationsystem.R;
import com.muh_sam.studentinformationsystem.data.SISContract;

/**
 * Created by muh_s_000 on 17/03/2015.
 */
public class DatesheetDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = DatesheetDetailFragment.class.getSimpleName();
    private static final int DETAIL_LOADER = 0;
    private String[] DatesheetDetailColumns = new String[]{
            SISContract.SisDatesheet.TABLE_NAME + "." + SISContract.SisTimetable._ID,
            SISContract.SisDatesheet.COLUMN_SUBJECT_TEXT,
            SISContract.SisDatesheet.COLUMN_DATE_TEXT,
            SISContract.SisDatesheet.COLUMN_TIME_TEXT
    };
    private static final int COL_DATESHEET_ID = 0;
    private static final int COL_DATESHEET_SUBJECT = 1;
    private static final int COL_DATESHEET_DATE = 2;
    private static final int COL_DATESHEET_TIME = 3;

    private TextView subjectTextView;
    private TextView dateTextView;
    private TextView timeTextView;

    public DatesheetDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datesheet_detail, container, false);

        subjectTextView = (TextView)rootView.findViewById(R.id.detail_subject_textview);
        dateTextView = (TextView)rootView.findViewById(R.id.detail_date_textview);
        timeTextView = (TextView)rootView.findViewById(R.id.detail_time_textview);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(DatesheetDetailActivity.DATESHEETKEY)){
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int datesheetId = getActivity().getIntent().getIntExtra(DatesheetDetailActivity.DATESHEETKEY, 0);
        Uri buildUri = SISContract.SisDatesheet.buildDatesheetUri(datesheetId);
        return new CursorLoader(
                getActivity(),
                buildUri,
                DatesheetDetailColumns,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor.moveToFirst()){
            String subject = cursor.getString(COL_DATESHEET_SUBJECT);
            String day = cursor.getString(COL_DATESHEET_DATE);
            String time = cursor.getString(COL_DATESHEET_TIME);
            subjectTextView.setText(subject);
            dateTextView.setText(day);
            timeTextView.setText(time);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
