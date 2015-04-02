package com.muh_sam.studentinformationsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.muh_sam.studentinformationsystem.Security.LoginActivity;
import com.muh_sam.studentinformationsystem.data.SISContract;
import com.muh_sam.studentinformationsystem.data.Utility;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isUser = checkUser();
        if(isUser)
        {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new StudentInformationSystemFragment())
                        .commit();
            }
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        //SISSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   ContentResolver.requestSync(Account account, String authority, Bundle extras)
      //  SISSyncAdapter.initializeSyncAdapter(this);
    }

    private boolean checkUser() {
        Cursor cursor = this.getContentResolver().query(
                SISContract.SisUser.CONTENT_URI,
                new String[]{SISContract.SisUser._ID},
                null,
                null,
                null
        );
        if(cursor.moveToFirst()){
            return true;
        }else
        {

            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Utility.Logout(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}