package com.muh_sam.studentinformationsystem.timetable;

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
 * Created by muh_s_000 on 15/03/2015.
 */
public class TimetableDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = TimetableDetailFragment.class.getSimpleName();
    private static final int DETAIL_LOADER = 0;
    private String[] TimetableDetailColumns = new String[]{
            SISContract.SisTimetable.TABLE_NAME + "." + SISContract.SisTimetable._ID,
            SISContract.SisTimetable.COLUMN_SEMESTER_KEY,
            SISContract.SisTimetable.COLUMN_SUBJECT_TEXT,
            SISContract.SisTimetable.COLUMN_TEACHER_TEXT,
            SISContract.SisTimetable.COLUMN_DAY_TEXT,
            SISContract.SisTimetable.COLUMN_TIME_TEXT
    };
    private static final int COL_TIMETABLE_ID = 0;
    private static final int COL_TIMETABLE_SEMESTER = 1;
    private static final int COL_TIMETABLE_SUBJECT = 2;
    private static final int COL_TIMETABLE_TEACHER = 3;
    private static final int COL_TIMETABLE_DAY = 4;
    private static final int COL_TIMETABLE_TIME = 5;

    private TextView subjectTextView;
    private TextView teacherTextView;
    private TextView dayTextView;
    private TextView timeTextView;

    public TimetableDetailFragment() {
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(TimetableDetailActivity.TIMETABLE_KEY)){
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable_detail, container, false);
        subjectTextView = (TextView)rootView.findViewById(R.id.detail_subject_textview);
        teacherTextView = (TextView)rootView.findViewById(R.id.detail_teacher_textview);
        dayTextView = (TextView)rootView.findViewById(R.id.detail_day_textview);
        timeTextView = (TextView)rootView.findViewById(R.id.detail_time_textview);
        return rootView;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        int timetableId = getActivity().getIntent().getIntExtra(TimetableDetailActivity.TIMETABLE_KEY, 0);
        Uri buildUri = SISContract.SisTimetable.buildTimetableUri(timetableId);
        return new CursorLoader(
                getActivity(),
                buildUri,
                TimetableDetailColumns,
                null,
                null,
                null
        );
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor.moveToFirst()){
            String subject = cursor.getString(COL_TIMETABLE_SUBJECT);
            String teacher = cursor.getString(COL_TIMETABLE_TEACHER);
            String day = cursor.getString(COL_TIMETABLE_DAY);
            String time = cursor.getString(COL_TIMETABLE_TIME);
            subjectTextView.setText(subject);
            teacherTextView.setText(teacher);
            dayTextView.setText(day);
            timeTextView.setText(time);
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> cursor) {
    }
}