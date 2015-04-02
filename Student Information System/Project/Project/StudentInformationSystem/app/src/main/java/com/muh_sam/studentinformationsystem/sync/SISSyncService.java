package com.muh_sam.studentinformationsystem.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by muh_s_000 on 23/03/2015.
 */
public class SISSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static SISSyncAdapter sSISSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("StudentInfo", "onCreate - Student Information System");
        synchronized (sSyncAdapterLock) {
            if (sSISSyncAdapter == null) {
                sSISSyncAdapter = new SISSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSISSyncAdapter.getSyncAdapterBinder();
    }
}
