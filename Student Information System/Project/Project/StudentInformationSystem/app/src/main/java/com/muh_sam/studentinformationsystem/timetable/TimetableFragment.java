package com.muh_sam.studentinformationsystem.timetable;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.muh_sam.studentinformationsystem.R;
import com.muh_sam.studentinformationsystem.data.SISContract;

/**
 * Created by muh_s_000 on 15/03/2015.
 */
public class TimetableFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    TimetableAdapter mTimetableAdapter;
    private String SELECTED_KEY = "position";
    private static final int TIMETABLE_LOADER = 0;
    private ListView mListView;
    public String[] TimetableColumns = new String[]{
            SISContract.SisTimetable._ID,
            SISContract.SisTimetable.COLUMN_SUBJECT_TEXT,
            SISContract.SisTimetable.COLUMN_TEACHER_TEXT,
            SISContract.SisTimetable.COLUMN_DAY_TEXT,
            SISContract.SisTimetable.COLUMN_TIME_TEXT
    };
    public static final int COL_TIMETABLE_ID = 0;
    public static final int COL_TIMETABLE_SUBJECT = 1;
    public static final int COL_TIMETABLE_TEACHER = 2;
    public static final int COL_TIMETABLE_DAY = 3;
    public static final int COL_TIMETABLE_TIME = 4;
    private static int mPosition;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(TIMETABLE_LOADER, null, this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public TimetableFragment() {
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPosition != ListView.INVALID_POSITION)
        {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);
        mListView = (ListView)rootView.findViewById(R.id.listview_timetable);
        mTimetableAdapter = new TimetableAdapter(
                getActivity(),
                null,
                0
        );
        mListView.setAdapter(mTimetableAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mTimetableAdapter.getCursor();
                if(null != cursor && cursor.moveToPosition(position))
                {
                    ((CallBack) getActivity()).onItemSelected(cursor.getInt(COL_TIMETABLE_ID));
                }
                mPosition = position;
            }
        });
        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY))
        {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return rootView;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                SISContract.SisTimetable.CONTENT_URI,
                TimetableColumns,
                null,
                null,
                null
        );
    }
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mTimetableAdapter.swapCursor(cursor);
        if(mPosition != ListView.INVALID_POSITION)
        {
            mListView.setSelection(mPosition);
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mTimetableAdapter.swapCursor(null);
    }
    public interface CallBack {
        public void onItemSelected(int TimetableId);
    }
}