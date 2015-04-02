package com.muh_sam.studentinformationsystem.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by muh_s_000 on 23/03/2015.
 */
public class SISAuthenticatorService extends Service {

    private SISAuthenticator mSISAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mSISAuthenticator = new SISAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mSISAuthenticator.getIBinder();
    }
}
