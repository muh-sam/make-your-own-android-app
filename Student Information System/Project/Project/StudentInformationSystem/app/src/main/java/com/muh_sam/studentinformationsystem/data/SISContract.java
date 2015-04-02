package com.muh_sam.studentinformationsystem.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by muh_s_000 on 12/03/2015.
 */
public class SISContract {


    public static final String CONTENT_AUTHORITY = "com.muh_sam.studentinformationsystem";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MENU = "menus";
    public static final String PATH_USER = "user";
    public static final String PATH_SEMESTER = "semester";
    public static final String PATH_TIMETABLE = "timetable";
    public static final String PATH_DATESHEET = "datesheet";
    public static final class SisTimetable implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TIMETABLE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TIMETABLE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TIMETABLE;

        public static Uri buildTimetableUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI, _id);
        }

        public static Uri buildTimetableWithSemesterUri(String _id){
            return CONTENT_URI.buildUpon().appendPath(_id).build();
        }

        public static final String TABLE_NAME = "timetable";
        public static final String COLUMN_SUBJECT_TEXT = "subject";
        public static final String COLUMN_TEACHER_TEXT = "teacher";
        public static final String COLUMN_DAY_TEXT = "day";
        public static final String COLUMN_TIME_TEXT = "time";
        public static final String COLUMN_SEMESTER_KEY = "semester_id";

        public static String getSemesterFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class SisDatesheet implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DATESHEET).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATESHEET;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATESHEET;
        public static Uri buildDatesheetUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI, _id);
        }

        public static final String TABLE_NAME = "datesheet";
        public static final String COLUMN_SUBJECT_TEXT = "subject";
        public static final String COLUMN_DATE_TEXT = "date";
        public static final String COLUMN_TIME_TEXT = "time";
        public static final String COLUMN_SEMESTER_KEY = "semester_id";


        public static String getSemesterFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }



    }



    public static final class SisMenu implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MENU).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MENU;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MENU;

        public static Uri buildMenuUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI, _id);
        }

        public static final String TABLE_NAME = "menus";
        public static final String COLUMN_NAME_TEXT = "name";
    }
    public static final class SisSemester implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEMESTER).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEMESTER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEMESTER;

        public static Uri buildSemesterUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI, _id);
        }

        public static final String TABLE_NAME = "semester";
        public static final String COLUMN_NAME_TEXT = "name";
    }

    public static final class SisUser implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static Uri buildSisUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI, _id);
        }
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_EMAIL_TEXT = "email";
        public static final String COLUMN_NAME_TEXT = "name";
        public static final String COLUMN_CLASS_TEXT = "class";
        public static final String COLUMN_ROLLNO_TEXT = "rollno";
        public static final String COLUMN_SESSION_TEXT = "session";
    }







    private interface BaseColumns {
        public static final String _ID = "_id";
        public static final String _COUNT = "_count";
    }
}
