package com.muh_sam.studentinformationsystem.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by muh_s_000 on 12/03/2015.
 */
public class SISDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bzu.db";

    public SISDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MENU_TABLE =
                "CREATE TABLE "+ SISContract.SisMenu.TABLE_NAME + " (" +
                SISContract.SisMenu._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SISContract.SisMenu.COLUMN_NAME_TEXT + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_SEMESTER_TABLE =
                "CREATE TABLE "+ SISContract.SisSemester.TABLE_NAME + " (" +
                        SISContract.SisSemester._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SISContract.SisSemester.COLUMN_NAME_TEXT + " TEXT NOT NULL " +
                        " );";


        final String SQL_CREATE_USER_TABLE =
                "CREATE TABLE "+ SISContract.SisUser.TABLE_NAME + " (" +
                        SISContract.SisUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SISContract.SisUser.COLUMN_NAME_TEXT + " TEXT NOT NULL, " +
                        SISContract.SisUser.COLUMN_EMAIL_TEXT + " TEXT NOT NULL, " +
                        SISContract.SisUser.COLUMN_CLASS_TEXT + " TEXT NOT NULL, " +
                        SISContract.SisUser.COLUMN_SESSION_TEXT + " TEXT NOT NULL, " +
                        SISContract.SisUser.COLUMN_ROLLNO_TEXT + " TEXT NOT NULL " +
                        " );";

        final String SQL_CREATE_TIMETABLE_TABLE =
                "CREATE TABLE "+ SISContract.SisTimetable.TABLE_NAME + " (" +
                        SISContract.SisTimetable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SISContract.SisTimetable.COLUMN_SEMESTER_KEY + " INTEGER NOT NULL, " +
                        SISContract.SisTimetable.COLUMN_SUBJECT_TEXT + " TEXT NULL, " +
                        SISContract.SisTimetable.COLUMN_TEACHER_TEXT + " TEXT NULL, " +
                        SISContract.SisTimetable.COLUMN_DAY_TEXT + " TEXT NULL, " +
                        SISContract.SisTimetable.COLUMN_TIME_TEXT + " TEXT NULL " +
                        " );";
        final String SQL_CREATE_DATESHEET_TABLE =
                "CREATE TABLE "+ SISContract.SisDatesheet.TABLE_NAME + " (" +
                        SISContract.SisDatesheet._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SISContract.SisDatesheet.COLUMN_SEMESTER_KEY + " INTEGER NOT NULL, " +
                        SISContract.SisDatesheet.COLUMN_SUBJECT_TEXT + " TEXT NULL, " +
                        SISContract.SisDatesheet.COLUMN_DATE_TEXT + " TEXT NULL, " +
                        SISContract.SisDatesheet.COLUMN_TIME_TEXT + " TEXT NULL " +
                        " );";

        db.execSQL(SQL_CREATE_MENU_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_SEMESTER_TABLE);
        db.execSQL(SQL_CREATE_TIMETABLE_TABLE);
        db.execSQL(SQL_CREATE_DATESHEET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SISContract.SisMenu.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SISContract.SisUser.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SISContract.SisSemester.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SISContract.SisTimetable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SISContract.SisDatesheet.TABLE_NAME);
        onCreate(db);
    }

    public void resetTables()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.execSQL("delete from "+ SISContract.SisDatesheet.TABLE_NAME);
        db.execSQL("delete from "+ SISContract.SisTimetable.TABLE_NAME);
        db.execSQL("delete from "+ SISContract.SisUser.TABLE_NAME);
        db.execSQL("delete from "+ SISContract.SisSemester.TABLE_NAME);
        db.execSQL("delete from "+ SISContract.SisMenu.TABLE_NAME);
        db.close();
    }
}
