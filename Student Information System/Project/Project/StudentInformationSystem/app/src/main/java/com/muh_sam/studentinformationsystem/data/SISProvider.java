package com.muh_sam.studentinformationsystem.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by muh_s_000 on 18/03/2015.
 */
public class SISProvider extends ContentProvider {


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SISDbHelper mOpenHelper;

    static final int USER = 100;
    static final int USER_ID = 101;

    static final int MENU = 200;
    static final int MENU_ID = 201;

    static final int SEMESTER = 300;
    static final int SEMESTER_ID = 301;

    static final int TIMETABLE = 400;
    static final int TIMETABLE_ID = 401;
    static final int TIMETABLEBYSEMESTER = 402;

    static final int DATESHEET = 500;
    static final int DATESHEET_ID = 501;


    private static final SQLiteQueryBuilder sTimetableBySemesterQueryBuilder;
    private static final SQLiteQueryBuilder sDatesheetBySemesterQueryBuilder;

    private static final String sSemesterSelection = SISContract.SisSemester.TABLE_NAME +
            "." + SISContract.SisSemester._ID + " = ? ";

    static {
        sTimetableBySemesterQueryBuilder = new SQLiteQueryBuilder();
        sTimetableBySemesterQueryBuilder.setTables(
                SISContract.SisTimetable.TABLE_NAME + " INNER JOIN " +
                        SISContract.SisSemester.TABLE_NAME + " ON " +
                        SISContract.SisSemester.TABLE_NAME +
                        "." + SISContract.SisSemester._ID +
                        " = " + SISContract.SisTimetable.TABLE_NAME +
                        "." + SISContract.SisTimetable.COLUMN_SEMESTER_KEY);
    }

    static {
        sDatesheetBySemesterQueryBuilder = new SQLiteQueryBuilder();
        sDatesheetBySemesterQueryBuilder.setTables(
                SISContract.SisDatesheet.TABLE_NAME + " INNER JOIN " +
                        SISContract.SisSemester.TABLE_NAME + " ON " +
                        SISContract.SisSemester.TABLE_NAME +
                        "." + SISContract.SisSemester._ID +
                        " = " + SISContract.SisDatesheet.TABLE_NAME +
                        "." + SISContract.SisDatesheet.COLUMN_SEMESTER_KEY);
    }




    static UriMatcher buildUriMatcher()
    {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SISContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, SISContract.PATH_USER, USER);
        matcher.addURI(authority, SISContract.PATH_USER + "/#", USER_ID);

        matcher.addURI(authority, SISContract.PATH_MENU, MENU);
        matcher.addURI(authority, SISContract.PATH_MENU + "/#", MENU_ID);

        matcher.addURI(authority, SISContract.PATH_SEMESTER, SEMESTER);
        matcher.addURI(authority, SISContract.PATH_SEMESTER + "/#", SEMESTER_ID);

        matcher.addURI(authority, SISContract.PATH_TIMETABLE, TIMETABLE);
        matcher.addURI(authority, SISContract.PATH_TIMETABLE + "/#", TIMETABLE_ID);
        matcher.addURI(authority, SISContract.PATH_TIMETABLE + "/*",   TIMETABLEBYSEMESTER);

        matcher.addURI(authority, SISContract.PATH_DATESHEET, DATESHEET);
        matcher.addURI(authority, SISContract.PATH_DATESHEET + "/#", DATESHEET_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new SISDbHelper(getContext());
        return true;
    }


