package com.muh_sam.studentinformationsystem.Datesheet;

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
 * Created by muh_s_000 on 16/03/2015.
 */
public class DatesheetFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = DatesheetFragment.class.getSimpleName();
    DatesheetAdapter mDatesheetAdapter;

    private String SELECTED_KEY = "position";
    private static final int DATESHEET_LOADER = 0;
    private ListView mListView;

    public String[] DatesheetColumns = new String[]{
            SISContract.SisDatesheet._ID,
            SISContract.SisDatesheet.COLUMN_SUBJECT_TEXT,
            SISContract.SisDatesheet.COLUMN_DATE_TEXT,
            SISContract.SisDatesheet.COLUMN_TIME_TEXT
    };
    public static final int COL_DATESHEET_ID = 0;
    public static final int COL_DATESHEET_SUBJECT = 1;
    public static final int COL_DATESHEET_DATE = 2;
    public static final int COL_DATESHEET_TIME = 3;

    private static int mPosition;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(DATESHEET_LOADER, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPosition != ListView.INVALID_POSITION)
        {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    public DatesheetFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datesheet, container, false);
        mListView = (ListView)rootView.findViewById(R.id.listview_datesheet);

        mDatesheetAdapter = new DatesheetAdapter(
                getActivity(),
                null,
                0
        );
        mListView.setAdapter(mDatesheetAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mDatesheetAdapter.getCursor();
                if(null != cursor && cursor.moveToPosition(position))
                {
                    ((CallBack) getActivity()).onItemSelected(cursor.getInt(COL_DATESHEET_ID));
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
                SISContract.SisDatesheet.CONTENT_URI,
                DatesheetColumns,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mDatesheetAdapter.swapCursor(cursor);
        if(mPosition != ListView.INVALID_POSITION)
        {
            mListView.setSelection(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mDatesheetAdapter.swapCursor(null);
    }

    public interface CallBack {
        public void onItemSelected(int datesheetId);
    }
}
