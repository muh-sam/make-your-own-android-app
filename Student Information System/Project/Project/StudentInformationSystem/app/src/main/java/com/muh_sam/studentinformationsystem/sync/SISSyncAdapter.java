package com.muh_sam.studentinformationsystem.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.muh_sam.studentinformationsystem.R;
import com.muh_sam.studentinformationsystem.data.JSONParser;
import com.muh_sam.studentinformationsystem.data.SISContract;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muh_s_000 on 23/03/2015.
 */
public class SISSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String LOG_TAG = SISSyncAdapter.class.getSimpleName();
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

    private static final String[] USER_COLUMN = new String[] {
            SISContract.SisUser._ID,
            SISContract.SisUser.COLUMN_CLASS_TEXT,
            SISContract.SisUser.COLUMN_SESSION_TEXT,
            SISContract.SisUser.COLUMN_ROLLNO_TEXT
    };

    private static final String[] SemesterColumn = new String[]{
        SISContract.SisSemester._ID,
        SISContract.SisSemester.COLUMN_NAME_TEXT
    };
    private static final int INDEX_ID = 0;
    private static final int INDEX_NAME = 1;



    // these indices must match the projection
    private static final int INDEX_USER_ID = 0;
    private static final int INDEX_CLASS_TEXT = 1;
    private static final int INDEX_SESSION_TEXT = 2;
    private static final int INDEX_ROLLNO_TEXT = 3;


    public SISSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting sync");
        JSONObject json;
        String KEY_SUCCESS = "success";
        String webURL="http://bzu.comze.com/api_access.php";
        String[] sisSemester;
        String classs = null;
        String session = null;
        try {
            JSONParser jsonParser = new JSONParser(this.getContext());
            Cursor cursor = this.getContext().getContentResolver().query(SISContract.SisUser.CONTENT_URI, USER_COLUMN, null, null, null);
            if (cursor.moveToFirst()) {
                classs = cursor.getString(INDEX_CLASS_TEXT);
                session = cursor.getString(INDEX_SESSION_TEXT);
            }

            addSemester(classs);

            Cursor cursor1 = this.getContext().getContentResolver().query(SISContract.SisSemester.CONTENT_URI, SemesterColumn, null, null, null);



            long semesterId;
            if(cursor1.moveToFirst())
            {
                do {


                    Uri timetableUri = SISContract.SisTimetable.buildTimetableWithSemesterUri(String.valueOf(cursor1.getInt(INDEX_ID)));

                    Cursor cursorTimeTable = this.getContext().getContentResolver().query(timetableUri, new String[]{SISContract.SisTimetable._ID}, SISContract.SisTimetable.COLUMN_SEMESTER_KEY, new String[]{
                            String.valueOf(cursor1.getInt(INDEX_ID))},null
                    );

                    List<NameValuePair> paramsTimetable = new ArrayList<NameValuePair>();
                    paramsTimetable.add(new BasicNameValuePair("tag", "timetable"));
                    paramsTimetable.add(new BasicNameValuePair("classs", classs));
                    paramsTimetable.add(new BasicNameValuePair("session", session));
                    paramsTimetable.add(new BasicNameValuePair("semester", cursor1.getString(INDEX_NAME)));
                    // Getting JSONObject
                    json = jsonParser.getJSONFromUrl(webURL, paramsTimetable);

                    if (json.getString(KEY_SUCCESS) != null) {
                        String resTime = json.getString(KEY_SUCCESS);
                        if (Integer.parseInt(resTime) == 1) {
                            JSONObject json_Time = json.getJSONObject("user");

                            for (int j = 1; j < 7; j++) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(SISContract.SisTimetable.COLUMN_SUBJECT_TEXT, json_Time.getString("sub_name" + j));
                                contentValues.put(SISContract.SisTimetable.COLUMN_TEACHER_TEXT, json_Time.getString("teacher" + j));
                                contentValues.put(SISContract.SisTimetable.COLUMN_DAY_TEXT, json_Time.getString("day" + j));
                                contentValues.put(SISContract.SisTimetable.COLUMN_TIME_TEXT, json_Time.getString("time" + j));
                                contentValues.put(SISContract.SisTimetable.COLUMN_SEMESTER_KEY, cursor1.getString(INDEX_ID));
                                this.getContext().getContentResolver().insert(SISContract.SisTimetable.CONTENT_URI, contentValues);
                            }
                        }
                    }

                    List<NameValuePair> paramsDatesheet = new ArrayList<NameValuePair>();
                    paramsDatesheet.add(new BasicNameValuePair("tag", "datesheet"));
                    paramsDatesheet.add(new BasicNameValuePair("classs", classs));
                    paramsDatesheet.add(new BasicNameValuePair("session", session));
                    paramsDatesheet.add(new BasicNameValuePair("semester", cursor1.getString(INDEX_NAME)));
                    // Getting JSONObject
                    json = jsonParser.getJSONFromUrl(webURL, paramsDatesheet);

                    if (json.getString(KEY_SUCCESS) != null) {
                        String resDatesheet = json.getString(KEY_SUCCESS);
                        if (Integer.parseInt(resDatesheet) == 1) {
                            JSONObject json_datesheet = json.getJSONObject("user");
                            for (int j = 1; j < 7; j++) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(SISContract.SisDatesheet.COLUMN_SUBJECT_TEXT, json_datesheet.getString("sub_name" + j));
                                contentValues.put(SISContract.SisDatesheet.COLUMN_DATE_TEXT, json_datesheet.getString("date" + j));
                                contentValues.put(SISContract.SisDatesheet.COLUMN_TIME_TEXT, json_datesheet.getString("time" + j));
                                contentValues.put(SISContract.SisDatesheet.COLUMN_SEMESTER_KEY, cursor1.getString(INDEX_ID));
                                this.getContext().getContentResolver().insert(SISContract.SisDatesheet.CONTENT_URI, contentValues);
                            }
                        }
                    }
                    Log.d(LOG_TAG, "Fetch SIS Task Completed. ");
                }
                while (cursor1.moveToNext());
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void addSemester(String classs) {

        String[] sisSemester;
        if (classs.toUpperCase().contains("MCS")) {
            sisSemester = new String[]{
                    "1st",
                    "2nd",
                    "3rd",
                    "4th"
            };
        } else {
            sisSemester = new String[]{
                    "1st",
                    "2nd",
                    "3rd",
                    "4th",
                    "5th",
                    "6th",
                    "7th",
                    "8th"
            };
        }
        for(int i=0; i<sisSemester.length; i++) {
            ContentValues semesterValues = new ContentValues();
            semesterValues.put(SISContract.SisSemester.COLUMN_NAME_TEXT, sisSemester[i]);
            this.getContext().getContentResolver().insert(SISContract.SisSemester.CONTENT_URI, semesterValues);
        }
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }


    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }


    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        SISSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}