    private Cursor getTimetableBySemester(Uri uri, String[] projection, String sortOrder)
    {
        String semester = SISContract.SisTimetable.getSemesterFromUri(uri);
        String[] selectionArgs;
        String selection;

        selection = sSemesterSelection;
        selectionArgs = new String[]{
                    semester
        };
        return sTimetableBySemesterQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri))
        {
            case TIMETABLEBYSEMESTER:
            {
                retCursor = null;
                retCursor = getTimetableBySemester(uri, projection, sortOrder);
                break;
            }
            case USER:
            {
                retCursor = null;
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SISContract.SisUser.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MENU:
            {
                retCursor = null;
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SISContract.SisMenu.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case SEMESTER:
            {
                retCursor = null;
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SISContract.SisSemester.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TIMETABLE:
            {
                retCursor = null;
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SISContract.SisTimetable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TIMETABLE_ID:
            {
                retCursor = null;
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SISContract.SisTimetable.TABLE_NAME,
                        projection,
                        SISContract.SisTimetable._ID + " = '" + ContentUris.parseId(uri) +"' " ,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case DATESHEET:
            {
                retCursor = null;
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SISContract.SisDatesheet.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case DATESHEET_ID:
            {
                retCursor = null;
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SISContract.SisDatesheet.TABLE_NAME,
                        projection,
                        SISContract.SisDatesheet._ID + " = '" + ContentUris.parseId(uri) +"' " ,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown : " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {

        switch (sUriMatcher.match(uri)){
            case USER:
                return SISContract.SisUser.CONTENT_TYPE;
            case MENU:
                return SISContract.SisMenu.CONTENT_TYPE;
            case SEMESTER:
                return SISContract.SisSemester.CONTENT_TYPE;
            case TIMETABLE:
                return SISContract.SisTimetable.CONTENT_TYPE;
            case TIMETABLE_ID:
                return SISContract.SisTimetable.CONTENT_ITEM_TYPE;
            case TIMETABLEBYSEMESTER:
                return SISContract.SisTimetable.CONTENT_TYPE;
            case DATESHEET:
                return SISContract.SisDatesheet.CONTENT_TYPE;
            case DATESHEET_ID:
                return SISContract.SisDatesheet.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri retUri = null;
        switch (sUriMatcher.match(uri)){
            case USER:
            {
                long _id = db.insert(SISContract.SisUser.TABLE_NAME, null, values);
                if (_id > 0)
                    retUri = SISContract.SisUser.buildSisUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            case MENU: {
                long _id = db.insert(SISContract.SisMenu.TABLE_NAME, null, values);
                if (_id > 0)
                    retUri = SISContract.SisMenu.buildMenuUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            case SEMESTER: {
                long _id = db.insert(SISContract.SisSemester.TABLE_NAME, null, values);
                if (_id > 0)
                    retUri = SISContract.SisSemester.buildSemesterUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            case TIMETABLE: {
                long _id = db.insert(SISContract.SisTimetable.TABLE_NAME, null, values);
                if (_id > 0)
                    retUri = SISContract.SisTimetable.buildTimetableUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            case DATESHEET: {
                long _id = db.insert(SISContract.SisDatesheet.TABLE_NAME, null, values);
                if (_id > 0)
                    retUri = SISContract.SisDatesheet.buildDatesheetUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;
        if(null == selection) selection = "1";
        switch (sUriMatcher.match(uri)){
            case USER:
                rowsDeleted = db.delete(SISContract.SisUser.TABLE_NAME, selection, selectionArgs);
                break;
            case MENU:
                rowsDeleted = db.delete(SISContract.SisMenu.TABLE_NAME, selection, selectionArgs);
                break;
            case SEMESTER:
                rowsDeleted = db.delete(SISContract.SisSemester.TABLE_NAME, selection, selectionArgs);
                break;
            case TIMETABLE:
                rowsDeleted = db.delete(SISContract.SisTimetable.TABLE_NAME, selection, selectionArgs);
                break;
            case DATESHEET:
                rowsDeleted = db.delete(SISContract.SisDatesheet.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        if(rowsDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (sUriMatcher.match(uri)){
            case USER:
                rowsUpdated = db.update(SISContract.SisUser.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MENU:
                rowsUpdated = db.update(SISContract.SisMenu.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SEMESTER:
                rowsUpdated = db.update(SISContract.SisSemester.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TIMETABLE:
                rowsUpdated = db.update(SISContract.SisTimetable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case DATESHEET:
                rowsUpdated = db.update(SISContract.SisDatesheet.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(rowsUpdated != 0 )
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
