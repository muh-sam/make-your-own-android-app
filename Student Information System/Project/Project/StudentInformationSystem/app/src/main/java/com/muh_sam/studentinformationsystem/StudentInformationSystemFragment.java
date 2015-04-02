package com.muh_sam.studentinformationsystem;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.muh_sam.studentinformationsystem.Datesheet.DatesheetActivity;
import com.muh_sam.studentinformationsystem.Security.MenuAdapter;
import com.muh_sam.studentinformationsystem.data.SISContract;
import com.muh_sam.studentinformationsystem.timetable.Timetable;

/**
 * Created by muh_s_000 on 12/03/2015.
 */
public class StudentInformationSystemFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = StudentInformationSystemFragment.class.getSimpleName();
    private static final int MENU_LOADER = 0;
    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(MENU_LOADER, null, this);
    }



    private static final String[] MENU_COLUMNS = {
            SISContract.SisMenu.TABLE_NAME + "." + SISContract.SisMenu._ID,
            SISContract.SisMenu.COLUMN_NAME_TEXT
    };
    public static final int COL_MENU_ID = 0;
    public static final int COL_MENU_TEXT = 1;
    //static SimpleCursorAdapter  mSisAdapter;
    static MenuAdapter mSisAdapter;
    public StudentInformationSystemFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MENU_LOADER, null, this);
    }

    @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
       /* mSisAdapter = new SimpleCursorAdapter(
            getActivity(),
            R.layout.list_item_sis,
            null,
            new String[]{
                SISContract.SisMenu.COLUMN_NAME_TEXT
            },
            new int[]{
                R.id.list_item_sis_textview
            },
            0
        );


        mSisAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
                @Override
                public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            final TextView menuText = (TextView)view.findViewById(R.id.list_item_sis_textview);
            switch(columnIndex)
            {
                case COL_MENU_TEXT:
                menuText.setText(cursor.getString(COL_MENU_TEXT));
                return true;
            }
            return false;
        }
        });
        */

        mSisAdapter = new MenuAdapter(getActivity(), null, 0);
        ListView listView = (ListView)rootView.findViewById(R.id.listview_sis);
        listView.setAdapter(mSisAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       //         android.support.v4.widget.SimpleCursorAdapter adapter = (android.support.v4.widget.SimpleCursorAdapter)parent.getAdapter();
                Cursor cursor = mSisAdapter.getCursor();
                if(null != cursor && cursor.moveToPosition(position)) {
                     if(cursor.getString(1).equals("Timetable")){
                         Intent intent = new Intent(getActivity(), Timetable.class);
                         startActivity(intent);
                     }
                     else if(cursor.getString(1).equals("Date sheet")){
                        Intent intent = new Intent(getActivity(), DatesheetActivity.class);
                        startActivity(intent);
                    }
                }
            }
            });
        return rootView;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri menuUri = SISContract.SisMenu.CONTENT_URI;
        Log.d(LOG_TAG, "Uri: " + menuUri.toString());
        return new CursorLoader(
                getActivity(),
                menuUri,
                MENU_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mSisAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mSisAdapter.swapCursor(null);
    }
}
