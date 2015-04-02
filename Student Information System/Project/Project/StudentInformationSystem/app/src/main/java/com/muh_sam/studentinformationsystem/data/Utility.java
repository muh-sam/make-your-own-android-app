package com.muh_sam.studentinformationsystem.data;

import android.content.Context;

/**
 * Created by muh_s_000 on 13/03/2015.
 */
public class Utility {
    public static void Logout(Context context) {
        context.getContentResolver().delete(SISContract.SisTimetable.CONTENT_URI, null, null);
        context.getContentResolver().delete(SISContract.SisDatesheet.CONTENT_URI, null, null);
        context.getContentResolver().delete(SISContract.SisSemester.CONTENT_URI, null, null);
        context.getContentResolver().delete(SISContract.SisMenu.CONTENT_URI, null, null);
        context.getContentResolver().delete(SISContract.SisUser.CONTENT_URI, null, null);
    }
}




